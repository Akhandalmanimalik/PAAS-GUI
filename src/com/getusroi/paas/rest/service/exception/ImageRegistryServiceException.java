package com.getusroi.paas.rest.service.exception;

public class ImageRegistryServiceException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 795548155156521039L;
	public ImageRegistryServiceException() {
		super();
	}

	public ImageRegistryServiceException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ImageRegistryServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ImageRegistryServiceException(String message) {
		super(message);
	}

	public ImageRegistryServiceException(Throwable cause) {
		super(cause);
	}

}
