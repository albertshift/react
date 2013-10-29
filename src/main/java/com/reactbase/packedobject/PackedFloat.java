package com.reactbase.packedobject;

import java.nio.ByteBuffer;

import com.shvid.react.RC;
import com.shvid.react.UnsafeHolder;

public final class PackedFloat extends PackedObject {

	final float defaultValue;
	
	public PackedFloat(long offset) {
		this(offset, PrimitiveTypes.FLOAT_SIZEOF);
	}
	
	public PackedFloat(long offset, float defaultValue) {
		super(offset);
		this.defaultValue = defaultValue;
	}

	public void format(Object address, long ptr) {
		setFloat(address, ptr, defaultValue);
	}

	public float getFloat(Object address, long ptr) {
		if (address instanceof byte[]) {
			return getFloatA((byte[]) address, ptr);
		}
		else if (address instanceof Long) {
			return getFloatL((Long) address, ptr);
		}
		else if (address instanceof ByteBuffer) {
			return getFloatB((ByteBuffer) address, ptr);
		}
		else {
			throw new IllegalArgumentException("unknown object " + address);
		}
	}

	public float getFloatA(byte[] blob, long ptr) {
		float value = UnsafeHolder.UNSAFE.getFloat(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapFloat(value);
	}
	
	public float getFloatL(long address, long ptr) {
		float value = UnsafeHolder.UNSAFE.getFloat(address + offset + ptr);
		return RC.getInstance().isLittleEndian ? value : Swapper.swapFloat(value);
	}

	public float getFloatB(ByteBuffer bb, long ptr) {
		return bb.getFloat((int)(offset + ptr));
	}
	
	public void setFloat(Object address, long ptr, float value) {
		if (address instanceof byte[]) {
			setFloatA((byte[]) address, ptr, value);
		}
		else if (address instanceof Long) {
			setFloatL((Long) address, ptr, value);
		}
		else if (address instanceof ByteBuffer) {
			setFloatB((ByteBuffer) address, ptr, value);
		}
		else {
			throw new IllegalArgumentException("unknown object " + address);
		}
	}
	
	public void setFloatA(byte[] blob, long ptr, float value) {
		value = RC.getInstance().isLittleEndian ? value : Swapper.swapFloat(value);
		UnsafeHolder.UNSAFE.putFloat(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset, value);
	}
	
	public void setFloatL(long address, long ptr, float value) {
		value = RC.getInstance().isLittleEndian ? value : Swapper.swapFloat(value);
		UnsafeHolder.UNSAFE.putFloat(address + offset + ptr, value);
	}
	
	public void setFloatB(ByteBuffer bb, long ptr, float value) {
		bb.putFloat((int)(offset + ptr), value);
	}	
	
	public int getTypeId() {
		return TypeRegistry.FLOAT_ID;
	}
	
	public int sizeOf() {
		return PrimitiveTypes.FLOAT_SIZEOF;
	}
	
}
