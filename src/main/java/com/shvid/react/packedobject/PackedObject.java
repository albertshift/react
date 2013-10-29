package com.shvid.react.packedobject;

/*
 * PackedObject is the structure of metadata in memory, not a object itself
 * 
 * PackedObject has two parameters: memory address and ptr
 * 
 * memory address can be:
 *   - on-heap   byte[]
 *   - off-heap  long
 * 
 */

public abstract class PackedObject {

	final long offset;
	
	PackedObject(long offset) {
		this.offset = offset;
	}
	
	public abstract int getTypeId();
	
	public abstract int sizeOf();
	
	public abstract void format(Object address, long ptr);
	
	public void copyTo(Object address, long ptr, Object des, long desPtr) {
		PackedObjectMemory.copyTo(address, ptr + offset, des, desPtr + offset, sizeOf());
	}
	
}
