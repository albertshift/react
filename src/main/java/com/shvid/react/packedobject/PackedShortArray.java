package com.shvid.react.packedobject;

import com.shvid.react.UnsafeHolder;

public class PackedShortArray implements PackedClass  {

	final long offset;
	final int length;
	final short defaultValue;
	
	public PackedShortArray(long offset, int length) {
		this(offset, length, PackedConstants.DEFAULT_SHORT);
	}
	
	public PackedShortArray(long offset, int length, short defaultValue) {
		this.offset = offset;
		this.length = length;
		this.defaultValue = defaultValue;
	}
	
	public void format(byte[] blob, long ptr) {
		for (int i = 0; i != length; ++i) {
			setShort(blob, ptr, i, defaultValue);
		}
	}
	
	public void format(long address, long ptr) {
		for (int i = 0; i != length; ++i) {
			setShort(address, ptr, i, defaultValue);
		}
	}
	
	public short getShort(HeapPackedObject<?> po, int pos) {
		return getShort(po.blob, po.ptr, pos);
	}
	
	public short getShort(byte[] blob, long ptr, int pos) {
		ensurePosition(pos);
		pos <<= PackedConstants.SHORT_SHIFT_BITS;
		return UnsafeHolder.UNSAFE.getShort(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset + pos * UnsafeHolder.byteArrayIndexScale);
	}
	
	public short getShort(AddressPackedObject<?> po, int pos) {
		return getShort(po.address, po.ptr, pos);
	}
	
	public short getShort(long address, long ptr, int pos) {
		ensurePosition(pos);
		pos <<= PackedConstants.SHORT_SHIFT_BITS;
		return UnsafeHolder.UNSAFE.getShort(address + offset + ptr + pos);
	}
	
	public void setShort(HeapPackedObject<?> po, int pos, short value) {
		setShort(po.blob, po.ptr, pos, value);
	}
	
	public void setShort(byte[] blob, long ptr, int pos, short value) {
		ensurePosition(pos);
		pos <<= PackedConstants.SHORT_SHIFT_BITS;
		UnsafeHolder.UNSAFE.putShort(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset + pos * UnsafeHolder.byteArrayIndexScale, value);
	}	
	
	public void setShort(AddressPackedObject<?> po, int pos, short value) {
		setShort(po.address, po.ptr, pos, value);
	}
	
	public void setShort(long address, long ptr, int pos, short value) {
		ensurePosition(pos);
		pos <<= PackedConstants.SHORT_SHIFT_BITS;
		UnsafeHolder.UNSAFE.putShort(address + offset + ptr + pos, value);
	}
	
	public int getFixedSize() {
		return length << PackedConstants.SHORT_SHIFT_BITS;
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
