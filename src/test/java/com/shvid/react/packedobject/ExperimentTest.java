package com.shvid.react.packedobject;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

import com.shvid.react.Dumper;

public class ExperimentTest extends TestCase {

	
	private static String format1(int pos, int length) {
		return "pos=" + pos + ", length=" + length;
	}
	
	private static String format2(int pos, int length) {
		return String.format("pos=%s, length=%s", pos, length);
	}
	
	@Test
	public void test() {
		Box box = new Box(0);
		
		int pos = 20;
		int length = 100;
		
		/*
		long n0 = System.currentTimeMillis();
		for (long i = 0; i != 100000000; ++i) {
			format1(pos, length);
		}
		long n1 = System.currentTimeMillis() - n0;
		System.out.println("n1 = " + n1);
		
		n0 = System.currentTimeMillis();
		for (long i = 0; i != 100000000; ++i) {
			format2(pos, length);
		}
		n1 = System.currentTimeMillis() - n0;
		System.out.println("n1 = " + n1);
		
		System.out.println(format1(pos, length));
		System.out.println(format2(pos, length));
		*/
		
		
		
		pos = 10;
		pos <<= PackedConstants.CHAR_SHIFT_BITS;
		System.out.println("pos = " + pos);
		
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
