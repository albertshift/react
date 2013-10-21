package com.shvid.react;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public final class PropertiesUtil {

	public static Properties readProperties(String cfgFile) {
		if(cfgFile == null) {
			throw new IllegalArgumentException("config file is null");
		}
		Properties cfg = new Properties();
		InputStream stream = null;
		if (cfgFile.startsWith("classpath:")) {
			cfgFile = cfgFile.substring("classpath:".length());
			ClassLoader classLoader = Thread.currentThread()
					.getContextClassLoader();
			URL url = classLoader.getResource(cfgFile);
			if (url == null) {
				url = ClassLoader.getSystemResource(cfgFile);
			}
			if (url == null) {
				throw new ReactRuntimeException("File not found in classpath "
						+ cfgFile);
			}
			try {
				stream = url.openStream();
			} catch (IOException e) {
				throw new ReactRuntimeException("fail to open stream " + url, e);
			}
		} else {
			try {
				stream = new FileInputStream(cfgFile);
			} catch (IOException e) {
				throw new ReactRuntimeException("fail to open file " + cfgFile, e);
			}
		}
		try {
			cfg.load(stream);
		} 
	    catch (IOException e) {
		    throw new ReactRuntimeException("fail to read properties from file " + cfgFile, e);
	    }
		finally {
			if (stream != null) {
				try {
					stream.close();
				}
				catch(IOException e) {
				}
			}
		}
		return cfg;
	}

}
