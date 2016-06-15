package com.getusroi.paas.rest.service.exception;

public class UserRegisterAndLoginServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1129979850018417155L;

	public UserRegisterAndLoginServiceException() {
		super();
	}

	public UserRegisterAndLoginServiceException(String message,
			Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UserRegisterAndLoginServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserRegisterAndLoginServiceException(String message) {
		super(message);
	}

	public UserRegisterAndLoginServiceException(Throwable cause) {
		super(cause);
	}

	
}
