package com.shvid.react.packedobject;

import com.shvid.react.RC;

public class Ref<T extends PackedClass> implements PackedClass {

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
	
	public void format(byte[] blob, long ptr) {
		setNull(blob, ptr);
	}

	@Override
	public void format(long address, long ptr) {
		setNull(address, ptr);
	}
	
	public boolean isNull(byte[] blob, long ptr) {
		long dataPtr = getDataPtr(blob, ptr);
		return dataPtr == NULL;
	}
	
	public boolean isNull(long address, long ptr) {
		long dataPtr = getDataPtr(address, ptr);
		return dataPtr == NULL;
	}
	
	public void setNull(byte[] blob, long ptr) {
		setDataPtr(blob, ptr, NULL);
	}
	
	public void setNull(long address, long ptr) {
		setDataPtr(address, ptr, NULL);
	}
	
	public T getType(byte[] blob, long ptr) {
		long dataPtr = getDataPtr(blob, ptr);
		if (dataPtr == NULL) {
			return null;
		}
		return TypeRegistry.resolveType(TYPEID.getInt(blob, dataPtr));
	}

	public long newInstance(byte[] blob, long ptr, int typeId) {
		PackedClass po = TypeRegistry.resolveType(typeId);
		
		long oldDataPtr = getDataPtr(blob, ptr);
		long dataPtr = PackedObjectMemory.newMemory(blob, po.getFixedSize() + TYPEID.getFixedSize());
		
		setDataPtr(blob, ptr, dataPtr);
		TYPEID.setInt(blob, dataPtr, po.getTypeId());
		
		long pcPtr = dataPtr + TYPEID.getFixedSize();
		po.format(blob, pcPtr);
		
		eraseInstance(blob, oldDataPtr);
		return pcPtr;
	}
	
	public long newArrayInstance(byte[] blob, long ptr, int elementTypeId, int length) {
		PackedClass elementPO = TypeRegistry.resolveType(elementTypeId);
		Array po = (Array) TypeRegistry.resolveType(TypeRegistry.ARRAY_ID);
		
		long oldDataPtr = getDataPtr(blob, ptr);
		long dataPtr = PackedObjectMemory.newMemory(blob, po.getFixedSize() + TYPEID.getFixedSize() + elementPO.getFixedSize() * length);
		
		setDataPtr(blob, ptr, dataPtr);
		TYPEID.setInt(blob, dataPtr, po.getTypeId());
		
		long pcPtr = dataPtr + TYPEID.getFixedSize();
		po.format(blob, pcPtr, elementTypeId, length);
		
		eraseInstance(blob, oldDataPtr);
		return pcPtr;
	}
	
	public long newInstance(long address, long ptr, int typeId) {
		PackedClass po = TypeRegistry.resolveType(typeId);
		
		long oldDataPtr = getDataPtr(address, ptr);
		long dataPtr = PackedObjectMemory.newMemory(address, po.getFixedSize() + TYPEID.getFixedSize());
		
		setDataPtr(address, ptr, dataPtr);
		TYPEID.setInt(address, dataPtr, po.getTypeId());
		
		long pcPtr = dataPtr + TYPEID.getFixedSize();
		po.format(address, pcPtr);
		
		eraseInstance(address, oldDataPtr);
		return pcPtr;
	}
	
	public long newArrayInstance(long address, long ptr, int elementTypeId, int length) {
		PackedClass elementPO = TypeRegistry.resolveType(elementTypeId);
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
	
	private void eraseInstance(byte[] blob, long dataPtr) {
		if (dataPtr != NULL) {
			PackedClass previousClass = TypeRegistry.resolveType(TYPEID.getInt(blob, dataPtr));
			PackedObjectMemory.incrementTrash(blob, previousClass.getFixedSize());
		}
	}
	
	private void eraseInstance(long address, long dataPtr) {
		if (dataPtr != NULL) {
			PackedClass previousClass = TypeRegistry.resolveType(TYPEID.getInt(address, dataPtr));
			PackedObjectMemory.incrementTrash(address, previousClass.getFixedSize());
		}
	}

	public long getInstance(byte[] blob, long ptr) {
		long dataPtr = getDataPtr(blob, ptr);
		if (dataPtr == NULL) {
			throw new NullPointerException();
		}
		return dataPtr + TYPEID.getFixedSize();
	}
	
	public long getInstance(long address, long ptr) {
		long dataPtr = getDataPtr(address, ptr);
		if (dataPtr == NULL) {
			throw new NullPointerException();
		}
		return dataPtr + TYPEID.getFixedSize();
	}
	
	private long getDataPtr(byte[] blob, long ptr) {
		return RC.getInstance().ptr64 ? PTR_64.getLong(blob, ptr) : UnsignedInt.toLong( PTR_32.getInt(blob, ptr) );
	}
	
	private long getDataPtr(long address, long ptr) {
		return RC.getInstance().ptr64 ? PTR_64.getLong(address, ptr) : UnsignedInt.toLong( PTR_32.getInt(address, ptr) );
	}
	
	private void setDataPtr(byte[] blob, long ptr, long dataPtr) {
		if (RC.getInstance().ptr64) {
			PTR_64.setLong(blob, ptr, dataPtr);
		}
		else {
			PTR_32.setInt(blob, ptr, UnsignedInt.fromLong(dataPtr) );
		}
	}
	
	private void setDataPtr(long address, long ptr, long dataPtr) {
		if (RC.getInstance().ptr64) {
			PTR_64.setLong(address, ptr, dataPtr);
		}
		else {
			PTR_32.setInt(address, ptr, UnsignedInt.fromLong(dataPtr) );
		}
	}

	@Override
	public void copyTo(byte[] blob, long ptr, byte[] des, long desPtr) {
		throw new IllegalAccessError("copy data by value");
	}

	@Override
	public void copyTo(long address, long ptr, long des, long desPtr) {
		throw new IllegalAccessError("copy data by value");
	}


	@Override
	public int getFixedSize() {
		return RC.getInstance().ptr64 ? PackedConstants.PTR64_SIZE : PackedConstants.PTR32_SIZE;
	}

	@Override
	public int getInitCapacity() {
		return PackedConstants.INT_SIZE;
	}
	
	@Override
	public int getTypeId() {
		return TypeRegistry.REF_ID;
	}
	
}
