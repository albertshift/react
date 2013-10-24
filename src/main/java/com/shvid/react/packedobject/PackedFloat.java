package com.shvid.react.packedobject;

import com.shvid.react.RC;
import com.shvid.react.Swapper;
import com.shvid.react.UnsafeHolder;

public final class PackedFloat implements PackedClass {

	final long offset;
	final float defaultValue;
	
	public PackedFloat(long offset) {
		this(offset, PackedConstants.FLOAT_SIZE);
	}
	
	public PackedFloat(long offset, float defaultValue) {
		this.offset = offset;
		this.defaultValue = defaultValue;
	}

	public void format(byte[] blob, long ptr) {
		setFloat(blob, ptr, defaultValue);
	}
	
	public void format(long address, long ptr) {
		setFloat(address, ptr, defaultValue);
	}
	
	public float getFloat(HeapPackedObject<?> po) {
		return getFloat(po.blob, po.ptr);
	}	
	
	public float getFloat(byte[] blob, long ptr) {
		float value = UnsafeHolder.UNSAFE.getFloat(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapFloat(value);
	}
	
	public float getFloat(AddressPackedObject<?> po) {
		return getFloat(po.address, po.ptr);
	}	
	
	public float getFloat(long address, long ptr) {
		float value = UnsafeHolder.UNSAFE.getFloat(address + offset + ptr);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapFloat(value);
	}
	
	public void setFloat(HeapPackedObject<?> po, float value) {
		setFloat(po.blob, po.ptr, value);
	}
	
	public void setFloat(byte[] blob, long ptr, float value) {
		value = RC.getInstance().isLittleEndian ? value : Swapper.swapFloat(value);
		UnsafeHolder.UNSAFE.putFloat(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset, value);
	}
	
	public void setFloat(AddressPackedObject<?> po, float value) {
		setFloat(po.address, po.ptr, value);
	}
	
	public void setFloat(long address, long ptr, float value) {
		value = RC.getInstance().isLittleEndian ? value : Swapper.swapFloat(value);
		UnsafeHolder.UNSAFE.putFloat(address + offset + ptr, value);
	}
	
	public int getFixedSize() {
		return PackedConstants.FLOAT_SIZE;
	}
	
	public int getInitCapacity() {
		return 0;
	}
}
