package com.shvid.react.packedobject;

import java.nio.ByteBuffer;

import com.shvid.react.RC;
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
	
	public double getDouble(byte[] blob, long ptr) {
		double value = UnsafeHolder.UNSAFE.getDouble(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapDouble(value);
	}
	
	public double getDouble(long address, long ptr) {
		double value = UnsafeHolder.UNSAFE.getDouble(address + offset + ptr);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapDouble(value);
	}

	public double getDouble(ByteBuffer bb, long ptr) {
		return bb.getDouble((int)(offset + ptr));
	}
	
	public void setDouble(byte[] blob, long ptr, double value) {
		value = RC.getInstance().isLittleEndian ? value : Swapper.swapDouble(value);
		UnsafeHolder.UNSAFE.putDouble(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset, value);
	}
	
	public void setDouble(long address, long ptr, double value) {
		value = RC.getInstance().isLittleEndian ? value : Swapper.swapDouble(value);
		UnsafeHolder.UNSAFE.putDouble(address + offset + ptr, value);
	}
	
	public void setDouble(ByteBuffer bb, long ptr, double value) {
		bb.putDouble((int)(offset + ptr), value);
	}	
	
	public int getTypeId() {
		return TypeRegistry.DOUBLE_ID;
	}
	
	public int getFixedSize() {
		return PackedConstants.DOUBLE_SIZE;
	}
	
	public int getInitCapacity() {
		return 0;
	}
}
