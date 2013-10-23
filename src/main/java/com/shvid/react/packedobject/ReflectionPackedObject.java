package com.shvid.react.packedobject;


public class ReflectionPackedObject implements PackedObject {
	
	private final PackedObject[] fields;
	
	private final int fixedSize;
	private final int initCapacity;
	
	public ReflectionPackedObject(long offset, ClassReflection cr) {
		
		/*
		 * Initialize local cache of fields
		 */
		fields = cr.constructFields(this, offset);

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
	public void format(byte[] blob) {
		for (PackedObject field : fields) {
			field.format(blob);
		}
	}

	@Override
	public void format(long address) {
		for (PackedObject field : fields) {
			field.format(address);
		}
	}

}
