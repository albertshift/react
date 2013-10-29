package com.reactbase.react;

public class ReactException extends Exception {

	private static final long serialVersionUID = 8095162210395445975L;

	public ReactException() {
	}

	public ReactException(String message) {
		super(message);
	}
	
	public ReactException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ReactException(Throwable cause) {
		super(cause);
	}
	
}
