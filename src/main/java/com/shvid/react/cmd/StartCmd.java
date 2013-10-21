package com.shvid.react.cmd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shvid.react.PropertiesUtil;
import com.shvid.react.ReactInstance;
import com.shvid.react.RuntimeConfiguration;


public class StartCmd extends AbstractCmd {

	final static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
	
	public static void main(String[] args) {
				
		System.out.println("React Start");

		RuntimeConfiguration.getInstance().log();
		
		PropertiesUtil.log("REACT {}={}", getReactProps());
		
		logger.info("Current directory {}", System.getProperty("user.dir"));	
	
		ReactInstance instance = new ReactInstance(getReactProps());
		
		
	}

}


