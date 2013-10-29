package com.reactbase.packedobject;

/**
 * 
 * @author Alex Shvid
 *
 * @param <T>
 */

public class Array<T extends PackedObject> extends PackedObject {

	final PackedInt typeId;
	final PackedInt length;
	
	public Array(long offset) {
		super(offset);
		this.typeId = new PackedInt(offset);
		this.length = new PackedInt(offset + PrimitiveTypes.INT_SIZEOF);
	}

	public void format(Object address, long ptr, int elementTypeId, int length) {
		this.typeId.setInt(address, ptr, elementTypeId);
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
		PackedObject po = getType(address, ptr);
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
		return calculateElementPtr(ptr, index, getType(address, ptr));
	}
	
	private long calculateElementPtr(long ptr, int index, PackedObject po) {
		int scale = po.sizeOf();
		return ptr + offset + sizeOf() + index * scale;
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
	public int sizeOf() {
		return PrimitiveTypes.INT_SIZEOF + PrimitiveTypes.INT_SIZEOF;
	}
	
}
