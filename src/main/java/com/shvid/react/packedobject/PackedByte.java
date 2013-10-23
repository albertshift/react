package com.shvid.react.packedobject;

import com.shvid.react.UnsafeHolder;

public final class PackedByte implements PackedObject {

	final long offset;
	final byte defaultValue;
	public final static int fixedSize = 1;

	public PackedByte(long offset) {
		this(offset, (byte)0);
	}

	public PackedByte(long offset, byte defaultValue) {
		this.offset = offset;
		this.defaultValue = defaultValue;
	}
	
	public void format(byte[] blob) {
		setByte(blob, 0, defaultValue);
	}
	
	public void format(long address) {
		setByte(address, 0, defaultValue);
	}
	
	public byte getByte(byte[] blob, long ptr) {
		return UnsafeHolder.UNSAFE.getByte(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset);
	}
	
	public byte getByte(long address, long ptr) {
		return UnsafeHolder.UNSAFE.getByte(address + offset + ptr);
	}
	
	public void setByte(byte[] blob, long ptr, byte value) {
		UnsafeHolder.UNSAFE.putByte(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset, value);
	}	
	
	public void setByte(long address, long ptr, byte value) {
		UnsafeHolder.UNSAFE.putByte(address + offset + ptr, value);
	}
	
	public int getFixedSize() {
		return fixedSize;
	}
	
	public int getInitCapacity() {
		return 0;
	}
	
}
