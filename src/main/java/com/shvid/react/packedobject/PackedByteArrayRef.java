package com.shvid.react.packedobject;


public class PackedByteArrayRef implements PackedClass {

	static final PackedInt LENGTH = new PackedInt(0);
	
	final PackedPtr ref;
	final int initLength;
	
	public PackedByteArrayRef(long offset, int initLength) {
		this.ref = new PackedPtr(offset);
		this.initLength = initLength;
	}
	
	@Override
	public void format(byte[] blob, long ptr) {
		ref.setNull(blob, ptr);
		if (initLength > 0) {
			ensureLength(blob, ptr, initLength);
		}
	}

	@Override
	public void format(long address, long ptr) {
		ref.setNull(address, ptr);
		if (initLength > 0) {
			ensureLength(address, ptr, initLength);
		}
	}

	@Override
	public void copyTo(byte[] blob, long ptr, byte[] des, long desPtr) {
		long dataPtr = ref.getPtr(blob, ptr);
		if (dataPtr == PackedPtr.NULL) {
			ref.setNull(des, desPtr);
		}
		else {
			int length = LENGTH.getInt(blob, dataPtr);
			int dataLength = LENGTH.getFixedSize() + length; 
			long desDataPtr = PackedObjectMemory.copyMemory(blob, dataPtr, dataLength, des);
			ref.setPtr(des, desPtr, desDataPtr);
		}
	}

	@Override
	public void copyTo(long address, long ptr, long des, long desPtr) {
		long dataPtr = ref.getPtr(address, ptr);
		if (dataPtr == PackedPtr.NULL) {
			ref.setNull(des, desPtr);
		}
		else {
			int length = LENGTH.getInt(address, dataPtr);
			int dataLength = LENGTH.getFixedSize() + length; 
			long desDataPtr = PackedObjectMemory.copyMemory(address, dataPtr, dataLength, des);
			ref.setPtr(des, desPtr, desDataPtr);
		}
	}

	public int getLength(byte[] blob, long ptr) {
		long dataPtr = ref.getPtr(blob, ptr);
		if (dataPtr == PackedPtr.NULL) {
			throw new NullPointerException();
		}
		return LENGTH.getInt(blob, dataPtr);
	}
	
	public int getLength(long address, long ptr) {
		long dataPtr = ref.getPtr(address, ptr);
		if (dataPtr == PackedPtr.NULL) {
			throw new NullPointerException();
		}
		return LENGTH.getInt(address, dataPtr);
	}
	
	public void ensureLength(byte[] blob, long ptr, int length) {
		int currentLength = -1;
		long dataPtr = ref.getPtr(blob, ptr);
		if (dataPtr != PackedPtr.NULL) {
			currentLength = LENGTH.getInt(blob, dataPtr);
			if (currentLength >= length) {
				return;
			}
		}
		dataPtr = PackedObjectMemory.newMemory(blob, length + LENGTH.getFixedSize());
		LENGTH.setInt(blob, dataPtr, length);
		ref.setPtr(blob, ptr, dataPtr);
		if (currentLength != -1) {
			PackedObjectMemory.incrementTrash(blob, currentLength + LENGTH.getFixedSize());
		}
	}
	
	public void ensureLength(long address, long ptr, int length) {
		int currentLength = -1;
		long dataPtr = ref.getPtr(address, ptr);
		if (dataPtr != PackedPtr.NULL) {
			currentLength = LENGTH.getInt(address, dataPtr);
			if (currentLength >= length) {
				return;
			}
		}
		dataPtr = PackedObjectMemory.newMemory(address, length + LENGTH.getFixedSize());
		LENGTH.setInt(address, dataPtr, length);
		ref.setPtr(address, ptr, dataPtr);
		if (currentLength != -1) {
			PackedObjectMemory.incrementTrash(address, currentLength + LENGTH.getFixedSize());
		}
	}
	
	@Override
	public int getFixedSize() {
		return ref.getFixedSize();
	}

	@Override
	public int getInitCapacity() {
		return initLength + LENGTH.getFixedSize();
	}
	
}
