package com.shvid.react.packedobject;

import com.shvid.react.RC;
import com.shvid.react.Swapper;
import com.shvid.react.UnsafeHolder;

public final class PackedPtr implements PackedObject {
	
	public final static int NULL = 0;

	final long offset;
	public final static int fixedSize = 4;
	
	public PackedPtr(long offset) {
		this.offset = offset;
	}

	public void format(byte[] blob) {
		setPtr(blob, NULL);
	}

	@Override
	public void format(long address) {
		setPtr(address, NULL);
	}

	public int getPtr(byte[] blob) {
		int value = UnsafeHolder.UNSAFE.getInt(blob, offset + UnsafeHolder.byteArrayBaseOffset);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapInt(value);
	}
	
	public int getPtr(long address) {
		int value = UnsafeHolder.UNSAFE.getInt(address + offset);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapInt(value);
	}
	
	public void setPtr(byte[] blob, int value) {
		value = RC.getInstance().isLittleEndian ? value : Swapper.swapInt(value);
		UnsafeHolder.UNSAFE.putInt(blob, offset + UnsafeHolder.byteArrayBaseOffset, value);
	}
	
	public void setPtr(long address, int value) {
		value = RC.getInstance().isLittleEndian ? value : Swapper.swapInt(value);
		UnsafeHolder.UNSAFE.putInt(address + offset, value);
	}
	
	@Override
	public int getFixedSize() {
		return fixedSize;
	}
	
	public int getInitCapacity() {
		return 0;
	}
}


