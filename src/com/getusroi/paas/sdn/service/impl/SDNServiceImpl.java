package com.getusroi.paas.sdn.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getusroi.paas.helper.PAASGenericHelper;
import com.getusroi.paas.helper.UnableToLoadPropertyFileException;
import com.getusroi.paas.sdn.service.SDNContant;
import com.getusroi.paas.vo.ImageRegistry;



public class SDNServiceImpl{
	 static final Logger logger = LoggerFactory.getLogger(SDNServiceImpl.class);
	 
	 /**
	  * This method is used to create volume
	  * @param postData : Data need to be posted
	  * @return boolean : true if volume creation success else false
	 * @throws SDNServiceImplException 
	  */	
	 public boolean createVolume(JSONObject  postData) throws SDNServiceImplException{
		 logger.debug(".createVolume method of SDNServiceImpl");
		 HttpURLConnection httpConnection=null;
		 boolean response=false;
		 try {
			Properties prop=PAASGenericHelper.getPropertyFile(SDNContant.NAS_PROPERTY_FILE_KEY);
			String baseurl=prop.getProperty(SDNContant.NAS_URL);
			String username=prop.getProperty(SDNContant.NAS_USERNAME);
			String password=prop.getProperty(SDNContant.NAS_PASSWORD);
			logger.debug("nas url : "+baseurl+", usename : "+username+", password : "+password);
			if((baseurl !=null && !(baseurl.isEmpty())) && (username !=null && !(username.isEmpty())) && (password !=null && !(password.isEmpty())) && (postData !=null )){
				try {
					response=httpDataRequest(baseurl.trim(), username.trim(), password.trim(),"POST", postData);
					return response;
				} catch (HTTPConnectionException e) {
					logger.debug("Error in getting the http connection");
					throw new SDNServiceImplException("Error in getting the http connection for createVolume ",e);
				}finally{
					closeHttpConnection(httpConnection);
				}
			}//end of if
		} catch (UnableToLoadPropertyFileException e) {
			logger.debug("Error in loading  the property file name : "+SDNContant.NAS_PROPERTY_FILE_KEY);
			throw new SDNServiceImplException("Error in loading  the property file name : "+SDNContant.NAS_PROPERTY_FILE_KEY,e);
		}	
		 return response;
	 }//end of
	 
	 /**
	  * This method is used create controller for sdn
	  * @param postData : JSONObject data need to be posted to create  controller
	  * @return boolean : true if volume creation success else false
	 * @throws SDNServiceImplException 
	  */
	 public boolean createController(JSONObject  postData) throws SDNServiceImplException{
		 logger.debug(".createController method of SDNServiceImpl");
		 HttpURLConnection httpConnection=null;
		 boolean response=false;
		 try {
			Properties prop=PAASGenericHelper.getPropertyFile(SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY);
			String url=prop.getProperty(SDNContant.SDN_CONTROLLER_URL);
			//url to create controller in SDN 
			String baseurl=url+""+prop.getProperty(SDNContant.SDN_GET_CONTROLLERS_URL_KEY);
			String username=prop.getProperty(SDNContant.SDN_CONTROLLER_USERNAME);
			String password=prop.getProperty(SDNContant.SDN_CONTROLLER_PASSWORD);
			logger.debug("controller url : "+baseurl+", usename : "+username+", password : "+password);
			if((baseurl !=null && !(baseurl.isEmpty())) && (username !=null && !(username.isEmpty())) && (password !=null && !(password.isEmpty())) && (postData !=null )){
				try {
					response=httpDataRequest(baseurl.trim(), username.trim(), password.trim(),"POST", postData);
					return response;
				} catch (HTTPConnectionException e) {
					logger.debug("Error in getting the http connection");
					throw new SDNServiceImplException("Error in getting the http connection for createController ",e);
				}finally{
					closeHttpConnection(httpConnection);
				}
			}//end of if
		} catch (UnableToLoadPropertyFileException e) {
			logger.debug("Error in loading  the property file name : "+SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY);
			throw new SDNServiceImplException("Error in loading  the property file name : "+SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY+" for createController ",e);
		}	
		 return response;
	 }
	 
	 /**
	  * This method is used create VTN for sdn
	  * @param postData : JSONObject data need to be posted to create  VTN
	  * @return boolean : true if volume creation success else false
	 * @throws SDNServiceImplException 
	  */
	 public boolean createVtn(JSONObject postData) throws SDNServiceImplException{
		 logger.debug(".createVtn method of SDNServiceImpl");
		 HttpURLConnection httpConnection=null;
		 boolean response=false;
		 try {
			Properties prop=PAASGenericHelper.getPropertyFile(SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY);
			String url=prop.getProperty(SDNContant.SDN_CONTROLLER_URL);
			//url to create controller in SDN 
			String baseurl=url+prop.getProperty(SDNContant.SDN_CONTROLLER_VTN_URL_KEY);
			String username=prop.getProperty(SDNContant.SDN_CONTROLLER_USERNAME);
			String password=prop.getProperty(SDNContant.SDN_CONTROLLER_PASSWORD);
			logger.debug("controller url : "+baseurl+", usename : "+username+", password : "+password);
			if((baseurl !=null && !(baseurl.isEmpty())) && (username !=null && !(username.isEmpty())) && (password !=null && !(password.isEmpty())) && (postData !=null )){
				try {
					response=httpDataRequest(baseurl.trim(), username.trim(), password.trim(),"POST", postData);
					return response;
				} catch (HTTPConnectionException e) {
					logger.debug("Error in getting the http connection");
					throw new SDNServiceImplException("Error in getting the http connection for createVtn ",e);
				}finally{
					closeHttpConnection(httpConnection);
				}
			}//end of if
		} catch (UnableToLoadPropertyFileException e) {
			logger.debug("Error in loading  the property file name : "+SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY);
			throw new SDNServiceImplException("Error in loading  the property file name : "+SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY+" for createVtn",e);
		}	
		 return response;
	 }//end of method createVtn
	 
	 
	 /**
	  * This method is used to get the VTN from SDN
	  * @param jsonData : JSON Object data to used to get the VTN
	  * @return boolean : true if volume creation success else false
	 * @throws SDNServiceImplException 
	  */
	 public boolean getVtn(JSONObject jsonData) throws SDNServiceImplException{
		 logger.debug(".getVtn method of SDNServiceImpl");
		 HttpURLConnection httpConnection=null;
		 boolean response=false;
		 try {
			Properties prop=PAASGenericHelper.getPropertyFile(SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY);
			String url=prop.getProperty(SDNContant.SDN_CONTROLLER_URL);
			//url to create controller in SDN 
			String baseurl=url+prop.getProperty(SDNContant.SDN_CONTROLLER_VTN_URL_KEY);
			String username=prop.getProperty(SDNContant.SDN_CONTROLLER_USERNAME);
			String password=prop.getProperty(SDNContant.SDN_CONTROLLER_PASSWORD);
			logger.debug("controller url : "+baseurl+", usename : "+username+", password : "+password);
			if((baseurl !=null && !(baseurl.isEmpty())) && (username !=null && !(username.isEmpty())) && (password !=null && !(password.isEmpty())) && (jsonData !=null )){
				try {
					response=httpDataRequest(baseurl.trim(), username.trim(), password.trim(),"GET", jsonData);
					return response;
				} catch (HTTPConnectionException e) {
					logger.debug("Error in getting the http connection");
					throw new SDNServiceImplException("Error in getting the http connection for getVtn ",e);
				}finally{
					closeHttpConnection(httpConnection);
				}
			}//end of if
		} catch (UnableToLoadPropertyFileException e) {
			logger.debug("Error in loading  the property file name : "+SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY);
			throw new SDNServiceImplException("Error in loading  the property file name : "+SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY+" for getVTN",e);
		}	
		 return response;
	 }
	 
	 /**
	  * This method is used create Virtual Bridge for sdn
	  * @param postData : JSONObject data need to be posted to create  Virtual Bridge
	  * @param vtnName : name of vtn in String
	  * @return boolean : true if volume creation success else false
	 * @throws SDNServiceImplException 
	  */
	 public boolean createVirtualBridge(JSONObject postData,String  vtnName) throws SDNServiceImplException{
		 logger.debug(".createVirtualBridge method of SDNServiceImpl");
		 HttpURLConnection httpConnection=null;
		 boolean response=false;
		 try {
			Properties prop=PAASGenericHelper.getPropertyFile(SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY);
			String url=prop.getProperty(SDNContant.SDN_CONTROLLER_URL);
			//url to create controller in SDN 
			String baseurl=url+prop.getProperty(SDNContant.SDN_VTNS_URI_KEY).trim()+vtnName+prop.getProperty(SDNContant.SDN_BRIDGE_URL_KEY).trim();
			String username=prop.getProperty(SDNContant.SDN_CONTROLLER_USERNAME);
			String password=prop.getProperty(SDNContant.SDN_CONTROLLER_PASSWORD);
			logger.debug("virtual bridge url : "+baseurl+", usename : "+username+", password : "+password);
			if((baseurl !=null && !(baseurl.isEmpty())) && (username !=null && !(username.isEmpty())) && (password !=null && !(password.isEmpty())) && (postData !=null )){
				try {
					response=httpDataRequest(baseurl.trim(), username.trim(), password.trim(),"POST", postData);
					return response;
				} catch (HTTPConnectionException e) {
					logger.debug("Error in getting the http connection");
					throw new SDNServiceImplException("Error in getting the http connection for getVtn ",e);
				}finally{
					closeHttpConnection(httpConnection);
				}
			}//end of if
		} catch (UnableToLoadPropertyFileException e) {
			logger.debug("Error in loading  the property file name : "+SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY);
			throw new SDNServiceImplException("Error in loading  the property file name : "+SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY+" for getVTN ",e);
		}	
		 return response;
	 }//end of method createVirtualBridge
	 
	 /**
	  * This method is used create Interface for sdn
	  * @param postData : JSONObject data need to be posted to create  Interface
	  * @param vtnName : name of vtn im String
	  * @param vbridgeName : bridge name in String
	  * @return boolean : true if volume creation success else false
	 * @throws SDNServiceImplException 
	  */
	 public boolean createInterfaces(JSONObject postData,String  vtnName,String  vbridgeName) throws SDNServiceImplException{
		 logger.debug(".createInterfaces method of SDNServiceImpl");
		 HttpURLConnection httpConnection=null;
		 boolean response=false;
		 try {
			Properties prop=PAASGenericHelper.getPropertyFile(SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY);
			String url=prop.getProperty(SDNContant.SDN_CONTROLLER_URL);
			//url to create controller in SDN 
			String baseurl=url+prop.getProperty(SDNContant.SDN_VTNS_URI_KEY).trim()+vtnName.trim()+prop.getProperty(SDNContant.SDN_BRIDGE_URI_KEY).trim()+vbridgeName.trim()+prop.getProperty(SDNContant.SDN_INTERFACE_URL_KEY).trim();
			String username=prop.getProperty(SDNContant.SDN_CONTROLLER_USERNAME);
			String password=prop.getProperty(SDNContant.SDN_CONTROLLER_PASSWORD);
			logger.debug("Interface url : "+baseurl+", usename : "+username+", password : "+password);
			if((baseurl !=null && !(baseurl.isEmpty())) && (username !=null && !(username.isEmpty())) && (password !=null && !(password.isEmpty())) && (postData !=null )){
				try {
					response=httpDataRequest(baseurl.trim(), username.trim(), password.trim(),"POST", postData);
					return response;
				} catch (HTTPConnectionException e) {
					logger.debug("Error in getting the http connection");
					throw new SDNServiceImplException("Error in getting the http connection for createInterfaces ",e);
				}finally{
					closeHttpConnection(httpConnection);
				}
			}//end of if
		} catch (UnableToLoadPropertyFileException e) {
			logger.debug("Error in loading  the property file name : "+SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY);
			throw new SDNServiceImplException("Error in loading  the property file name : "+SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY+" for createInterfaces",e);
		}	
		 return response;		 
	 }
	 
	 /**
	  * This method is used create Mapping for sdn
	  * @param postData : JSONObject data need to be posted to create  Mapping
	  * @param vtnName : name of vtn in String
	  * @param vbridgeName : virtual bridge name in String
	  * @param interfaceName : interface name is String
	  * @return boolean : true if volume creation success else false
	 * @throws SDNServiceImplException :  error in creating mapping for sdn
	  */
	 public boolean createMapping(JSONObject postData,String  vtnName,String  vbridgeName,String  interfacsesName) throws SDNServiceImplException{
		 logger.debug(".createMapping method of SDNServiceImpl");
		 HttpURLConnection httpConnection=null;
		 boolean response=false;
		 try {
			Properties prop=PAASGenericHelper.getPropertyFile(SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY);
			String url=prop.getProperty(SDNContant.SDN_CONTROLLER_URL);
			//url to create controller in SDN 
			String baseurl=url+prop.getProperty(SDNContant.SDN_VTNS_URI_KEY).trim()+vtnName.trim()+prop.getProperty(SDNContant.SDN_BRIDGE_URI_KEY).trim()+vbridgeName.trim()+prop.getProperty(SDNContant.SDN_INTERFACE_URI_KEY).trim()+interfacsesName.trim()+prop.getProperty(SDNContant.SDN_PORT_URL_KEY).trim();
			String username=prop.getProperty(SDNContant.SDN_CONTROLLER_USERNAME);
			String password=prop.getProperty(SDNContant.SDN_CONTROLLER_PASSWORD);
			logger.debug("Interface url : "+baseurl+", usename : "+username+", password : "+password);
			if((baseurl !=null && !(baseurl.isEmpty())) && (username !=null && !(username.isEmpty())) && (password !=null && !(password.isEmpty())) && (postData !=null )){
				try {
					response=httpDataRequest(baseurl.trim(), username.trim(), password.trim(),"POST", postData);
					return response;
				} catch (HTTPConnectionException e) {
					logger.debug("Error in getting the http connection");
					throw new SDNServiceImplException("Error in getting the http connection createMapping",e);
				}finally{
					closeHttpConnection(httpConnection);
				}
			}//end of if
		} catch (UnableToLoadPropertyFileException e) {
			logger.debug("Error in loading  the property file name : "+SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY);
			throw new SDNServiceImplException("Error in loading  the property file name : "+SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY+" for createMapping",e);
		}	
		 return response;		 
	 }
	 
	 /**
	  * This method is used create FlowList for sdn
	  * @param postData : JSONObject data need to be posted to create  FlowList
	  * @return boolean : true if volume creation success else false
	 * @throws SDNServiceImplException  : error in creating flow List
	  */
	 public boolean createFlowList(JSONObject  postData) throws SDNServiceImplException{
		 logger.debug(".createFlowList method of SDNServiceImpl");
		 HttpURLConnection httpConnection=null;
		 boolean response=false;
		 try {
			Properties prop=PAASGenericHelper.getPropertyFile(SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY);
			String url=prop.getProperty(SDNContant.SDN_CONTROLLER_URL);
			//url to create controller in SDN 
			String baseurl=url+prop.getProperty(SDNContant.SDN_FLOWLIST_URL_KEY).trim();
			String username=prop.getProperty(SDNContant.SDN_CONTROLLER_USERNAME);
			String password=prop.getProperty(SDNContant.SDN_CONTROLLER_PASSWORD);
			logger.debug("Interface url : "+baseurl+", usename : "+username+", password : "+password);
			if((baseurl !=null && !(baseurl.isEmpty())) && (username !=null && !(username.isEmpty())) && (password !=null && !(password.isEmpty())) && (postData !=null )){
				try {
					response=httpDataRequest(baseurl.trim(), username.trim(), password.trim(),"POST", postData);
					return response;
				} catch (HTTPConnectionException e) {
					logger.debug("Error in getting the http connection");
					throw new SDNServiceImplException("Error in getting the http connection createFlowList",e);
				}finally{
					closeHttpConnection(httpConnection);
				}
			}//end of if
		} catch (UnableToLoadPropertyFileException e) {
			logger.debug("Error in loading  the property file name : "+SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY);
			throw new SDNServiceImplException("Error in loading  the property file name : "+SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY+" for createFlowList",e);
		}	
		 return response;
	 }//end of method createFlowList
	 
	 /**
	  * This method is used create createFlowListEntry for sdn
	  * @param postData : JSONObject data need to be posted to create  createFlowListEntry
	  * @param flowList : flow list value in String
	  * @return boolean : true if volume creation success else false
	 * @throws SDNServiceImplException  : Error in createFlowListEntry
	  */
	 public boolean createFlowListEntry(JSONObject  postData,String  flowList) throws SDNServiceImplException{
		 logger.debug(".createFlowListEntry method of SDNServiceImpl");
		 HttpURLConnection httpConnection=null;
		 boolean response=false;
		 try {
			Properties prop=PAASGenericHelper.getPropertyFile(SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY);
			String url=prop.getProperty(SDNContant.SDN_CONTROLLER_URL);
			String baseurl=url+prop.getProperty(SDNContant.SDN_FLOWLIST_URI_KEY).trim()+flowList.trim()+prop.getProperty(SDNContant.SDN_FLOWLIST_ENTRY_URL_KEY).trim();
			String username=prop.getProperty(SDNContant.SDN_CONTROLLER_USERNAME);
			String password=prop.getProperty(SDNContant.SDN_CONTROLLER_PASSWORD);
			logger.debug("Interface url : "+baseurl+", usename : "+username+", password : "+password);
			if((baseurl !=null && !(baseurl.isEmpty())) && (username !=null && !(username.isEmpty())) && (password !=null && !(password.isEmpty())) && (postData !=null )){
				try {
					response=httpDataRequest(baseurl.trim(), username.trim(), password.trim(),"POST", postData);
					return response;
				} catch (HTTPConnectionException e) {
					logger.debug("Error in getting the http connection");
					throw new SDNServiceImplException("Error in getting the http connection createFlowListEntry",e);
				}finally{
					closeHttpConnection(httpConnection);
				}
			}//end of if
		} catch (UnableToLoadPropertyFileException e) {
			logger.debug("Error in loading  the property file name : "+SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY);
			throw new SDNServiceImplException("Error in loading  the property file name : "+SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY+" for createFlowListEntry",e);
		}	
		 return response; 
	 }//end of method createFlowListEntry
	 
	 /**
	  * This method is used add Registry for sdn
	  * @param postData : JSONObject data need to be posted to add  Registry
	  * @return boolean : true if volume creation success else false
	 * @throws SDNServiceImplException  
	  */
	 public boolean addRegistry(JSONObject  postData) throws SDNServiceImplException {
		 logger.debug(".addRegistry method of SDNServiceImpl");
		 HttpURLConnection httpConnection=null;
		 boolean response=false;
		 try {
			Properties prop=PAASGenericHelper.getPropertyFile(SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY);
			String url=prop.getProperty(SDNContant.SDN_CONTROLLER_URL);
			String baseurl=url+prop.getProperty(SDNContant.SDN_GET_CONTROLLERS_URL_KEY).trim();
			String username=prop.getProperty(SDNContant.SDN_CONTROLLER_USERNAME);
			String password=prop.getProperty(SDNContant.SDN_CONTROLLER_PASSWORD);
			logger.debug("Interface url : "+baseurl+", usename : "+username+", password : "+password);
			if((baseurl !=null && !(baseurl.isEmpty())) && (username !=null && !(username.isEmpty())) && (password !=null && !(password.isEmpty())) && (postData !=null )){
				try {
					response=httpDataRequest(baseurl.trim(), username.trim(), password.trim(),"POST", postData);
					return response;
				} catch (HTTPConnectionException e) {
					logger.debug("Error in getting the http connection ");
					throw new SDNServiceImplException("Error in getting the http connection for addRegistry");
				}finally{
					closeHttpConnection(httpConnection);
				}
			}//end of if
		} catch (UnableToLoadPropertyFileException e) {
			logger.debug("Error in loading  the property file name : "+SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY);
			throw new SDNServiceImplException("Error in loading  the property file name : "+SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY+" for addRegistry",e);
		}	
		 return response; 		 

	 }//end of method addRegistry
	 
	 /**
	  * This method is used create  Firewall rule for sdn
	  * @param postData : JSONObject data need to be posted to create  Firewall rule
	  * @return boolean : true if volume creation success else false
	 * @throws SDNServiceImplException : Error in createFirewallRule in sdn
	  */
	 public boolean createFirewallRule(JSONObject postData) throws SDNServiceImplException{
		 logger.debug(".createFirewallRule method of SDNServiceImpl");
		 HttpURLConnection httpConnection=null;
		 boolean response=false;
		 try {
			Properties prop=PAASGenericHelper.getPropertyFile(SDNContant.SDN_FIREWALL_PROPERTY_FILE_KEY);
			String url=prop.getProperty(SDNContant.SDN_FIREWALL_URL).trim();
			//url to create controller in SDN 
			String baseurl=url+prop.getProperty(SDNContant.SDN_FIREWALL_URI).trim();
			String username=prop.getProperty(SDNContant.SDN_FIREWALL_USERNAME);
			String password=prop.getProperty(SDNContant.SDN_FIREWALL_PASSWORD);
			logger.debug("Firewall url : "+baseurl+", usename : "+username+", password : "+password);
			if((baseurl !=null && !(baseurl.isEmpty())) && (username !=null && !(username.isEmpty())) && (password !=null && !(password.isEmpty())) && (postData !=null )){
				try {
					response=httpDataRequest(baseurl.trim(), username.trim(), password.trim(),"POST", postData);
					return response;
				} catch (HTTPConnectionException e) {
					logger.debug("Error in getting the http connection createFirewallRule");
					throw new SDNServiceImplException("Error in getting the http connection for createFirewallRule");
				}finally{
					closeHttpConnection(httpConnection);
				}
			}//end of if
		} catch (UnableToLoadPropertyFileException e) {
			logger.debug("Error in loading  the property file name : "+SDNContant.SDN_FIREWALL_PROPERTY_FILE_KEY);
			throw new SDNServiceImplException("Error in loading  the property file name : "+SDNContant.SDN_FIREWALL_PROPERTY_FILE_KEY+" for createFirewallRule",e);
		}	
		 return response; 
	 }//end of method createFirewallRule
	 
	 
	 /**
	  * This method is used to install flow for sdn
	  * @param nodeId : node ID in string
	  * @param flowName : flow name in string
	  * @param postData : JSONObject data is need to send request
	  * @return boolean : true if volume creation success else false
	 * @throws SDNServiceImplException : Error in installFlow for sdn
	  */
	 public boolean installFlow(String nodeId, String flowName,JSONObject postData) throws SDNServiceImplException{
		 logger.debug(".installFlow method of SDNServiceImpl");
		 HttpURLConnection httpConnection=null;
		 boolean response=false;
		 try {
			Properties prop=PAASGenericHelper.getPropertyFile(SDNContant.SDN_FLOW_PROPERTY_FILE_KEY);
			String url=prop.getProperty(SDNContant.SDN_FLOW_URL).trim();
			//url to create controller in SDN 
			String baseurl=url+prop.getProperty(SDNContant.SDN_FLOW_DEFAULT_NODE_URI_KEY).trim()+nodeId+prop.getProperty(SDNContant.SDN_FLOW_STATIC_FLOW_URI_KEY).trim()+flowName.trim();
			String username=prop.getProperty(SDNContant.SDN_FLOW_USERNAME_KEY);
			String password=prop.getProperty(SDNContant.SDN_FLOW_PASSWORD_KEY);
			logger.debug("flow url : "+baseurl+", usename : "+username+", password : "+password);
			if((baseurl !=null && !(baseurl.isEmpty())) && (username !=null && !(username.isEmpty())) && (password !=null && !(password.isEmpty())) && (postData !=null )){
				try {
					response=httpDataRequest(baseurl.trim(), username.trim(), password.trim(),"PUT", postData);
					return response;
				} catch (HTTPConnectionException e) {
					logger.debug("Error in getting the http connection installFlow");
					throw new SDNServiceImplException("Error in getting the http connection for installFlow");
				}finally{
					closeHttpConnection(httpConnection);
				}
			}//end of if
		} catch (UnableToLoadPropertyFileException e) {
			logger.debug("Error in loading  the property file name : "+SDNContant.SDN_FLOW_PROPERTY_FILE_KEY);
			throw new SDNServiceImplException("Error in loading  the property file name : "+SDNContant.SDN_FLOW_PROPERTY_FILE_KEY+" for installFlow",e);
		}	
		 return response; 		 
	 }//end of method installFlow
	 
	 /**
	  * This method is used to delete flow for sdn
	  * @param nodeId : node ID in string
	  * @param flowName : flow name in string
	  * @return boolean : true if volume creation success else false
	 * @throws SDNServiceImplException : Error in deleteFlow of sdn
	  */
	 public boolean deleteFlow(String flowName, String nodeId) throws SDNServiceImplException{
		 logger.debug(".deleteFlow method of SDNServiceImpl");
		 HttpURLConnection httpConnection=null;
		 boolean response=false;
		 try {
			Properties prop=PAASGenericHelper.getPropertyFile(SDNContant.SDN_FLOW_PROPERTY_FILE_KEY);
			String url=prop.getProperty(SDNContant.SDN_FLOW_URL).trim();
			//url to create controller in SDN 
			String baseurl=url+prop.getProperty(SDNContant.SDN_FLOW_DEFAULT_NODE_URI_KEY).trim()+nodeId+prop.getProperty(SDNContant.SDN_FLOW_STATIC_FLOW_URI_KEY).trim()+flowName.trim();
			String username=prop.getProperty(SDNContant.SDN_FLOW_USERNAME_KEY);
			String password=prop.getProperty(SDNContant.SDN_FLOW_PASSWORD_KEY);
			logger.debug("flow url : "+baseurl+", usename : "+username+", password : "+password);
			if((baseurl !=null && !(baseurl.isEmpty())) && (username !=null && !(username.isEmpty())) && (password !=null && !(password.isEmpty()))){
				try {
					response=httpDataRequest(baseurl.trim(), username.trim(), password.trim(),"PUT", null);
					return response;
				} catch (HTTPConnectionException e) {
					logger.debug("Error in getting the http connection deleteFlow");
					throw new SDNServiceImplException("Error in getting the http connection for  deleteFlow");
				}finally{
					closeHttpConnection(httpConnection);
				}
			}//end of if
		} catch (UnableToLoadPropertyFileException e) {
			logger.debug("Error in loading  the property file name : "+SDNContant.SDN_FLOW_PROPERTY_FILE_KEY);
			throw new SDNServiceImplException("Error in loading  the property file name : "+SDNContant.SDN_FLOW_PROPERTY_FILE_KEY+" for deleteFlow",e);
		}	
		 return response; 		 
	 }//end of method deleteFlow
	 
	/**
	 * This method is used for action for SDN
	 * @param postData : Data to be posted in JSONObject 
	 * @param vtnName : vtn name in String
	 * @param vbridgeName : virtual bridge in String
	 * @param interfacsesName : interface name in String
	 * @return boolean : true if volume creation success else false
	 * @throws SDNServiceImplException : error in paasActionType of sdn
	 */
	 public boolean paasActionType(JSONObject  postData,String  vtnName,String vbridgeName,String interfacsesName) throws SDNServiceImplException{
		 logger.debug(".paasActionType method of SDNServiceImpl");
		 HttpURLConnection httpConnection=null;
		 boolean response=false;
		 try {
			Properties prop=PAASGenericHelper.getPropertyFile(SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY);
			String url=prop.getProperty(SDNContant.SDN_CONTROLLER_URL);
			String baseurl=url+prop.getProperty(SDNContant.SDN_VTNS_URI_KEY).trim()+vtnName.trim()+prop.getProperty(SDNContant.SDN_BRIDGE_URI_KEY).trim()+vbridgeName.trim()+prop.getProperty(SDNContant.SDN_INTERFACE_URI_KEY).trim()+interfacsesName.trim()+prop.getProperty(SDNContant.SDN_Flow_ACTION_TYPE_KEY).trim();
			String username=prop.getProperty(SDNContant.SDN_CONTROLLER_USERNAME);
			String password=prop.getProperty(SDNContant.SDN_CONTROLLER_PASSWORD);
			logger.debug("action type  url : "+baseurl+", usename : "+username+", password : "+password);
			if((baseurl !=null && !(baseurl.isEmpty())) && (username !=null && !(username.isEmpty())) && (password !=null && !(password.isEmpty())) && (postData !=null )){
				try {
					response=httpDataRequest(baseurl.trim(), username.trim(), password.trim(),"PUT", postData);
					return response;
				} catch (HTTPConnectionException e) {
					logger.debug("Error in getting the http connection paasActionType");
					throw new SDNServiceImplException("Error in getting the http connection for  paasActionType");
				}finally{
					closeHttpConnection(httpConnection);
				}
			}//end of if
		} catch (UnableToLoadPropertyFileException e) {
			logger.debug("Error in loading  the property file name : "+SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY);
			throw new SDNServiceImplException("Error in loading  the property file name : "+SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY+" for paasActionType ",e);
		}	
		 return response;		 
	 }//end of method paasActionType
	 
	 /**
		 * This method is used for drop action for SDN
		 * @param postData : Data to be posted in JSONObject 
		 * @param vtnName : vtn name in String
		 * @param vbridgeName : virtual bridge in String
		 * @param interfacsesName : interface name in String
		 * @return boolean : true if volume creation success else false
	 * @throws SDNServiceImplException : Error in dropActionType of sdn
		 */
	 public boolean dropActionType(JSONObject  postData,String  vtnName,String vbridgeName,String interfacsesName) throws SDNServiceImplException{
		 logger.debug(".dropActionType method of SDNServiceImpl");
		 HttpURLConnection httpConnection=null;
		 boolean response=false;
		 try {
			Properties prop=PAASGenericHelper.getPropertyFile(SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY);
			String url=prop.getProperty(SDNContant.SDN_CONTROLLER_URL);
			String baseurl=url+prop.getProperty(SDNContant.SDN_VTNS_URI_KEY).trim()+vtnName.trim()+prop.getProperty(SDNContant.SDN_BRIDGE_URI_KEY).trim()+vbridgeName.trim()+prop.getProperty(SDNContant.SDN_INTERFACE_URI_KEY).trim()+interfacsesName.trim()+prop.getProperty(SDNContant.SDN_FLOW_DROP_ACTION_KEY).trim();
			String username=prop.getProperty(SDNContant.SDN_CONTROLLER_USERNAME);
			String password=prop.getProperty(SDNContant.SDN_CONTROLLER_PASSWORD);
			logger.debug("drop action type url : "+baseurl+", usename : "+username+", password : "+password);
			if((baseurl !=null && !(baseurl.isEmpty())) && (username !=null && !(username.isEmpty())) && (password !=null && !(password.isEmpty())) && (postData !=null )){
				try {
					response=httpDataRequest(baseurl.trim(), username.trim(), password.trim(),"POST", postData);
					return response;
				} catch (HTTPConnectionException e) {
					logger.debug("Error in getting the http connection dropActionType");
					throw new SDNServiceImplException("Error in getting the http connection for  dropActionType");
				}finally{
					closeHttpConnection(httpConnection);
				}
			}//end of if
		} catch (UnableToLoadPropertyFileException e) {
			logger.debug("Error in loading  the property file name : "+SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY);
			throw new SDNServiceImplException("Error in loading  the property file name : "+SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY+" for dropActionType ",e);
		}	
		 return response;		
	 }//end of method dropActionType
	 
	 /**
	  * This method is used to create docker Container id in sdn
	  * @param jsonData : JSONObject data need to create docker container 
	  * @return boolean : true if volume creation success else false
	  * @throws SDNServiceImplException : Error in getDockerContainerId of sdn
	  */
	 public boolean getDockerContainerId(JSONObject jsonData) throws SDNServiceImplException{
		 logger.debug(".dropActionType method of SDNServiceImpl");
		 HttpURLConnection httpConnection=null;
		 boolean response=false;
		 try {
			Properties prop=PAASGenericHelper.getPropertyFile(SDNContant.SDN_DOCKER_PROPERTY_FILE_KEY);
			String url=prop.getProperty(SDNContant.SDN_DOCKER_URL_KEY);
			//url to get docker container in SDN 
			String baseurl=url+prop.getProperty(SDNContant.SDN_DOCKER_CONTAINER_KEY).trim();			
			logger.debug("drop action type url : "+baseurl);
			if((jsonData !=null )){
				try {
					response=httpDataRequestWithoutAuthentication(baseurl.trim(),"GET", jsonData);
					return response;
				} catch (HTTPConnectionException e) {
					logger.debug("Error in getting the http connection getDockerContainerId");
					throw new SDNServiceImplException("Error in getting the http connection for  getDockerContainerId");
				}finally{
					closeHttpConnection(httpConnection);
				}
			}//end of if
		} catch (UnableToLoadPropertyFileException e) {
			logger.debug("Error in loading  the property file name : "+SDNContant.SDN_DOCKER_PROPERTY_FILE_KEY);
			throw new SDNServiceImplException("Error in loading  the property file name : "+SDNContant.SDN_DOCKER_PROPERTY_FILE_KEY+" for getDockerContainerId ",e);
		}	
		 return response;		
	 }
	 
	 /**
	  * This method is used to create virtual bridge interface flow filter for sdn
	  * @param postData : Data to be posted in JSONObject 
	  * @param vtnName : vtn name in String
	  * @param vbridgeName : virtual bridge in String
	  * @param interfacsesName : interface name in String
	  * @return boolean : true if volume creation success else false
	 * @throws SDNServiceImplException : Error in virtualBridgeInterfacesFlowFilter sdn
	  */
	 public boolean virtualBridgeInterfacesFlowFilter(JSONObject  postData,String  vtnName ,String  vbridgeName,String  interfacsesName) throws SDNServiceImplException{
		 logger.debug(".virtualBridgeInterfacesFlowFilter method of SDNServiceImpl");
		 HttpURLConnection httpConnection=null;
		 boolean response=false;
		 try {
			Properties prop=PAASGenericHelper.getPropertyFile(SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY);
			String url=prop.getProperty(SDNContant.SDN_CONTROLLER_URL);
			String baseurl=url+prop.getProperty(SDNContant.SDN_VTNS_URI_KEY).trim()+vtnName.trim()+prop.getProperty(SDNContant.SDN_BRIDGE_URI_KEY).trim()+vbridgeName.trim()+prop.getProperty(SDNContant.SDN_INTERFACE_URI_KEY).trim()+interfacsesName.trim()+prop.getProperty(SDNContant.SDN_FLOW_FILTER_KEY).trim();
			String username=prop.getProperty(SDNContant.SDN_CONTROLLER_USERNAME);
			String password=prop.getProperty(SDNContant.SDN_CONTROLLER_PASSWORD);
			logger.debug("virtualBridgeInterfacesFlowFilter type  url : "+baseurl+", usename : "+username+", password : "+password);
			if((baseurl !=null && !(baseurl.isEmpty())) && (username !=null && !(username.isEmpty())) && (password !=null && !(password.isEmpty())) && (postData !=null )){
				try {
					response=httpDataRequest(baseurl.trim(), username.trim(), password.trim(),"POST", postData);
					return response;
				} catch (HTTPConnectionException e) {
					logger.debug("Error in getting the http connection virtualBridgeInterfacesFlowFilter");
					throw new SDNServiceImplException("Error in getting the http connection for  virtualBridgeInterfacesFlowFilter");
				}finally{
					closeHttpConnection(httpConnection);
				}
			}//end of if
		} catch (UnableToLoadPropertyFileException e) {
			logger.debug("Error in loading  the property file name : "+SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY);
			throw new SDNServiceImplException("Error in loading  the property file name : "+SDNContant.SDN_CONTROLLER_PROPERTY_FILE_KEY+" for virtualBridgeInterfacesFlowFilter ",e);
		}	
		 return response;	
	 }//end of method virtualBridgeInterfacesFlowFilter
	 
	 /**
	  * This method is used to get the user details
	  * @param username : username in String
	  * @param password : password in String
	  * @param baseURL : baseUrl in String
	  * @return boolean : true if volume creation success else false
	 * @throws SDNServiceImplException : Error in getUserDetails 
	  */
	 public boolean getUserDetails(String username, String password,String baseURL) throws SDNServiceImplException{
		 logger.debug(".getUserDetails method of SDNServiceImpl");
		 HttpURLConnection httpConnection=null;
		 boolean response=false;
		 baseURL = "https://"+baseURL + "/v1/users/";		
		logger.debug("getUserDetails type  url : "+baseURL+", usename : "+username+", password : "+password);
		if((baseURL !=null && !(baseURL.isEmpty())) && (username !=null && !(username.isEmpty())) && (password !=null && !(password.isEmpty()))){
				try {
					response=httpDataRequest(baseURL.trim(), username.trim(), password.trim(),"GET", null);
					return response;
				} catch (HTTPConnectionException e) {
					logger.debug("Error in getting the http connection getUserDetails");
					throw new SDNServiceImplException("Error in getting the http connection for  getUserDetails");
				}finally{
					closeHttpConnection(httpConnection);
				}
			}//end of if
		
		 return response;
	 }//end of method getUserDetails
	 
	 /**
	  * This method is used to get user Details Registry
	  * @param imageRegistry : ImageRegistry Object 
	  * @return boolean : true if volume creation success else false
	 * @throws SDNServiceImplException : Error in getUserDetailsRegistry
	  */
	 public boolean getUserDetailsRegistry(ImageRegistry imageRegistry) throws SDNServiceImplException{
		 logger.debug(".getUserDetails method of SDNServiceImpl");
		 HttpURLConnection httpConnection=null;
		 boolean response=false;
		 String baseURL = "https://"+imageRegistry.getLocation() + "/v1/users/";		
		logger.debug("getUserDetailsRegistry   url : "+baseURL);
		if((baseURL !=null && !(baseURL.isEmpty()))){
			try {
				response=httpDataRequestWithoutAuthentication(baseURL.trim(),"GET", null);
				return response;
			} catch (HTTPConnectionException e) {
				logger.debug("Error in getting the http connection getUserDetailsRegistry");
				throw new SDNServiceImplException("Error in getting the http connection for  getUserDetailsRegistry");
			}finally{
				closeHttpConnection(httpConnection);
			}
		}//end of if
		return response;
	 }//end of  method getUserDetailsRegistry
	 
	 /**
	  * This method is used to post data using rest client without authenticating
	  * @param baseURL : base url in String
	  * @param requestMethod : http request method in String
	  * @param jsonData : JSONObject data need to be posted
	  * @return boolean : true if success or false otherwise
	  * @throws HTTPConnectionException : Error in connection to specified url using http client
	  */
	private boolean httpDataRequestWithoutAuthentication(String baseURL,String requestMethod,JSONObject jsonData) throws HTTPConnectionException{
		logger.debug(".postData method of SDNServiceImpl");
		HttpURLConnection httpConnection=null;
		URL url=null;
		boolean response=false;
		try {
			//url to connect to
			url = new URL(baseURL);				
			try {
				// Create Http connection
				httpConnection = (HttpURLConnection) url.openConnection();
				// Set connection properties
				httpConnection.setRequestMethod(requestMethod);
				httpConnection.setRequestProperty("Authorization", "Basic");
				httpConnection.setRequestProperty("Content-Type", "application/json");
				if(jsonData!=null){
				httpConnection.setUseCaches(false);
				httpConnection.setDoInput(true);
				httpConnection.setDoOutput(true);
				// Set Post Data
				OutputStream os = httpConnection.getOutputStream();
				os.write(jsonData.toString().getBytes());
				os.close();
				}
				
				response=getHttpResponseStatus(httpConnection);
				return response;
			} catch (IOException e) {
				logger.error("Unable to connect using http connection for url:"+url+" for http method : "+requestMethod);
				throw new HTTPConnectionException("Unable to connect using http connection for url:"+url+" for http method : "+requestMethod, e);
			}
		} catch (MalformedURLException e) {
			logger.error("URL used to connect is not valid : "+url);
			throw new HTTPConnectionException("URL used to connect is not valid : "+url, e);
		}		
	}//end of method httpDataRequest
	 
	 /**
	  * This method is used to post data using rest client 
	  * @param baseURL : base url in String
	  * @param username : username in String
	  * @param password : password in String
	  * @param requestMethod : http request method in String
	  * @param jsonData : JSONObject data need to be posted
	  * @return boolean : true if success or false otherwise
	  * @throws HTTPConnectionException : Error in connection to specified url using http client
	  */
	private boolean httpDataRequest(String baseURL,String username,String password,String requestMethod,JSONObject jsonData) throws HTTPConnectionException{
		logger.debug(".postData method of SDNServiceImpl");
		HttpURLConnection httpConnection=null;
		URL url=null;
		boolean response=false;
		try {
			//url to connect to
			url = new URL(baseURL);			
			// Create authentication string and encode it to Base64
			String authStr = username.trim()+ ":" +password.trim();
			String encodedAuthStr = Base64.encodeBase64String(authStr.getBytes());	
			logger.debug("authentication : "+authStr+", encoded authentication : "+encodedAuthStr);
			try {
				// Create Http connection
				httpConnection = (HttpURLConnection) url.openConnection();
				// Set connection properties
				httpConnection.setRequestMethod(requestMethod);
				httpConnection.setRequestProperty("Authorization", "Basic"
						+ encodedAuthStr);
				httpConnection.setRequestProperty("Content-Type", "application/json");
				if(jsonData!=null){
				httpConnection.setUseCaches(false);
				httpConnection.setDoInput(true);
				httpConnection.setDoOutput(true);
				// Set Post Data
				OutputStream os = httpConnection.getOutputStream();
				os.write(jsonData.toString().getBytes());
				os.close();
				}
				
				response=getHttpResponseStatus(httpConnection);
				return response;
			} catch (IOException e) {
				logger.error("Unable to connect using http connection for url:"+url+", usename : "+username+", password : "+password+" for http method : "+requestMethod);
				throw new HTTPConnectionException("Unable to connect using http connection for url:"+url+", usename : "+username+", password : "+password+" for http method : "+requestMethod, e);
			}
		} catch (MalformedURLException e) {
			logger.error("URL used to connect is not valid : "+url);
			throw new HTTPConnectionException("URL used to connect is not valid : "+url, e);
		}		
	}//end of method httpDataRequest
	
	/**
	 * This method is used to give response of httpclient, true if success or false otherwise
	 * @param httpConnection : HttpUrlConnection Object 
	 * @return boolean : true if success or false otherwise
	 * @throws HTTPConnectionException : Error in getting the reponse code
	 */
	private boolean getHttpResponseStatus(HttpURLConnection httpConnection) throws HTTPConnectionException{
		logger.debug(".getHttpResponseStatus method of SDNServiceImpl");
		boolean response=false;
		int status;
		try {
			status = httpConnection.getResponseCode();
			logger.debug("status : "+status);
			if (status == SDNContant.CREATED) {
				logger.debug(" added Successfully");
				response=true;
				return response;
			} else {
				logger.debug("Failed to add " +status);
				response=false;
				return response;
			}
		} catch (IOException e) {
			logger.error("Error in getting the reponse code");
			throw new HTTPConnectionException("Error in getting the reponse code", e);
		}					
	}//end of method getHttpResponseStatus
	
	private void closeHttpConnection(HttpURLConnection httpConnection){
		logger.debug(".closeHttpConnection method of SDNServiceImpl");
		if (httpConnection != null)
			httpConnection.disconnect();
	}//end of method closeHttpConnection

}
