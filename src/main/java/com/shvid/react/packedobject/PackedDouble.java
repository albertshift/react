package com.shvid.react.packedobject;

import com.shvid.react.RC;
import com.shvid.react.Swapper;
import com.shvid.react.UnsafeHolder;

public final class PackedDouble implements PackedObject{
	
	final long offset;
	final double defaultValue;
	public final static int fixedSize = 8;
	
	public PackedDouble(long offset) {
		this(offset, 0.0);
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
	
	public double getDouble(byte[] blob, int ptr) {
		double value = UnsafeHolder.UNSAFE.getDouble(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapDouble(value);
	}
	
	public double getDouble(long address, int ptr) {
		double value = UnsafeHolder.UNSAFE.getDouble(address + offset + ptr);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapDouble(value);
	}
	
	public void setDouble(byte[] blob, int ptr, double value) {
		value = RC.getInstance().isLittleEndian ? value : Swapper.swapDouble(value);
		UnsafeHolder.UNSAFE.putDouble(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset, value);
	}
	
	public void setDouble(long address, int ptr, double value) {
		value = RC.getInstance().isLittleEndian ? value : Swapper.swapDouble(value);
		UnsafeHolder.UNSAFE.putDouble(address + offset + ptr, value);
	}
	
	public int getFixedSize() {
		return fixedSize;
	}
	
	public int getInitCapacity() {
		return 0;
	}
}
