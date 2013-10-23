package com.shvid.react;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class Dumper {

	final static Logger logger = LoggerFactory.getLogger(Dumper.class);
	
	public static final String HEX_CHARS = "0123456789ABCDEF";
	public static final int INSERT_SPACE_IDX = 4;
	public static final int INSERT_LN_IDX = 16;
	
	public static final LineAware CONSOLE_PRINTER = new ConsoleLinePrinter();
	public static final LineAware LOG_PRINTER = new LogLinePrinter();
	
	public interface LineAware {
		void call(CharSequence line); 
	}
	
	public static class ConsoleLinePrinter implements LineAware {

		@Override
		public void call(CharSequence line) {
			System.out.println(line);
		}
		
	}
	
	public static class LogLinePrinter implements LineAware {

		@Override
		public void call(CharSequence line) {
			logger.info(line.toString());
		}
		
	}
	
	
	public static boolean isPrintable(int unsignedByte) {
		return unsignedByte > 10;
	}
	
	public static void dump(byte[] blob, LineAware lineAware) {
		dump(blob, 0, blob.length, lineAware);
	}
	
	public static void dump(byte[] blob, int offset, int size, LineAware lineAware) {
		StringBuilder hexCollector = new StringBuilder();
		StringBuilder charsCollector = new StringBuilder();
		int end = offset + size;
		int j = 0;
		for (int i = offset; i != end; ++i) {
			int unsignedByte = blob[i] & 0xFF;
			
			int lowPart= unsignedByte & 0xF;
			int highPart = unsignedByte >> 4;
			
			hexCollector.append(HEX_CHARS.charAt(highPart));
			hexCollector.append(HEX_CHARS.charAt(lowPart));
			
			if (isPrintable(unsignedByte)) {

				charsCollector.append((char) unsignedByte);
			}
			else {
				charsCollector.append(".");
			}
			j++;
			if (j % INSERT_SPACE_IDX == 0) {
				hexCollector.append(' ');
			}
			if (j == INSERT_LN_IDX) {
				hexCollector.append(" | ").append(charsCollector);
				lineAware.call(hexCollector);
				hexCollector.setLength(0);
				charsCollector.setLength(0);
				j = 0;
			}
		}
	    if (hexCollector.length() > 0) {
	    	while (hexCollector.length() < 32 + (16 / INSERT_SPACE_IDX)) {
	    		hexCollector.append(' ');
	    	}
	    	hexCollector.append(" | ").append(charsCollector);
	    	lineAware.call(hexCollector.toString());
	    }
	}
	
}
