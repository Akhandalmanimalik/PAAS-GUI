package com.getusroi.elastic_search.exception;

/**
 * WriteToYMLFailedException Class <br>
 * Exception handled when failed to add users, to Elastic search yml.
 * 
 * @author bizruntime
 */
public class WriteToYMLFailedException extends Exception {

	private static final long serialVersionUID = 6183682620596857772L;

	public WriteToYMLFailedException() {
		super();
	}

	public WriteToYMLFailedException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public WriteToYMLFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public WriteToYMLFailedException(String message) {
		super(message);
	}

	public WriteToYMLFailedException(Throwable cause) {
		super(cause);
	}
}
