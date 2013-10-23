package com.shvid.react.packedobject;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

import com.shvid.react.packedobject.PackedInt;

public class PackedIntTest {

	@Test
	public void test() {
		check(-123, new byte[] { -123, -1, -1, -1 });
		check(123, new byte[] { 123, 0, 0, 0 });
		check(0, new byte[] { 0, 0, 0, 0 });
		check(Integer.MAX_VALUE, new byte[] { -1, -1, -1, 127 });
		check(Integer.MIN_VALUE, new byte[] { 0, 0, 0, -128 });
	}
	
	private void check(int expected, byte[] serialized) {
		PackedInt pi = new PackedInt(0);
		byte[] blob = new byte[pi.getFixedSize()];
		Assert.assertEquals(PackedConstants.INT_SIZE, blob.length);
		pi.setInt(blob, 0, expected);
		//System.out.println(Arrays.toString(blob));
		Assert.assertTrue(Arrays.equals(blob, serialized));
		int actual = pi.getInt(blob, 0);
		Assert.assertEquals(expected, actual);
	}
	
}
