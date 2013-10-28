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
		offset += num.getFixedSize();
		
		ba = new Array<PackedByte>(offset);
		offset += ba.getFixedSize() + PackedConstants.BYTE_SIZE * 100;
		
		total = new PackedLong(offset);
		offset += total.getFixedSize();
		
		bref = new Ref<PackedByte>(offset);
		offset += bref.getFixedSize();
		
		baref = new Ref<Array<PackedByte>>(offset);
		offset += baref.getFixedSize();
		
		fixedSize = (int) (offset - ioffset);
	}
	

	@Override
	public void format(byte[] blob, long ptr) {
		num.format(blob, ptr);
		ba.format(blob, ptr, TypeRegistry.BYTE_ID, 100);
		total.format(blob, ptr);
		bref.format(blob, ptr);
		baref.format(blob, ptr);
	}

	@Override
	public void format(long address, long ptr) {
		num.format(address, ptr);
		ba.format(address, ptr, TypeRegistry.BYTE_ID, 100);
		total.format(address, ptr);
		bref.format(address, ptr);
		baref.format(address, ptr);
	}

	@Override
	public void copyTo(byte[] blob, long ptr, byte[] des, long desPtr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void copyTo(long address, long ptr, long des, long desPtr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFixedSize() {
		return fixedSize;
	}

	@Override
	public int getInitCapacity() {
		return 0;
	}
	
	@Override
	public int getTypeId() {
		return 0;
	}
	
}
