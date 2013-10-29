package com.reactbase.packedobject;

import com.reactbase.packedobject.Array;
import com.reactbase.packedobject.ClassDefinition;
import com.reactbase.packedobject.Length;
import com.reactbase.packedobject.PackedByte;
import com.reactbase.packedobject.PackedCharArray;
import com.reactbase.packedobject.PackedHeader;
import com.reactbase.packedobject.PackedLong;
import com.reactbase.packedobject.PackedString;
import com.reactbase.packedobject.Ref;
import com.reactbase.packedobject.ReflectionBasedPackedClass;

public final class Box extends ReflectionBasedPackedClass<Box> {

	@Override
	public int getTypeId() {
		// TODO Auto-generated method stub
		return 55;
	}

	static final ClassDefinition<Box> CD = ClassDefinition.create(Box.class);
	
	PackedLong num;
	Point origin;
	Point extent;
	
	@Length(200)
	PackedCharArray ba;
	
	@Length(20)
	PackedString name;
	
	Ref<PackedCharArray> baref;
	
	Array<PackedByte> baa;
	
	public Box() {
		this(PackedHeader.fixedOffset());
	}
	
	public Box(long offset) {
		super(offset, CD);
	}

	public int manuaFixedSize() {
		return num.sizeOf() + origin.sizeOf() + extent.sizeOf() + ba.sizeOf() + name.sizeOf();
	}
	
}
