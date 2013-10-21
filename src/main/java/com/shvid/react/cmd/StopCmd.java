package com.shvid.react.cmd;

import com.shvid.react.PropertiesUtil;

public class StopCmd extends AbstractCmd {

	public static void main(String[] args) {
		
		System.out.println("React Stop");
		
		PropertiesUtil.log("REACT {}={}", getReactProps());
		
	}
	
}
