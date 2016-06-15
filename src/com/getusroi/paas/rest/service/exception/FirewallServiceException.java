package com.getusroi.paas.rest.service.exception;

public class FirewallServiceException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2040700214089011021L;
	
	public FirewallServiceException() {
		super();
	}

	public FirewallServiceException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FirewallServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public FirewallServiceException(String message) {
		super(message);
	}

	public FirewallServiceException(Throwable cause) {
		super(cause);
	}

}
