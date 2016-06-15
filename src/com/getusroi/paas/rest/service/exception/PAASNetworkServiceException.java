package com.getusroi.paas.rest.service.exception;

public class PAASNetworkServiceException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7152390609273765270L;
	
	public PAASNetworkServiceException() {
		super();
	}

	public PAASNetworkServiceException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PAASNetworkServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public PAASNetworkServiceException(String message) {
		super(message);
	}

	public PAASNetworkServiceException(Throwable cause) {
		super(cause);
	}

}
