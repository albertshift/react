package com.shvid.react.packedobject;

import com.shvid.react.RC;
import com.shvid.react.Swapper;
import com.shvid.react.UnsafeHolder;

public final class PackedDouble extends FixedPackedClass {

	final double defaultValue;
	
	public PackedDouble(long offset) {
		this(offset, PackedConstants.DEFAULT_DOUBLE);
	}
	
	public PackedDouble(long offset, double defaultValue) {
		super(offset);
		this.defaultValue = defaultValue;
	}
	
	public void format(byte[] blob, long ptr) {
		setDouble(blob, ptr, defaultValue);
	}
	
	public void format(long address, long ptr) {
		setDouble(address, ptr, defaultValue);
	}
	
	public double getDouble(HeapPackedObject<?> po) {
		return getDouble(po.blob, po.ptr);
	}	
	
	public double getDouble(byte[] blob, long ptr) {
		double value = UnsafeHolder.UNSAFE.getDouble(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapDouble(value);
	}
	
	public double getDouble(AddressPackedObject<?> po) {
		return getDouble(po.address, po.ptr);
	}	
	
	public double getDouble(long address, long ptr) {
		double value = UnsafeHolder.UNSAFE.getDouble(address + offset + ptr);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapDouble(value);
	}
	
	public void setDouble(HeapPackedObject<?> po, double value) {
		setDouble(po.blob, po.ptr, value);
	}
	
	public void setDouble(byte[] blob, long ptr, double value) {
		value = RC.getInstance().isLittleEndian ? value : Swapper.swapDouble(value);
		UnsafeHolder.UNSAFE.putDouble(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset, value);
	}
	
	public void setDouble(AddressPackedObject<?> po, double value) {
		setDouble(po.address, po.ptr, value);
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
