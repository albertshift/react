package com.shvid.react.packedobject;

import junit.framework.Assert;

import org.junit.Test;

public class UnsignedIntTest {

	
	@Test
	public void testMaxAddOne() {

		long expected = (long) Integer.MAX_VALUE;
		expected++;
		
		int value = Integer.MAX_VALUE;
		value++;
		
		long actual = UnsignedInt.toLong(value);
	
		Assert.assertEquals(expected, actual);
		
		int intValue = UnsignedInt.fromLong(actual);
		Assert.assertEquals(value, intValue);
		
	}
	
	@Test
	public void testMaxAddTwo() {

		long expected = (long) Integer.MAX_VALUE;
		expected++;
		expected++;
		
		int value = Integer.MAX_VALUE;
		value++;
		value++;

		long actual = UnsignedInt.toLong(value);
	
		Assert.assertEquals(expected, actual);
		
		int intValue = UnsignedInt.fromLong(actual);
		Assert.assertEquals(value, intValue);
		
	}
	
	@Test
	public void testMaxAddMax() {

		long expected = (long) Integer.MAX_VALUE;
		expected += (long) Integer.MAX_VALUE;
		
		int value = Integer.MAX_VALUE;
		value += Integer.MAX_VALUE;
		
		long actual = UnsignedInt.toLong(value);
	
		Assert.assertEquals(expected, actual);
		
		int intValue = UnsignedInt.fromLong(actual);
		Assert.assertEquals(value, intValue);
		
	}
	
	
	@Test
	public void testMaxAddMaxAddOne() {

		long expected = (long) Integer.MAX_VALUE;
		expected += (long) Integer.MAX_VALUE;
		expected++;
		
		int value = Integer.MAX_VALUE;
		value += Integer.MAX_VALUE;
		value++;
		
		long actual = UnsignedInt.toLong(value);
	
		Assert.assertEquals(expected, actual);
		
		int intValue = UnsignedInt.fromLong(actual);
		Assert.assertEquals(value, intValue);
	}
	
}
