package com.shvid.react.packedobject;

import com.shvid.react.RC;

public class Ref<T extends PackedObject> implements PackedObject {

	public final static int NULL = 0;

	final static PackedInt TYPEID = new PackedInt(0);
	
	final long offset;

	final PackedInt PTR_32;
	final PackedLong PTR_64;
	
	public Ref(long offset) {
		this.offset = offset;
		this.PTR_32 = new PackedInt(offset);
		this.PTR_64 = new PackedLong(offset);
	}
	
	@Override
	public void format(Object address, long ptr) {
		setNull(address, ptr);
	}
	
	public boolean isNull(Object address, long ptr) {
		long dataPtr = getDataPtr(address, ptr);
		return dataPtr == NULL;
	}
	
	public void setNull(Object address, long ptr) {
		setDataPtr(address, ptr, NULL);
	}
	
	public T getType(Object address, long ptr) {
		long dataPtr = getDataPtr(address, ptr);
		if (dataPtr == NULL) {
			return null;
		}
		return TypeRegistry.resolveType(TYPEID.getInt(address, dataPtr));
	}
	
	public long newInstance(Object address, long ptr, int typeId) {
		PackedObject po = TypeRegistry.resolveType(typeId);
		
		long oldDataPtr = getDataPtr(address, ptr);
		long dataPtr = PackedObjectMemory.newMemory(address, po.getFixedSize() + TYPEID.getFixedSize());
		
		setDataPtr(address, ptr, dataPtr);
		TYPEID.setInt(address, dataPtr, po.getTypeId());
		
		long pcPtr = dataPtr + TYPEID.getFixedSize();
		po.format(address, pcPtr);
		
		eraseInstance(address, oldDataPtr);
		return pcPtr;
	}
	
	public long newArrayInstance(Object address, long ptr, int elementTypeId, int length) {
		PackedObject elementPO = TypeRegistry.resolveType(elementTypeId);
		Array po = (Array) TypeRegistry.resolveType(TypeRegistry.ARRAY_ID);
		
		long oldDataPtr = getDataPtr(address, ptr);
		long dataPtr = PackedObjectMemory.newMemory(address, po.getFixedSize() + TYPEID.getFixedSize() + elementPO.getFixedSize() * length);
		
		setDataPtr(address, ptr, dataPtr);
		TYPEID.setInt(address, dataPtr, po.getTypeId());
		
		long pcPtr = dataPtr + TYPEID.getFixedSize();
		po.format(address, pcPtr, elementTypeId, length);
		
		eraseInstance(address, oldDataPtr);
		return pcPtr;
	}
	
	private void eraseInstance(Object address, long dataPtr) {
		if (dataPtr != NULL) {
			PackedObject previousClass = TypeRegistry.resolveType(TYPEID.getInt(address, dataPtr));
			PackedObjectMemory.incrementTrash(address, previousClass.getFixedSize());
		}
	}

	public long getInstance(Object address, long ptr) {
		long dataPtr = getDataPtr(address, ptr);
		if (dataPtr == NULL) {
			throw new NullPointerException();
		}
		return dataPtr + TYPEID.getFixedSize();
	}
	
	private long getDataPtr(Object address, long ptr) {
		return RC.getInstance().ptr64 ? PTR_64.getLong(address, ptr) : UnsignedInt.toLong( PTR_32.getInt(address, ptr) );
	}
	
	private void setDataPtr(Object address, long ptr, long dataPtr) {
		if (RC.getInstance().ptr64) {
			PTR_64.setLong(address, ptr, dataPtr);
		}
		else {
			PTR_32.setInt(address, ptr, UnsignedInt.fromLong(dataPtr) );
		}
	}

	@Override
	public void copyTo(Object address, long ptr, Object des, long desPtr) {
		throw new IllegalAccessError("copy data by value");
	}

	@Override
	public int getFixedSize() {
		return RC.getInstance().ptr64 ? PackedConstants.PTR64_SIZE : PackedConstants.PTR32_SIZE;
	}
	
	@Override
	public int getTypeId() {
		return TypeRegistry.REF_ID;
	}
	
}
