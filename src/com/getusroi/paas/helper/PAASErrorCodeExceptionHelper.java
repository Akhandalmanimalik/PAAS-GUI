package com.getusroi.paas.helper;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PAASErrorCodeExceptionHelper {
	
	protected static Logger logger = LoggerFactory.getLogger(PAASErrorCodeExceptionHelper.class);
	private static String errorCode;
	private static String errorMessage;
	public static String getErrorCode() {
		return errorCode;
	}
	public static void setErrorCode(String errorCode) {
		PAASErrorCodeExceptionHelper.errorCode = errorCode;
	}
	public static String getErrorMessage() {
		return errorMessage;
	}
	public static void setErrorMessage(String errorMessage) {
		PAASErrorCodeExceptionHelper.errorMessage = errorMessage;
	}

	public static String exceptionFormat(String errorCode) {
		PAASErrorCodeExceptionHelper.errorCode = errorCode;
		Properties properties;
		try {
			properties = PAASGenericHelper.getPropertyFile("paasErrorCode.properties");
			String message = (String)properties.getProperty(errorCode); 
			errorMessage = "ErrorCode=" + errorCode + ", message=" + message;
			return errorMessage;
		} catch(UnableToLoadPropertyFileException e) {
			logger.debug("Error in reading property file");
		}
		return errorMessage;
	}
	
}
