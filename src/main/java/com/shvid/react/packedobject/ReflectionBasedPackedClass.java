package com.shvid.react.packedobject;


public abstract class ReflectionBasedPackedClass<T extends PackedObject> implements PackedObject {

	private final PackedObject[] fields;
	
	private final int fixedSize;
	private final int initCapacity;
	
	public ReflectionBasedPackedClass(long offset, ClassDefinition<T> cr) {
		
		/*
		 * Initialize local cache of fields
		 */
		fields = cr.constructFields((T) this, offset);

		/*
		 * Precalculate sizes
		 */
		
		int size = 0;
		int initCap = 0;
		for (PackedObject field : fields) {
			size += field.getFixedSize();
			initCap += field.getInitCapacity();
		}
		fixedSize = size;
		initCapacity = initCap;
		
	}
	
	@Override
	public int getFixedSize() {
		return fixedSize;
	}

	@Override
	public int getInitCapacity() {
		return initCapacity;
	}

	@Override
	public void format(Object address, long ptr) {
		for (PackedObject field : fields) {
			field.format(address, ptr);
		}
	}

	@Override
	public void copyTo(byte[] blob, long ptr, byte[] des, long desPtr) {
		for (PackedObject field : fields) {
			field.copyTo(blob, ptr, des, desPtr);
		}
	}

	@Override
	public void copyTo(long address, long ptr, long des, long desPtr) {
		for (PackedObject field : fields) {
			field.copyTo(address, ptr, des, desPtr);
		}
	}
	
}
