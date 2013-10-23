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

public interface PackedClass {

	int getFixedSize();
	
	int getInitCapacity();
	
	void format(byte[] blob);
	
	void format(long address);
	
}
