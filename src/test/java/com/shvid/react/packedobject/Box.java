package com.shvid.react.packedobject;

public final class Box implements PackedObject {

	public final PackedLong num;  
	public final Point origin;
	public final Point extent; 
	public final PackedString name; 
	
	public final static int fixedSize = PackedLong.fixedSize + Point.fixedSize + Point.fixedSize + PackedString.fixedSize;
	
	public Box() {
		this(PackedHeader.objBaseOffset);
	}
	
	public Box(long offset) {
		num = new PackedLong(offset);
		offset += PackedLong.fixedSize;
		
		origin = new Point(offset);
		offset += Point.fixedSize;
		
		extent = new Point(offset);
		offset += Point.fixedSize;
		
		name = new PackedString(offset, 20);
		offset += PackedString.fixedSize;

	}

	public void format(byte[] blob) {
	}
	
	public void format(long address) {
	}
	
	public int getFixedSize() {
		return fixedSize;
	}
	
	public int getInitCapacity() {
		return 0;
	}
}
