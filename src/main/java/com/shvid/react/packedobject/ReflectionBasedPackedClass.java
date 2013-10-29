package com.shvid.react.packedobject;


public abstract class ReflectionBasedPackedClass<T extends PackedObject> implements PackedObject {

	private final PackedObject[] fields;
	
	private final int fixedSize;
	
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
			size += field.sizeOf();
		}
		fixedSize = size;
		
	}
	
	@Override
	public int sizeOf() {
		return fixedSize;
	}

	@Override
	public void format(Object address, long ptr) {
		for (PackedObject field : fields) {
			field.format(address, ptr);
		}
	}

	@Override
	public void copyTo(Object address, long ptr, Object des, long desPtr) {
		for (PackedObject field : fields) {
			field.copyTo(address, ptr, des, desPtr);
		}
	}
	
}
