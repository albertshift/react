package com.shvid.react.packedobject;

import com.shvid.react.RC;
import com.shvid.react.Swapper;
import com.shvid.react.UnsafeHolder;

public final class PackedShort implements PackedObject  {

	final long offset;
	final short defaultValue;
	public final static int fixedSize = 2;
	
	public PackedShort(long offset) {
		this(offset, (short)0);
	}
	
	public PackedShort(long offset, short defaultValue) {
		this.offset = offset;
		this.defaultValue = defaultValue;
	}
	
	public void format(byte[] blob) {
		setShort(blob, 0, defaultValue);
	}
	
	public void format(long address) {
		setShort(address, 0, defaultValue);
	}
	
	public short getShort(byte[] blob, int ptr) {
		short value = UnsafeHolder.UNSAFE.getShort(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapShort(value);
	}
	
	public short getShort(long address, int ptr) {
		short value = UnsafeHolder.UNSAFE.getShort(address + offset + ptr);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapShort(value);
	}
	
	public void setShort(long address, int ptr, short value) {
		value = RC.getInstance().isLittleEndian ? value : Swapper.swapShort(value);
		UnsafeHolder.UNSAFE.putShort(address + offset + ptr, value);
	}
	
	public void setShort(byte[] blob, int ptr, short value) {
		value = RC.getInstance().isLittleEndian ? value : Swapper.swapShort(value);
		UnsafeHolder.UNSAFE.putShort(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset, value);
	}
	
	public int getFixedSize() {
		return fixedSize;
	}
	
	public int getInitCapacity() {
		return 0;
	}
}
