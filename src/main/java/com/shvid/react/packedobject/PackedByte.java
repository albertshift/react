package com.shvid.react.packedobject;

import java.nio.ByteBuffer;

import com.shvid.react.UnsafeHolder;

public final class PackedByte extends FixedPackedClass {

	final byte defaultValue;

	public PackedByte(long offset) {
		this(offset, PackedConstants.DEFAULT_BYTE);
	}

	public PackedByte(long offset, byte defaultValue) {
		super(offset);
		this.defaultValue = defaultValue;
	}
	
	public void format(byte[] blob, long ptr) {
		setByte(blob, ptr, defaultValue);
	}
	
	public void format(long address, long ptr) {
		setByte(address, ptr, defaultValue);
	}
	
	public byte getByte(byte[] blob, long ptr) {
		return UnsafeHolder.UNSAFE.getByte(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset);
	}
	
	public byte getByte(long address, long ptr) {
		return UnsafeHolder.UNSAFE.getByte(address + offset + ptr);
	}
	
	public byte getByte(ByteBuffer bb, long ptr) {
		return bb.get((int)(offset + ptr));
	}
	
	public void setByte(byte[] blob, long ptr, byte value) {
		UnsafeHolder.UNSAFE.putByte(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset, value);
	}	
	
	public void setByte(long address, long ptr, byte value) {
		UnsafeHolder.UNSAFE.putByte(address + offset + ptr, value);
	}
	
	public void setByte(ByteBuffer bb, long ptr, byte value) {
		bb.put((int)(offset + ptr), value);
	}
	
	public int getTypeId() {
		return TypeRegistry.BYTE_ID;
	}
	
	public int getFixedSize() {
		return PackedConstants.BYTE_SIZE;
	}
	
	public int getInitCapacity() {
		return 0;
	}
	
}
