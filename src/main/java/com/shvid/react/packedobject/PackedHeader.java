package com.shvid.react.packedobject;

import com.shvid.react.RC;

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


public final class PackedHeader extends FixedPackedClass {

	final PackedInt totalFixedSize;
	final PackedInt trashSize;
	final PackedInt endOffset32;
	final PackedLong endOffset64;
	
	public PackedHeader() {
		super(0);
		
		long offset = 0;
		totalFixedSize = new PackedInt(offset);
		offset += totalFixedSize.getFixedSize();
		
		trashSize = new PackedInt(offset);
		offset += trashSize.getFixedSize();
		
		endOffset32 = new PackedInt(offset);
		endOffset64 = new PackedLong(offset);

	}
	
	public void format(byte[] blob, long ptr) {
		format(blob, ptr, 0);
	}

	public void format(long address, long ptr) {
		format(address, ptr, 0);
	}

	public void format(byte[] blob, long ptr, int objFixedSize) {
		totalFixedSize.setInt(blob, ptr, objFixedSize);
		trashSize.setInt(blob, ptr, 0);
		if (RC.getInstance().ptr64) {
			endOffset64.setLong(blob, ptr, fixedOffset() + objFixedSize);
		}
		else {
			endOffset32.setInt(blob, ptr, (int)fixedOffset() + objFixedSize); 
		}
	}
	
	public void format(long address, long ptr, int objFixedSize) {
		totalFixedSize.setInt(address, ptr, objFixedSize);
		trashSize.setInt(address, ptr, 0);
		if (RC.getInstance().ptr64) {
			endOffset64.setLong(address, ptr, fixedOffset() + objFixedSize);
		}
		else {
			endOffset32.setInt(address, ptr, (int)fixedOffset() + objFixedSize); 
		}
	}
	
	public int getFixedSize() {
		return (int) fixedOffset();
	}

	public int getInitCapacity() {
		return 0;
	}
	
	public void addTrash(byte[] blob, int addon) {
		int trash = trashSize.getInt(blob, 0);
		trashSize.setInt(blob, 0, trash + addon);
	}
	
	public void addTrash(long address, int addon) {
		int trash = trashSize.getInt(address, 0);
		trashSize.setInt(address, 0, trash + addon);
	}
	
	public long getEndOffset(byte[] blob) {
		return RC.getInstance().ptr64 ? endOffset64.getLong(blob, 0) : endOffset32.getInt(blob, 0);
	}
	
	public long getEndOffset(long address) {
		return RC.getInstance().ptr64 ? endOffset64.getLong(address, 0) : endOffset32.getInt(address, 0);
	}
	
	public void setEndOffset(byte[] blob, long endOffset) {
		if (RC.getInstance().ptr64) {
			endOffset64.setLong(blob, 0, endOffset);
		}
		else {
			endOffset32.setInt(blob, 0, (int) endOffset);
		}
	}
	
	public void setEndOffset(long address, long endOffset) {
		if (RC.getInstance().ptr64) {
			endOffset64.setLong(address, 0, endOffset);
		}
		else {
			endOffset32.setInt(address, 0, (int) endOffset);
		}
	}
	
	public static long fixedOffset() {
		return (RC.getInstance().ptr64 ? PackedConstants.PTR64_SIZE : PackedConstants.PTR32_SIZE) + PackedConstants.INT_SIZE + PackedConstants.INT_SIZE;
	}
	
}
