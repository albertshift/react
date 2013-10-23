package com.shvid.react.packedobject;

public final class Point implements PackedClass {

	public final PackedInt x;
	public final PackedInt y;
	
	public Point(long offset) {
		x = new PackedInt(offset);
		offset += PackedConstants.INT_SIZE;
		y = new PackedInt(offset);
	}
	
	public void format(byte[] blob) {
	}
	
	public void format(long address) {
	}

	public int getFixedSize() {
		return PackedConstants.INT_SIZE * 2;
	}
	
	public int getInitCapacity() {
		return 0;
	}
}
