package com.shvid.react.packedobject;

/**
 * 
 * Stores variable offset in memory to the string plus size plus capacity
 * 
 * @author ashvid
 *
 */

public final class PackedString implements PackedObject {
	
	
	@Override
	public int getTypeId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public PackedString(long offset) {
		this(offset, 0);
	}
	
	public PackedString(long offset, int initLength) {

	}
	
	public void format(byte[] blob, long ptr) {

	}
	
	public void format(long address, long ptr) {

	}
	
	@Override
	public void copyTo(byte[] blob, long ptr, byte[] des, long desPtr) {
		
	}

	@Override
	public void copyTo(long address, long ptr, long des, long desPtr) {
	
	}

	
	public void setString(byte[] blob, long ptr, CharSequence value) {
		
	}
	
	public void setString(long address, long ptr, CharSequence value) {
		
	}

	@Override
	public int getFixedSize() {
		return 0;
	}
	
	public int getInitCapacity() {
		return 0;
	}
}
