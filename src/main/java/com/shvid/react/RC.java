package com.shvid.react;

import java.nio.ByteOrder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Runtime Configuration
 * 
 * @author ashvid
 *
 */

public final class RC {

	final static Logger logger = LoggerFactory
			.getLogger(RC.class);

	public final int pid;
	public final int availableProcessors;
	public final long freeMemory;
	public final long maxMemory;
	public final boolean isLittleEndian;

	private static final class Lazy {
		private static final RC INSTANCE = new RC();
	}

	public RC() {
		pid = getPid();
		availableProcessors = Runtime.getRuntime().availableProcessors();
		freeMemory = Runtime.getRuntime().freeMemory();
		maxMemory = Runtime.getRuntime().maxMemory();
		isLittleEndian = ByteOrder.nativeOrder()
				.equals(ByteOrder.LITTLE_ENDIAN);
	}

	public static RC getInstance() {
		return Lazy.INSTANCE;
	}

	public void log() {
		logger.info("availableProcessors={}", availableProcessors);
		logger.info("freeMemory={}", freeMemory);
		logger.info("maxMemory={}", maxMemory);
	}

	private static int getPid() {
		try {
			java.lang.management.RuntimeMXBean runtime = java.lang.management.ManagementFactory
					.getRuntimeMXBean();
			java.lang.reflect.Field jvm = runtime.getClass()
					.getDeclaredField("jvm");
			jvm.setAccessible(true);
			sun.management.VMManagement mgmt = (sun.management.VMManagement) jvm
					.get(runtime);
			java.lang.reflect.Method pid_method = mgmt.getClass()
					.getDeclaredMethod("getProcessId");
			pid_method.setAccessible(true);
	
			int pid = (Integer) pid_method.invoke(mgmt);
			return pid;
		}
		catch(Exception e) {
			logger.warn("fail to get process id", e);
			return 0;
		}
	}

}
