package com.shvid.react;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map.Entry;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PropertiesUtil {

	final static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
	
	public static void overrideStatsWith(String prefix, Properties dest, Properties src) {
		for(Entry<Object, Object> entry : src.entrySet()) {
			String key = entry.getKey().toString();
			if (key.startsWith(prefix)) {
				dest.put(entry.getKey(), entry.getValue());
			}
		}
	}
	
	public static void log(String format, Properties props) {
		for (Entry<Object, Object> entry : props.entrySet()) {
			logger.info(format, entry.getKey(), entry.getValue());
		}
	}
	
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
