package com.shvid.react;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ThreadUtil {

	final static Logger logger = LoggerFactory.getLogger(ThreadUtil.class);
	
	/**
	 * 
	 * @param mls
	 * @return true if interrupted
	 */
	
	public static boolean interruptedInSleep(int mls) {
		
		try {
			Thread.sleep(mls);
			return false;
		} catch (InterruptedException e) {
			return true;
		}
	}
	
	public static void pause(String text) {
		if (text != null) {
			System.out.println(text);
		}
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			br.readLine();
		}
		catch(Exception e) {
			logger.error("console write error", e);
		}
	}
	
}
