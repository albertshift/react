package com.shvid.react.packedobject;

import com.shvid.react.UnsafeHolder;

public class Array<T extends PackedObject> implements PackedObject, LengthAware {

	final long offset;
	final PackedInt typeId;
	final PackedInt length;
	
	public Array(long offset) {
		this.offset = offset;
		this.typeId = new PackedInt(offset);
		this.length = new PackedInt(offset + PackedConstants.INT_SIZE);
	}

	public void format(byte[] blob, long ptr, int elementTypeId, int length) {
		this.typeId.setInt(blob, ptr, elementTypeId);
		this.length.setInt(blob, ptr, length);
		PackedObject pc = TypeRegistry.resolveType(elementTypeId);
		for (int i = 0; i != length; ++i) {
			long elementPtr = getElementPtr(ptr, i, pc);
			pc.format(blob, elementPtr);
		}
	}
	
	public void format(long address, long ptr, int elementTypeId, int length) {
		this.typeId.setInt(address, ptr, elementTypeId);
		this.length.setInt(address, ptr, length);
		PackedObject pc = TypeRegistry.resolveType(elementTypeId);
		for (int i = 0; i != length; ++i) {
			long elementPtr = getElementPtr(ptr, i, pc);
			pc.format(address, elementPtr);
		}
	}
	
	@Override
	public void format(byte[] blob, long ptr) {
		throw new IllegalAccessError("call format(blob, ptr, typeId, length)");
	}

	@Override
	public void format(long address, long ptr) {
		throw new IllegalAccessError("call format(address, ptr, typeId, length)");
	}

	@Override
	public void copyTo(byte[] blob, long ptr, byte[] des, long desPtr) {
		PackedObject pc = getType(blob, ptr);
		int length = getLength(blob, ptr);
		if (pc instanceof FixedPackedClass) {
			int copySize = getFixedSize() + pc.getFixedSize() * length;
			UnsafeHolder.UNSAFE.copyMemory(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset, des, offset + desPtr + UnsafeHolder.byteArrayBaseOffset, copySize);
		}
		else {
			int copySize = getFixedSize();
			UnsafeHolder.UNSAFE.copyMemory(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset, des, offset + desPtr + UnsafeHolder.byteArrayBaseOffset, copySize);
			for (int i = 0; i != length; ++i) {
				long elementPtr = getElementPtr(ptr, i, pc);
				long desElementPtr = getElementPtr(desPtr, i, pc);
				pc.copyTo(blob, elementPtr, des, desElementPtr);
			}
		}
	}

	@Override
	public void copyTo(long address, long ptr, long des, long desPtr) {
		PackedObject pc = getType(address, ptr);
		int length = getLength(address, ptr);
		if (pc instanceof FixedPackedClass) {
			int copySize = getFixedSize() + pc.getFixedSize() * length;
			UnsafeHolder.UNSAFE.copyMemory(address, offset + ptr + UnsafeHolder.byteArrayBaseOffset, des, offset + desPtr + UnsafeHolder.byteArrayBaseOffset, copySize);
		}
		else {
			int copySize = getFixedSize();
			UnsafeHolder.UNSAFE.copyMemory(address, offset + ptr + UnsafeHolder.byteArrayBaseOffset, des, offset + desPtr + UnsafeHolder.byteArrayBaseOffset, copySize);
			for (int i = 0; i != length; ++i) {
				long elementPtr = getElementPtr(ptr, i, pc);
				long desElementPtr = getElementPtr(desPtr, i, pc);
				pc.copyTo(address, elementPtr, des, desElementPtr);
			}
		}
	}

	public long elementAt(byte[] blob, long ptr, int index) {
		int len = getLength(blob, ptr);
		if (index < 0 || index >= len) {
			throw new IndexOutOfBoundsException();
		}
		return getElementPtr(ptr, index, getType(blob, ptr));
	}
	
	public long elementAt(long address, long ptr, int index) {
		int len = getLength(address, ptr);
		if (index < 0 || index >= len) {
			throw new IndexOutOfBoundsException();
		}
		return getElementPtr(ptr, index, getType(address, ptr));
	}
	
	private long getElementPtr(long ptr, int index, PackedObject pc) {
		int scale = pc.getFixedSize();
		return ptr + offset + getFixedSize() + index * scale;
	}
	
	public T getType(byte[] blob, long ptr) {
		return TypeRegistry.resolveType(typeId.getInt(blob, ptr));
	}

	public T getType(long address, long ptr) {
		return TypeRegistry.resolveType(typeId.getInt(address, ptr));
	}

	public void setType(byte[] blob, long ptr, PackedObject pc) {
		this.typeId.setInt(blob, ptr, pc.getTypeId());
	}
	
	public void setType(long address, long ptr, PackedObject pc) {
		this.typeId.setInt(address, ptr, pc.getTypeId());
	}

	public int getTypeId(byte[] blob, long ptr) {
		return typeId.getInt(blob, ptr);
	}

	public int getTypeId(long address, long ptr) {
		return typeId.getInt(address, ptr);
	}

	public int getLength(byte[] blob, long ptr) {
		return length.getInt(blob, ptr);
	}
	
	public int getLength(long address, long ptr) {
		return length.getInt(address, ptr);
	}

	public int getTypeId() {
		return TypeRegistry.ARRAY_ID;
	}
	
	@Override
	public int getFixedSize() {
		return PackedConstants.INT_SIZE + PackedConstants.INT_SIZE;
	}

	@Override
	public int getInitCapacity() {
		return 0;
	}
	
}
