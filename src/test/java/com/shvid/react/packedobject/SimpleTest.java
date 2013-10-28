package com.shvid.react.packedobject;

import junit.framework.Assert;

import org.junit.Test;

import com.shvid.react.Dumper;

public class SimpleTest {

	@Test
	public void test() {
		
		System.out.println("test");
		
		Simple simple = new Simple();
		
		System.out.println("size = " + simple.getFixedSize());
		
		byte[] blob = PackedObjectMemory.newHeapInstance(simple, 1000);
		
		
		
		simple.format(blob, 0);
		
		Dumper.dump(blob, Dumper.CONSOLE_PRINTER);
	
		Assert.assertEquals(TypeRegistry.BYTE_ID, simple.ba.getTypeId(blob, 0));
		Assert.assertEquals(100, simple.ba.getLength(blob, 0));
		
		long arrayref = simple.baref.newArrayInstance(blob, 0, TypeRegistry.BYTE_ID, 100);
		System.out.println("arrayref = " + arrayref);
		
		long ref = simple.bref.newInstance(blob, 0, TypeRegistry.BYTE_ID);
		System.out.println("ref = " + ref);
		
		PackedObject po = simple.bref.getType(blob, 0);
		System.out.println("PackedClass = " + po);
		
		Assert.assertEquals(TypeRegistry.BYTE_ID, po.getTypeId());

		
		
		
	}
	
}
