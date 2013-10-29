package com.shvid.react.packedobject;


public abstract class FixedPackedClass implements PackedObject {

	final long offset;
	
	FixedPackedClass(long offset) {
		this.offset = offset;
	}
	
	public void copyTo(Object address, long ptr, Object des, long desPtr) {
		PackedObjectMemory.copyTo(address, ptr, des, desPtr, getFixedSize());
	}

	
}
