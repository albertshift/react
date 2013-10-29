package com.shvid.react.packedobject;

public class Simple implements PackedObject {

	final PackedInt num;
	final Array<PackedByte> ba;
	final PackedLong total;
	final Ref<PackedByte> bref;

	final Ref<Array<PackedByte>> baref;
	
	final int fixedSize;
	
	public Simple() {
		this(PackedHeader.fixedOffset());
	}
	
	public Simple(long ioffset) {
		long offset = ioffset;
		
		num = new PackedInt(offset);
		offset += num.sizeOf();
		
		ba = new Array<PackedByte>(offset);
		offset += ba.sizeOf() + PackedConstants.BYTE_SIZE * 100;
		
		total = new PackedLong(offset);
		offset += total.sizeOf();
		
		bref = new Ref<PackedByte>(offset);
		offset += bref.sizeOf();
		
		baref = new Ref<Array<PackedByte>>(offset);
		offset += baref.sizeOf();
		
		fixedSize = (int) (offset - ioffset);
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
		return 0;
	}
	
}
