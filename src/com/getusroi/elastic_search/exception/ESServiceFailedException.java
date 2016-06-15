package com.getusroi.elastic_search.exception;

/**
 * ESServiceFailedException Class <br>
 * Exception handled during, Elastic search service failed
 * 
 * @author bizruntime
 */
public class ESServiceFailedException extends Exception {

	private static final long serialVersionUID = 645681476702412720L;

	public ESServiceFailedException() {
		super();
	}

	public ESServiceFailedException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ESServiceFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ESServiceFailedException(String message) {
		super(message);
	}

	public ESServiceFailedException(Throwable cause) {
		super(cause);
	}
}
