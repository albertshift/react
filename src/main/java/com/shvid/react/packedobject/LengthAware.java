package com.shvid.react.packedobject;

public interface LengthAware {

	void format(byte[] blob, long ptr, int elementTypeId, int length);
	
	void format(long address, long ptr, int elementTypeId, int length);
	
	
}
