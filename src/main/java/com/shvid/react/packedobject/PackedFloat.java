package com.shvid.react.packedobject;

import com.shvid.react.RC;
import com.shvid.react.UnsafeHolder;

public final class PackedFloat extends FixedPackedClass {

	final float defaultValue;
	
	public PackedFloat(long offset) {
		this(offset, PackedConstants.FLOAT_SIZE);
	}
	
	public PackedFloat(long offset, float defaultValue) {
		super(offset);
		this.defaultValue = defaultValue;
	}

	public void format(byte[] blob, long ptr) {
		setFloat(blob, ptr, defaultValue);
	}
	
	public void format(long address, long ptr) {
		setFloat(address, ptr, defaultValue);
	}
	
	public float getFloat(byte[] blob, long ptr) {
		float value = UnsafeHolder.UNSAFE.getFloat(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapFloat(value);
	}
	
	public float getFloat(long address, long ptr) {
		float value = UnsafeHolder.UNSAFE.getFloat(address + offset + ptr);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapFloat(value);
	}
	
	public void setFloat(byte[] blob, long ptr, float value) {
		value = RC.getInstance().isLittleEndian ? value : Swapper.swapFloat(value);
		UnsafeHolder.UNSAFE.putFloat(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset, value);
	}
	
	public void setFloat(long address, long ptr, float value) {
		value = RC.getInstance().isLittleEndian ? value : Swapper.swapFloat(value);
		UnsafeHolder.UNSAFE.putFloat(address + offset + ptr, value);
	}
	
	public int getTypeId() {
		return TypeRegistry.FLOAT_ID;
	}
	
	public int getFixedSize() {
		return PackedConstants.FLOAT_SIZE;
	}
	
	public int getInitCapacity() {
		return 0;
	}
}
