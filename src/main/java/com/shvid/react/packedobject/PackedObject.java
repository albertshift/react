package com.shvid.react.packedobject;


public interface PackedObject {

	int getFixedSize();
	
	int getInitCapacity();
	
	void format(byte[] blob);
	
	void format(long address);
	
}
