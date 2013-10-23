package com.shvid.react.cmd;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shvid.react.PropertiesUtil;
import com.shvid.react.ReactInstance;
import com.shvid.react.RC;


public class StartCmd extends AbstractCmd {

	final static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
	
	public static void main(String[] args) {
				
		System.out.println("React Start");

		RC.getInstance(getReactProps()).log();
		
		PropertiesUtil.log("REACT {}={}", getReactProps());
		
		
		logger.info("Current directory {}", System.getProperty("user.dir"));	
	
		ReactInstance instance = new ReactInstance(getReactProps());

		saveRunPidFile();
		
	}

	private static void saveRunPidFile() {
		String dataDir = getReactProps().getProperty("react.node.dataDir");
		String filePath = dataDir + File.separatorChar + "run.pid";
		try {
			FileOutputStream fout = new FileOutputStream(filePath);
			fout.write(Integer.toString(RC.getInstance().pid).getBytes("UTF-8"));
			fout.close();
		}
		catch(IOException e) {
			logger.error("fail to write " + filePath, e);
		}
	}
	
}


