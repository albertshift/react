package com.shvid.react.packedobject;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

public class PackedByteTest {

	@Test
	public void test() {
		check((byte)-123, new byte[] { -123 });
		check((byte)123, new byte[] { 123 });
		check((byte)0, new byte[] { 0 });
		check(Byte.MAX_VALUE, new byte[] { 127 });
		check(Byte.MIN_VALUE, new byte[] { -128 });
	}
	
	private void check(byte expected, byte[] serialized) {
		PackedByte pb = new PackedByte(0);
		byte[] blob = new byte[pb.getFixedSize()];
		Assert.assertEquals(PackedConstants.BYTE_SIZE, blob.length);
		pb.setByte(blob, 0, expected);
		//System.out.println(Arrays.toString(blob));
		Assert.assertTrue(Arrays.equals(blob, serialized));
		byte actual = pb.getByte(blob, 0);
		Assert.assertEquals(expected, actual);
	}
	
}
