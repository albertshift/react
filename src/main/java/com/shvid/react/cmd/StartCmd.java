package com.shvid.react.cmd;

import com.shvid.react.PropertiesUtil;
import com.shvid.react.ReactInstance;


public class StartCmd extends AbstractCmd {

	public static void main(String[] args) {
				
		System.out.println("React Start");

		PropertiesUtil.log("REACT {}={}", getReactProps());
		
		ReactInstance instance = new ReactInstance(getReactProps());
		
		
	}

}


