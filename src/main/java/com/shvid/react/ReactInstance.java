package com.shvid.react;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReactInstance {

	final static Logger logger = LoggerFactory.getLogger(ReactInstance.class);
	
	private final ReactNode node;
	
	public ReactInstance(String reactPropsFile) {
		this(PropertiesUtil.readProperties(reactPropsFile));
	}
	
	public ReactInstance(Properties reactProps) {
		node = new ReactNode(reactProps);

	}
	
	public ReactCluster getCluster() {
		return null;
	}
	

	
}
