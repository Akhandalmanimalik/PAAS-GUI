package com.getusroi.elastic_search.exception;

/**
 * ESConfigFileLoadException Class <br>
 * Exception handled when Config file, fails to load
 * 
 * @author bizruntime
 */
public class ESConfigFileLoadException extends Exception {

	private static final long serialVersionUID = -1759486870175447405L;

	public ESConfigFileLoadException() {
		super();
	}

	public ESConfigFileLoadException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ESConfigFileLoadException(String message, Throwable cause) {
		super(message, cause);
	}

	public ESConfigFileLoadException(String message) {
		super(message);
	}

	public ESConfigFileLoadException(Throwable cause) {
		super(cause);
	}
}
