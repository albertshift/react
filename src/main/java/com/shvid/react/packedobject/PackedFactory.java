package com.shvid.react.packedobject;

import com.shvid.react.UnsafeHolder;

public final class PackedFactory {

	private static final PackedInt memCapacity = new PackedInt(0);
	public static final PackedHeader header = new PackedHeader();
	
	public static byte[] newHeapInstance(PackedObject obj) {
		int memSize = PackedHeader.fixedSize + obj.getFixedSize() + obj.getInitCapacity();
		byte[] blob = new byte[memSize];
		header.objTotalSize.setInt(blob, 0, obj.getFixedSize());
		header.objFixedSize.setInt(blob, 0, obj.getFixedSize());
		obj.format(blob);
		return blob;
	}
	
	public static long newInstance(PackedObject obj) {
		int memSize = PackedHeader.fixedSize + obj.getFixedSize() + obj.getInitCapacity();
		long address = UnsafeHolder.UNSAFE.allocateMemory(memSize);
		memCapacity.setInt(address, 0, memSize);
		address += memCapacity.getFixedSize();
		header.objTotalSize.setInt(address, 0, obj.getFixedSize());
		header.objFixedSize.setInt(address, 0, obj.getFixedSize());
		obj.format(address);
		return address;
	}

	public static void free(long address) {
		address -= memCapacity.getFixedSize();
		UnsafeHolder.UNSAFE.freeMemory(address);
	}
	
	public static int allocate(byte[] blob, int capacity) {
		int totalSize = header.objTotalSize.getInt(blob, 0);
		if (totalSize + capacity > blob.length) {
			throw new PackedObjectOverflowException();
		}
		header.objTotalSize.setInt(blob, 0, totalSize + capacity);
		return totalSize;
	}
	
	public static int allocate(long address, int capacity) {
		int memSize = memCapacity.getInt(address, -memCapacity.getFixedSize());
		int totalSize = header.objTotalSize.getInt(address, 0);
		if (totalSize + capacity > memSize) {
			throw new PackedObjectOverflowException();
		}
		header.objTotalSize.setInt(address, 0, totalSize + capacity);
		return totalSize;
	}
	
}
