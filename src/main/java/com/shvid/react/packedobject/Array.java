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

	public void format(Object address, long ptr, int elementTypeId, int length) {
		this.typeId.setInt(address, ptr, elementTypeId);
		this.length.setInt(address, ptr, length);
		PackedObject pc = TypeRegistry.resolveType(elementTypeId);
		for (int i = 0; i != length; ++i) {
			long elementPtr = calculateElementPtr(ptr, i, pc);
			pc.format(address, elementPtr);
		}
	}
	
	@Override
	public void format(Object address, long ptr) {
		throw new IllegalAccessError("call format(blob, ptr, typeId, length)");
	}

	@Override
	public void copyTo(Object address, long ptr, Object des, long desPtr) {
		PackedObject po = getType(address, ptr);
		int length = getLength(address, ptr);
		if (po instanceof FixedPackedClass) {
			int arraySize = getFixedSize() + po.getFixedSize() * length;
			PackedObjectMemory.copyTo(address, ptr, des, desPtr, arraySize);
		}
		else {
			PackedObjectMemory.copyTo(address, ptr, des, desPtr, getFixedSize());
			for (int i = 0; i != length; ++i) {
				long elementPtr = calculateElementPtr(ptr, i, po);
				long desElementPtr = calculateElementPtr(desPtr, i, po);
				po.copyTo(address, elementPtr, des, desElementPtr);
			}
		}
	}

	public long elementAt(Object address, long ptr, int index) {
		int len = getLength(address, ptr);
		if (index < 0 || index >= len) {
			throw new IndexOutOfBoundsException();
		}
		return calculateElementPtr(ptr, index, getType(address, ptr));
	}
	
	private long calculateElementPtr(long ptr, int index, PackedObject po) {
		int scale = po.getFixedSize();
		return ptr + offset + getFixedSize() + index * scale;
	}
	
	public T getType(Object address, long ptr) {
		return TypeRegistry.resolveType(typeId.getInt(address, ptr));
	}

	public void setType(Object address, long ptr, PackedObject po) {
		this.typeId.setInt(address, ptr, po.getTypeId());
	}

	public int getTypeId(Object address, long ptr) {
		return typeId.getInt(address, ptr);
	}
	
	public int getLength(Object address, long ptr) {
		return length.getInt(address, ptr);
	}

	public int getTypeId() {
		return TypeRegistry.ARRAY_ID;
	}
	
	@Override
	public int getFixedSize() {
		return PackedConstants.INT_SIZE + PackedConstants.INT_SIZE;
	}
	
}
