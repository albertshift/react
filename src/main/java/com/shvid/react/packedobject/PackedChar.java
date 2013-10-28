package com.shvid.react.packedobject;

import java.nio.ByteBuffer;

import com.shvid.react.RC;
import com.shvid.react.UnsafeHolder;

public final class PackedChar extends FixedPackedClass {
	
	final char defaultValue;

	public PackedChar(long offset) {
		this(offset, PackedConstants.DEFAULT_CHAR);
	}

	public PackedChar(long offset, char defaultValue) {
		super(offset);
		this.defaultValue = defaultValue;
	}
	
	public void format(byte[] blob, long ptr) {
		setChar(blob, ptr, defaultValue);
	}
	
	public void format(long address, long ptr) {
		setChar(address, ptr, defaultValue);
	}
	
	public char getChar(byte[] blob, long ptr) {
		char charValue = UnsafeHolder.UNSAFE.getChar(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset);
		return RC.getInstance().isLittleEndian ? charValue : Swapper.swapChar(charValue);
	}
	
	public char getChar(long address, long ptr) {
		char charValue = UnsafeHolder.UNSAFE.getChar(address + offset + ptr);
		return RC.getInstance().isLittleEndian ? charValue : Swapper.swapChar(charValue);
	}
	
	public char getChar(ByteBuffer bb, long ptr) {
		return bb.getChar((int)(offset + ptr));
	}
	
	public void setChar(byte[] blob, long ptr, char value) {
		char converted = RC.getInstance().isLittleEndian ? value : Swapper.swapChar(value);
		UnsafeHolder.UNSAFE.putChar(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset, converted);
	}	
		
	public void setChar(long address, long ptr, char value) {
		char converted = RC.getInstance().isLittleEndian ? value : Swapper.swapChar(value);
		UnsafeHolder.UNSAFE.putChar(address + offset + ptr, converted);
	}

	public void setChar(ByteBuffer bb, long ptr, char value) {
		bb.putChar((int)(offset + ptr), value);
	}

	public int getTypeId() {
		return TypeRegistry.CHAR_ID;
	}
	
	public int getFixedSize() {
		return PackedConstants.CHAR_SIZE;
	}
	
	public int getInitCapacity() {
		return 0;
	}
}
