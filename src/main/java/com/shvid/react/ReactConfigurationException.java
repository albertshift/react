package com.shvid.react;

public class ReactConfigurationException extends ReactRuntimeException {

	private static final long serialVersionUID = 3195342341082919880L;

	public ReactConfigurationException() {
	}

	public ReactConfigurationException(String message) {
		super(message);
	}
	
	public ReactConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ReactConfigurationException(Throwable cause) {
		super(cause);
	}
	
}
