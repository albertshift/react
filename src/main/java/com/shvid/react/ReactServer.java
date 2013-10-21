package com.shvid.react;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReactServer {

	final static Logger logger = LoggerFactory.getLogger(ReactNode.class);
	
	final boolean enabled;
	
	public ReactServer(Properties props) {
		
		String enabledStr= props.getProperty("react.server.enabled");
		if (enabledStr == null) {
			enabledStr = "false";
		}
		
		enabled = "true".equalsIgnoreCase(enabledStr);
		if (!enabled) {
			return;
		}

		String bindAddress = props.getProperty("react.server.bindAddress");
		if (bindAddress == null) {
			throw new ReactConfigurationException("node server address can not be null");
		}
		
		String bindPortStr = props.getProperty("react.server.bindPort");
		if (bindPortStr == null) {
			throw new ReactConfigurationException("server bind port can not be null");
		}
		int bindPort;
		try {
			bindPort = Integer.parseInt(bindPortStr);
		}
		catch(NumberFormatException e) {
			throw new ReactConfigurationException("invalid server bind port " + bindPortStr, e);
		}
		
		NetworkUtil.ensurePortIsFree(bindAddress, bindPort);
		
		logger.info("Starting server at {}:{}", bindAddress, bindPort);
		
	}
	
}
