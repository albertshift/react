package com.shvid.react.packedobject;

public final class Point extends PackedObject {

	@Override
	public int getTypeId() {
		// TODO Auto-generated method stub
		return 54;
	}

	public final PackedInt x;
	public final PackedInt y;
	
	public Point(long offset) {
		super(offset);
		x = new PackedInt(offset);
		offset += PackedConstants.INT_SIZE;
		y = new PackedInt(offset);
	}
	
	public void format(Object address, long ptr) {
		x.format(address, ptr);
		y.format(address, ptr);
	}

	public int sizeOf() {
		return PackedConstants.INT_SIZE * 2;
	}
	
}
