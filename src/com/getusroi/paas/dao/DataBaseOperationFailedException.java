package com.getusroi.paas.dao;

public class DataBaseOperationFailedException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataBaseOperationFailedException() {
		super();
	}

	public DataBaseOperationFailedException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DataBaseOperationFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataBaseOperationFailedException(String message) {
		super(message);
	}

	public DataBaseOperationFailedException(Throwable cause) {
		super(cause);
	}
	
	

}
