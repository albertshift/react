package com.shvid.react.packedobject;

import java.nio.ByteBuffer;

import com.shvid.react.UnsafeHolder;

public final class PackedObjectMemory {

	static final PackedHeader HEADER = new PackedHeader();
	static final PackedLong MEMORY_SIZE = new PackedLong(0);
	
	public static byte[] newHeapInstance(PackedObject obj, int thresholdCapacity) {
		int requestCapacity = (int) PackedHeader.fixedOffset() + obj.getFixedSize() + obj.getInitCapacity() + thresholdCapacity;
		byte[] blob = HeapMemoryManager.allocateMemory(requestCapacity);
		HEADER.format(blob, 0, obj.getFixedSize());
		return blob;
	}
	
	public static long newInstance(PackedObject obj, long thresholdCapacity) {
		long requestCapacity = PackedHeader.fixedOffset() + obj.getFixedSize() + obj.getInitCapacity() + thresholdCapacity;
		long address = AddressMemoryManager.allocateMemory(requestCapacity);
		HEADER.format(address, 0, obj.getFixedSize());
		return address;
	}
	
	public static void incrementTrash(Object address, int addTrash) {
		HEADER.addTrash(address, addTrash);
	}
	
	public static long newMemory(Object address, int capacity) throws PackedObjectOverflowException {
		long endOffset = HEADER.getEndOffset(address);
		if (endOffset + capacity > getMemorySize(address)) {
			throw new PackedObjectOverflowException();
		}
		HEADER.setEndOffset(address, endOffset + capacity);
		return endOffset;
	}
	
	public static long copyMemory(byte[] src, long ptr, int length, byte[] des) throws PackedObjectOverflowException {
		long desPtr = newMemory(des, length);
		UnsafeHolder.UNSAFE.copyMemory(src, ptr + UnsafeHolder.byteArrayBaseOffset, des, desPtr + UnsafeHolder.byteArrayBaseOffset, length);
		return desPtr;
	}
	
	public static long copyMemory(long src, long ptr, int length, long des) throws PackedObjectOverflowException {
		long desPtr = newMemory(des, length);
		UnsafeHolder.UNSAFE.copyMemory(src, ptr, des, desPtr, length);
		return desPtr;
	}

	private static long getMemorySize(Object address) {
		if (address instanceof byte[]) {
			byte[] blob = (byte[]) address;
			return blob.length;
		}
		else if (address instanceof Long) {
			long allocatedAddress = (Long) address - MEMORY_SIZE.getFixedSize();
			return MEMORY_SIZE.getLong(allocatedAddress, 0);
		}
		else if (address instanceof ByteBuffer) {
			ByteBuffer bb = (ByteBuffer) address;
			return bb.capacity();
		}
		else {
			throw new IllegalArgumentException("unknown object " + address);
		}
	}
	
	public static final class ByteBufferMemoryManager {

		public static ByteBuffer allocateMemory(long requestedSize) {
			if (requestedSize < 0 || requestedSize > Integer.MAX_VALUE) {
				throw new IllegalArgumentException("size = " + requestedSize);
			}
			return ByteBuffer.allocate((int) requestedSize);
		}

		public static ByteBuffer allocateDirectMemory(long requestedSize) {
			if (requestedSize < 0 || requestedSize > Integer.MAX_VALUE) {
				throw new IllegalArgumentException("size = " + requestedSize);
			}
			return ByteBuffer.allocateDirect((int) requestedSize);
		}

		public static long getMemorySize(ByteBuffer bb) {
			return bb.capacity();
		}
		
	}
	
	public static final class HeapMemoryManager {

		public static byte[] allocateMemory(long requestedSize) {
			if (requestedSize < 0 || requestedSize > Integer.MAX_VALUE) {
				throw new IllegalArgumentException("size = " + requestedSize);
			}
			return new byte[(int) requestedSize];
		}

		public static long getMemorySize(byte[] blob) {
			return blob.length;
		}
		
	}

	public static final class AddressMemoryManager {

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
}
