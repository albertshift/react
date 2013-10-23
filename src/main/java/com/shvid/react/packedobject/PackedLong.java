package com.shvid.react.packedobject;

import com.shvid.react.Swapper;
import com.shvid.react.RC;
import com.shvid.react.UnsafeHolder;

public final class PackedLong implements PackedObject {

	final long offset;
	final long defaultValue;
	public final static int fixedSize = 8;
	
	public PackedLong(long offset) {
		this(offset, 0);
	}
	
	public PackedLong(long offset, long defaultValue) {
		this.offset = offset;
		this.defaultValue = defaultValue;
	}
	
	public void format(byte[] blob) {
		setLong(blob, 0, defaultValue);
	}
	
	public void format(long address) {
		setLong(address, 0, defaultValue);
	}
	
	public long getLong(byte[] blob, int ptr) {
		long value = UnsafeHolder.UNSAFE.getLong(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapLong(value);
	}
	
	public long getLong(long address, int ptr) {
		long value = UnsafeHolder.UNSAFE.getLong(address + offset + ptr);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapLong(value);
	}
	
	public void setLong(byte[] blob, int ptr, long value) {
		value = RC.getInstance().isLittleEndian ? value : Swapper.swapLong(value);
		UnsafeHolder.UNSAFE.putLong(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset, value);
	}
	
	public void setLong(long address, int ptr, long value) {
		value = RC.getInstance().isLittleEndian ? value : Swapper.swapLong(value);
		UnsafeHolder.UNSAFE.putLong(address + offset + ptr, value);
	}
	
	public int getFixedSize() {
		return fixedSize;
	}
	
	public int getInitCapacity() {
		return 0;
	}
	
}