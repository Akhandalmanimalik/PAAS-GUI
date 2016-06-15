package com.getusroi.paas.sdn.service.impl;

public class HTTPConnectionException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 676867783974812117L;

	public HTTPConnectionException() {
		super();
	}

	public HTTPConnectionException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public HTTPConnectionException(String message, Throwable cause) {
		super(message, cause);
	}

	public HTTPConnectionException(String message) {
		super(message);
	}

	public HTTPConnectionException(Throwable cause) {
		super(cause);
	}
	

}
