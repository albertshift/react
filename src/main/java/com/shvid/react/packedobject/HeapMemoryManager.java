package com.shvid.react.packedobject;


public final class HeapMemoryManager {

	public static byte[] allocateMemory(long requestedSize) {
		if (requestedSize < 0 || requestedSize > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("asked size = " + requestedSize);
		}
		return new byte[(int) requestedSize];
	}

	public static long getMemorySize(byte[] blob) {
		return blob.length;
	}
	
}
