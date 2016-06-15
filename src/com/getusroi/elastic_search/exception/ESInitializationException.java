package com.getusroi.elastic_search.exception;

/**
 * ESInitializationException Class <br>
 * Exception handled when failed to initialize, Properties file.
 * 
 * @author bizruntime
 */
public class ESInitializationException extends Exception {

	private static final long serialVersionUID = -78833962139123877L;

	public ESInitializationException() {
		super();
	}

	public ESInitializationException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ESInitializationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ESInitializationException(String message) {
		super(message);
	}

	public ESInitializationException(Throwable cause) {
		super(cause);
	}

}
