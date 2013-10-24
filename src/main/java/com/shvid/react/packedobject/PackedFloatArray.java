package com.shvid.react.packedobject;

import com.shvid.react.UnsafeHolder;

public class PackedFloatArray implements PackedClass {

	final long offset;
	final int length;
	final float defaultValue;
	
	public PackedFloatArray(long offset, int length) {
		this(offset, length, PackedConstants.DEFAULT_FLOAT);
	}
	
	public PackedFloatArray(long offset, int length, float defaultValue) {
		this.offset = offset;
		this.length = length;
		this.defaultValue = defaultValue;
	}
	
	public void format(byte[] blob, long ptr) {
		for (int i = 0; i != length; ++i) {
			setFloat(blob, ptr, i, defaultValue);
		}
	}
	
	public void format(long address, long ptr) {
		for (int i = 0; i != length; ++i) {
			setFloat(address, ptr, i, defaultValue);
		}
	}
	
	public float getFloat(HeapPackedObject<?> po, int pos) {
		return getFloat(po.blob, po.ptr, pos);
	}
	
	public float getFloat(byte[] blob, long ptr, int pos) {
		ensurePosition(pos);
		pos <<= PackedConstants.FLOAT_SHIFT_BITS;
		return UnsafeHolder.UNSAFE.getFloat(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset + pos * UnsafeHolder.byteArrayIndexScale);
	}
	
	public float getFloat(AddressPackedObject<?> po, int pos) {
		return getFloat(po.address, po.ptr, pos);
	}
	
	public float getFloat(long address, long ptr, int pos) {
		ensurePosition(pos);
		pos <<= PackedConstants.FLOAT_SHIFT_BITS;
		return UnsafeHolder.UNSAFE.getFloat(address + offset + ptr + pos);
	}
	
	public void setFloat(HeapPackedObject<?> po, int pos, float value) {
		setFloat(po.blob, po.ptr, pos, value);
	}
	
	public void setFloat(byte[] blob, long ptr, int pos, float value) {
		ensurePosition(pos);
		pos <<= PackedConstants.FLOAT_SHIFT_BITS;
		UnsafeHolder.UNSAFE.putFloat(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset + pos * UnsafeHolder.byteArrayIndexScale, value);
	}	
	
	public void setFloat(AddressPackedObject<?> po, int pos, float value) {
		setFloat(po.address, po.ptr, pos, value);
	}
	
	public void setFloat(long address, long ptr, int pos, float value) {
		ensurePosition(pos);
		pos <<= PackedConstants.FLOAT_SHIFT_BITS;
		UnsafeHolder.UNSAFE.putFloat(address + offset + ptr + pos, value);
	}
	
	public int getFixedSize() {
		return length << PackedConstants.FLOAT_SHIFT_BITS;
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
