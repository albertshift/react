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
		
		long pcPtr = dataPtr + objectTypeId.sizeOf();
		po.format(address, pcPtr);
		
		eraseInstance(address, oldDataPtr);
		return pcPtr;
	}
	
	public long newArrayInstance(Object address, long ptr, int elementTypeId, int length) {
		PackedObject elementPO = TypeRegistry.resolveType(elementTypeId);
		Array po = (Array) TypeRegistry.resolveType(TypeRegistry.ARRAY_ID);
		
		long oldDataPtr = getDataPtr(address, ptr);
		long dataPtr = PackedObjectMemory.newMemory(address, po.sizeOf() + objectTypeId.sizeOf() + elementPO.sizeOf() * length);
		
		setDataPtr(address, ptr, dataPtr);
		objectTypeId.setInt(address, dataPtr, po.getTypeId());
		
		long pcPtr = dataPtr + objectTypeId.sizeOf();
		po.format(address, pcPtr, elementTypeId, length);
		
		eraseInstance(address, oldDataPtr);
		return pcPtr;
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
		throw new IllegalAccessError("copy data by value");
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
