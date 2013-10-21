package com.shvid.react.cmd;

import com.shvid.react.PropertiesUtil;

public class DeployCmd extends AbstractCmd {

	public static void main(String[] args) {
		
		System.out.println("React Deploy");
		
		PropertiesUtil.log("REACT {}={}", getReactProps());
		
	}
	
}
