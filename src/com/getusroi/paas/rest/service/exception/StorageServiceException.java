package com.getusroi.paas.rest.service.exception;

public class StorageServiceException extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public StorageServiceException(String message) {
		super(message);
	}

	public StorageServiceException() {
		super();
	}

	public StorageServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public StorageServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public StorageServiceException(Throwable cause) {
		super(cause);
	}


}
