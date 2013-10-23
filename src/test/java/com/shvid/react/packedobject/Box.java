package com.shvid.react.packedobject;

public final class Box implements PackedObject {

	public final PackedLong num;  
	public final Point origin;
	public final Point extent; 
	public final PackedString name; 

	public final int fixedSize;
	
	public Box() {
		this(PackedHeader.objBaseOffset);
	}
	
	public Box(long ioffset) {
		long offset = ioffset;
		
		num = new PackedLong(offset);
		offset += num.getFixedSize();
		
		origin = new Point(offset);
		offset += origin.getFixedSize();
		
		extent = new Point(offset);
		offset += extent.getFixedSize();
		
		name = new PackedString(offset, 20);
		offset += name.getFixedSize();
		
		fixedSize = (int) (offset - ioffset);

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
