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
		return AddressMemoryManager.getMemorySize(address);
	}
	
	public boolean isHeap() {
		return false;
	}
	
	public T getPackedClass() {
		return pclass;
	}
	
	public static <T extends PackedClass> AddressPackedObject<T> newInstance(T pclass, long thresholdCapacity) {
		long address = PackedObjectMemory.newInstance(pclass, thresholdCapacity);
		return new AddressPackedObject<T>(pclass, address, 0);
	}
}
