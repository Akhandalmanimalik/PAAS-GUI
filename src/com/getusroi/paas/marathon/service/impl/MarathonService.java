package com.getusroi.paas.marathon.service.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getusroi.paas.helper.PAASConstant;
import com.getusroi.paas.helper.PAASGenericHelper;
import com.getusroi.paas.helper.UnableToLoadPropertyFileException;
import com.getusroi.paas.marathon.service.IMarathonService;
import com.getusroi.paas.marathon.service.MarathonServiceException;
import com.getusroi.paas.vo.Service;
import com.getusroi.paas.vo.EnvironmentVariable;
import com.getusroi.paas.vo.Scale;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import static com.getusroi.paas.marathon.service.MarathonConstant.*;
import static com.getusroi.paas.sdn.service.SDNContant.*;

public class MarathonService implements IMarathonService {
	 static final Logger logger = LoggerFactory.getLogger(MarathonService.class);

	 static final String NETWORK="BRIDGE";
	 static final String TENANT="tenant";
	/**
	 * this method is used to create instance in marathon
	 * @param id : id in int
	 * @param env : environment in String
	 * @return int : no number of instance in marathon for specific environment
	 * @throws MarathonServiceException : Error in creating marathon instance
	 */
	public int getInstanceCount(int id,String env) throws MarathonServiceException{
		logger.debug(".createInstanceInMarathon method of MarathonService");
		int count=0;
		try {
			Properties prop=PAASGenericHelper.getPropertyFile(MARATHON_CONFIG_FILE_KEY);
			String baseurl=prop.getProperty(MARATHON_ID_URL_KEY);
			String url=baseurl+TENANT+id+"-"+env;
			logger.debug("url : "+url);
			String output=getResponseFromMarathon(url);
			logger.debug("out put  server "+output);
			if(output !=null && !(output.isEmpty())){
				String findStr = ID_KEY;
				int lastIndex = 0;			
				while(lastIndex != -1){
				    lastIndex = output.indexOf(findStr,lastIndex);
				    if(lastIndex != -1){
				        count ++;
				        lastIndex += findStr.length();
				    }//end of  if
				}//end of while
			}//end of outter if
		} catch (UnableToLoadPropertyFileException e) {
			logger.error("Error in reading file : "+MARATHON_CONFIG_FILE_KEY+" in createInstanceInMarathon");
			throw new MarathonServiceException("Error in reading file : "+MARATHON_CONFIG_FILE_KEY+" in createInstanceInMarathon");
		}
		return count;		
	}//end of method createInstanceInMarathon
	/**
	 * This method used to update marathon instance 
	 * @param data : instance  in String
	 * @throws MarathonServiceException : Unable to update marathod Insatnce
	 */
	public void updateMarathonInsance(String data) throws MarathonServiceException{
		logger.debug(".updateMarathonInsance of MarathonService");
		String id="10/"+"102";
		try {
			Properties prop=PAASGenericHelper.getPropertyFile(MARATHON_CONFIG_FILE_KEY);
			String baseurl=prop.getProperty(MARATHON_APPS_URL_KEY);
			String url=baseurl+"/"+id;
			String input = "{"+
					"	\"id\": \""+id+"\","+
					"	\"instances\": "+data+"}";
			getResponseFromMarathon(url, input);
		} catch (UnableToLoadPropertyFileException e) {
			logger.error("Error in reading file : "+MARATHON_CONFIG_FILE_KEY+" in updateMarathonInsance");
			throw new MarathonServiceException("Error in reading file : "+MARATHON_CONFIG_FILE_KEY+" in updateMarathonInsance");
		}
	}//end of method updateMarathonInsance
	
	/**
	 * This method is used to get docker container id
	 * @return String : docker conainter id
	 * @throws MarathonServiceException : Error in getting docker container ID
	 */
	public String getDockerContainerID() throws MarathonServiceException{
		logger.debug(".getDockerContainerID method of MarathonService");
		String containerId=null;
		try {
			Properties prop=PAASGenericHelper.getPropertyFile(SDN_DOCKER_PROPERTY_FILE_KEY);
			String baseurl=prop.getProperty(SDN_DOCKER_URL_KEY);
			String containerurl=prop.getProperty(SDN_DOCKER_CONTAINER_KEY);
			String url=baseurl+"/"+containerurl;	
			logger.debug("url : "+url);
			String output=getResponseFromMarathon(url);
			logger.debug("output : "+output);
			if(output !=null && !(output.isEmpty())){
				try {
					JSONArray  jarry = new JSONArray(output);
					logger.debug("json array of output : "+jarry.getJSONObject(0).getString(NAME_KEY));					
					JSONArray  nameJsonArray=jarry.getJSONObject(0).getJSONArray(NAME_KEY);
					logger.debug("name json array : "+nameJsonArray.getString(0).replace("/",""));					
					  containerId=nameJsonArray.getString(0).replace("/","");
				} catch (JSONException e) {
					logger.debug("Error in forming json array for : "+output+" in getDockerContainerID");
				}				
			}//end of outter if
		} catch (UnableToLoadPropertyFileException e) {
			logger.error("Error in reading file : "+SDN_DOCKER_PROPERTY_FILE_KEY+" in getDockerContainerID");
			throw new MarathonServiceException("Error in reading file : "+SDN_DOCKER_PROPERTY_FILE_KEY+" in getDockerContainerID");
		}
		return containerId;
	}//end of getDockerContainerID
	
	/**
	 * This method is used to get gateway route
	 * @return String : marathon gateway route url
	 * @throws MarathonServiceException : Error in getting marathon gateway route
	 */
	public String  getGatewayRoute() throws MarathonServiceException{
		logger.debug(".getGatewayRoute method of MarathonService");
		String  gatewayRoute=null;
		Properties prop;
		try {
			prop = PAASGenericHelper.getPropertyFile(MARATHON_CONFIG_FILE_KEY);
			String url=prop.getProperty(MARATHON_ID_URL_KEY);
			String output=getResponseFromMarathon(url);
			if(output !=null && !(output.isEmpty())){
				try {
					JSONArray  outputInJsonArray=new JSONArray(output);
					String  gateway=outputInJsonArray.getJSONObject(0).getString(SERVICE_NAME_KEY);
					logger.debug("ServiceName  is : "+gateway);
					gatewayRoute=MARATHON_GATEWAY_ROUTE_URL_KEY+gateway;
				} catch (JSONException e) {
					logger.debug("Error in forming json array for : "+output+" in getGatewayRoute");
				}
			}//end of if
		} catch (UnableToLoadPropertyFileException e) {
			logger.error("Error in reading file : "+MARATHON_CONFIG_FILE_KEY+" in getGatewayRoute");
			throw new MarathonServiceException("Error in reading file : "+MARATHON_CONFIG_FILE_KEY+" in getGatewayRoute");
		}		
		return gatewayRoute;
	}//end of method getGatewayRoute
	
	/**
	 * This method is used to post request data to marathon
	 * @param addService : AddService object
	 * @throws MarathonServiceException : Error in reuesting data to marathon
	 */
	public String postRequestToMarathon(Service addService,int memomry) throws MarathonServiceException{
		logger.debug(".postRequestToMarathon method of MarathonService");
		ObjectMapper objectMapper = new ObjectMapper();
		String appId=PAASGenericHelper.getCustomUUID();
		String id=addService.getTenantId()+"/"+appId;
		String script=addService.getRun();
		int instances=1;
/*		String [] array=addService.getSubnetName().split("-");
*/		String memory=memomry+"";
		
		//#TODO need  change to get  values from confuig file
		String image=PAASConstant.IMAGE_RESISTRY_NAME+"/"+addService.getImageRepository()+":"+   addService.getTag();
		logger.debug("Image  is"+image);
		String protocol=addService.getProtocal();		
		String  failure=addService.getEnvThreshold()+"";
		String  timeOut=addService.getEnvtimeout();
		String  inteval=addService.getEnvInterval()+"";		
		String environment="dev";
		Integer containerport =0;
		String network=NETWORK;
		Map<String,String> env=setEnvironment(addService);
		String hostpath=null;
		String hostkey=null;
		String containervalue=null;
		/*if(addService.getVolume()+"" !=null){
			 hostpath=addService.getVolume()+"";
			 String host[] = hostpath.split(":");
				 hostkey =host[0].toString();
				containervalue =host[1].toString();
		}		*/
	
			containerport=Integer.valueOf(addService.getContainerPort());			
		logger.debug("env name : "+environment);
	    StringWriter stringWriter = new StringWriter();
	    try {
			objectMapper.writeValue(stringWriter, env);
			
			//this one has to be out not in the method
			String input = "{"+
					"	\"id\": \""+id+"\","+
					"	\"cmd\": \""+script+"\","+
					"	\"instances\": "+instances+","+
					"	\"mem\": "+memory+","+
					/*"    \"uris\": ["+
					"    \""+uris+"\""+
					"  ],"+*/
					"  \"labels\": {"+
					"                \"environment\": \""+environment+"\""+
					"                  },"+
					"	\"container\": {"+
					"		\"type\": \"DOCKER\","+
					"		\"docker\": {"+
					"			\"image\": \""+image+"\","+
					"			\"network\": \""+network+"\","+
					"			\"portMappings\": [{"+
					"				\"containerPort\": "+containerport+","+
					"				\"hostPort\": 0"+
					"			}],"+
					"			\"ports\": "+containerport+","+
					"			\"env\": "+
					"           "+stringWriter.toString()+""+
					"			,"+
					"     \"volumes\": ["+
					"      {"+
					"        \"hostPath\": \""+hostkey+"\","+
					"        \"containerPath\": \""+containervalue+"\""+
					"      }"+
					"    ]"+
					"		},"+
					"		\"healthChecks\": [{"+
					"			\"path\": \"/api/health\","+
					"			\"portIndex\": 0,"+
					"			\"protocol\": \""+protocol+"\","+
					/*"			\"gracePeriodSeconds\":\""+protocol+"\","+*/
					"			\"intervalSeconds\": \""+inteval+"\","+
					"			\"timeoutSeconds\": \""+timeOut+"\","+
					"			\"maxConsecutiveFailures\": \""+failure+"\","+
					"			\"ignoreHttp1xx\": false"+
					"		}]	"+
					"	}"+
					"}";
			
			try {
				Properties prop = PAASGenericHelper.getPropertyFile(MARATHON_CONFIG_FILE_KEY);
				String appsUrl=prop.getProperty(MARATHON_APPS_URL_KEY);
				
				
				
				logger.debug("Marthon input data "+input);
				
				
				getResponseFromMarathon(appsUrl, input);
			} catch (UnableToLoadPropertyFileException e) {
				logger.error("Unable to read file : "+MARATHON_CONFIG_FILE_KEY+" in postRequestToMarathon");
				throw new MarathonServiceException("Unable to read file : "+MARATHON_CONFIG_FILE_KEY+" in postRequestToMarathon");
			}
		} catch (IOException e) {
			logger.error("Unable to write in Object mapper with value : "+env+" using string writer");
			throw new MarathonServiceException("Unable to write in Object mapper with value : "+env+" using string writer");
		}
		return appId;
	}//end of method postRequestToMarathon
	
	
	
	/**
	 * This method is used to create storage 
	 * @param addService : it is used to add service name
	 * @param containerdisk : storage size in String
	 * @throws MarathonServiceException : Error in attaching NAS storage
	 */
	 public void  attachNasStorage(Service addService,String  containerdisk) throws MarathonServiceException{
		 logger.debug(".attachNasStorage method of MarathonService");
		 String input = "{"+
					"	\"name\": \""+addService.getServiceName()+"\","+
					"	\"volsize\": \""+containerdisk+"\","+
					"}";
		 try {
			Properties prop = PAASGenericHelper.getPropertyFile(NAS_PROPERTY_FILE_KEY);
			String nasStorageUrl=prop.getProperty(NAS_URL);
			logger.debug("nas storage url : "+nasStorageUrl);
			 getResponseFromMarathon(nasStorageUrl,input);			
		} catch (UnableToLoadPropertyFileException e) {
			logger.error("Error in reading file : "+NAS_PROPERTY_FILE_KEY+" in getGatewayRoute");
			throw new MarathonServiceException("Error in reading file : "+NAS_PROPERTY_FILE_KEY+" in getGatewayRoute");
		}
		 
	 }//end of method attachNasStorage
	 
	 public boolean deletInstanceformMarathan(String appId) throws MarathonServiceException{
		 
		
		 logger.debug(".deletInstanceformMarathan  method of MarathonService");
		 try {
			Properties prop = PAASGenericHelper.getPropertyFile(MARATHON_CONFIG_FILE_KEY);
			String appsUrl=prop.getProperty(MARATHON_ID_URL_KEY);
			appsUrl=appsUrl+appId;
			deleteInstanceFromMarathan(appsUrl);
			} catch (UnableToLoadPropertyFileException e) {
				logger.error("Unable to read file : "+MARATHON_CONFIG_FILE_KEY+" in postRequestToMarathon");
				throw new MarathonServiceException("Unable to read file : "+MARATHON_CONFIG_FILE_KEY+" in postRequestToMarathon");
			}
		 
		 return false;
	 }
	 
	 
	 /**
	  * This method is used to set Environment
	  * @param addService : AddService Object
	  * @return Map<String,String>
	  */
	 private Map<String,String> setEnvironment(Service addService){
		 logger.debug(".setEnvironment method of MarathonService");
		 List<EnvironmentVariable> listenv =addService.getEnv();
			Map<String,String> env = new HashMap<String, String>();
			for(EnvironmentVariable evn : listenv){				
				env.put(evn.getEnvkey(),evn.getEnvvalue());				
			}
			return env;
	 }//end of method setEnvironment
	 
	 
	 private String setHostAndContainerInfo(Service addService){
		 logger.debug(".setHostAndContainerInfo method of MarathonService");
		 String hostpath=null;
			//String hostkey=null;
			String containervalue=null;
			if(addService.getVolume()+"" !=null){
				 hostpath=addService.getVolume()+"";
				 String host[] = hostpath.split(":");
					// hostkey =host[0].toString();
					containervalue =host[1].toString();
			}
			return containervalue;
	 }//emd of method setHostAndContainerInfo

	/**
	 * This method is used to get response from marathon in String
	 * @param url : url in String
	 * @return String : response from marathon
	 */
	private String getResponseFromMarathon(String url){
		logger.debug(".getResponseFromMarathon method of  MarathonService");
		String output=null;
		Client client = Client.create();
		WebResource webResource = client
				.resource(url);
		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
		if (response.getStatus() != 200) {
			   throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
			}
		output = response.getEntity(String.class);
		return output;
	}//end of method of getResponseFromMarathon
	
	/**
	 * This method is used to get response from marathon in String
	 * @param url : url in String
	 * @param input : input data in String
	 * @return String : response from marathon
	 */
	private void getResponseFromMarathon(String url,String put){
		logger.debug(".getResponseFromMarathon method of  MarathonService");
		Client client = Client.create();
		String output=null;
		WebResource webResource = client
				.resource(url);
		
		ClientResponse response = webResource.type("application/json").post(ClientResponse.class,put);
		logger.debug("Response status from marathan "+response.getStatus());

		if ( response.getStatus() != 201 ) {
			   throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
			}
		output = response.getEntity(String.class);
		logger.debug("Response  from marathon "+output);

	}//end of method of getResponseFromMarathon
	
	private boolean deleteInstanceFromMarathan(String url){

		Client client = Client.create();
		String output=null;
		WebResource webResource = client
				.resource(url);
		
		ClientResponse response = webResource.type("application/json").delete(ClientResponse.class);
		logger.debug("Response status from marathan after deletion of instance  "+response.getStatus());

		if ( response.getStatus() != 201 ) {
			   throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
			}
		logger.debug("Response  from marathon after deletion of instance "+output);
		return false;
	}
	
}
