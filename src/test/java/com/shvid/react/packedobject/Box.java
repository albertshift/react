package com.shvid.react.packedobject;

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
