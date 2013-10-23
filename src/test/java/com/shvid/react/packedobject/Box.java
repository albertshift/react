package com.shvid.react.packedobject;

public final class Box extends ReflectionPackedObject {

	static final ClassReflection CR = new ClassReflection(Box.class);
	
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
