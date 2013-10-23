package com.shvid.react.packedobject;

import com.shvid.react.RC;

/**
 * 
 * Packet memory structure
 * 
 *       address         floatingOffset freeOffset
 * memsize | [ header, fixed, | floating, | free, tail ]
 * 
 * header contains: freeOffset and totalFixedSize
 * 
 * floatingOffset = header.sizeof() + totalSizeOf
 * 
 * @author ashvid
 *
 */


public final class PackedHeader implements PackedClass {

	final PackedPtr freeOffset;
	final PackedInt totalFixedSize;
	
	public PackedHeader() {
		freeOffset = new PackedPtr(0);
		totalFixedSize = new PackedInt(freeOffset.getFixedSize());
	}
	
	public void format(byte[] blob) {
	}

	public void format(long address) {
	}

	public int getFixedSize() {
		return objBaseOffset();
	}

	public int getInitCapacity() {
		return 0;
	}
	
	public static int objBaseOffset() {
		return (RC.getInstance().ptr64 ? PackedConstants.PTR64_SIZE : PackedConstants.PTR32_SIZE) + PackedConstants.INT_SIZE;
	}
	
}
