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

	static final PackedLong MEMORY_CAPACITY = new PackedLong(0);

	public enum HolderType {
		BYTE_ARRAY, MEMORY, HEAP_BUFFER, DIRECT_BUFFER;
	}

	public static class Header {

		final PackedInt objectTypeId;
		final PackedInt trashCounter;
		final PackedInt endOffset32;
		final PackedLong endOffset64;

		Header() {

			long offset = 0;
			objectTypeId = new PackedInt(offset);
			offset += objectTypeId.sizeOf();

			trashCounter = new PackedInt(offset);
			offset += trashCounter.sizeOf();

			endOffset32 = new PackedInt(offset);
			endOffset64 = new PackedLong(offset);

		}

		public void addTrash(Object address, long addon) {
			long trash = (long) trashCounter.getInt(address, 0);
			trash += addon;
			trashCounter.setInt(address, 0, (int) Math.min(Integer.MAX_VALUE, trash));
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
	
	public static Object allocate(HolderType type, long capacity) {
		switch (type) {
		case BYTE_ARRAY:
			return new byte[positiveInt(capacity, "size")];
		case MEMORY:
			long allocatedAddress = UnsafeUtil.UNSAFE.allocateMemory(capacity + MEMORY_CAPACITY.sizeOf());
			MEMORY_CAPACITY.setLong(allocatedAddress, 0, capacity);
			long address = allocatedAddress + MEMORY_CAPACITY.sizeOf();
			return address;
		case HEAP_BUFFER:
			return ByteBuffer.allocate(positiveInt(capacity, "size"));
		case DIRECT_BUFFER:
			return ByteBuffer.allocateDirect(positiveInt(capacity, "size"));

		}
		throw new IllegalArgumentException("unknown type " + type);
	}

	public static void format(Object address, PackedObject po) {
		int size = po.sizeOf();
		header.objectTypeId.setInt(address, 0, po.getTypeId());
		header.trashCounter.setInt(address, 0, 0);
		header.setEndOffset(address, header.sizeOf() + size);
		
		if (po.getOffset() != 0) {
			throw new IllegalArgumentException("offset must be 0, but " + po.getOffset());
		}
		po.format(address, getObjectOffset());
	}
	
	public static void formatArray(Object address, PackedObject elementPO, int length) {
		header.objectTypeId.setInt(address, 0, TypeRegistry.ARRAY_ID);
		header.trashCounter.setInt(address, 0, 0);
		header.setEndOffset(address, header.sizeOf() + Array.sizeOf(elementPO, length));
		
		TypeRegistry.ARRAY.format(address, getObjectOffset(), elementPO.getTypeId(), length);
	}
	
	public static void free(Object address) {
		if (address instanceof Long) {
			long allocatedAddress = (Long) address - MEMORY_CAPACITY.sizeOf();
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

	public static HolderType getHolderType(Object address) {
		if (address instanceof byte[]) {
			return HolderType.BYTE_ARRAY;
		} else if (address instanceof Long) {
			return HolderType.MEMORY;
		} else if (address instanceof ByteBuffer) {
			ByteBuffer bb = (ByteBuffer) address;
			return bb.isDirect() ? HolderType.DIRECT_BUFFER : HolderType.HEAP_BUFFER;
		} else {
			throw new IllegalArgumentException("unknown object " + address);
		}
	}
	
	public static long getCapacity(Object address) {
		if (address instanceof byte[]) {
			byte[] blob = (byte[]) address;
			return blob.length;
		} else if (address instanceof Long) {
			long allocatedAddress = (Long) address - MEMORY_CAPACITY.sizeOf();
			return MEMORY_CAPACITY.getLong(allocatedAddress, 0);
		} else if (address instanceof ByteBuffer) {
			ByteBuffer bb = (ByteBuffer) address;
			return bb.capacity();
		} else {
			throw new IllegalArgumentException("unknown object " + address);
		}
	}

	public static long getSize(Object address) {
		return header.getEndOffset(address);
	}
	
	public static int getTrashCounter(Object address) {
		return header.trashCounter.getInt(address, 0);
	}
	
	public static void gc(Object address, Object desAddress) {
		int typeId = header.objectTypeId.getInt(address, 0);
		header.objectTypeId.setInt(desAddress, 0, typeId);
		header.trashCounter.setInt(desAddress, 0, 0);
		
		long objectSize = getObjectSize(address, getObjectOffset(), typeId);
		header.setEndOffset(desAddress, getObjectOffset() + objectSize);
		
		PackedObject po = TypeRegistry.resolveType(typeId);
		po.copyTo(address, getObjectOffset(), desAddress, getObjectOffset());
	}
	
	public static long getObjectSize(Object address, long ptr, int typeId) {
		if (typeId == TypeRegistry.ARRAY_ID) {
			PackedObject elementPO = TypeRegistry.ARRAY.getElementType(address, ptr);
			int length = TypeRegistry.ARRAY.getLength(address, ptr);
			return Array.sizeOf(elementPO, length);
		}
		else {
			PackedObject po = TypeRegistry.resolveType(typeId);
			return po.sizeOf();
		}
	}
	
	public static long newInternalObject(Object address, long capacity) throws PackedObjectOverflowException {
		long endOffset = header.getEndOffset(address);
		if (endOffset + capacity > getCapacity(address)) {
			throw new PackedObjectOverflowException();
		}
		header.setEndOffset(address, endOffset + capacity);
		return endOffset;
	}
	
	public static void incrementTrash(Object address, long addTrash) {
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
