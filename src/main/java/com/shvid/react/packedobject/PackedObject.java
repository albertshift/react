package com.shvid.react.packedobject;

public interface PackedObject<T extends PackedClass> {

	long getPtr();
	
	long getSize();
	
	boolean isHeap();
	
	T getPackedClass();
	
}
