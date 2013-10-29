package com.reactbase.packedobject;

/**
 * 
 * Stores variable offset in memory to the string plus size plus capacity
 * 
 * @author ashvid
 *
 */

public final class PackedString extends PackedObject {
	
	
	@Override
	public int getTypeId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public PackedString(long offset) {
		this(offset, 0);
	}
	
	public PackedString(long offset, int initLength) {
		super(offset);
	}
	
	
	public void format(Object address, long ptr) {

	}
	
	@Override
	public void copyTo(Object address, long ptr, Object des, long desPtr) {
	
	}
	
	public void setString(Object address, long ptr, CharSequence value) {
		
	}

	@Override
	public int sizeOf() {
		return 0;
	}
	
}
