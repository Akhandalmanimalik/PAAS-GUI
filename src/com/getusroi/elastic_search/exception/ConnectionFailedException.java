package com.getusroi.elastic_search.exception;

/**
 * ConnectionFailedException class <br>
 * Exception handled when the connection, to the elastic search is failed
 * 
 * @author bizruntime
 */
public class ConnectionFailedException extends Exception {

	private static final long serialVersionUID = 6486477788342846863L;

	public ConnectionFailedException() {
		super();
	}

	public ConnectionFailedException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ConnectionFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConnectionFailedException(String message) {
		super(message);
	}

	public ConnectionFailedException(Throwable cause) {
		super(cause);
	}

}
