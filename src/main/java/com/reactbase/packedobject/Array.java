package com.reactbase.packedobject;

/**
 * 
 * @author Alex Shvid
 *
 * @param <T>
 */

public class Array<T extends PackedObject> extends PackedObject {

	final PackedInt elementTypeId;
	final PackedInt length;
	
	public Array(long offset) {
		super(offset);
		this.elementTypeId = new PackedInt(offset);
		this.length = new PackedInt(offset + PrimitiveTypes.INT_SIZEOF);
	}

	public void format(Object address, long ptr, int elementTypeId, int length) {
		this.elementTypeId.setInt(address, ptr, elementTypeId);
		this.length.setInt(address, ptr, length);
		PackedObject po = TypeRegistry.resolveType(elementTypeId);
		for (int i = 0; i != length; ++i) {
			long elementPtr = calculateElementPtr(ptr, i, po);
			po.format(address, elementPtr);
		}
	}
	
	@Override
	public void format(Object address, long ptr) {
		throw new IllegalAccessError("call format(blob, ptr, typeId, length)");
	}

	@Override
	public void copyTo(Object address, long ptr, Object des, long desPtr) {
		PackedObject po = getElementType(address, ptr);
		int length = getLength(address, ptr);
		if (po instanceof Array) {
			PackedObjectMemory.copyTo(address, ptr + offset, des, desPtr + offset, sizeOf());
			for (int i = 0; i != length; ++i) {
				long elementPtr = calculateElementPtr(ptr, i, po);
				long desElementPtr = calculateElementPtr(desPtr, i, po);
				po.copyTo(address, elementPtr, des, desElementPtr);
			}
		}
		else {
			int arraySize = sizeOf() + po.sizeOf() * length;
			PackedObjectMemory.copyTo(address, ptr + offset, des, desPtr + offset, arraySize);
		}
	}

	public long elementAt(Object address, long ptr, int index) {
		int len = getLength(address, ptr);
		if (index < 0 || index >= len) {
			throw new IndexOutOfBoundsException();
		}
		return calculateElementPtr(ptr, index, getElementType(address, ptr));
	}
	
	private long calculateElementPtr(long ptr, int index, PackedObject po) {
		int scale = po.sizeOf();
		return ptr + offset + sizeOf() + index * scale;
	}
	
	public T getElementType(Object address, long ptr) {
		return TypeRegistry.resolveType(elementTypeId.getInt(address, ptr));
	}

	public void setElementType(Object address, long ptr, PackedObject po) {
		this.elementTypeId.setInt(address, ptr, po.getTypeId());
	}

	public int getElementTypeId(Object address, long ptr) {
		return elementTypeId.getInt(address, ptr);
	}
	
	public int getLength(Object address, long ptr) {
		return length.getInt(address, ptr);
	}

	@Override
	public int getTypeId() {
		return TypeRegistry.ARRAY_ID;
	}
	
	@Override
	public int sizeOf() {
		return PrimitiveTypes.INT_SIZEOF + PrimitiveTypes.INT_SIZEOF;
	}
	
}
