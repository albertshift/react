package com.shvid.react;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RuntimeConfiguration {

	final static Logger logger = LoggerFactory.getLogger(RuntimeConfiguration.class);
	
	final int availableProcessors;
	final long freeMemory;
	final long maxMemory;
	
	private static final class RuntimeConfigurationSingleton {
		private static final RuntimeConfiguration INSTANCE = new RuntimeConfiguration();
	}

	public RuntimeConfiguration() {
		availableProcessors = Runtime.getRuntime().availableProcessors();
		freeMemory = Runtime.getRuntime().freeMemory();
		maxMemory = Runtime.getRuntime().maxMemory();
	}
	
	public static RuntimeConfiguration getInstance() {
		return RuntimeConfigurationSingleton.INSTANCE;
	}
	
	public void log() {
		logger.info("availableProcessors={}", availableProcessors);
		logger.info("freeMemory={}", freeMemory);
		logger.info("maxMemory={}", maxMemory);
	}
	
}
