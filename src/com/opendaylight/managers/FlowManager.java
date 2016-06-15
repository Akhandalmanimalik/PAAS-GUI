package com.opendaylight.managers;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jettison.json.JSONObject;

import com.opendaylight.settings.ODL;

public class FlowManager {

	// REST NBI Hydrogen for FlowGrogrammerNorthbound. More details at
	// http://opendaylight.nbi.sdngeeks.com/
	private static String FLOW_PROGRAMMER_REST_API = "/controller/nb/v2/flowprogrammer/default/node/OF/";
	private  static  String  Controller_PROGRAMMER_REST_API="/vtn-webapi/controllers.json";
	private  static  String  VTN_PROGRAMMER_REST_API="/vtn-webapi/vtns.json";
	private  static  String  VTN_PROGRAMMER_GET_REST_API="/vtn-webapi/vtns/detail.json";
    //private  static  String  VBridge_PROGRAMMER_REST_API="/vtn-webapi/vtns/vtn10/vbridges.json";
	private  static  String  Interfaces_PROGRAMMER_REST_API="/vtn-webapi/vtns/vtn10/vbridges/vBridge10/interfaces.json";
//	private  static  String  Mapping_PROGRAMMER_REST_API="/vtn-webapi/vtns/vtn_one/vbridges/vbr_two/interfaces/if1/portmap.json";
    
	private static String SDN_FIREWALL_Rule = "/restconf/config/sdnfirewall:rule-registry/";
	// HTTP statuses for checking the call output
	private static final int NO_CONTENT = 204;
	private static final int CREATED = 201;
	private static final int OK = 200;
	public  static  boolean  createVolume(JSONObject  postData){
		HttpURLConnection connection = null;
		int callStatus = 0;

		// Creating the actual URL to call
		String baseURL = ODL.freeNasURL ;
		
		try {

			// Create URL = base URL + container
			URL url = new URL(baseURL);

			// Create authentication string and encode it to Base64
			String authStr = ODL.FreeNasUSERNAME + ":" + ODL.FreeNasPASSWORD;
			String encodedAuthStr = Base64.encodeBase64String(authStr
					.getBytes());

			// Create Http connection
			connection = (HttpURLConnection) url.openConnection();

			// Set connection properties
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", "Basic "
					+ encodedAuthStr);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Set Post Data
						OutputStream os = connection.getOutputStream();
						os.write(postData.toString().getBytes());
						os.close();



			// Getting the response code
			callStatus = connection.getResponseCode();

		} catch (Exception e) {
			System.err.println("Unexpected error while volume installation.. "
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (connection != null)
				connection.disconnect();
		}

		if (callStatus == CREATED) {
			System.out.println("FreeNas volume created Successfully");
			return true;
		} else {
			System.err.println("Failed to create Controller.. " + callStatus);
			return false;
		}
		
		
		
	}
	public  static  boolean  createController(JSONObject  postData){
		HttpURLConnection connection = null;
		int callStatus = 0;

		// Creating the actual URL to call
		String baseURL = ODL.URL1 + Controller_PROGRAMMER_REST_API ;
		
		try {

			// Create URL = base URL + container
			URL url = new URL(baseURL);

			// Create authentication string and encode it to Base64
			String authStr = ODL.USERNAME1 + ":" + ODL.PASSWORD1;
			String encodedAuthStr = Base64.encodeBase64String(authStr
					.getBytes());

			// Create Http connection
			connection = (HttpURLConnection) url.openConnection();

			// Set connection properties
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", "Basic "
					+ encodedAuthStr);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Set Post Data
						OutputStream os = connection.getOutputStream();
						os.write(postData.toString().getBytes());
						os.close();



			// Getting the response code
			callStatus = connection.getResponseCode();

		} catch (Exception e) {
			System.err.println("Unexpected error while flow installation.. "
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (connection != null)
				connection.disconnect();
		}

		if (callStatus == CREATED) {
			System.out.println("Contoller created Successfully");
			return true;
		} else {
			System.err.println("Failed to create Controller.. " + callStatus);
			return false;
		}
		
		
		
	}
	public  static  boolean  createVtn(JSONObject  postData){
		HttpURLConnection connection = null;
		int callStatus = 0;

		// Creating the actual URL to call
		String baseURL = ODL.URL1 + VTN_PROGRAMMER_REST_API ;
		
		try {

			// Create URL = base URL + container
			URL url = new URL(baseURL);

			// Create authentication string and encode it to Base64
			String authStr = ODL.USERNAME1 + ":" + ODL.PASSWORD1;
			String encodedAuthStr = Base64.encodeBase64String(authStr
					.getBytes());

			// Create Http connection
			connection = (HttpURLConnection) url.openConnection();

			// Set connection properties
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", "Basic "
					+ encodedAuthStr);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Set Post Data
						OutputStream os = connection.getOutputStream();
						os.write(postData.toString().getBytes());
						os.close();



			// Getting the response code
			callStatus = connection.getResponseCode();

		} catch (Exception e) {
			System.err.println("Unexpected error while flow installation.. "
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (connection != null)
				connection.disconnect();
		}

		if (callStatus == CREATED) {
			System.out.println("VTN created Successfully");
			return true;
		} else {
			System.err.println("Failed to create VTN.. " + callStatus);
			return false;
		}
		
		
		
	}
	public  static  boolean  getVtn(JSONObject getVTNData){
		HttpURLConnection connection = null;
		int callStatus = 0;

		// Creating the actual URL to call
		String baseURL = ODL.URL1 + VTN_PROGRAMMER_GET_REST_API ;
		
		try {

			// Create URL = base URL + container
			URL url = new URL(baseURL);

			// Create authentication string and encode it to Base64
			String authStr = ODL.USERNAME1 + ":" + ODL.PASSWORD1;
			String encodedAuthStr = Base64.encodeBase64String(authStr
					.getBytes());

			// Create Http connection
			connection = (HttpURLConnection) url.openConnection();

			// Set connection properties
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Authorization", "Basic "
					+ encodedAuthStr);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Set Post Data
						OutputStream os = connection.getOutputStream();
						os.write(getVTNData.toString().getBytes());
						os.close();



			// Getting the response code
			callStatus = connection.getResponseCode();

		} catch (Exception e) {
			System.err.println("Unexpected error while flow installation.. "
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (connection != null)
				connection.disconnect();
		}

		if (callStatus == CREATED) {
			System.out.println("VTN created Successfully");
			return true;
		} else {
			System.err.println("Failed to create VTN.. " + callStatus);
			return false;
		}
		
		
		
	}
	
	public  static  boolean  getDockerContainerId(JSONObject getVTNData){
		HttpURLConnection connection = null;
		int callStatus = 0;

		// Creating the actual URL to call
		String baseURL = ODL.URL4 + "containers/json" ;
		
		try {

			// Create URL = base URL + container
			URL url = new URL(baseURL);

			// Create authentication string and encode it to Base64
			
		

			// Create Http connection
			connection = (HttpURLConnection) url.openConnection();

			// Set connection properties
			connection.setRequestMethod("GET");
			
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Set Post Data
						OutputStream os = connection.getOutputStream();
						os.write(getVTNData.toString().getBytes());
						os.close();



			// Getting the response code
			callStatus = connection.getResponseCode();

		} catch (Exception e) {
			System.err.println("Unexpected error while flow installation.. "
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (connection != null)
				connection.disconnect();
		}

		if (callStatus == CREATED) {
			System.out.println("get  container");
			return true;
		} else {
			System.err.println("Failed to create VTN.. " + callStatus);
			return false;
		}
		
		
		
	}
	
	public  static  boolean  createVBridge (JSONObject  postData,String  vtnName){
		HttpURLConnection connection = null;
		int callStatus = 0;

		// Creating the actual URL to call
		String baseURL = ODL.URL1 + "/vtn-webapi/vtns/"+vtnName+"/vbridges.json" ;
		
		System.out.println("Base  URL  for  VirtualBridges"+baseURL);
		
		try {

			// Create URL = base URL + container
			URL url = new URL(baseURL);

			// Create authentication string and encode it to Base64
			String authStr = ODL.USERNAME1 + ":" + ODL.PASSWORD1;
			String encodedAuthStr = Base64.encodeBase64String(authStr
					.getBytes());

			// Create Http connection
			connection = (HttpURLConnection) url.openConnection();

			// Set connection properties
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", "Basic "
					+ encodedAuthStr);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Set Post Data
						OutputStream os = connection.getOutputStream();
						os.write(postData.toString().getBytes());
						os.close();



			// Getting the response code
			callStatus = connection.getResponseCode();

		} catch (Exception e) {
			System.err.println("Unexpected error while flow installation.. "
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (connection != null)
				connection.disconnect();
		}

		if (callStatus == CREATED) {
			System.out.println("VBridge created Successfully");
			return true;
		} else {
			System.err.println("Failed to create VBridge.. " + callStatus);
			return false;
		}
		
		
		
	}
	public  static  boolean  createInterfaces(JSONObject  postData,String vtnName,String  vbridgeName){
		HttpURLConnection connection = null;
		int callStatus = 0;

		// Creating the actual URL to call
		String baseURL = ODL.URL1 + "/vtn-webapi/vtns/"+vtnName+"/vbridges/"+vbridgeName+"/interfaces.json" ;

		//String baseURL = ODL.URL1 + Interfaces_PROGRAMMER_REST_API ;
		
		try {

			// Create URL = base URL + container
			URL url = new URL(baseURL);

			// Create authentication string and encode it to Base64
			String authStr = ODL.USERNAME1 + ":" + ODL.PASSWORD1;
			String encodedAuthStr = Base64.encodeBase64String(authStr
					.getBytes());

			// Create Http connection
			connection = (HttpURLConnection) url.openConnection();

			// Set connection properties
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", "Basic "
					+ encodedAuthStr);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Set Post Data
						OutputStream os = connection.getOutputStream();
						os.write(postData.toString().getBytes());
						os.close();



			// Getting the response code
			callStatus = connection.getResponseCode();

		} catch (Exception e) {
			System.err.println("Unexpected error while flow installation.. "
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (connection != null)
				connection.disconnect();
		}

		if (callStatus == CREATED) {
			System.out.println("Interfaces created Successfully");
			return true;
		} else {
			System.err.println("Failed to create Interfaces.. " + callStatus);
			return false;
		}
		
		
		
	}
	
	public  static  boolean  createMapping(JSONObject  postData,String vtnName,String  vbridgeName,String  interfacsesName ){
		HttpURLConnection connection = null;
		int callStatus = 0;

		// Creating the actual URL to call
		
		String baseURL = ODL.URL1 + "/vtn-webapi/vtns/"+vtnName+"/vbridges/"+vbridgeName+"/interfaces/"+interfacsesName+"/portmap.json" ;
		System.out.println("createMapping  base  url  is for  "+baseURL);

		
		try {

			// Create URL = base URL + container
			URL url = new URL(baseURL);

			// Create authentication string and encode it to Base64
			String authStr = ODL.USERNAME1 + ":" + ODL.PASSWORD1;
			String encodedAuthStr = Base64.encodeBase64String(authStr
					.getBytes());

			// Create Http connection
			connection = (HttpURLConnection) url.openConnection();

			// Set connection properties
			connection.setRequestMethod("PUT");
			connection.setRequestProperty("Authorization", "Basic "
					+ encodedAuthStr);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Set Post Data
						OutputStream os = connection.getOutputStream();
						os.write(postData.toString().getBytes());
						os.close();



			// Getting the response code
			callStatus = connection.getResponseCode();

		} catch (Exception e) {
			System.err.println("Unexpected error while flow installation.. "
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (connection != null)
				connection.disconnect();
		}

		if (callStatus == CREATED) {
			System.out.println("----------Mapping  created Successfully-----------");
			return true;
		} else {
			System.err.println("Failed to create Mapping.. " + callStatus);
			return false;
		}
		
		
		
	}
	
	
	public  static  boolean  createFlowList(JSONObject  postData ){
		HttpURLConnection connection = null;
		int callStatus = 0;

		// Creating the actual URL to call
		
		String baseURL = ODL.URL1 + "/vtn-webapi/flowlists.json" ;
		System.out.println("CreateFlowList  base  url  is for  "+baseURL);

		
		try {

			// Create URL = base URL + container
			URL url = new URL(baseURL);

			// Create authentication string and encode it to Base64
			String authStr = ODL.USERNAME1 + ":" + ODL.PASSWORD1;
			String encodedAuthStr = Base64.encodeBase64String(authStr
					.getBytes());

			// Create Http connection
			connection = (HttpURLConnection) url.openConnection();

			// Set connection properties
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", "Basic "
					+ encodedAuthStr);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Set Post Data
						OutputStream os = connection.getOutputStream();
						os.write(postData.toString().getBytes());
						os.close();



			// Getting the response code
			callStatus = connection.getResponseCode();

		} catch (Exception e) {
			System.err.println("Unexpected error while flow installation.. "
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (connection != null)
				connection.disconnect();
		}

		if (callStatus == CREATED) {
			System.out.println("----------FlowList  created Successfully-----------");
			return true;
		} else {
			System.err.println("Failed to create FlowList.. " + callStatus);
			return false;
		}
		
		
		
	}
	
	
	public  static  boolean  createFlowListEntry(JSONObject  postData,String  flowList ){
		HttpURLConnection connection = null;
		int callStatus = 0;

		// Creating the actual URL to call
		
		String baseURL = ODL.URL1 + "/vtn-webapi/flowlists/"+flowList+"/flowlistentries.json" ;
		System.out.println("CreateFlowList  base  url  is for  "+baseURL);

		
		try {

			// Create URL = base URL + container
			URL url = new URL(baseURL);

			// Create authentication string and encode it to Base64
			String authStr = ODL.USERNAME1 + ":" + ODL.PASSWORD1;
			String encodedAuthStr = Base64.encodeBase64String(authStr
					.getBytes());

			// Create Http connection
			connection = (HttpURLConnection) url.openConnection();

			// Set connection properties
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", "Basic "
					+ encodedAuthStr);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Set Post Data
						OutputStream os = connection.getOutputStream();
						os.write(postData.toString().getBytes());
						os.close();



			// Getting the response code
			callStatus = connection.getResponseCode();

		} catch (Exception e) {
			System.err.println("Unexpected error while flow Entry.. "
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (connection != null)
				connection.disconnect();
		}

		if (callStatus == CREATED) {
			System.out.println("----------FlowList  created Successfully-----------");
			return true;
		} else {
			System.err.println("Failed to create FlowEntry.. " + callStatus);
			return false;
		}
		
		
		
	}
	
	public  static  boolean  vBridgeInterfacesFlowFilter(JSONObject  postData,String  vtnName ,String  vBridgeName,String  interFaces){
		HttpURLConnection connection = null;
		int callStatus = 0;

		// Creating the actual URL to call
		
		String baseURL = ODL.URL1 + "/vtn-webapi/vtns/"+vtnName+"/vbridges/"+vBridgeName+"/interfaces/"+interFaces+"/flowfilters.json" ;
		System.out.println("CreateFlowList  base  url  is for  "+baseURL);

		
		try {

			// Create URL = base URL + container
			URL url = new URL(baseURL);

			// Create authentication string and encode it to Base64
			String authStr = ODL.USERNAME1 + ":" + ODL.PASSWORD1;
			String encodedAuthStr = Base64.encodeBase64String(authStr
					.getBytes());

			// Create Http connection
			connection = (HttpURLConnection) url.openConnection();

			// Set connection properties
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", "Basic "
					+ encodedAuthStr);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Set Post Data
						OutputStream os = connection.getOutputStream();
						os.write(postData.toString().getBytes());
						os.close();



			// Getting the response code
			callStatus = connection.getResponseCode();

		} catch (Exception e) {
			System.err.println("Unexpected error while vBridgeInterfacesFlowFilter  installation.. "
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (connection != null)
				connection.disconnect();
		}

		if (callStatus == CREATED) {
			System.out.println("----------vBridgeInterfacesFlowFilter List created Successfully-----------");
			return true;
		} else {
			System.err.println("Failed to create vBridgeInterfacesFlowFilter.. " + callStatus);
			return false;
		}
		
		
		
	}
	
	public  static  boolean  pASSactiontype(JSONObject  postData,String  vtnName,String vBridge,String interFaces){
		HttpURLConnection connection = null;
		int callStatus = 0;

		// Creating the actual URL to call
		
		String baseURL = ODL.URL1 + "/vtn-webapi/vtns/"+vtnName+"/vbridges/"+vBridge+"/interfaces/"+interFaces+"/flowfilters/in/flowfilterentries/233.json" ;
		System.out.println("CreateFlowList  base  url  is for  "+baseURL);

		
		try {

			// Create URL = base URL + container
			URL url = new URL(baseURL);

			// Create authentication string and encode it to Base64
			String authStr = ODL.USERNAME1 + ":" + ODL.PASSWORD1;
			String encodedAuthStr = Base64.encodeBase64String(authStr
					.getBytes());

			// Create Http connection
			connection = (HttpURLConnection) url.openConnection();

			// Set connection properties
			connection.setRequestMethod("PUT");
			connection.setRequestProperty("Authorization", "Basic "
					+ encodedAuthStr);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Set Post Data
						OutputStream os = connection.getOutputStream();
						os.write(postData.toString().getBytes());
						os.close();



			// Getting the response code
			callStatus = connection.getResponseCode();

		} catch (Exception e) {
			System.err.println("Unexpected error while .. passActionType"
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (connection != null)
				connection.disconnect();
		}

		if (callStatus == CREATED) {
			System.out.println("----------passActionType  created Successfully-----------");
			return true;
		} else {
			System.err.println("Failed to create passActionType  .. " + callStatus);
			return false;
		}
		
		
		
	}
	
	public  static  boolean  dropactiontype(JSONObject  postData,String  vtnName,String vBridge,String interFaces){
		HttpURLConnection connection = null;
		int callStatus = 0;

		// Creating the actual URL to call
		
		String baseURL = ODL.URL1 + "/vtn-webapi/vtns/"+vtnName+"/vbridges/"+vBridge+"/interfaces/"+interFaces+"/flowfilters/in/flowfilterentries.json" ;
		System.out.println("CreateFlowList  base  url  is for  "+baseURL);

		
		try {

			// Create URL = base URL + container
			URL url = new URL(baseURL);

			// Create authentication string and encode it to Base64
			String authStr = ODL.USERNAME1 + ":" + ODL.PASSWORD1;
			String encodedAuthStr = Base64.encodeBase64String(authStr
					.getBytes());

			// Create Http connection
			connection = (HttpURLConnection) url.openConnection();

			// Set connection properties
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", "Basic "
					+ encodedAuthStr);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Set Post Data
						OutputStream os = connection.getOutputStream();
						os.write(postData.toString().getBytes());
						os.close();



			// Getting the response code
			callStatus = connection.getResponseCode();

		} catch (Exception e) {
			System.err.println("Unexpected error while dropActionType.. "
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (connection != null)
				connection.disconnect();
		}

		if (callStatus == CREATED) {
			System.out.println("----------dropActionType  created Successfully-----------");
			return true;
		} else {
			System.err.println("Failed to create dropActionType  .. " + callStatus);
			return false;
		}
		
		
		
	}
	
	
	public static boolean installFlow(String nodeId, String flowName,
			JSONObject postData) {
		
		

		HttpURLConnection connection = null;
		int callStatus = 0;

		// Creating the actual URL to call
		String baseURL = ODL.URL + FLOW_PROGRAMMER_REST_API + nodeId
				+ "/staticFlow/" + flowName;

		try {

			// Create URL = base URL + container
			URL url = new URL(baseURL);

			// Create authentication string and encode it to Base64
			String authStr = ODL.USERNAME + ":" + ODL.PASSWORD;
			String encodedAuthStr = Base64.encodeBase64String(authStr
					.getBytes());

			// Create Http connection
			connection = (HttpURLConnection) url.openConnection();

			// Set connection properties
			connection.setRequestMethod("PUT");
			connection.setRequestProperty("Authorization", "Basic "
					+ encodedAuthStr);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Set Post Data
			OutputStream os = connection.getOutputStream();
			os.write(postData.toString().getBytes());
			os.close();

			// Getting the response code
			callStatus = connection.getResponseCode();

		} catch (Exception e) {
			System.err.println("Unexpected error while flow installation.. "
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (connection != null)
				connection.disconnect();
		}

		if (callStatus == CREATED) {
			System.out.println("Flow installed Successfully");
			return true;
		} else {
			System.err.println("Failed to install flow.. " + callStatus);
			return false;
		}
	}

	public static boolean createFirewallRule(JSONObject postData) {
		
		

		HttpURLConnection connection = null;
		int callStatus = 0;

		// Creating the actual URL to call
		String baseURL = ODL.URL3 + SDN_FIREWALL_Rule;
				

		try {

			// Create URL = base URL + container
			URL url = new URL(baseURL);

			// Create authentication string and encode it to Base64
			String authStr = ODL.USERNAME + ":" + ODL.PASSWORD;
			String encodedAuthStr = Base64.encodeBase64String(authStr
					.getBytes());

			// Create Http connection
			connection = (HttpURLConnection) url.openConnection();

			// Set connection properties
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", "Basic "
					+ encodedAuthStr);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Set Post Data
			OutputStream os = connection.getOutputStream();
			os.write(postData.toString().getBytes());
			os.close();

			// Getting the response code
			callStatus = connection.getResponseCode();

		} catch (Exception e) {
			System.err.println("Unexpected error while creating  firewall  rule......... "
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (connection != null)
				connection.disconnect();
		}

		if (callStatus == CREATED) {
			System.out.println("firewall  rule created Successfully");
			return true;
		} else {
			System.err.println("Failed to create firewall  rule.. " + callStatus);
			return false;
		}
	}
	public static boolean deleteFlow(String flowName, String nodeId) {

		HttpURLConnection connection = null;
		int callStatus = 0;
		String baseURL = ODL.URL + FLOW_PROGRAMMER_REST_API + nodeId
				+ "/staticFlow/" + flowName;

		try {

			// Create URL = base URL + container
			URL url = new URL(baseURL);

			// Create authentication string and encode it to Base64
			String authStr = ODL.USERNAME + ":" + ODL.PASSWORD;
			String encodedAuthStr = Base64.encodeBase64String(authStr
					.getBytes());

			// Create Http connection
			connection = (HttpURLConnection) url.openConnection();

			// Set connection properties
			connection.setRequestMethod("DELETE");
			connection.setRequestProperty("Authorization", "Basic "
					+ encodedAuthStr);
			connection.setRequestProperty("Content-Type", "application/json");

			callStatus = connection.getResponseCode();

		} catch (Exception e) {
			System.err.println("Unexpected error while flow deletion.."
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (connection != null)
				connection.disconnect();
		}

		if (callStatus == NO_CONTENT) {
			System.out.println("Flow deleted Successfully");
			return true;
		} else {
			System.err.println("Failed to delete the flow..." + callStatus);
			return false;
		}
	}
	
	
	public  static  boolean  addRegistry(JSONObject  postData){
		HttpURLConnection connection = null;
		int callStatus = 0;

		// Creating the actual URL to call
		String baseURL = ODL.URL1 + Controller_PROGRAMMER_REST_API ;
		
		try {

			// Create URL = base URL + container
			URL url = new URL(baseURL);

			// Create authentication string and encode it to Base64
			String authStr = ODL.USERNAME1 + ":" + ODL.PASSWORD1;
			String encodedAuthStr = Base64.encodeBase64String(authStr
					.getBytes());

			// Create Http connection
			connection = (HttpURLConnection) url.openConnection();

			// Set connection properties
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", "Basic "
					+ encodedAuthStr);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Set Post Data
						OutputStream os = connection.getOutputStream();
						os.write(postData.toString().getBytes());
						os.close();



			// Getting the response code
			callStatus = connection.getResponseCode();

		} catch (Exception e) {
			System.err.println("Unexpected error while flow installation.. "
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (connection != null)
				connection.disconnect();
		}

		if (callStatus == CREATED) {
			System.out.println("Contoller created Successfully");
			return true;
		} else {
			System.err.println("Failed to create Controller.. " + callStatus);
			return false;
		}
		
		
		
	}

}
