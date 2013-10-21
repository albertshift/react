package com.shvid.react.cmd;

import java.util.Properties;

import com.shvid.react.PropertiesUtil;

public abstract class AbstractCmd {

	private final static String REACT_PROPS = System.getProperty("react.properties", "classpath:react.properties");

    private static class PropertiesHolder {
    	
    	Properties reactProps;
    	
    	public PropertiesHolder() {
    		reactProps = PropertiesUtil.readProperties(REACT_PROPS);
    		PropertiesUtil.overrideStatsWith("react.", reactProps, System.getProperties());
    	}
    }
	
    private static class PropertiesHolderSingleton {
    	static final PropertiesHolder INSTANCE = new PropertiesHolder();
    }
    
	static Properties getReactProps() {
		return PropertiesHolderSingleton.INSTANCE.reactProps;
	}
	
}
