package com.reactbase.packedobject;

import java.nio.ByteBuffer;

public final class PackedLong extends PackedObject {

	final long defaultValue;
	
	public PackedLong(long offset) {
		this(offset, PrimitiveTypes.DEFAULT_LONG);
	}
	
	public PackedLong(long offset, long defaultValue) {
		super(offset);
		this.defaultValue = defaultValue;
	}
	
	public void format(Object address, long ptr) {
		setLong(address, ptr, defaultValue);
	}

	public long getLong(Object address, long ptr) {
		if (address instanceof byte[]) {
			return getLongA((byte[]) address, ptr);
		}
		else if (address instanceof Long) {
			return getLongL((Long) address, ptr);
		}
		else if (address instanceof ByteBuffer) {
			return getLongB((ByteBuffer) address, ptr);
		}
		else {
			throw new IllegalArgumentException("unknown object " + address);
		}
	}

	public long getLongA(byte[] blob, long ptr) {
		long value = UnsafeUtil.UNSAFE.getLong(blob, offset + ptr + UnsafeUtil.byteArrayBaseOffset);
		return isLittleEndian ? value : Swapper.swapLong(value);
	}
	
	public long getLongL(long address, long ptr) {
		long value = UnsafeUtil.UNSAFE.getLong(address + offset + ptr);
		return isLittleEndian ? value : Swapper.swapLong(value);
	}

	public long getLongB(ByteBuffer bb, long ptr) {
		return bb.getLong((int)(offset+ptr));
	}

	public void setLong(Object address, long ptr, long value) {
		if (address instanceof byte[]) {
			setLongA((byte[]) address, ptr, value);
		}
		else if (address instanceof Long) {
			setLongL((Long) address, ptr, value);
		}
		else if (address instanceof ByteBuffer) {
			setLongB((ByteBuffer) address, ptr, value);
		}
		else {
			throw new IllegalArgumentException("unknown object " + address);
		}
	}
	
	public void setLongA(byte[] blob, long ptr, long value) {
		value = isLittleEndian ? value : Swapper.swapLong(value);
		UnsafeUtil.UNSAFE.putLong(blob, offset + ptr + UnsafeUtil.byteArrayBaseOffset, value);
	}
	
	public void setLongL(long address, long ptr, long value) {
		value = isLittleEndian ? value : Swapper.swapLong(value);
		UnsafeUtil.UNSAFE.putLong(address + offset + ptr, value);
	}
	
	public void setLongB(ByteBuffer bb, long ptr, long value) {
		bb.putLong((int)(offset+ptr), value);
	}	
	
	public int getTypeId() {
		return TypeRegistry.LONG_ID;
	}
	
	public int sizeOf() {
		return PrimitiveTypes.LONG_SIZEOF;
	}
	
	
}
