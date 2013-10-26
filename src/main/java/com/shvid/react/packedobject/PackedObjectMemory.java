package com.shvid.react.packedobject;

import com.shvid.react.UnsafeHolder;

public final class PackedObjectMemory {

	static final PackedHeader HEADER = new PackedHeader();
	
	public static byte[] newHeapInstance(PackedClass obj, int thresholdCapacity) {
		int requestCapacity = (int) PackedHeader.fixedOffset() + obj.getFixedSize() + obj.getInitCapacity() + thresholdCapacity;
		byte[] blob = HeapMemoryManager.allocateMemory(requestCapacity);
		HEADER.format(blob, 0, obj.getFixedSize());
		return blob;
	}
	
	public static long newInstance(PackedClass obj, long thresholdCapacity) {
		long requestCapacity = PackedHeader.fixedOffset() + obj.getFixedSize() + obj.getInitCapacity() + thresholdCapacity;
		long address = AddressMemoryManager.allocateMemory(requestCapacity);
		HEADER.format(address, 0, obj.getFixedSize());
		return address;
	}
	
	public static void incrementTrash(byte[] blob, int addTrash) {
		HEADER.addTrash(blob, addTrash);
	}
	
	public static void incrementTrash(long address, int addTrash) {
		HEADER.addTrash(address, addTrash);
	}
	
	public static long newMemory(byte[] blob, int capacity) throws PackedObjectOverflowException {
		long endOffset = HEADER.getEndOffset(blob);
		System.out.println("endOffset" + endOffset);
		System.out.println("capacity" + capacity);
		System.out.println("blob" + blob.length);
		if (endOffset + capacity > HeapMemoryManager.getMemorySize(blob)) {
			throw new PackedObjectOverflowException();
		}
		HEADER.setEndOffset(blob, endOffset + capacity);
		return endOffset;
	}
	
	public static long copyMemory(byte[] src, long ptr, int length, byte[] des) throws PackedObjectOverflowException {
		long desPtr = newMemory(des, length);
		UnsafeHolder.UNSAFE.copyMemory(src, ptr + UnsafeHolder.byteArrayBaseOffset, des, desPtr + UnsafeHolder.byteArrayBaseOffset, length);
		return desPtr;
	}
	
	public static long newMemory(long address, int capacity) throws PackedObjectOverflowException {
		long endOffset = HEADER.getEndOffset(address);
		if (endOffset + capacity > AddressMemoryManager.getMemorySize(address)) {
			throw new PackedObjectOverflowException();
		}
		HEADER.setEndOffset(address, endOffset + capacity);
		return endOffset;
	}
	
	public static long copyMemory(long src, long ptr, int length, long des) throws PackedObjectOverflowException {
		long desPtr = newMemory(des, length);
		UnsafeHolder.UNSAFE.copyMemory(src, ptr, des, desPtr, length);
		return desPtr;
	}
}
