package com.shvid.react.packedobject;


public abstract class SimplePackedObject implements PackedObject {

	final long offset;
	
	SimplePackedObject(long offset) {
		this.offset = offset;
	}
	
	public void copyTo(Object address, long ptr, Object des, long desPtr) {
		PackedObjectMemory.copyTo(address, ptr, des, desPtr, getFixedSize());
	}

	
}
