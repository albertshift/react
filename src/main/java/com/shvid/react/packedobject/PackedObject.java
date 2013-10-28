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

public interface PackedObject {

	int getTypeId();
	
	int getFixedSize();
	
	int getInitCapacity();
	
	void format(Object address, long ptr);
	
	void copyTo(byte[] blob, long ptr, byte[] des, long desPtr);
	
	void copyTo(long address, long ptr, long des, long desPtr);
	
}
