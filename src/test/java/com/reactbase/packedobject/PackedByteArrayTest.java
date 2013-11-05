package com.reactbase.packedobject;


public class PackedByteArrayTest {
/*
	@Test
	public void test() {
		check((byte)-123, 0, new byte[] { -123, 0, 0, 0, 0 });
		check((byte)123, 1, new byte[] { 0, 123, 0, 0, 0 });
		check((byte)0, 2, new byte[] { 0, 0, 0, 0, 0 });
		check(Byte.MAX_VALUE, 3, new byte[] { 0, 0, 0, 127, 0 });
		check(Byte.MIN_VALUE, 4, new byte[] { 0, 0, 0, 0, -128 });
	}
	
	private void check(byte expected, int pos, byte[] serialized) {
		PackedByteArray pba = new PackedByteArray(0, 5);
		byte[] blob = new byte[pba.getFixedSize()];
		Assert.assertEquals(pba.getFixedSize(), blob.length);
		pba.setByte(blob, 0, pos, expected);
		//System.out.println(Arrays.toString(blob));
		Assert.assertTrue(Arrays.equals(blob, serialized));
		byte actual = pba.getByte(blob, 0, pos);
		Assert.assertEquals(expected, actual);
	}
	*/
}
