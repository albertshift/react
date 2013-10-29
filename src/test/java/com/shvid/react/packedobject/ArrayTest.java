package com.shvid.react.packedobject;

import junit.framework.Assert;

import org.junit.Test;

import com.shvid.react.Dumper;

public class ArrayTest {

	@Test
	public void test() {
		
		int length = 100;
		
		Array<PackedByte> array = new Array<PackedByte>(0);
		
		int size = array.sizeOf() + length * PrimitiveTypes.BYTE_SIZEOF;
		
		byte[] blob = new byte[size];
		
		array.format(blob, 0, TypeRegistry.BYTE_ID, length);
		
		Assert.assertEquals(TypeRegistry.BYTE_ID, array.getTypeId(blob, 0));
		Assert.assertEquals(length, array.getLength(blob, 0));
		
		Dumper.dump(blob, Dumper.CONSOLE_PRINTER);
	}
	
}
