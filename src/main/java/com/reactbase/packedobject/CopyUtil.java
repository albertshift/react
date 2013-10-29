package com.reactbase.packedobject;

import java.nio.ByteBuffer;

public final class CopyUtil {

	public static void copyTo(Object address, long ptr, Object des, long desPtr, long length) {
		if (address instanceof byte[]) {
			copyToA((byte[]) address, positiveInt(ptr, "ptr"), des, desPtr, positiveInt(length, "length"));
		}
		else if (address instanceof Long) {
			copyToL((Long) address, ptr, des, desPtr, length);
		}
		else if (address instanceof ByteBuffer) {
			copyToB((ByteBuffer) address, positiveInt(ptr, "ptr"), des, desPtr, positiveInt(length, "length"));
		}
		else {
			throw new IllegalArgumentException("unknown src object " + address);
		}
	}

	private static void copyToA(byte[] blob, int ptr, Object des, long desPtr, int length) {
		if (des instanceof byte[]) {
			System.arraycopy(blob, ptr, des, positiveInt(desPtr, "desPtr"), length);
		}
		else if (des instanceof Long) {
			UnsafeUtil.copyMemory(blob, ptr + UnsafeUtil.byteArrayBaseOffset, null, (Long) des + desPtr, length);
		}
		else if (des instanceof ByteBuffer) {
			ByteBuffer desBB = (ByteBuffer) des;
			desBB.position(positiveInt(desPtr, "desPtr"));
			desBB.put(blob, ptr, length);
		}
		else {
			throw new IllegalArgumentException("unknown des object " + des);
		}
	}

	private static void copyToL(long address, long ptr, Object des, long desPtr, long length) {
		if (des instanceof byte[]) {
			UnsafeUtil.copyMemory(null, address + ptr, des, desPtr + UnsafeUtil.byteArrayBaseOffset, length);
		}
		else if (des instanceof Long) {
			UnsafeUtil.copyMemory(address + ptr, (Long)des + desPtr, length);
		}
		else if (des instanceof ByteBuffer) {
			ByteBuffer desBB = (ByteBuffer) des;
			int desPtrInt = positiveInt(desPtr, "desPtr");
			int lengthInt = positiveInt(length, "length");
			if (desBB.hasArray()) {
				desBB.position(desPtrInt + lengthInt);
				UnsafeUtil.copyMemory(null, address + ptr, desBB.array(), desPtr + desBB.arrayOffset() + UnsafeUtil.byteArrayBaseOffset, length);
			}
			else {
				desBB.position(desPtrInt);
				for (int i = 0; i != length; ++i) {
					desBB.put(UnsafeUtil.UNSAFE.getByte(address + ptr + i));
				}
			}
		}
		else {
			throw new IllegalArgumentException("unknown des object " + des);
		}
	}

	private static void copyToB(ByteBuffer bb, long ptr, Object des, long desPtr, int length) {
		if (des instanceof byte[]) {
			bb.position(positiveInt(ptr, "ptr"));
			bb.get((byte[])des, positiveInt(desPtr, "desPtr"), length);
		}
		else if (des instanceof Long) {
			if (bb.hasArray()) {
				UnsafeUtil.copyMemory(bb.array(), ptr + bb.arrayOffset() + UnsafeUtil.byteArrayBaseOffset, null, (Long)des + desPtr, length);
			}
			else {
				int ptrInt = positiveInt(ptr, "ptr");
				for (int i = 0; i != length; ++i) {
					UnsafeUtil.UNSAFE.putByte((Long) des + desPtr, bb.get(i + ptrInt));
				}
			}
		}
		else if (des instanceof ByteBuffer) {
			ByteBuffer desBB = (ByteBuffer) des;
			
			int ptrInt = positiveInt(ptr, "ptr");
			int saveLimit = bb.limit();
		
			bb.position(ptrInt);
			bb.limit(ptrInt + length);
			
			desBB.position(positiveInt(desPtr, "desPtr"));
			desBB.put(bb);
			
			bb.limit(saveLimit);
		}
		else {
			throw new IllegalArgumentException("unknown des object " + des);
		}
	}
	
	private static int positiveInt(long value, String name) {
		if (value < 0 || value > Integer.MAX_VALUE) {
			throw new IllegalArgumentException(name + "=" + value);
		}
		return (int) value;
	}

}
