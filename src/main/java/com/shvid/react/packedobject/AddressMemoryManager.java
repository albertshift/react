package com.shvid.react.packedobject;

import com.shvid.react.UnsafeHolder;

public final class AddressMemoryManager {

	static final PackedLong MEMORY_SIZE = new PackedLong(0);
	
	public static long allocateMemory(long requestedSize) {
		long allocatedAddress = UnsafeHolder.UNSAFE.allocateMemory(requestedSize + MEMORY_SIZE.getFixedSize());
		MEMORY_SIZE.setLong(allocatedAddress, 0, requestedSize);
		long address = allocatedAddress + MEMORY_SIZE.getFixedSize();
		return address;
	}
	
	public static void freeMemory(long address) {
		long allocatedAddress = address - MEMORY_SIZE.getFixedSize();
		UnsafeHolder.UNSAFE.freeMemory(allocatedAddress);
	}

	public static long getMemorySize(long address) {
		return MEMORY_SIZE.getLong(address, 0);
	}
	
	
}
