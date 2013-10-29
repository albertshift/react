package com.shvid.react.packedobject;

import java.nio.ByteBuffer;

import com.shvid.react.RC;
import com.shvid.react.UnsafeHolder;

public final class PackedChar extends PackedObject {
	
	final char defaultValue;

	public PackedChar(long offset) {
		this(offset, PackedConstants.DEFAULT_CHAR);
	}

	public PackedChar(long offset, char defaultValue) {
		super(offset);
		this.defaultValue = defaultValue;
	}
	
	public void format(Object address, long ptr) {
		setChar(address, ptr, defaultValue);
	}

	public char getChar(Object address, long ptr) {
		if (address instanceof byte[]) {
			return getCharA((byte[]) address, ptr);
		}
		else if (address instanceof Long) {
			return getCharL((Long) address, ptr);
		}
		else if (address instanceof ByteBuffer) {
			return getCharB((ByteBuffer) address, ptr);
		}
		else {
			throw new IllegalArgumentException("unknown object " + address);
		}
	}

	public char getCharA(byte[] blob, long ptr) {
		char charValue = UnsafeHolder.UNSAFE.getChar(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset);
		return RC.getInstance().isLittleEndian ? charValue : Swapper.swapChar(charValue);
	}
	
	public char getCharL(long address, long ptr) {
		char charValue = UnsafeHolder.UNSAFE.getChar(address + offset + ptr);
		return RC.getInstance().isLittleEndian ? charValue : Swapper.swapChar(charValue);
	}
	
	public char getCharB(ByteBuffer bb, long ptr) {
		return bb.getChar((int)(offset + ptr));
	}
	
	public void setChar(Object address, long ptr, char value) {
		if (address instanceof byte[]) {
			setCharA((byte[]) address, ptr, value);
		}
		else if (address instanceof Long) {
			setCharL((Long) address, ptr, value);
		}
		else if (address instanceof ByteBuffer) {
			setCharB((ByteBuffer) address, ptr, value);
		}
		else {
			throw new IllegalArgumentException("unknown object " + address);
		}
	}	
	
	public void setCharA(byte[] blob, long ptr, char value) {
		char converted = RC.getInstance().isLittleEndian ? value : Swapper.swapChar(value);
		UnsafeHolder.UNSAFE.putChar(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset, converted);
	}	
		
	public void setCharL(long address, long ptr, char value) {
		char converted = RC.getInstance().isLittleEndian ? value : Swapper.swapChar(value);
		UnsafeHolder.UNSAFE.putChar(address + offset + ptr, converted);
	}

	public void setCharB(ByteBuffer bb, long ptr, char value) {
		bb.putChar((int)(offset + ptr), value);
	}

	public int getTypeId() {
		return TypeRegistry.CHAR_ID;
	}
	
	public int sizeOf() {
		return PackedConstants.CHAR_SIZE;
	}
	
}
