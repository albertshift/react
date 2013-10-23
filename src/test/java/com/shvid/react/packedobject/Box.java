package com.shvid.react.packedobject;

public final class Box extends ReflectionPackedObject<Box> {

	static final ClassReflection<Box> CR = ClassReflection.create(Box.class);
	
	private PackedLong num;  
	private Point origin;
	private Point extent; 
	
	@Length(20)
	public PackedString name; 
	
	public Box() {
		this(PackedHeader.objBaseOffset());
	}
	
	public Box(long offset) {
		super(offset, CR);
	}

}
