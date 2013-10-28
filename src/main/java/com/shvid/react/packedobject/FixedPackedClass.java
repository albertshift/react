package com.shvid.react.packedobject;

import com.shvid.react.UnsafeHolder;

public abstract class FixedPackedClass implements PackedObject {

	final long offset;
	
	FixedPackedClass(long offset) {
		this.offset = offset;
	}
	
	public void copyTo(byte[] blob, long ptr, byte[] des, long desPtr) {
		UnsafeHolder.UNSAFE.copyMemory(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset, des, offset + desPtr + UnsafeHolder.byteArrayBaseOffset, getFixedSize());
	}

	public void copyTo(long address, long ptr, long des, long desPtr) {
		UnsafeHolder.UNSAFE.copyMemory(address, offset + ptr, des, offset + desPtr, getFixedSize());
	}
	
}
