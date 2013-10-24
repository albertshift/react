package com.shvid.react.packedobject;

import com.shvid.react.RC;
import com.shvid.react.UnsafeHolder;

public final class PackedPtr implements PackedClass {
	
	public final static int NULL = 0;

	final long offset;
	
	public PackedPtr(long offset) {
		this.offset = offset;
	}

	public void format(byte[] blob, long ptr) {
		setPtr(blob, ptr, NULL);
	}

	@Override
	public void format(long address, long ptr) {
		setPtr(address, ptr, NULL);
	}

	public long getPtr(byte[] blob, long ptr) {
		if (RC.getInstance().ptr64) {
			long value = UnsafeHolder.UNSAFE.getLong(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset);
			return RC.getInstance().isLittleEndian ? value : Swapper.swapLong(value);
		}
		else {
			int value = UnsafeHolder.UNSAFE.getInt(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset);
			return RC.getInstance().isLittleEndian ? value : Swapper.swapInt(value);
		}
	}
	
	public long getPtr(long address, long ptr) {
		if (RC.getInstance().ptr64) {
			long value = UnsafeHolder.UNSAFE.getLong(address + offset + ptr);
			return RC.getInstance().isLittleEndian ? value : Swapper.swapLong(value);
		}
		else {
			int value = UnsafeHolder.UNSAFE.getInt(address + offset + ptr);
			return RC.getInstance().isLittleEndian ? value : Swapper.swapInt(value);
		}
	}
	
	public void setPtr(byte[] blob, long ptr, long value) {
		if (RC.getInstance().ptr64) {
			value = RC.getInstance().isLittleEndian ? value : Swapper.swapLong(value);
			UnsafeHolder.UNSAFE.putLong(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset, value);
		}
		else {
			int ivalue = (int) value;
			ivalue = RC.getInstance().isLittleEndian ? ivalue : Swapper.swapInt(ivalue);
			UnsafeHolder.UNSAFE.putInt(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset, ivalue);
		}
	}
	
	public void setPtr(long address, long ptr, long value) {
		if (RC.getInstance().ptr64) {
			value = RC.getInstance().isLittleEndian ? value : Swapper.swapLong(value);
			UnsafeHolder.UNSAFE.putLong(address + offset + ptr, value);
		}
		else {
			int ivalue = (int) value;
			ivalue = RC.getInstance().isLittleEndian ? ivalue : Swapper.swapInt(ivalue);
			UnsafeHolder.UNSAFE.putInt(address + offset + ptr, ivalue);
		}
	}
	
	@Override
	public int getFixedSize() {
		return RC.getInstance().ptr64 ? PackedConstants.PTR64_SIZE : PackedConstants.PTR32_SIZE;
	}
	
	public int getInitCapacity() {
		return 0;
	}
}


