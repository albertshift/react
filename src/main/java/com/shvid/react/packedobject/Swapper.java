package com.shvid.react.packedobject;

/**
 * Needs for Big Endian support
 * 
 * @author ashvid
 *
 */

public final class Swapper {

	public static char swapChar(char value) {
		return (char) ((((value >> 0) & 0xff) << 8) | 
				       (((value >> 8) & 0xff) << 0));

	}

	public static short swapShort(short value) {
		return (short) ((((value >> 0) & 0xff) << 8) | 
				        (((value >> 8) & 0xff) << 0));

	}

	public static int swapInt(int value) {
		return (((value >> 0) & 0xff) << 24) | 
			   (((value >> 8) & 0xff) << 16) | 
			   (((value >> 16) & 0xff) << 8) | 
			   (((value >> 24) & 0xff) << 0);

	}

	public static long swapLong(long value) {
		return (((value >> 0) & 0xff) << 56) | 
			   (((value >> 8) & 0xff) << 48) |
			   (((value >> 16) & 0xff) << 40) |
			   (((value >> 24) & 0xff) << 32) |
			   (((value >> 32) & 0xff) << 24) |
			   (((value >> 40) & 0xff) << 16) |
			   (((value >> 48) & 0xff) << 8) | 
			   (((value >> 56) & 0xff) << 0);
	}
	
	public static float swapFloat(float value) {
		int intValue = Float.floatToIntBits(value);
		intValue = swapInt(intValue);
		return Float.intBitsToFloat(intValue);
	}
	
	public static double swapDouble(double value) {
		long longValue = Double.doubleToLongBits(value);
		longValue = swapLong(longValue);
		return Double.longBitsToDouble(longValue);
	}
}
