package com.reactbase.packedobject;

import java.nio.ByteBuffer;

import sun.nio.ch.DirectBuffer;
import sun.misc.Cleaner;

/**
 * 
 * Packet memory structure
 * 
 *       address         heapOffset  endOffset
 * memsize | [ header, fixed, | heap, | free  ]
 * 
 * heapOffset = head+fixed
 * 
 * @author ashvid
 *
 */


public final class Holder {

	static final PackedLong MEMORY_SIZE = new PackedLong(0);

	public enum HolderType {
		BYTE_ARRAY, MEMORY, HEAP_BUFFER, DIRECT_BUFFER;
	}

	public static class Header {

		final PackedInt objectSizeOf;
		final PackedInt trashCounter;
		final PackedInt endOffset32;
		final PackedLong endOffset64;

		Header() {

			long offset = 0;
			objectSizeOf = new PackedInt(offset);
			offset += objectSizeOf.sizeOf();

			trashCounter = new PackedInt(offset);
			offset += trashCounter.sizeOf();

			endOffset32 = new PackedInt(offset);
			endOffset64 = new PackedLong(offset);

		}

		public void addTrash(Object address, int addon) {
			int trash = trashCounter.getInt(address, 0);
			trashCounter.setInt(address, 0, trash + addon);
		}

		long getEndOffset(Object address) {
			return PackedObject.usePtr64 ? endOffset64.getLong(address, 0)
					: endOffset32.getInt(address, 0);
		}

		void setEndOffset(Object address, long endOffset) {
			if (PackedObject.usePtr64) {
				endOffset64.setLong(address, 0, endOffset);
			} else {
				endOffset32.setInt(address, 0, (int) endOffset);
			}
		}

		int sizeOf() {
			return (PackedObject.usePtr64 ? PrimitiveTypes.PTR64_SIZEOF
					: PrimitiveTypes.PTR32_SIZEOF)
					+ PrimitiveTypes.INT_SIZEOF
					+ PrimitiveTypes.INT_SIZEOF;

		}
	}

	private final static Header header = new Header();
	
	public static Object allocate(HolderType type, long size) {
		switch (type) {
		case BYTE_ARRAY:
			return new byte[positiveInt(size, "size")];
		case MEMORY:
			long allocatedAddress = UnsafeUtil.UNSAFE.allocateMemory(size + MEMORY_SIZE.sizeOf());
			MEMORY_SIZE.setLong(allocatedAddress, 0, size);
			long address = allocatedAddress + MEMORY_SIZE.sizeOf();
			return address;
		case HEAP_BUFFER:
			return ByteBuffer.allocate(positiveInt(size, "size"));
		case DIRECT_BUFFER:
			return ByteBuffer.allocateDirect(positiveInt(size, "size"));

		}
		throw new IllegalArgumentException("unknown type " + type);
	}

	public static void format(Object address, PackedObject po) {
		int size = po.sizeOf();
		header.objectSizeOf.setInt(address, 0, size);
		header.trashCounter.setInt(address, 0, 0);
		header.setEndOffset(address, size);
		if (po.getOffset() != header.sizeOf()) {
			throw new IllegalArgumentException("offset must be " + header.sizeOf());
		}
		po.format(address, 0);
	}
	
	public static void free(Object address) {
		if (address instanceof byte[]) {
			// do nothing, it is GC work
		}
		else if (address instanceof Long) {
			long allocatedAddress = (Long) address - MEMORY_SIZE.sizeOf();
			UnsafeUtil.UNSAFE.freeMemory(allocatedAddress);
		}
		else if (address instanceof ByteBuffer) {
			ByteBuffer bb = (ByteBuffer) address;
			if (bb.isDirect()) {
				Cleaner cleaner = ((DirectBuffer) bb).cleaner();
			    if (cleaner != null) { 
			    	cleaner.clean();
			    }
			}
		}
	}

	public static long getSize(Object address) {
		if (address instanceof byte[]) {
			byte[] blob = (byte[]) address;
			return blob.length;
		} else if (address instanceof Long) {
			long allocatedAddress = (Long) address - MEMORY_SIZE.sizeOf();
			return MEMORY_SIZE.getLong(allocatedAddress, 0);
		} else if (address instanceof ByteBuffer) {
			ByteBuffer bb = (ByteBuffer) address;
			return bb.capacity();
		} else {
			throw new IllegalArgumentException("unknown object " + address);
		}
	}

	public static long newInternalObject(Object address, int capacity) throws PackedObjectOverflowException {
		long endOffset = header.getEndOffset(address);
		if (endOffset + capacity > getSize(address)) {
			throw new PackedObjectOverflowException();
		}
		header.setEndOffset(address, endOffset + capacity);
		return endOffset;
	}
	
	public static void incrementTrash(Object address, int addTrash) {
		header.addTrash(address, addTrash);
	}
	
	public static long getObjectOffset() {
		return header.sizeOf();
	}
	
	public static int positiveInt(long value, String name) {
		if (value < 0 || value > Integer.MAX_VALUE) {
			throw new IllegalArgumentException(name + "=" + value);
		}
		return (int) value;
	}
}
