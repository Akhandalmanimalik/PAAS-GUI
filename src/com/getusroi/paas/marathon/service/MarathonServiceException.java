package com.getusroi.paas.marathon.service;

public class MarathonServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4132774643187109292L;

	public MarathonServiceException() {
		super();
	}

	public MarathonServiceException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MarathonServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public MarathonServiceException(String message) {
		super(message);
	}

	public MarathonServiceException(Throwable cause) {
		super(cause);
	}
	
	

}
