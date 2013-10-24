package com.shvid.react.packedobject;

import com.shvid.react.RC;
import com.shvid.react.UnsafeHolder;

public final class PackedInt extends FixedPackedClass {

	final int defaultValue;
	
	public PackedInt(long offset) {
		this(offset, PackedConstants.DEFAULT_INT);
	}
	
	public PackedInt(long offset, int defaultValue) {
		super(offset);
		this.defaultValue = defaultValue;
	}

	public void format(byte[] blob, long ptr) {
		setInt(blob, ptr, defaultValue);
	}
	
	public void format(long address, long ptr) {
		setInt(address, ptr, defaultValue);
	}
	
	public int getInt(HeapPackedObject<?> po) {
		return getInt(po.blob, po.ptr);
	}	
	
	public int getInt(byte[] blob, long ptr) {
		int value = UnsafeHolder.UNSAFE.getInt(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapInt(value);
	}
	
	public int getInt(AddressPackedObject<?> po) {
		return getInt(po.address, po.ptr);
	}
	
	public int getInt(long address, long ptr) {
		int value = UnsafeHolder.UNSAFE.getInt(address + offset + ptr);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapInt(value);
	}
	
	public void setInt(HeapPackedObject<?> po, int value) {
		setInt(po.blob, po.ptr, value);
	}
	
	public void setInt(byte[] blob, long ptr, int value) {
		value = RC.getInstance().isLittleEndian ? value : Swapper.swapInt(value);
		UnsafeHolder.UNSAFE.putInt(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset, value);
	}
	
	public void setInt(AddressPackedObject<?> po, int value) {
		setInt(po.address, po.ptr, value);
	}
	
	public void setInt(long address, long ptr, int value) {
		value = RC.getInstance().isLittleEndian ? value : Swapper.swapInt(value);
		UnsafeHolder.UNSAFE.putInt(address + offset + ptr, value);
	}
	
	public int getFixedSize() {
		return PackedConstants.INT_SIZE;
	}
	
	public int getInitCapacity() {
		return 0;
	}
	
}
