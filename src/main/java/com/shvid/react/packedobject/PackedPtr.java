package com.shvid.react.packedobject;

import com.shvid.react.RC;

/**
 * Stores pointer to the data in the PacketObject
 * 
 * Fixed {
 * 
 *    ptr -> (reference can be 32 bit unsigned int or 64 bit to address blobs more then 4Gb)
 * 
 * }
 * PackedHeap {
 * 
 *   [data]
 * 
 * }
 * 
 * 
 * @author ashvid
 *
 */

public final class PackedPtr implements PackedClass {

	public final static int NULL = 0;

	final long offset;

	final PackedInt PTR_32;
	final PackedLong PTR_64;
		
	public PackedPtr(long offset) {
		this.offset = offset;
		this.PTR_32 = new PackedInt(offset);
		this.PTR_64 = new PackedLong(offset);
	}

	public void format(byte[] blob, long ptr) {
		setNull(blob, ptr);
	}

	@Override
	public void format(long address, long ptr) {
		setNull(address, ptr);
	}
	
	public boolean isNull(byte[] blob, long ptr) {
		long ref = getPtr(blob, ptr);
		return ref == NULL;
	}
	
	public boolean isNull(long address, long ptr) {
		long ref = getPtr(address, ptr);
		return ref == NULL;
	}
	
	public void setNull(byte[] blob, long ptr) {
		setPtr(blob, ptr, NULL);
	}
	
	public void setNull(long address, long ptr) {
		setPtr(address, ptr, NULL);
	}
		
	public long getPtr(byte[] blob, long ptr) {
		return RC.getInstance().ptr64 ? PTR_64.getLong(blob, ptr) : UnsignedInt.toLong( PTR_32.getInt(blob, ptr) );
	}
	
	public long getPtr(long address, long ptr) {
		return RC.getInstance().ptr64 ? PTR_64.getLong(address, ptr) : UnsignedInt.toLong( PTR_32.getInt(address, ptr) );
	}
	
	public void setPtr(byte[] blob, long ptr, long ref) {
		if (RC.getInstance().ptr64) {
			PTR_64.setLong(blob, ptr, ref);
		}
		else {
			PTR_32.setInt(blob, ptr, UnsignedInt.fromLong(ref) );
		}
	}
	
	public void setPtr(long address, long ptr, long ref) {
		if (RC.getInstance().ptr64) {
			PTR_64.setLong(address, ptr, ref);
		}
		else {
			PTR_32.setInt(address, ptr, UnsignedInt.fromLong(ref) );
		}
	}
	
	@Override
	public void copyTo(byte[] blob, long ptr, byte[] des, long desPtr) {
		throw new IllegalAccessError("copy data by value");
	}

	@Override
	public void copyTo(long address, long ptr, long des, long desPtr) {
		throw new IllegalAccessError("copy data by value");
	}
	
	@Override
	public int getFixedSize() {
		return RC.getInstance().ptr64 ? PackedConstants.PTR64_SIZE : PackedConstants.PTR32_SIZE;
	}
	
	public int getInitCapacity() {
		throw new IllegalAccessError("ask value of preffered size");
	}
	
	
}
