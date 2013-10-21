package com.shvid.react;

public class ReactRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -7483069657253846585L;

	public ReactRuntimeException() {
	}

	public ReactRuntimeException(String message) {
		super(message);
	}
	
	public ReactRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ReactRuntimeException(Throwable cause) {
		super(cause);
	}
	
}
