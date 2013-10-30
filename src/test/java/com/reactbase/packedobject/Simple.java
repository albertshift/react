package com.reactbase.packedobject;


public class Simple extends PackedObject {

	final PackedInt num;
	final Array<PackedByte> ba;
	final PackedLong total;
	final Ref<PackedByte> bref;

	final Ref<Array<PackedByte>> baref;
	
	final int fixedSize;
	
	public Simple() {
		this(Holder.getObjectOffset());
	}
	
	public Simple(long ioffset) {
		super(ioffset);
		
		num = new PackedInt(ioffset);
		ioffset += num.sizeOf();
		
		ba = new Array<PackedByte>(ioffset);
		ioffset += ba.sizeOf() + PrimitiveTypes.BYTE_SIZEOF * 100;
		
		total = new PackedLong(ioffset);
		ioffset += total.sizeOf();
		
		bref = new Ref<PackedByte>(ioffset);
		ioffset += bref.sizeOf();
		
		baref = new Ref<Array<PackedByte>>(ioffset);
		ioffset += baref.sizeOf();
		
		fixedSize = (int) (ioffset - offset);
	}
	
	@Override
	public void format(Object address, long ptr) {
		num.format(address, ptr);
		ba.format(address, ptr, TypeRegistry.BYTE_ID, 100);
		total.format(address, ptr);
		bref.format(address, ptr);
		baref.format(address, ptr);
	}

	@Override
	public void copyTo(Object address, long ptr, Object des, long desPtr) {
		
	}

	@Override
	public int sizeOf() {
		return fixedSize;
	}

	@Override
	public int getTypeId() {
		return 777;
	}
	
}
