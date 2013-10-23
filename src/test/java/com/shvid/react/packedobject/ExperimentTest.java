package com.shvid.react.packedobject;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

import com.shvid.react.Dumper;

public class ExperimentTest extends TestCase {

	@Test
	public void test() {
		Box box = new Box(0);
		
		Assert.assertEquals(box.manuaFixedSize(), box.getFixedSize());
		
		System.out.println("box-size = " + box.getFixedSize());
		
		byte[] blob = new byte[box.getFixedSize()];
		
		//blob[4] = 44;
		
		box.num.setLong(blob, 0, 123);
		
		long v = box.num.getLong(blob, 0);

		box.name.setString(blob, 0, "hello world");
		
		System.out.println("v =  " + v);
		
		Dumper.dump(blob, Dumper.CONSOLE_PRINTER);
		
		HeapPackedObject<Box> instance = HeapPackedObject.newInstance(box);

		instance.pclass.num.setLong(instance, 123);
	
		
	}
	
}
