package com.shvid.react.packedobject;

import com.shvid.react.UnsafeHolder;

public class PackedDoubleArray implements PackedClass{

	final long offset;
	final int length;
	final double defaultValue;
	
	public PackedDoubleArray(long offset, int length) {
		this(offset, length, PackedConstants.DEFAULT_DOUBLE);
	}
	
	public PackedDoubleArray(long offset, int length, double defaultValue) {
		this.offset = offset;
		this.length = length;
		this.defaultValue = defaultValue;
	}
	
	public void format(byte[] blob, long ptr) {
		for (int i = 0; i != length; ++i) {
			setDouble(blob, ptr, i, defaultValue);
		}
	}
	
	public void format(long address, long ptr) {
		for (int i = 0; i != length; ++i) {
			setDouble(address, ptr, i, defaultValue);
		}
	}
	
	public double getDouble(HeapPackedObject<?> po, int pos) {
		return getDouble(po.blob, po.ptr, pos);
	}
	
	public double getDouble(byte[] blob, long ptr, int pos) {
		ensurePosition(pos);
		pos <<= PackedConstants.DOUBLE_SHIFT_BITS;
		return UnsafeHolder.UNSAFE.getDouble(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset + pos * UnsafeHolder.byteArrayIndexScale);
	}
	
	public double getDouble(AddressPackedObject<?> po, int pos) {
		return getDouble(po.address, po.ptr, pos);
	}
	
	public double getDouble(long address, long ptr, int pos) {
		ensurePosition(pos);
		pos <<= PackedConstants.DOUBLE_SHIFT_BITS;
		return UnsafeHolder.UNSAFE.getDouble(address + offset + ptr + pos);
	}
	
	public void setDouble(HeapPackedObject<?> po, int pos, double value) {
		setDouble(po.blob, po.ptr, pos, value);
	}
	
	public void setDouble(byte[] blob, long ptr, int pos, double value) {
		ensurePosition(pos);
		pos <<= PackedConstants.DOUBLE_SHIFT_BITS;
		UnsafeHolder.UNSAFE.putDouble(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset + pos * UnsafeHolder.byteArrayIndexScale, value);
	}	
	
	public void setDouble(AddressPackedObject<?> po, int pos, double value) {
		setDouble(po.address, po.ptr, pos, value);
	}
	
	public void setDouble(long address, long ptr, int pos, double value) {
		ensurePosition(pos);
		pos <<= PackedConstants.DOUBLE_SHIFT_BITS;
		UnsafeHolder.UNSAFE.putDouble(address + offset + ptr + pos, value);
	}
	
	public int getFixedSize() {
		return length << PackedConstants.DOUBLE_SHIFT_BITS;
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
