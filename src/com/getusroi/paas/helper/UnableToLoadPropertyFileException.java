package com.getusroi.paas.helper;

public class UnableToLoadPropertyFileException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8117818943359032623L;

	public UnableToLoadPropertyFileException() {
		super();
	}

	public UnableToLoadPropertyFileException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public UnableToLoadPropertyFileException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnableToLoadPropertyFileException(String message) {
		super(message);
	}

	public UnableToLoadPropertyFileException(Throwable cause) {
		super(cause);
	}
	

}
