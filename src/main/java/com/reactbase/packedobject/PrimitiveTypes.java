package com.reactbase.packedobject;

public final class PrimitiveTypes {

	public static final int BYTE_SIZEOF = 1;
	public static final int CHAR_SIZEOF = 2;
	public static final int SHORT_SIZEOF = 2;
	public static final int INT_SIZEOF = 4;
	public static final int LONG_SIZEOF = 8;
	public static final int FLOAT_SIZEOF = 4;
	public static final int DOUBLE_SIZEOF = 8;

	public static final int PTR32_SIZEOF = INT_SIZEOF;
	public static final int PTR64_SIZEOF = LONG_SIZEOF;
	public static final int CAPACITY_SIZEOF = INT_SIZEOF;
	
	public static final byte DEFAULT_BYTE = (byte)0;
	public static final char DEFAULT_CHAR = '\000';
	public static final short DEFAULT_SHORT = 0;
	public static final int DEFAULT_INT = 0;
	public static final long DEFAULT_LONG = 0L;
	public static final float DEFAULT_FLOAT = 0.0f;
	public static final double DEFAULT_DOUBLE = 0.0;
	
}
