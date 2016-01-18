package com.kata.exception;

public class ServiceLocationException extends Exception {

	public ServiceLocationException() {
		super();
	}

	public ServiceLocationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ServiceLocationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceLocationException(String message) {
		super(message);
	}

	public ServiceLocationException(Throwable cause) {
		super(cause);
	}	
}
