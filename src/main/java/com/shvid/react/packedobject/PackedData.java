package com.shvid.react.packedobject;

import com.shvid.react.RC;
import com.shvid.react.Swapper;
import com.shvid.react.UnsafeHolder;

public final class PackedData {

	public static int getCapacity(byte[] blob, long ptr) {
		int value = UnsafeHolder.UNSAFE.getInt(blob, ptr + UnsafeHolder.byteArrayBaseOffset);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapInt(value);
	}
	
	public static void setCapacity(byte[] blob, long ptr, int capacity) {
		capacity = RC.getInstance().isLittleEndian ? capacity : Swapper.swapInt(capacity);
		UnsafeHolder.UNSAFE.putInt(blob, ptr + UnsafeHolder.byteArrayBaseOffset, capacity);
	}
	
	public static int getCapacity(long address, long ptr) {
		int value = UnsafeHolder.UNSAFE.getInt(address + ptr);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapInt(value);
	}
	
	public static void setCapacity(long address, long ptr, int capacity) {
		capacity = RC.getInstance().isLittleEndian ? capacity : Swapper.swapInt(capacity);
		UnsafeHolder.UNSAFE.putInt(address + ptr, capacity);
	}
	
	public static byte getByte(byte[] blob, long ptr, int pos, int capacity) {
		if (pos < 0 || pos >= capacity) {
			throw new IndexOutOfBoundsException();
		}
		return UnsafeHolder.UNSAFE.getByte(blob, ptr + UnsafeHolder.byteArrayBaseOffset + pos * UnsafeHolder.byteArrayIndexScale);
	}
	
	public static void setByte(byte[] blob, long ptr, int pos, int capacity, byte value) {
		if (pos < 0 || pos >= capacity) {
			throw new IndexOutOfBoundsException();
		}
		UnsafeHolder.UNSAFE.putByte(blob, ptr + UnsafeHolder.byteArrayBaseOffset + pos * UnsafeHolder.byteArrayIndexScale, value);
	}
	
	public static byte getByte(long address, long ptr, int pos, int capacity) {
		if (pos < 0 || pos >= capacity) {
			throw new IndexOutOfBoundsException();
		}
		return UnsafeHolder.UNSAFE.getByte(address + ptr + pos);
	}
	
	public static void setByte(long address, long ptr, int pos, int capacity, byte value) {
		if (pos < 0 || pos >= capacity) {
			throw new IndexOutOfBoundsException();
		}
		UnsafeHolder.UNSAFE.putByte(address + ptr + pos, value);
	}
	
	
	public static int getCapacitySize() {
		return 4;
	}
	
}
