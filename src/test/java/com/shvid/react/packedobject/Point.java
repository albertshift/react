package com.shvid.react.packedobject;

public final class Point implements PackedObject {

	public final PackedInt x;
	public final PackedInt y;

	public final static int fixedSize = PackedInt.fixedSize + PackedInt.fixedSize;
	
	public Point(long offset) {
		x = new PackedInt(offset);
		offset += PackedInt.fixedSize;
		y = new PackedInt(offset);
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
