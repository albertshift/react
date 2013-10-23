package com.shvid.react.packedobject;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

import com.shvid.react.packedobject.PackedShort;

public class PackedShortTest {

	@Test
	public void test() {
		check((short)-123, new byte[] { -123, -1 });
		check((short)123, new byte[] { 123, 0 });
		check((short)0, new byte[] { 0, 0 });
		check(Short.MAX_VALUE, new byte[] { -1, 127 });
		check(Short.MIN_VALUE, new byte[] { 0, -128 });
	}
	
	private void check(short expected, byte[] serialized) {
		PackedShort ps = new PackedShort(0);
		byte[] blob = new byte[ps.getFixedSize()];
		Assert.assertEquals(PackedShort.fixedSize, blob.length);
		ps.setShort(blob, 0, expected);
		//System.out.println(Arrays.toString(blob));
		Assert.assertTrue(Arrays.equals(blob, serialized));
		short actual = ps.getShort(blob, 0);
		Assert.assertEquals(expected, actual);
	}
}
