package com.reactbase.packedobject;

import org.junit.Test;

import com.reactbase.packedobject.Holder.HolderType;
import com.reactbase.react.Dumper;

public class SimpleTest {

	@Test
	public void test() {
		
		System.out.println("test");
		
		Simple simple = new Simple();
		
		System.out.println("size = " + simple.sizeOf());
		
		Object address = Holder.allocate(HolderType.BYTE_ARRAY, 1000);
		Holder.format(address, simple);
		
		Dumper.dump((byte[]) address, Dumper.CONSOLE_PRINTER);
	
		
		
		
	}
	
}
