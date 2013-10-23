package com.shvid.react.packedobject;

public final class Box extends ReflectionPackedObject<Box> {

	static final ClassReflection<Box> CR = ClassReflection.create(Box.class);
	
	PackedLong num;  
	Point origin;
	Point extent; 
	
	@Length(200)
	PackedByteArray ba;
	
	@Length(20)
	PackedString name; 
	
	public Box() {
		this(PackedHeader.objBaseOffset());
	}
	
	public Box(long offset) {
		super(offset, CR);
	}

	public int manuaFixedSize() {
		return num.getFixedSize() + origin.getFixedSize() + extent.getFixedSize() + ba.getFixedSize() + name.getFixedSize();
	}
	
}
