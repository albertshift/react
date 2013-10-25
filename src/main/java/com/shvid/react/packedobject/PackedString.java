package com.shvid.react.packedobject;

/**
 * 
 * Stores variable offset in memory to the string plus size plus capacity
 * 
 * @author ashvid
 *
 */

public final class PackedString implements PackedClass {
	
	final PackedByteArrayRef data;
	
	public PackedString(long offset) {
		this(offset, 0);
	}
	
	public PackedString(long offset, int initLength) {
		data = new PackedByteArrayRef(offset, initLength);
	}
	
	public void format(byte[] blob, long ptr) {
		data.format(blob, ptr);
	}
	
	public void format(long address, long ptr) {
		data.format(address, ptr);
	}
	
	@Override
	public void copyTo(byte[] blob, long ptr, byte[] des, long desPtr) {
		data.copyTo(blob, ptr, des, desPtr);
	}

	@Override
	public void copyTo(long address, long ptr, long des, long desPtr) {
		data.copyTo(address, ptr, des, desPtr);
	}

	
	public void setString(byte[] blob, long ptr, CharSequence value) {
		
	}
	
	public void setString(long address, long ptr, CharSequence value) {
		
	}

	@Override
	public int getFixedSize() {
		return data.getFixedSize();
	}
	
	public int getInitCapacity() {
		return data.getInitCapacity();
	}
}
