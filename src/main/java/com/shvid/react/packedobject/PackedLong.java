package com.shvid.react.packedobject;

import com.shvid.react.Swapper;
import com.shvid.react.RC;
import com.shvid.react.UnsafeHolder;

public final class PackedLong implements PackedClass {

	final long offset;
	final long defaultValue;
	
	public PackedLong(long offset) {
		this(offset, PackedConstants.DEFAULT_LONG);
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
	
	public long getLong(HeapPackedObject<?> po) {
		return getLong(po.blob, po.ptr);
	}	
	
	public long getLong(byte[] blob, long ptr) {
		long value = UnsafeHolder.UNSAFE.getLong(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapLong(value);
	}
	
	public long getLong(AddressPackedObject<?> po) {
		return getLong(po.address, po.ptr);
	}	
	
	public long getLong(long address, long ptr) {
		long value = UnsafeHolder.UNSAFE.getLong(address + offset + ptr);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapLong(value);
	}
	
	public void setLong(HeapPackedObject<?> po, long value) {
		setLong(po.blob, po.ptr, value);
	}
	
	public void setLong(byte[] blob, long ptr, long value) {
		value = RC.getInstance().isLittleEndian ? value : Swapper.swapLong(value);
		UnsafeHolder.UNSAFE.putLong(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset, value);
	}
	
	public void setLong(AddressPackedObject<?> po, long value) {
		setLong(po.address, po.ptr, value);
	}
	
	public void setLong(long address, long ptr, long value) {
		value = RC.getInstance().isLittleEndian ? value : Swapper.swapLong(value);
		UnsafeHolder.UNSAFE.putLong(address + offset + ptr, value);
	}
	
	public int getFixedSize() {
		return PackedConstants.LONG_SIZE;
	}
	
	public int getInitCapacity() {
		return 0;
	}
	
}
