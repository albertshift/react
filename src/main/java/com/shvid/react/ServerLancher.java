package com.shvid.react;


public class ServerLancher {

	private final static String REACT_PROPS = System.getProperty("react-properties", "classpath:react.properties");
	
	public static void main(String[] args) {
				
		System.out.println("React lancher");

		ReactInstance instance = new ReactInstance(REACT_PROPS);
		
		
	}

}


