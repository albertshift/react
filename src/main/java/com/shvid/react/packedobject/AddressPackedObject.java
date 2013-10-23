package com.shvid.react.packedobject;

public final class AddressPackedObject<T extends PackedClass> implements PackedObject<T> {

	public final T pclass;
	final long address;
	final long ptr;
	
	public AddressPackedObject(T pclass, long address, long ptr) {
		this.pclass = pclass;
		this.address = address;
		this.ptr = ptr;
	}
	
	public long getAddress() {
		return address;
	}

	public long getPtr() {
		return ptr;
	}

	public long getSize() {
		return PackedFactory.memCapacity.getLong(address, -PackedFactory.memCapacity.getFixedSize());
	}
	
	public boolean isHeap() {
		return false;
	}
	
	public T getPackedClass() {
		return pclass;
	}
	
	public static <T extends PackedClass> AddressPackedObject<T> newInstance(T pclass) {
		long address = PackedFactory.newInstance(pclass);
		return new AddressPackedObject<T>(pclass, address, 0);
	}
}
