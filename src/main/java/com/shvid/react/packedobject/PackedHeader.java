package com.shvid.react.packedobject;

public final class PackedHeader implements PackedObject {

	final PackedInt objTotalSize;
	final PackedInt objFixedSize;
	
	public static final int fixedSize = PackedInt.fixedSize + PackedInt.fixedSize;
	
	public static final int objBaseOffset = fixedSize;
	
	public PackedHeader() {
		long offset = 0;
		objTotalSize = new PackedInt(offset);
		offset += PackedInt.fixedSize;
		objFixedSize = new PackedInt(offset);
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
