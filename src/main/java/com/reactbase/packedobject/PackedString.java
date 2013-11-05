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

	public static int getUTF8Length(CharSequence str) {
		int len = str.length();
		int utf8bytes = 0;
		for (int i = 0; i != len; i++) {
			int c = str.charAt(i);
			if ((c >= 0x0001) && (c <= 0x007F)) {
				utf8bytes++;
			}
			else if (c > 0x07FF) {
				utf8bytes += 3;
			}
			else {
				utf8bytes += 2;
			}
		}
		return utf8bytes;
	}
	
	@Override
	public int sizeOf() {
		return 0;
	}
	
}
