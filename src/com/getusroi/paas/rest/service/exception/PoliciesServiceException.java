package com.getusroi.paas.rest.service.exception;

public class PoliciesServiceException extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PoliciesServiceException(String message) {
		super(message);
	}
	
	public PoliciesServiceException() {
		super();
	}

	public PoliciesServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PoliciesServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public PoliciesServiceException(Throwable cause) {
		super(cause);
	}

	

}
