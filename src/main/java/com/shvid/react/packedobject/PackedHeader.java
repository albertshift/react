package com.shvid.react.packedobject;

public final class PackedHeader implements PackedObject {

	final PackedInt objTotalSize;
	final PackedInt objFixedSize;
	
	static final int fixedSize = PackedConstants.INT_SIZE + PackedConstants.INT_SIZE;
	public static final int objBaseOffset = fixedSize;
	
	public PackedHeader() {
		long offset = 0;
		objTotalSize = new PackedInt(offset);
		offset += objTotalSize.getFixedSize();
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
