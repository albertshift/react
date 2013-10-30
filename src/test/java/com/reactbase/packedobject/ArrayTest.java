package com.reactbase.packedobject;

import junit.framework.Assert;

import org.junit.Test;

import com.reactbase.packedobject.Holder.HolderType;

public class ArrayTest {

	@Test
	public void test() {
		
		int length = 100;		
		Array<PackedByte> array = new Array<PackedByte>(0);
		
		int size = (int) Array.sizeOf(TypeRegistry.BYTE, length);
		
		byte[] blob = new byte[size];
		
		array.format(blob, 0, TypeRegistry.BYTE_ID, length);
		
		Assert.assertEquals(TypeRegistry.BYTE_ID, array.getElementTypeId(blob, 0));
		Assert.assertEquals(length, array.getLength(blob, 0));
		
		//Dumper.dump(blob, Dumper.CONSOLE_PRINTER);
	}
	
	@Test
	public void testWithHolder() {
		
		int length = 100;
		
		int size = (int) Array.sizeOf(TypeRegistry.BYTE, length);
		
		Object address = Holder.allocate(HolderType.BYTE_ARRAY, Holder.getObjectOffset() + size);
		
		Holder.formatArray(address, TypeRegistry.BYTE, length);
		
		Assert.assertEquals(TypeRegistry.BYTE_ID, TypeRegistry.ARRAY.getElementTypeId(address, Holder.getObjectOffset()));
		Assert.assertEquals(length, TypeRegistry.ARRAY.getLength(address, Holder.getObjectOffset()));
		
		//Dumper.dump((byte[]) address, Dumper.CONSOLE_PRINTER);
		
	}
	
	@Test
	public void testRefs() {
		
		int length = 100;
		
		Object address = Holder.allocate(HolderType.BYTE_ARRAY, 1000);
		
		Holder.format(address, TypeRegistry.REF);
		
		long size = (int) Holder.getSize(address);
		
		Assert.assertEquals(Holder.getObjectOffset() + TypeRegistry.REF.sizeOf(), size);
		
		long arrayPtr = TypeRegistry.REF.newArrayInstance(address, Holder.getObjectOffset(), TypeRegistry.BYTE_ID, length);
		
		Assert.assertEquals(TypeRegistry.BYTE_ID, TypeRegistry.ARRAY.getElementTypeId(address, arrayPtr));
		Assert.assertEquals(length, TypeRegistry.ARRAY.getLength(address, arrayPtr));
		
		size = (int) Holder.getSize(address);
		Assert.assertEquals(Holder.getObjectOffset() + TypeRegistry.REF.sizeOf() + TypeRegistry.REF.getDataSize(address, Holder.getObjectOffset()), size);
		
		arrayPtr = TypeRegistry.REF.newArrayInstance(address, Holder.getObjectOffset(), TypeRegistry.BYTE_ID, length * 2);
		
		int trash = Holder.getTrashCounter(address);
		System.out.println("trash = " + trash);
	
		
		Object desAddress = Holder.allocate(HolderType.BYTE_ARRAY, 1000);
		Holder.gc(address, desAddress);

		Assert.assertEquals(Holder.getSize(address) - Holder.getTrashCounter(address), Holder.getSize(desAddress));
		
	}
	
	
}
