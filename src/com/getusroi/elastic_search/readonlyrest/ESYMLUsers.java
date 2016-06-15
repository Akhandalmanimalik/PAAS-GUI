package com.getusroi.elastic_search.readonlyrest;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import com.getusroi.elastic_search.exception.ESConfigFileLoadException;
import com.getusroi.elastic_search.exception.ESInitializationException;
import com.getusroi.elastic_search.exception.ESServiceFailedException;
import com.getusroi.elastic_search.exception.WriteToYMLFailedException;
import com.getusroi.elastic_search.helper.ESConfigHelper;
import com.getusroi.elastic_search.helper.ESConstant;
import com.getusroi.paas.vo.PaasUserRegister;

/**
 * ESYMLUsers class <br>
 * This class adds the users to yml file,<br>
 * of elastic search and restarts the server, <br>
 * Each user is assigned with its own Index in kibana.
 * 
 * @author bizruntime
 */
public class ESYMLUsers {

	protected static Logger logger = LoggerFactory.getLogger(ESYMLUsers.class);

	private static Properties elasticSearchConfigProperties;

	/**
	 * Loads all the files from properties file.<br>
	 * 
	 * @return elasticSearchConfigProperties
	 * @throws ESConfigFileLoadException
	 */
	public static Properties loadElasticSearchConfigPropertiesFile()
			throws ESConfigFileLoadException {
		try {
			elasticSearchConfigProperties = ESConfigHelper
					.getElasticSearchConfigProperties();
		} catch (ESInitializationException e) {
			throw new ESConfigFileLoadException(
					"Not able to load the properties file : ");
		}
		return elasticSearchConfigProperties;
	}

	/**
	 * Write user details to elasticsearch.yml, <br>
	 * for auth access to ElasticSearch and kibana.
	 * 
	 * @throws WriteToYMLFailedException
	 */
	public void writeUserToYML(PaasUserRegister paasUserRegister) throws WriteToYMLFailedException {

		logger.debug("Write user to YML file");

		try {
			loadElasticSearchConfigPropertiesFile();
		} catch (ESConfigFileLoadException e) {
			throw new WriteToYMLFailedException(e.getMessage()
					+ ESConstant.ES_CONFIG_PROPERTIES_FILE);
		}

		Map<String, Object> data = new LinkedHashMap<String, Object>();
		data.put(ESConstant.ES_NAME, paasUserRegister.getEmail());//dynamic value of user will come in place of user3
		data.put(ESConstant.ES_TYPE, "allow");
		data.put(ESConstant.ES_AUTH, paasUserRegister.getEmail()+":"+paasUserRegister.getPassword());//dynamic value will come as username and password.
		data.put(ESConstant.ES_ACCESS, "ro");
		data.put(ESConstant.ES_INDICES,
				new String[] { ".kibana*", "google" });//in place of google dynamic values will come.

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(data);

		DumperOptions options = new DumperOptions();
		options.setIndent(5);
		options.setIndicatorIndent(3);
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.AUTO);

		FileWriter writer;
		try {
			writer = new FileWriter(
					elasticSearchConfigProperties
							.getProperty(ESConstant.ES_FILE_PATH),
					true);

			Yaml yaml = new Yaml(options);
			yaml.dump(list, writer);

			try {
				restartESServer();
			} catch (ESServiceFailedException e) {
				throw new WriteToYMLFailedException(e.getMessage());
			}

			logger.debug("DONE!");

		} catch (IOException e) {
			throw new WriteToYMLFailedException(
					"Unable to Write user details to YML file : "
							+ e.getMessage());
		}

	}

	/**
	 * Restarts the elastic search server.<br>
	 * To enable the auth access for newly added users in yml.
	 * 
	 * @throws ESServiceFailedException
	 */
	private void restartESServer() throws ESServiceFailedException {
		String[] cmd = {
				ESConstant.ES_BASH,
				"-c",
				ESConstant.ES_RUN_CMD
						+ elasticSearchConfigProperties
								.getProperty(ESConstant.ES_SHELL_FILE) };

		try {
			Process pb = Runtime.getRuntime().exec(cmd);
			String line;
			BufferedReader input = new BufferedReader(new InputStreamReader(
					pb.getInputStream()));

			while ((line = input.readLine()) != null) {
				logger.debug(line);
			}

			input.close();
		} catch (IOException e) {
			throw new ESServiceFailedException(
					"Unable to connect to ElasticSearch server ");
		}
	}

	//To remove
	public static void main(String[] args) {
		PaasUserRegister paasUserRegister= new PaasUserRegister();
		paasUserRegister.setEmail("test@gmail.com");
		paasUserRegister.setPassword("testpwd");
		try {
			new ESYMLUsers().writeUserToYML(paasUserRegister);
		} catch (WriteToYMLFailedException e) {
			logger.error(e.getMessage());
		}
	}
}
