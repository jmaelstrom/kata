package com.kata.exception;

public class InvalidRepositoryNodeDefinitionException extends Exception {

	public InvalidRepositoryNodeDefinitionException() {
		super();
	}

	public InvalidRepositoryNodeDefinitionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidRepositoryNodeDefinitionException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidRepositoryNodeDefinitionException(String message) {
		super(message);
	}

	public InvalidRepositoryNodeDefinitionException(Throwable cause) {
		super(cause);
	}	
}
