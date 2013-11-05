package com.reactbase.packedobject;

public enum TypeSizes {

	BYTE(1),
	CHAR(2),
	SHORT(2),
	INT(4),
	LONG(8),
	FLOAT(4),
	DOUBLE(8),
	PTR32(4),
	PTR64(8);
		
	final private int size;
	
	TypeSizes(int sz) {
		this.size = sz;
	}

	public int sizeOf() {
		return size;
	}

}
