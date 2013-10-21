package com.shvid.react.cmd;

import com.shvid.react.PropertiesUtil;

public class StatusCmd extends AbstractCmd {

	public static void main(String[] args) {
		
		System.out.println("React Status");
	
		PropertiesUtil.log("REACT {}={}", getReactProps());
		
	}
	
}
