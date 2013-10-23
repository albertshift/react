package com.shvid.react.cmd;

import com.shvid.react.PropertiesUtil;
import com.shvid.react.RC;

public class DeployCmd extends AbstractCmd {

	public static void main(String[] args) {
		
		System.out.println("React Deploy");
		
		RC.getInstance(getReactProps()).log();
		
		PropertiesUtil.log("REACT {}={}", getReactProps());
		
	}
	
}
