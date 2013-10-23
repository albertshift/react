package com.shvid.react.packedobject;

import com.shvid.react.UnsafeHolder;

public final class PackedByteArray implements PackedObject {

	final long offset;
	final int length;
	final byte defaultValue;
	
	public PackedByteArray(long offset, int length) {
		this(offset, length, PackedConstants.DEFAULT_BYTE);
	}
	
	public PackedByteArray(long offset, int length, byte defaultValue) {
		this.offset = offset;
		this.length = length;
		this.defaultValue = defaultValue;
	}
	
	public void format(byte[] blob) {
		for (int i = 0; i != length; ++i) {
			setByte(blob, 0, i, defaultValue);
		}
	}
	
	public void format(long address) {
		for (int i = 0; i != length; ++i) {
			setByte(address, 0, i, defaultValue);
		}
	}
	
	public byte getByte(byte[] blob, long ptr, int pos) {
		ensurePosition(pos);
		return UnsafeHolder.UNSAFE.getByte(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset + pos * UnsafeHolder.byteArrayIndexScale);
	}
	
	public byte getByte(long address, long ptr, int pos) {
		ensurePosition(pos);
		return UnsafeHolder.UNSAFE.getByte(address + offset + ptr + pos);
	}
	
	public void setByte(long address, long ptr, int pos, byte value) {
		ensurePosition(pos);
		UnsafeHolder.UNSAFE.putByte(address + offset + ptr + pos, value);
	}
	
	public void setByte(byte[] blob, long ptr, int pos, byte value) {
		ensurePosition(pos);
		UnsafeHolder.UNSAFE.putByte(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset + pos * UnsafeHolder.byteArrayIndexScale, value);
	}
	
	public int getFixedSize() {
		return length;
	}

	public int getInitCapacity() {
		return 0;
	}
	
	private void ensurePosition(int pos) {
		if (pos < 0 || pos >= length) {
			throw new IndexOutOfBoundsException("pos=" + pos + ", length=" + length);
		}
	}
	
}
