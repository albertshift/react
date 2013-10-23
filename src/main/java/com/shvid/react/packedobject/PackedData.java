package com.shvid.react.packedobject;

import com.shvid.react.RC;
import com.shvid.react.Swapper;
import com.shvid.react.UnsafeHolder;

public final class PackedData {

	public static int getCapacity(byte[] blob, int ptr) {
		int value = UnsafeHolder.UNSAFE.getInt(blob, (long) ptr + UnsafeHolder.byteArrayBaseOffset);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapInt(value);
	}
	
	public static void setCapacity(byte[] blob, int ptr, int capacity) {
		capacity = RC.getInstance().isLittleEndian ? capacity : Swapper.swapInt(capacity);
		UnsafeHolder.UNSAFE.putInt(blob, (long) ptr + UnsafeHolder.byteArrayBaseOffset, capacity);
	}
	
	public static int getCapacity(long address, int ptr) {
		int value = UnsafeHolder.UNSAFE.getInt(address + (long) ptr);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapInt(value);
	}
	
	public static void setCapacity(long address, int ptr, int capacity) {
		capacity = RC.getInstance().isLittleEndian ? capacity : Swapper.swapInt(capacity);
		UnsafeHolder.UNSAFE.putInt(address + (long) ptr, capacity);
	}
	
	public static byte getByte(byte[] blob, int ptr, int pos, int capacity) {
		if (pos < 0 || pos >= capacity) {
			throw new IndexOutOfBoundsException();
		}
		return UnsafeHolder.UNSAFE.getByte(blob, (long) ptr + UnsafeHolder.byteArrayBaseOffset + pos * UnsafeHolder.byteArrayIndexScale);
	}
	
	public static void setByte(byte[] blob, int ptr, int pos, int capacity, byte value) {
		if (pos < 0 || pos >= capacity) {
			throw new IndexOutOfBoundsException();
		}
		UnsafeHolder.UNSAFE.putByte(blob, (long) ptr + UnsafeHolder.byteArrayBaseOffset + pos * UnsafeHolder.byteArrayIndexScale, value);
	}
	
	public static byte getByte(long address, int ptr, int pos, int capacity) {
		if (pos < 0 || pos >= capacity) {
			throw new IndexOutOfBoundsException();
		}
		return UnsafeHolder.UNSAFE.getByte(address + (long) ptr + pos);
	}
	
	public static void setByte(long address, int ptr, int pos, int capacity, byte value) {
		if (pos < 0 || pos >= capacity) {
			throw new IndexOutOfBoundsException();
		}
		UnsafeHolder.UNSAFE.putByte(address + (long) ptr + pos, value);
	}
	
	
	public static int getCapacitySize() {
		return 4;
	}
	
}
