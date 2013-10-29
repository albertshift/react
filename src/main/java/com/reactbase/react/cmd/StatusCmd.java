package com.reactbase.react.cmd;

import com.reactbase.react.PropertiesUtil;
import com.reactbase.react.RC;

public class StatusCmd extends AbstractCmd {

	public static void main(String[] args) {
		
		System.out.println("React Status");
	
		RC.getInstance(getReactProps()).log();
		
		PropertiesUtil.log("REACT {}={}", getReactProps());
		
	}
	
}
