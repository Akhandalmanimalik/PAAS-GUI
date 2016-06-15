package com.getusroi.paas.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getusroi.paas.rest.service.RegistrationAndLoginService;

public class RestServiceHelper {
	static final Logger LOGGER = LoggerFactory.getLogger(RegistrationAndLoginService.class);
	/**
	 * To Convert Form String Value into required Integer value
	 * @param inputString
	 * @return
	 */
	public Integer convertStringToInteger(String inputString) {
		LOGGER.info("Inside convertStringToInteger (.) of RestServiceHelper "+inputString);
		Integer result = null;
		if(inputString != null && !inputString.equalsIgnoreCase("")){
			try{
			result = Integer.parseInt(inputString);
			}catch(NumberFormatException pe){
				LOGGER.error("Error when parsing string to Integer ",pe);
			}
		}
		return result;
	}
}
