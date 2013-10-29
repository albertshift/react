package com.reactbase.packedobject;

import java.nio.ByteBuffer;

import com.shvid.react.UnsafeHolder;

public final class PackedObjectMemory {

	static final PackedHeader HEADER = new PackedHeader();
	static final PackedLong MEMORY_SIZE = new PackedLong(0);
	
	public static byte[] newHeapInstance(PackedObject obj, int thresholdCapacity) {
		int requestCapacity = (int) PackedHeader.fixedOffset() + obj.sizeOf() + thresholdCapacity;
		byte[] blob = HeapMemoryManager.allocateMemory(requestCapacity);
		HEADER.format(blob, 0, obj.sizeOf());
		return blob;
	}
	
	public static long newInstance(PackedObject obj, long thresholdCapacity) {
		long requestCapacity = PackedHeader.fixedOffset() + obj.sizeOf() + thresholdCapacity;
		long address = AddressMemoryManager.allocateMemory(requestCapacity);
		HEADER.format(address, 0, obj.sizeOf());
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

	public static void copyTo(Object address, long ptr, Object des, long desPtr, long length) {
		if (address instanceof byte[]) {
			copyToA((byte[]) address, positiveInt(ptr, "ptr"), des, desPtr, positiveInt(length, "length"));
		}
		else if (address instanceof Long) {
			copyToL((Long) address, ptr, des, desPtr, length);
		}
		else if (address instanceof ByteBuffer) {
			copyToB((ByteBuffer) address, positiveInt(ptr, "ptr"), des, desPtr, positiveInt(length, "length"));
		}
		else {
			throw new IllegalArgumentException("unknown src object " + address);
		}
	}

	private static void copyToA(byte[] blob, int ptr, Object des, long desPtr, int length) {
		if (des instanceof byte[]) {
			System.arraycopy(blob, ptr, des, positiveInt(desPtr, "desPtr"), length);
		}
		else if (des instanceof Long) {
			UnsafeHolder.copyMemory(blob, ptr + UnsafeHolder.byteArrayBaseOffset, null, (Long) des + desPtr, length);
		}
		else if (des instanceof ByteBuffer) {
			ByteBuffer desBB = (ByteBuffer) des;
			desBB.position(positiveInt(desPtr, "desPtr"));
			desBB.put(blob, ptr, length);
		}
		else {
			throw new IllegalArgumentException("unknown des object " + des);
		}
	}

	private static void copyToL(long address, long ptr, Object des, long desPtr, long length) {
		if (des instanceof byte[]) {
			UnsafeHolder.copyMemory(null, address + ptr, des, desPtr + UnsafeHolder.byteArrayBaseOffset, length);
		}
		else if (des instanceof Long) {
			UnsafeHolder.copyMemory(address + ptr, (Long)des + desPtr, length);
		}
		else if (des instanceof ByteBuffer) {
			ByteBuffer desBB = (ByteBuffer) des;
			int desPtrInt = positiveInt(desPtr, "desPtr");
			int lengthInt = positiveInt(length, "length");
			if (desBB.hasArray()) {
				desBB.position(desPtrInt + lengthInt);
				UnsafeHolder.copyMemory(null, address + ptr, desBB.array(), desPtr + desBB.arrayOffset() + UnsafeHolder.byteArrayBaseOffset, length);
			}
			else {
				desBB.position(desPtrInt);
				for (int i = 0; i != length; ++i) {
					desBB.put(UnsafeHolder.UNSAFE.getByte(address + ptr + i));
				}
			}
		}
		else {
			throw new IllegalArgumentException("unknown des object " + des);
		}
	}

	private static void copyToB(ByteBuffer bb, long ptr, Object des, long desPtr, int length) {
		if (des instanceof byte[]) {
			bb.position(positiveInt(ptr, "ptr"));
			bb.get((byte[])des, positiveInt(desPtr, "desPtr"), length);
		}
		else if (des instanceof Long) {
			if (bb.hasArray()) {
				UnsafeHolder.copyMemory(bb.array(), ptr + bb.arrayOffset() + UnsafeHolder.byteArrayBaseOffset, null, (Long)des + desPtr, length);
			}
			else {
				int ptrInt = positiveInt(ptr, "ptr");
				for (int i = 0; i != length; ++i) {
					UnsafeHolder.UNSAFE.putByte((Long) des + desPtr, bb.get(i + ptrInt));
				}
			}
		}
		else if (des instanceof ByteBuffer) {
			ByteBuffer desBB = (ByteBuffer) des;
			
			int ptrInt = positiveInt(ptr, "ptr");
			int saveLimit = bb.limit();
		
			bb.position(ptrInt);
			bb.limit(ptrInt + length);
			
			desBB.position(positiveInt(desPtr, "desPtr"));
			desBB.put(bb);
			
			bb.limit(saveLimit);
		}
		else {
			throw new IllegalArgumentException("unknown des object " + des);
		}
	}
	
	public static long getMemorySize(Object address) {
		if (address instanceof byte[]) {
			byte[] blob = (byte[]) address;
			return blob.length;
		}
		else if (address instanceof Long) {
			long allocatedAddress = (Long) address - MEMORY_SIZE.sizeOf();
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
	
	private static int positiveInt(long value, String name) {
		if (value < 0 || value > Integer.MAX_VALUE) {
			throw new IllegalArgumentException(name + "=" + value);
		}
		return (int) value;
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
			long allocatedAddress = UnsafeHolder.UNSAFE.allocateMemory(requestedSize + MEMORY_SIZE.sizeOf());
			MEMORY_SIZE.setLong(allocatedAddress, 0, requestedSize);
			long address = allocatedAddress + MEMORY_SIZE.sizeOf();
			return address;
		}
		
		public static void freeMemory(long address) {
			long allocatedAddress = address - MEMORY_SIZE.sizeOf();
			UnsafeHolder.UNSAFE.freeMemory(allocatedAddress);
		}

		public static long getMemorySize(long address) {
			return MEMORY_SIZE.getLong(address, 0);
		}
		
		
	}
}
