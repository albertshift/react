package com.shvid.react.packedobject;

import java.nio.ByteBuffer;

import com.shvid.react.RC;
import com.shvid.react.UnsafeHolder;

public final class PackedLong extends PackedObject {

	final long defaultValue;
	
	public PackedLong(long offset) {
		this(offset, PackedConstants.DEFAULT_LONG);
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
		long value = UnsafeHolder.UNSAFE.getLong(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapLong(value);
	}
	
	public long getLongL(long address, long ptr) {
		long value = UnsafeHolder.UNSAFE.getLong(address + offset + ptr);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapLong(value);
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
		value = RC.getInstance().isLittleEndian ? value : Swapper.swapLong(value);
		UnsafeHolder.UNSAFE.putLong(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset, value);
	}
	
	public void setLongL(long address, long ptr, long value) {
		value = RC.getInstance().isLittleEndian ? value : Swapper.swapLong(value);
		UnsafeHolder.UNSAFE.putLong(address + offset + ptr, value);
	}
	
	public void setLongB(ByteBuffer bb, long ptr, long value) {
		bb.putLong((int)(offset+ptr), value);
	}	
	
	public int getTypeId() {
		return TypeRegistry.LONG_ID;
	}
	
	public int sizeOf() {
		return PackedConstants.LONG_SIZEOF;
	}
	
	
}
