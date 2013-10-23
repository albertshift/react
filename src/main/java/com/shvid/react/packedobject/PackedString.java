package com.shvid.react.packedobject;

/**
 * 
 * Stores variable offset in memory to the string plus size plus capacity
 * 
 * @author ashvid
 *
 */

public final class PackedString implements PackedObject {
	
	final PackedPtr data;
	final int initCapacity;
	
	public final static int fixedSize = PackedPtr.fixedSize;
	
	public PackedString(long offset, int initCapacity) {
		data = new PackedPtr(offset);
		this.initCapacity = initCapacity;
	}
	
	public void format(byte[] blob) {
		if (initCapacity > 0) {
			int ptr = PackedFactory.allocate(blob, initCapacity + PackedData.getCapacitySize());
			PackedData.setCapacity(blob, ptr, initCapacity);
			PackedData.setByte(blob, ptr + PackedData.getCapacitySize(), 0, initCapacity, (byte)0);
			data.setPtr(blob, ptr);
		}
		else {
			data.setPtr(blob, PackedPtr.NULL);
		}
	}
	
	public void format(long address) {
		if (initCapacity > 0) {
			int ptr = PackedFactory.allocate(address, initCapacity + PackedData.getCapacitySize());
			PackedData.setCapacity(address, ptr, initCapacity);
			PackedData.setByte(address, ptr + PackedData.getCapacitySize(), 0, initCapacity, (byte)0);
			data.setPtr(address, ptr);
		}	
		else {
			data.setPtr(address, PackedPtr.NULL);
		}
	}

	@Override
	public int getFixedSize() {
		return fixedSize;
	}
	
	public int getInitCapacity() {
		return initCapacity;
	}
}
