package com.shvid.react.packedobject;

import com.shvid.react.Swapper;
import com.shvid.react.RC;
import com.shvid.react.UnsafeHolder;

public final class PackedInt implements PackedObject {
	
	final long offset;
	final int defaultValue;
	public final static int fixedSize = 4;
	
	public PackedInt(long offset) {
		this(offset, 0);
	}
	
	public PackedInt(long offset, int defaultValue) {
		this.offset = offset;
		this.defaultValue = defaultValue;
	}

	public void format(byte[] blob) {
		setInt(blob, 0, defaultValue);
	}
	
	public void format(long address) {
		setInt(address, 0, defaultValue);
	}
	
	public int getInt(byte[] blob, long ptr) {
		int value = UnsafeHolder.UNSAFE.getInt(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapInt(value);
	}
	
	public int getInt(long address, long ptr) {
		int value = UnsafeHolder.UNSAFE.getInt(address + offset + ptr);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapInt(value);
	}
	
	public void setInt(byte[] blob, long ptr, int value) {
		value = RC.getInstance().isLittleEndian ? value : Swapper.swapInt(value);
		UnsafeHolder.UNSAFE.putInt(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset, value);
	}
	
	public void setInt(long address, long ptr, int value) {
		value = RC.getInstance().isLittleEndian ? value : Swapper.swapInt(value);
		UnsafeHolder.UNSAFE.putInt(address + offset + ptr, value);
	}
	
	public int getFixedSize() {
		return fixedSize;
	}
	
	public int getInitCapacity() {
		return 0;
	}
	
}
