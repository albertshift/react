package com.shvid.react.packedobject;

import com.shvid.react.UnsafeHolder;

public class PackedLongArray implements PackedClass {

	final long offset;
	final int length;
	final long defaultValue;
	
	public PackedLongArray(long offset, int length) {
		this(offset, length, PackedConstants.DEFAULT_LONG);
	}
	
	public PackedLongArray(long offset, int length, long defaultValue) {
		this.offset = offset;
		this.length = length;
		this.defaultValue = defaultValue;
	}
	
	public void format(byte[] blob) {
		for (int i = 0; i != length; ++i) {
			setLong(blob, 0, i, defaultValue);
		}
	}
	
	public void format(long address) {
		for (int i = 0; i != length; ++i) {
			setLong(address, 0, i, defaultValue);
		}
	}
	
	public long getLong(HeapPackedObject<?> po, int pos) {
		return getLong(po.blob, po.ptr, pos);
	}
	
	public long getLong(byte[] blob, long ptr, int pos) {
		ensurePosition(pos);
		pos <<= PackedConstants.LONG_SHIFT_BITS;
		return UnsafeHolder.UNSAFE.getLong(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset + pos * UnsafeHolder.byteArrayIndexScale);
	}
	
	public long getLong(AddressPackedObject<?> po, int pos) {
		return getLong(po.address, po.ptr, pos);
	}
	
	public long getLong(long address, long ptr, int pos) {
		ensurePosition(pos);
		pos <<= PackedConstants.LONG_SHIFT_BITS;
		return UnsafeHolder.UNSAFE.getLong(address + offset + ptr + pos);
	}
	
	public void setLong(HeapPackedObject<?> po, int pos, long value) {
		setLong(po.blob, po.ptr, pos, value);
	}
	
	public void setLong(byte[] blob, long ptr, int pos, long value) {
		ensurePosition(pos);
		pos <<= PackedConstants.LONG_SHIFT_BITS;
		UnsafeHolder.UNSAFE.putLong(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset + pos * UnsafeHolder.byteArrayIndexScale, value);
	}	
	
	public void setLong(AddressPackedObject<?> po, int pos, long value) {
		setLong(po.address, po.ptr, pos, value);
	}
	
	public void setLong(long address, long ptr, int pos, long value) {
		ensurePosition(pos);
		pos <<= PackedConstants.LONG_SHIFT_BITS;
		UnsafeHolder.UNSAFE.putLong(address + offset + ptr + pos, value);
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
