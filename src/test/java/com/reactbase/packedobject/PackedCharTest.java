package com.reactbase.packedobject;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

public class PackedCharTest {

	@Test
	public void test() {
		check((char)'\123', new byte[] { 83, 0 });
		check((char)'\uF123', new byte[] { 35, -15 });
		check((char)'\000', new byte[] { 0, 0 });
		check(Character.MAX_HIGH_SURROGATE, new byte[] { -1, -37 });
		check(Character.MAX_LOW_SURROGATE, new byte[] { -1, -33 });
		check(Character.MIN_HIGH_SURROGATE, new byte[] { 0, -40 });
		check(Character.MIN_LOW_SURROGATE, new byte[] { 0, -36 });
	}
	
	private void check(char expected, byte[] serialized) {
		PackedChar pc = new PackedChar(0);
		byte[] blob = new byte[pc.sizeOf()];
		Assert.assertEquals(TypeSizes.CHAR.sizeOf(), blob.length);
		pc.setChar(blob, 0, expected);
		//System.out.println(Arrays.toString(blob));
		Assert.assertTrue(Arrays.equals(blob, serialized));
		char actual = pc.getChar(blob, 0);
		Assert.assertEquals(expected, actual);
	}
	
}
