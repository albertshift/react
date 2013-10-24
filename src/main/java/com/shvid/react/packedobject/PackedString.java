package com.shvid.react.packedobject;

/**
 * 
 * Stores variable offset in memory to the string plus size plus capacity
 * 
 * @author ashvid
 *
 */

public final class PackedString implements PackedClass {
	
	final PackedPtr data;
	final int initCapacity;
	
	public PackedString(long offset) {
		this(offset, 0);
	}
	
	public PackedString(long offset, int initCapacity) {
		data = new PackedPtr(offset);
		this.initCapacity = initCapacity;
	}
	
	public void format(byte[] blob, long ptr) {
		if (initCapacity > 0) {
			long dataPtr = PackedFactory.allocate(blob, initCapacity + PackedData.getCapacitySize());
			PackedData.setCapacity(blob, dataPtr, initCapacity);
			PackedData.setByte(blob, dataPtr + PackedData.getCapacitySize(), 0, initCapacity, (byte)0);
			data.setPtr(blob, ptr, dataPtr);
		}
		else {
			data.setPtr(blob, ptr, PackedPtr.NULL);
		}
	}
	
	public void format(long address, long ptr) {
		if (initCapacity > 0) {
			long dataPtr = PackedFactory.allocate(address, initCapacity + PackedData.getCapacitySize());
			PackedData.setCapacity(address, dataPtr, initCapacity);
			PackedData.setByte(address, dataPtr + PackedData.getCapacitySize(), 0, initCapacity, (byte)0);
			data.setPtr(address, ptr, dataPtr);
		}	
		else {
			data.setPtr(address, ptr, PackedPtr.NULL);
		}
	}

	public void setString(byte[] blob, long ptr, CharSequence value) {
		
	}
	
	public void setString(long address, long ptr, CharSequence value) {
		
	}
	
	
	
	@Override
	public int getFixedSize() {
		return data.getFixedSize();
	}
	
	public int getInitCapacity() {
		return initCapacity;
	}
}
