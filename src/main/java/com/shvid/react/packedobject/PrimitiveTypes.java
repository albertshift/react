package com.shvid.react.packedobject;

final class PrimitiveTypes {

	static final int BYTE_SIZEOF = 1;
	static final int CHAR_SIZEOF = 2;
	static final int SHORT_SIZEOF = 2;
	static final int INT_SIZEOF = 4;
	static final int LONG_SIZEOF = 8;
	static final int FLOAT_SIZEOF = 4;
	static final int DOUBLE_SIZEOF = 8;

	static final int PTR32_SIZE = INT_SIZEOF;
	static final int PTR64_SIZE = LONG_SIZEOF;
	static final int CAPACITY_SIZE = INT_SIZEOF;
	
	static final byte DEFAULT_BYTE = (byte)0;
	static final char DEFAULT_CHAR = '\000';
	static final short DEFAULT_SHORT = 0;
	static final int DEFAULT_INT = 0;
	static final long DEFAULT_LONG = 0L;
	static final float DEFAULT_FLOAT = 0.0f;
	static final double DEFAULT_DOUBLE = 0.0;
	
}
