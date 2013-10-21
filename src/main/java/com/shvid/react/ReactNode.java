package com.shvid.react;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReactNode {

	final static Logger logger = LoggerFactory.getLogger(ReactNode.class);
	
	public ReactNode(Properties props) {
		
		String bindAddress = props.getProperty("react.node.bindAddress");
		if (bindAddress == null) {
			throw new ReactConfigurationException("node bind address can not be null");
		}
		
		String bindPortStr = props.getProperty("react.node.bindPort");
		if (bindPortStr == null) {
			throw new ReactConfigurationException("node bind port can not be null");
		}
		int bindPort;
		try {
			bindPort = Integer.parseInt(bindPortStr);
		}
		catch(NumberFormatException e) {
			throw new ReactConfigurationException("invalid node bind port " + bindPortStr, e);
		}
		
		NetworkUtil.ensurePortIsFree(bindAddress, bindPort);
		
		logger.info("Starting node at {}:{}", bindAddress, bindPort);
		
	}
	
}
