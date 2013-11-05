package com.reactbase.packedobject;

import java.nio.ByteBuffer;

public final class PackedInt extends PackedObject {

	final int defaultValue;
	
	public PackedInt(long offset) {
		this(offset, PrimitiveTypes.DEFAULT_INT);
	}
	
	public PackedInt(long offset, int defaultValue) {
		super(offset);
		this.defaultValue = defaultValue;
	}

	public void format(Object address, long ptr) {
		setInt(address, ptr, defaultValue);
	}

	public int getInt(Object address, long ptr) {
		if (address instanceof byte[]) {
			return getIntA((byte[]) address, ptr);
		}
		else if (address instanceof Long) {
			return getIntL((Long) address, ptr);
		}
		else if (address instanceof ByteBuffer) {
			return getIntB((ByteBuffer) address, ptr);
		}
		else {
			throw new IllegalArgumentException("unknown object " + address);
		}
	}
	
	public int getIntA(byte[] blob, long ptr) {
		int value = UnsafeUtil.UNSAFE.getInt(blob, offset + ptr + UnsafeUtil.byteArrayBaseOffset);
		return isLittleEndian ? value : Swapper.swapInt(value);
	}
	
	public int getIntL(long address, long ptr) {
		int value = UnsafeUtil.UNSAFE.getInt(address + offset + ptr);
		return isLittleEndian ? value : Swapper.swapInt(value);
	}

	public int getIntB(ByteBuffer bb, long ptr) {
		return bb.getInt((int) (offset + ptr));
	}

	public void setInt(Object address, long ptr, int value) {
		if (address instanceof byte[]) {
			setIntA((byte[]) address, ptr, value);
		}
		else if (address instanceof Long) {
			setIntL((Long) address, ptr, value);
		}
		else if (address instanceof ByteBuffer) {
			setIntB((ByteBuffer) address, ptr, value);
		}
		else {
			throw new IllegalArgumentException("unknown object " + address);
		}
	}
	
	public void setIntA(byte[] blob, long ptr, int value) {
		value = isLittleEndian ? value : Swapper.swapInt(value);
		UnsafeUtil.UNSAFE.putInt(blob, offset + ptr + UnsafeUtil.byteArrayBaseOffset, value);
	}
	
	public void setIntL(long address, long ptr, int value) {
		value = isLittleEndian ? value : Swapper.swapInt(value);
		UnsafeUtil.UNSAFE.putInt(address + offset + ptr, value);
	}

	public void setIntB(ByteBuffer bb, long ptr, int value) {
		bb.putInt((int) (offset + ptr), value);
	}

	public int getTypeId() {
		return TypeRegistry.INT_ID;
	}
	
	public int sizeOf() {
		return TypeSizes.INT.getSize();
	}
	
}
