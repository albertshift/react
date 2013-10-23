package com.shvid.react.packedobject;

import com.shvid.react.RC;
import com.shvid.react.Swapper;
import com.shvid.react.UnsafeHolder;

public final class PackedDouble implements PackedClass{
	
	final long offset;
	final double defaultValue;
	
	public PackedDouble(long offset) {
		this(offset, PackedConstants.DEFAULT_DOUBLE);
	}
	
	public PackedDouble(long offset, double defaultValue) {
		this.offset = offset;
		this.defaultValue = defaultValue;
	}
	
	public void format(byte[] blob) {
		setDouble(blob, 0, defaultValue);
	}
	
	public void format(long address) {
		setDouble(address, 0, defaultValue);
	}
	
	public double getDouble(byte[] blob, long ptr) {
		double value = UnsafeHolder.UNSAFE.getDouble(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapDouble(value);
	}
	
	public double getDouble(long address, long ptr) {
		double value = UnsafeHolder.UNSAFE.getDouble(address + offset + ptr);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapDouble(value);
	}
	
	public void setDouble(byte[] blob, long ptr, double value) {
		value = RC.getInstance().isLittleEndian ? value : Swapper.swapDouble(value);
		UnsafeHolder.UNSAFE.putDouble(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset, value);
	}
	
	public void setDouble(long address, long ptr, double value) {
		value = RC.getInstance().isLittleEndian ? value : Swapper.swapDouble(value);
		UnsafeHolder.UNSAFE.putDouble(address + offset + ptr, value);
	}
	
	public int getFixedSize() {
		return PackedConstants.DOUBLE_SIZE;
	}
	
	public int getInitCapacity() {
		return 0;
	}
}
