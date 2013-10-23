package com.shvid.react.packedobject;

import com.shvid.react.RC;
import com.shvid.react.Swapper;
import com.shvid.react.UnsafeHolder;

public final class PackedFloat implements PackedObject {

	final long offset;
	final float defaultValue;
	public final static int fixedSize = 4;
	
	public PackedFloat(long offset) {
		this(offset, 0.0f);
	}
	
	public PackedFloat(long offset, float defaultValue) {
		this.offset = offset;
		this.defaultValue = defaultValue;
	}

	public void format(byte[] blob) {
		setFloat(blob, 0, defaultValue);
	}
	
	public void format(long address) {
		setFloat(address, 0, defaultValue);
	}
	
	public float getFloat(byte[] blob, int ptr) {
		float value = UnsafeHolder.UNSAFE.getFloat(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapFloat(value);
	}
	
	public float getFloat(long address, int ptr) {
		float value = UnsafeHolder.UNSAFE.getFloat(address + offset + ptr);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapFloat(value);
	}
	
	public void setFloat(byte[] blob, int ptr, float value) {
		value = RC.getInstance().isLittleEndian ? value : Swapper.swapFloat(value);
		UnsafeHolder.UNSAFE.putFloat(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset, value);
	}
	
	public void setFloat(long address, int ptr, float value) {
		value = RC.getInstance().isLittleEndian ? value : Swapper.swapFloat(value);
		UnsafeHolder.UNSAFE.putFloat(address + offset + ptr, value);
	}
	
	public int getFixedSize() {
		return fixedSize;
	}
	
	public int getInitCapacity() {
		return 0;
	}
}
