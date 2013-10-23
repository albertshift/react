package com.shvid.react.packedobject;

import junit.framework.TestCase;

import org.junit.Test;

import com.shvid.react.Dumper;

public class ExperimentTest extends TestCase {

	@Test
	public void test() {
		Box box = new Box(0);
		
		System.out.println("box-size = " + box.getFixedSize());
		
		byte[] blob = new byte[box.getFixedSize()];
		
		//blob[4] = 44;
		
		box.num.setLong(blob, 0, 123);
		
		long v = box.num.getLong(blob, 0);
		
		System.out.println("v =  " + v);
		
		Dumper.dump(blob, Dumper.CONSOLE_PRINTER);
		
	}
	
}
