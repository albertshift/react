package com.reactbase.packedobject;

import junit.framework.Assert;

import org.junit.Test;

import com.reactbase.packedobject.PackedCharArray;
import com.shvid.react.Dumper;

public class PackedCharArrayTest {

	@Test 
	public void test() {
		
		PackedCharArray arr = new PackedCharArray(0);
		
		String hello = "hello";
		
		byte[] blob = new byte[arr.sizeOf()];
		
		Assert.assertEquals(hello.length() * 2, blob.length);
		
		arr.setChars(blob, 0, hello);

		Dumper.dump(blob, Dumper.CONSOLE_PRINTER);

		CharSequence cs = arr.getChars(blob, 0);
		
		String str = cs.toString();
		
		Assert.assertEquals(hello, str);
		
		CharSequence sub = cs.subSequence(1, 3);
		
		Assert.assertEquals("el", sub.toString());
		
		byte[] des = new byte[12];
		arr.copyTo(blob, 0, des, 2);
		
		Dumper.dump(des, Dumper.CONSOLE_PRINTER);
		
	}
	
	
	
}
