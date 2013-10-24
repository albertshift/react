package com.shvid.react.packedobject;

import com.shvid.react.UnsafeHolder;

public class PackedCharArray implements PackedClass {

	final long offset;
	final int length;
	final char defaultValue;
	
	public PackedCharArray(long offset, int length) {
		this(offset, length, PackedConstants.DEFAULT_CHAR);
	}
	
	public PackedCharArray(long offset, int length, char defaultValue) {
		this.offset = offset;
		this.length = length;
		this.defaultValue = defaultValue;
	}
	
	public void format(byte[] blob) {
		for (int i = 0; i != length; ++i) {
			setChar(blob, 0, i, defaultValue);
		}
	}
	
	public void format(long address) {
		for (int i = 0; i != length; ++i) {
			setChar(address, 0, i, defaultValue);
		}
	}
	
	public char getChar(HeapPackedObject<?> po, int pos) {
		return getChar(po.blob, po.ptr, pos);
	}
	
	public char getChar(byte[] blob, long ptr, int pos) {
		ensurePosition(pos);
		pos <<= PackedConstants.CHAR_SHIFT_BITS;
		return UnsafeHolder.UNSAFE.getChar(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset + pos * UnsafeHolder.byteArrayIndexScale);
	}
	
	public char getChar(AddressPackedObject<?> po, int pos) {
		return getChar(po.address, po.ptr, pos);
	}
	
	public char getChar(long address, long ptr, int pos) {
		ensurePosition(pos);
		pos <<= PackedConstants.CHAR_SHIFT_BITS;
		return UnsafeHolder.UNSAFE.getChar(address + offset + ptr + pos);
	}
	
	public void setChar(HeapPackedObject<?> po, int pos, char value) {
		setChar(po.blob, po.ptr, pos, value);
	}
	
	public void setChar(byte[] blob, long ptr, int pos, char value) {
		ensurePosition(pos);
		pos <<= PackedConstants.CHAR_SHIFT_BITS;
		UnsafeHolder.UNSAFE.putChar(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset + pos * UnsafeHolder.byteArrayIndexScale, value);
	}	
	
	public void setChar(AddressPackedObject<?> po, int pos, char value) {
		setChar(po.address, po.ptr, pos, value);
	}
	
	public void setChar(long address, long ptr, int pos, char value) {
		ensurePosition(pos);
		pos <<= PackedConstants.CHAR_SHIFT_BITS;
		UnsafeHolder.UNSAFE.putChar(address + offset + ptr + pos, value);
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
