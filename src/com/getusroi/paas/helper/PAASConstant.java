package com.getusroi.paas.helper;

public class PAASConstant {
	
	public static final String MYSQL_DB="mysql";
	public static final String VPC_PREFIX="vpc-";
	public static final String SUBNET_PREFIX="subnet-";
	public static final String ACL_PREFIX="acl-";
	public static final String VB_KEY="vb";
	public static final String INTERFACE1_KEY="if1";
	public static final String BRIDGE1_KEY="PP-OF:00:00:00:00:00:00:00:03-s3-eth2";
	public static final String INTERFACE2_KEY="if2";
	public static final String BRIDGE2_KEY="PP-OF:00:00:00:00:00:00:00:02-s2-eth1";
	public static final String ACL_PASS_ACTION_KEY="OUTPUT=2";
	public static final String ACL_OTHER_ACTION_KEY="drop";
	public static final String HTTPS_PROTOCOL_KEY="HTTPS_PROTOCOL_KEY";
	public static final String ALL_REPOSTORY_KEY="ALL_REPOSTORY_KEY";
	public static final String ALL_TAGS_KEY="ALL_TAGS_KEY";
	public static final String TENANT="tenant-";
	// Error code exception constants.
	public static final String ERROR_IN_SQL_SYNTAX = "1064";
	public static final String TABLE_NOT_EXIST = "1146";
	public static final String IMAGE_RESISTRY_NAME="IMAGE_RESISTRY_NAME";
	public static final String DOCKER_HUB_PROPERTY_FILE_KEY="dockerhubConfig.properties";
			//"index.docker.io";

}
