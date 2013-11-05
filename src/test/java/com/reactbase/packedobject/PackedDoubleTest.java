package com.reactbase.packedobject;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

public class PackedDoubleTest {

	@Test
	public void test() {
		check(-12.3, new byte[] { -102, -103, -103, -103, -103, -103, 40, -64 });
		check(12.3, new byte[] { -102, -103, -103, -103, -103, -103, 40, 64 });
		check(0.0, new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 });
		check(Double.MAX_VALUE, new byte[] { -1, -1, -1, -1, -1, -1, -17, 127 });
		check(Double.MIN_VALUE, new byte[] { 1, 0, 0, 0, 0, 0, 0, 0 });
	}
	
	private void check(double expected, byte[] serialized) {
		PackedDouble pd = new PackedDouble(0);
		byte[] blob = new byte[pd.sizeOf()];
		Assert.assertEquals(TypeSizes.DOUBLE.getSize(), blob.length);
		pd.setDouble(blob, 0, expected);
		//System.out.println(Arrays.toString(blob));
		Assert.assertTrue(Arrays.equals(blob, serialized));
		double actual = pd.getDouble(blob, 0);
		Assert.assertEquals(expected, actual);
	}
	
}
