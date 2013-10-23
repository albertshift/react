package com.shvid.react.packedobject;

import com.shvid.react.RC;
import com.shvid.react.Swapper;
import com.shvid.react.UnsafeHolder;

public final class PackedPtr implements PackedObject {
	
	public final static int NULL = 0;

	final long offset;
	
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

	public long getPtr(byte[] blob) {
		if (RC.getInstance().ptr64) {
			long value = UnsafeHolder.UNSAFE.getLong(blob, offset + UnsafeHolder.byteArrayBaseOffset);
			return RC.getInstance().isLittleEndian ? value : Swapper.swapLong(value);
		}
		else {
			int value = UnsafeHolder.UNSAFE.getInt(blob, offset + UnsafeHolder.byteArrayBaseOffset);
			return RC.getInstance().isLittleEndian ? value : Swapper.swapInt(value);
		}
	}
	
	public long getPtr(long address) {
		if (RC.getInstance().ptr64) {
			long value = UnsafeHolder.UNSAFE.getLong(address + offset);
			return RC.getInstance().isLittleEndian ? value : Swapper.swapLong(value);
		}
		else {
			int value = UnsafeHolder.UNSAFE.getInt(address + offset);
			return RC.getInstance().isLittleEndian ? value : Swapper.swapInt(value);
		}
	}
	
	public void setPtr(byte[] blob, long ptr) {
		if (RC.getInstance().ptr64) {
			ptr = RC.getInstance().isLittleEndian ? ptr : Swapper.swapLong(ptr);
			UnsafeHolder.UNSAFE.putLong(blob, offset + UnsafeHolder.byteArrayBaseOffset, ptr);
		}
		else {
			int value = (int) ptr;
			value = RC.getInstance().isLittleEndian ? value : Swapper.swapInt(value);
			UnsafeHolder.UNSAFE.putInt(blob, offset + UnsafeHolder.byteArrayBaseOffset, value);
		}
	}
	
	public void setPtr(long address, long ptr) {
		if (RC.getInstance().ptr64) {
			ptr = RC.getInstance().isLittleEndian ? ptr : Swapper.swapLong(ptr);
			UnsafeHolder.UNSAFE.putLong(address + offset, ptr);
		}
		else {
			int value = (int) ptr;
			value = RC.getInstance().isLittleEndian ? value : Swapper.swapInt(value);
			UnsafeHolder.UNSAFE.putInt(address + offset, value);
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


