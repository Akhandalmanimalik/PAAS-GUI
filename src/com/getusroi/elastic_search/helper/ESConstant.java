package com.getusroi.elastic_search.helper;

/**
 * ElasticSearch constant class <br>
 * 
 * @author bizruntime
 */
public class ESConstant {

	// Path to config file.
	public static final String ES_CONFIG_PROPERTIES_FILE = "elasticsearch.properties";
	public static final String ES_FILE_PATH = "filePath";

	// Key User details to be given to yml.
	public static final String ES_NAME = "name";
	public static final String ES_TYPE = "type";
	public static final String ES_AUTH = "auth_key";
	public static final String ES_ACCESS = "kibana_access";
	public static final String ES_INDICES = "indices";

	// CMD to run shell file which has sudo. 
	// bizruntime@123 is the system password where elasticsearch is running.
	public static final String ES_BASH = "/bin/bash";
	public static final String ES_RUN_CMD = "echo bizruntime@123 | sudo -S sh ";

	// Path to shell file.
	public static final String ES_SHELL_FILE = "shellFilePath";
}
