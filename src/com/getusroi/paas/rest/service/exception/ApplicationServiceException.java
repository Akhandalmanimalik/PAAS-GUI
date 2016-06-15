package com.getusroi.paas.rest.service.exception;

public class ApplicationServiceException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7723816735347691474L;

	public ApplicationServiceException() {
		super();
	}

	public ApplicationServiceException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ApplicationServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApplicationServiceException(String message) {
		super(message);
	}

	public ApplicationServiceException(Throwable cause) {
		super(cause);
	}

}
