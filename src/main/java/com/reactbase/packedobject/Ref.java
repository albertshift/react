package com.reactbase.packedobject;


public class Ref<T extends PackedObject> extends PackedObject {

	public final static int NULL = 0;

	final static PackedInt objectTypeId = new PackedInt(0);

	final PackedInt ptr32;
	final PackedLong ptr64;
	
	public Ref(long offset) {
		super(offset);
		this.ptr32 = new PackedInt(offset);
		this.ptr64 = new PackedLong(offset);
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
		return TypeRegistry.resolveType(objectTypeId.getInt(address, dataPtr));
	}
	
	public long newInstance(Object address, long ptr, int typeId) {
		PackedObject po = TypeRegistry.resolveType(typeId);
		
		long oldDataPtr = getDataPtr(address, ptr);
		long dataPtr = PackedObjectMemory.newMemory(address, po.sizeOf() + objectTypeId.sizeOf());
		
		setDataPtr(address, ptr, dataPtr);
		objectTypeId.setInt(address, dataPtr, po.getTypeId());
		
		long poPtr = dataPtr + objectTypeId.sizeOf();
		po.format(address, poPtr);
		
		eraseInstance(address, oldDataPtr);
		return poPtr;
	}
	
	public long newArrayInstance(Object address, long ptr, int elementTypeId, int length) {
		PackedObject elementPO = TypeRegistry.resolveType(elementTypeId);
		Array po = (Array) TypeRegistry.resolveType(TypeRegistry.ARRAY_ID);
		
		long oldDataPtr = getDataPtr(address, ptr);
		long dataPtr = PackedObjectMemory.newMemory(address, po.sizeOf() + objectTypeId.sizeOf() + elementPO.sizeOf() * length);
		
		setDataPtr(address, ptr, dataPtr);
		objectTypeId.setInt(address, dataPtr, po.getTypeId());
		
		long poPtr = dataPtr + objectTypeId.sizeOf();
		po.format(address, poPtr, elementTypeId, length);
		
		eraseInstance(address, oldDataPtr);
		return poPtr;
	}
	
	private void eraseInstance(Object address, long dataPtr) {
		if (dataPtr != NULL) {
			PackedObject previousClass = TypeRegistry.resolveType(objectTypeId.getInt(address, dataPtr));
			PackedObjectMemory.incrementTrash(address, previousClass.sizeOf());
		}
	}

	public long getInstance(Object address, long ptr) {
		long dataPtr = getDataPtr(address, ptr);
		if (dataPtr == NULL) {
			throw new NullPointerException();
		}
		return dataPtr + objectTypeId.sizeOf();
	}
	
	private long getDataPtr(Object address, long ptr) {
		return usePtr64 ? ptr64.getLong(address, ptr) : UnsignedInt.toLong( ptr32.getInt(address, ptr) );
	}
	
	private void setDataPtr(Object address, long ptr, long dataPtr) {
		if (usePtr64) {
			ptr64.setLong(address, ptr, dataPtr);
		}
		else {
			ptr32.setInt(address, ptr, UnsignedInt.fromLong(dataPtr) );
		}
	}

	@Override
	public void copyTo(Object address, long ptr, Object des, long desPtr) {
		long dataPtr = getDataPtr(address, ptr);
		if (dataPtr == NULL) {
			setDataPtr(des, desPtr, NULL);
		}
		else {
			int typeId = objectTypeId.getInt(address, dataPtr);
			PackedObject po = TypeRegistry.resolveType(typeId);
			int objectSizeOf = objectTypeId.sizeOf() + po.sizeOf();
			if (po instanceof Array) {
				Array<PackedObject> arr = (Array<PackedObject>) po;
				int length = arr.getLength(address, dataPtr + objectTypeId.sizeOf());
				PackedObject elementPO = arr.getType(address, dataPtr + objectTypeId.sizeOf());
				objectSizeOf += length * elementPO.sizeOf();
			}
			long desDataPtr = PackedObjectMemory.newMemory(address, objectSizeOf);
			objectTypeId.setInt(des, desDataPtr, typeId);
			po.copyTo(address, dataPtr + objectTypeId.sizeOf(), des, desDataPtr + objectTypeId.sizeOf());
		}
	}

	@Override
	public int sizeOf() {
		return usePtr64 ? PrimitiveTypes.PTR64_SIZEOF : PrimitiveTypes.PTR32_SIZEOF;
	}
	
	@Override
	public int getTypeId() {
		return TypeRegistry.REF_ID;
	}
	
}
