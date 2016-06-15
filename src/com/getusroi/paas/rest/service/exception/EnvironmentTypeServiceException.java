package com.getusroi.paas.rest.service.exception;

public class EnvironmentTypeServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EnvironmentTypeServiceException() {
		super();
	}
	
	public EnvironmentTypeServiceException(String message) {
		super(message);
	}

	public EnvironmentTypeServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EnvironmentTypeServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public EnvironmentTypeServiceException(Throwable cause) {
		super(cause);
	}
	
}
