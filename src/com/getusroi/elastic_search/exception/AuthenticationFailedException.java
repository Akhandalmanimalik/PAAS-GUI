package com.getusroi.elastic_search.exception;

/**
 * AuthenticationFailedException Class <br>
 * Exception handled when the Authentication, for the given credential is
 * failed.
 * 
 * @author bizruntime
 */
public class AuthenticationFailedException extends Exception {

	private static final long serialVersionUID = -1222816925944530911L;

	public AuthenticationFailedException() {
		super();
	}

	public AuthenticationFailedException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AuthenticationFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthenticationFailedException(String message) {
		super(message);
	}

	public AuthenticationFailedException(Throwable cause) {
		super(cause);
	}

}
