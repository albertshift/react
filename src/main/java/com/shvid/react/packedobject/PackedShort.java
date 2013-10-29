package com.shvid.react.packedobject;

import java.nio.ByteBuffer;

import com.shvid.react.RC;
import com.shvid.react.UnsafeHolder;

public final class PackedShort extends SimplePackedObject {

	final short defaultValue;
	
	public PackedShort(long offset) {
		this(offset, PackedConstants.DEFAULT_SHORT);
	}
	
	public PackedShort(long offset, short defaultValue) {
		super(offset);
		this.defaultValue = defaultValue;
	}
	
	public void format(Object address, long ptr) {
		setShort((byte[]) address, ptr, defaultValue);
	}

	public short getShort(Object address, long ptr) {
		if (address instanceof byte[]) {
			return getShortA((byte[]) address, ptr);
		}
		else if (address instanceof Long) {
			return getShortL((Long) address, ptr);
		}
		else if (address instanceof ByteBuffer) {
			return getShortB((ByteBuffer) address, ptr);
		}
		else {
			throw new IllegalArgumentException("unknown object " + address);
		}
	}

	public short getShortA(byte[] blob, long ptr) {
		short value = UnsafeHolder.UNSAFE.getShort(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapShort(value);
	}
	
	public short getShortL(long address, long ptr) {
		short value = UnsafeHolder.UNSAFE.getShort(address + offset + ptr);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapShort(value);
	}
	
	public short getShortB(ByteBuffer bb, long ptr) {
		return bb.getShort((int) (offset + ptr));
	}
	
	public void setShort(Object address, long ptr, short value) {
		if (address instanceof byte[]) {
			setShortA((byte[]) address, ptr, value);
		}
		else if (address instanceof Long) {
			setShortL((Long) address, ptr, value);
		}
		else if (address instanceof ByteBuffer) {
			setShortB((ByteBuffer) address, ptr, value);
		}
		else {
			throw new IllegalArgumentException("unknown object " + address);
		}
	}
	
	public void setShortL(long address, long ptr, short value) {
		value = RC.getInstance().isLittleEndian ? value : Swapper.swapShort(value);
		UnsafeHolder.UNSAFE.putShort(address + offset + ptr, value);
	}
	
	public void setShortA(byte[] blob, long ptr, short value) {
		value = RC.getInstance().isLittleEndian ? value : Swapper.swapShort(value);
		UnsafeHolder.UNSAFE.putShort(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset, value);
	}
	
	public void setShortB(ByteBuffer bb, long ptr, short value) {
		bb.putShort((int)(offset + ptr), value);
	}	
	
	public int getTypeId() {
		return TypeRegistry.SHORT_ID;
	}
	
	public int getFixedSize() {
		return PackedConstants.SHORT_SIZE;
	}
	
}
