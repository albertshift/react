package com.shvid.react.packedobject;

public final class HeapPackedObject<T extends PackedClass> implements PackedObject<T> {

	public final T pclass;
	final byte[] blob;
	final long ptr;

	public HeapPackedObject(T pclass, byte[] blob, long ptr) {
		this.pclass = pclass;
		this.blob = blob;
		this.ptr = ptr;
	}

	public byte[] getBlob() {
		return blob;
	}

	public long getPtr() {
		return ptr;
	}

	public long getSize() {
		return blob.length;
	}
	
	public boolean isHeap() {
		return true;
	}
	
	public T getPackedClass() {
		return pclass;
	}
	
	public static <T extends PackedClass> HeapPackedObject<T> newInstance(T pclass, int thresholdCapacity) {
		byte[] blob = PackedObjectMemory.newHeapInstance(pclass, thresholdCapacity);
		return new HeapPackedObject<T>(pclass, blob, 0);
	}
}
