package com.shvid.react.packedobject;

import com.shvid.react.UnsafeHolder;

public final class PackedFactory {

	static final PackedLong memCapacity = new PackedLong(0);
	public static final PackedHeader header = new PackedHeader();
	
	public static byte[] newHeapInstance(PackedClass obj) {
		int memSize = PackedHeader.objBaseOffset() + obj.getFixedSize() + obj.getInitCapacity();
		byte[] blob = new byte[memSize];
		
		header.freeOffset.setPtr(blob, 0, obj.getFixedSize());
		header.totalFixedSize.setInt(blob, 0, obj.getFixedSize());

		obj.format(blob, 0);
		return blob;
	}
	
	public static long newInstance(PackedClass obj) {
		long memSize = PackedHeader.objBaseOffset() + obj.getFixedSize() + obj.getInitCapacity();
		long address = UnsafeHolder.UNSAFE.allocateMemory(memSize);
		memCapacity.setLong(address, 0, memSize);
		address += memCapacity.getFixedSize();
		header.freeOffset.setPtr(address, 0, obj.getFixedSize());
		header.totalFixedSize.setInt(address, 0, obj.getFixedSize());
		obj.format(address, 0);
		return address;
	}

	public static void free(long address) {
		address -= memCapacity.getFixedSize();
		UnsafeHolder.UNSAFE.freeMemory(address);
	}
	
	public static long allocate(byte[] blob, int capacity) {
		long freePtr = header.freeOffset.getPtr(blob, 0);
		if (freePtr + capacity > blob.length) {
			throw new PackedObjectOverflowException();
		}
		header.freeOffset.setPtr(blob, 0, freePtr + capacity);
		return freePtr;
	}
	
	public static long allocate(long address, int capacity) {
		long memSize = memCapacity.getLong(address, -memCapacity.getFixedSize());
		long freePtr = header.freeOffset.getPtr(address, 0);
		if (freePtr + capacity > memSize) {
			throw new PackedObjectOverflowException();
		}
		header.freeOffset.setPtr(address, 0, freePtr + capacity);
		return freePtr;
	}
	
}
