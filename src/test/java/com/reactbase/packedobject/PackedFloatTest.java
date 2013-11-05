package com.reactbase.packedobject;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

public class PackedFloatTest {

	@Test
	public void test() {
		check(-12.3f, new byte[] { -51, -52, 68, -63 });
		check(12.3f, new byte[] { -51, -52, 68, 65 });
		check(0f, new byte[] { 0, 0, 0, 0 });
		check(Float.MAX_VALUE, new byte[] { -1, -1, 127, 127 });
		check(Float.MIN_VALUE, new byte[] { 1, 0, 0, 0 });
	}
	
	private void check(float expected, byte[] serialized) {
		PackedFloat pf = new PackedFloat(0);
		byte[] blob = new byte[pf.sizeOf()];
		Assert.assertEquals(TypeSizes.FLOAT.getSize(), blob.length);
		pf.setFloat(blob, 0, expected);
		//System.out.println(Arrays.toString(blob));
		Assert.assertTrue(Arrays.equals(blob, serialized));
		float actual = pf.getFloat(blob, 0);
		Assert.assertEquals(expected, actual);
	}
	
}
