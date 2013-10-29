package com.reactbase.react.cmd;

import java.util.Properties;

import com.reactbase.react.PropertiesUtil;

public abstract class AbstractCmd {

	private final static String REACT_PROPS = System.getProperty("react.properties", "classpath:react.properties");

    private static final class PropertiesHolder {
    	
    	Properties reactProps;
    	
    	public PropertiesHolder() {
    		reactProps = PropertiesUtil.readProperties(REACT_PROPS);
    		PropertiesUtil.overrideStatsWith("react.", reactProps, System.getProperties());
    	}
    }
	
    private static final class PropertiesHolderSingleton {
    	static final PropertiesHolder INSTANCE = new PropertiesHolder();
    }
    
	static Properties getReactProps() {
		return PropertiesHolderSingleton.INSTANCE.reactProps;
	}
	
}
