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
	
	void format(Object address, long ptr);
	
	void copyTo(Object address, long ptr, Object des, long desPtr);
	
}
