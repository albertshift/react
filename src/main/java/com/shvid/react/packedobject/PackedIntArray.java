package com.shvid.react.packedobject;

import com.shvid.react.UnsafeHolder;

public class PackedIntArray extends FixedPackedClass {

	final int length;
	final int defaultValue;
	
	public PackedIntArray(long offset, int length) {
		this(offset, length, PackedConstants.DEFAULT_INT);
	}
	
	public PackedIntArray(long offset, int length, int defaultValue) {
		super(offset);
		this.length = length;
		this.defaultValue = defaultValue;
	}
	
	public void format(byte[] blob, long ptr) {
		for (int i = 0; i != length; ++i) {
			setInt(blob, ptr, i, defaultValue);
		}
	}
	
	public void format(long address, long ptr) {
		for (int i = 0; i != length; ++i) {
			setInt(address, ptr, i, defaultValue);
		}
	}
	
	public int getInt(HeapPackedObject<?> po, int pos) {
		return getInt(po.blob, po.ptr, pos);
	}
	
	public int getInt(byte[] blob, long ptr, int pos) {
		ensurePosition(pos);
		pos <<= PackedConstants.INT_SHIFT_BITS;
		return UnsafeHolder.UNSAFE.getInt(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset + pos * UnsafeHolder.byteArrayIndexScale);
	}
	
	public int getInt(AddressPackedObject<?> po, int pos) {
		return getInt(po.address, po.ptr, pos);
	}
	
	public int getInt(long address, long ptr, int pos) {
		ensurePosition(pos);
		pos <<= PackedConstants.INT_SHIFT_BITS;
		return UnsafeHolder.UNSAFE.getInt(address + offset + ptr + pos);
	}
	
	public void setInt(HeapPackedObject<?> po, int pos, int value) {
		setInt(po.blob, po.ptr, pos, value);
	}
	
	public void setInt(byte[] blob, long ptr, int pos, int value) {
		ensurePosition(pos);
		pos <<= PackedConstants.INT_SHIFT_BITS;
		UnsafeHolder.UNSAFE.putInt(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset + pos * UnsafeHolder.byteArrayIndexScale, value);
	}	
	
	public void setint(AddressPackedObject<?> po, int pos, int value) {
		setInt(po.address, po.ptr, pos, value);
	}
	
	public void setInt(long address, long ptr, int pos, int value) {
		ensurePosition(pos);
		pos <<= PackedConstants.INT_SHIFT_BITS;
		UnsafeHolder.UNSAFE.putInt(address + offset + ptr + pos, value);
	}
	
	public int getFixedSize() {
		return length << PackedConstants.INT_SHIFT_BITS;
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
