package com.reactbase.packedobject;


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


public final class PackedHeader extends PackedObject {

	final PackedInt totalFixedSize;
	final PackedInt trashSize;
	final PackedInt endOffset32;
	final PackedLong endOffset64;
	
	public PackedHeader() {
		super(0);
		
		long offset = 0;
		totalFixedSize = new PackedInt(offset);
		offset += totalFixedSize.sizeOf();
		
		trashSize = new PackedInt(offset);
		offset += trashSize.sizeOf();
		
		endOffset32 = new PackedInt(offset);
		endOffset64 = new PackedLong(offset);

	}
	
	public void format(Object address, long ptr) {
		format(address, ptr, 0);
	}

	public void format(Object address, long ptr, int objFixedSize) {
		totalFixedSize.setInt(address, ptr, objFixedSize);
		trashSize.setInt(address, ptr, 0);
		if (ptr64) {
			endOffset64.setLong(address, ptr, fixedOffset() + objFixedSize);
		}
		else {
			endOffset32.setInt(address, ptr, (int)fixedOffset() + objFixedSize); 
		}
	}
	
	public int sizeOf() {
		return (int) fixedOffset();
	}

	public int getInitCapacity() {
		return 0;
	}
	
	@Override
	public int getTypeId() {
		return TypeRegistry.HEADER_ID;
	}
	
	public void addTrash(Object address, int addon) {
		int trash = trashSize.getInt(address, 0);
		trashSize.setInt(address, 0, trash + addon);
	}
	
	public long getEndOffset(Object address) {
		return ptr64 ? endOffset64.getLong(address, 0) : endOffset32.getInt(address, 0);
	}
	
	public void setEndOffset(Object address, long endOffset) {
		if (ptr64) {
			endOffset64.setLong(address, 0, endOffset);
		}
		else {
			endOffset32.setInt(address, 0, (int) endOffset);
		}
	}
	
	public static long fixedOffset() {
		return (ptr64 ? PrimitiveTypes.PTR64_SIZE : PrimitiveTypes.PTR32_SIZE) + PrimitiveTypes.INT_SIZEOF + PrimitiveTypes.INT_SIZEOF;
	}
	
}
