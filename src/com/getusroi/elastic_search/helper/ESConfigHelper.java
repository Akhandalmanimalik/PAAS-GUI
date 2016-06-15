package com.getusroi.elastic_search.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.getusroi.elastic_search.exception.ESInitializationException;

/**
 * ESConfigHelper Class <br>
 * Helps in Initializing the properties file.
 * 
 * @author bizruntime
 */
public class ESConfigHelper {
	
	/**
	 * Loads the file path from the config file.
	 * @return properties
	 * @throws ESInitializationException
	 */
	public static Properties getElasticSearchConfigProperties()
			throws ESInitializationException {
		Properties properties = new Properties();
		InputStream input = ESConfigHelper.class.getClassLoader()
				.getResourceAsStream(ESConstant.ES_CONFIG_PROPERTIES_FILE);
		try {
			properties.load(input);
		} catch (IOException e) {
			// TODO ERROR CODE
			throw new ESInitializationException("Properties resource not found");
		}
		return properties;
	}

}
