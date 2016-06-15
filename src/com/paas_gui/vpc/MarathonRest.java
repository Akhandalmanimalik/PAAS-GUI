package com.paas_gui.vpc;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.JsonArray;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

public class MarathonRest {

	public static void main(String[] args) {

		int id=10;
		String env="prod";
		//MarathonRest.marathoncall(id, env);
	//String  s1=	MarathonRest.getContainer();
	//System.out.println("s1  is "+s1);
	
	MarathonRest.getgatewayRoute();
		
		
	}


public static int marathoncall(int id,String env)
{
	int count = 0;

	String output=null;
	try {

		Client client = Client.create();
        System.out.println(id+env);
		WebResource webResource = client
		.resource("http://52.22.39.197:8080/v2/apps?id="+id+"&label=environment=="+env+"");
		ClientResponse response = webResource.accept("application/json")
                   .get(ClientResponse.class);

		if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
			+ response.getStatus());
		}

		output = response.getEntity(String.class);
		String findStr = "ID";
		int lastIndex = 0;
	
		while(lastIndex != -1){

		    lastIndex = output.indexOf(findStr,lastIndex);

		    if(lastIndex != -1){
		        count ++;
		        lastIndex += findStr.length();
		    }
		}
		System.out.println(count);
		System.out.println("Output from Server  1 .... \n");
		System.out.println("out put  server "+output);
		
		JSONArray josArray=new JSONArray(output);
		System.out.println("-------------jobq"+josArray);

		JSONObject  job=new  JSONObject(output);
		
		String job1=job.getString("Names");
		System.out.println("-------------jobq"+job1);

	  } catch (Exception e) {

		e.printStackTrace();

	  }
	return count;
}
public static String  getContainer()
{
	

	String output=null;
	String  containerId=null;
	try {

		Client client = Client.create();
        
		WebResource webResource = client
		.resource("http://54.86.181.80:2375/containers/json");
		ClientResponse response = webResource.accept("application/json")
                   .get(ClientResponse.class);

		if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
			+ response.getStatus());
		}

		output = response.getEntity(String.class);
		
		
		System.out.println("Output from Server 1 .... \n");
		
		
		System.out.println(output);
		JSONArray  jarry=new JSONArray(output);
		System.out.println("jarry"+jarry.getJSONObject(0).getString("Names"));
		
		JSONArray  str=jarry.getJSONObject(0).getJSONArray("Names");
		System.out.println("-------str"+str.getString(0).replace("/",""));
		
		  containerId=str.getString(0).replace("/","");
		
		

	  } catch (Exception e) {

		e.printStackTrace();

	  }
	return containerId;
}

public static String  getgatewayRoute()
{
	

	String output=null;
	
	String  finalGateway=null;
	try {

		Client client = Client.create();
        
		WebResource webResource = client
		.resource("http://192.168.1.219:8500/v1/health/node/baseroute");
		ClientResponse response = webResource.accept("application/json")
                   .get(ClientResponse.class);
		
		
		
		
		
		

		if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
			+ response.getStatus());
		}

		output = response.getEntity(String.class);
		
		
		System.out.println("Output from Server 1 .... \n");
		
		
		System.out.println(output);
		JSONArray  jarry=new JSONArray(output);
		String  gateway=jarry.getJSONObject(0).getString("ServiceName");
		System.out.println("ServiceName  is"+gateway);
		
		 finalGateway="http://192.168.1.219/"+gateway;
		
		
		
	
		
		
		 
		
		

	  } catch (Exception e) {

		e.printStackTrace();

	  }
	return finalGateway;
}

public static   String  postData(AddService add) throws JsonGenerationException, JsonMappingException, IOException{
	
	System.out.println("Inside  PostData  for  Mesos  Call");
	ObjectMapper objectMapper = new ObjectMapper();
	
		
	
	String id="10/"+"102";
	String script=add.getRun();
	int instances=1;
	String memory=add.getType();
	String image=add.getImageRegistry()+":"+add.getTag();
	//String image="tomcat:latest";
	System.out.println("Image  is"+image);
	
	String network="BRIDGE";
	
	List<EnvironmentVariable> listenv =add.getEnv();
	Map<String,String> env = new HashMap<String, String>();
	for(EnvironmentVariable evn : listenv){
		
		env.put(evn.getEnvkey(),evn.getEnvvalue());
		
	}
	String hostpath=null;
	String hostkey=null;
	String containervalue=null;
	if(add.getVolume()!=null){
		 hostpath=add.getVolume();
		 String host[] = hostpath.split(":");
			 hostkey =host[0].toString();
			containervalue =host[1].toString();
	}
	
	
	
	System.out.println("hostkey : "+hostkey + "hostvalue:"+containervalue);
	//String containerPath=add.getContainerport();
	String protocol=add.getEnvirnament();
	
	String  failure=add.getEnvthresold();
	String  timeOut=add.getEnvtimeout();
	String  inteval=add.getEnvinterval();
	
	String environment="";
	Integer containerport =0;
	List<Scale> scales = add.getScales();
	for(Scale scale:scales){
		environment=scale.getPortname();
		containerport=Integer.valueOf(scale.getContainerport());
		
	}
	
	//System.out.println("container size : "+containerport.size());
	System.out.println("env name : "+environment);

	
   /* Map<String,String> portmap = new HashMap<String, String>();
    portmap.put("key1","value1");
    portmap.put("key2","value2");*/
    StringWriter stringWriter = new StringWriter();
    objectMapper.writeValue(stringWriter, env);
    
  
try {

	Client client = Client.create();

	WebResource webResource = client
			.resource("http://52.22.39.197:8080/v2/apps");
	
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
	System.out.println(input);
	ClientResponse response = webResource.type("application/json")
			.post(ClientResponse.class, input);

			if (response.getStatus() != 200) {
				System.out.println("----------Apps  Created  sucessfully---------"+response.getStatus());

	
	
			}

}catch(Exception e){
	e.printStackTrace();
	
	
	
}
return null;

}
public static   String  attachNasStorage(AddService add,String  containerdisk) throws JsonGenerationException, JsonMappingException, IOException{
	
	System.out.println("Inside  PostData  for  createNasStorage  Call");
	
	String volume_name=add.getServiceName();
	String volsize=containerdisk;
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	String input = "{"+
			"	\"name\": \""+volume_name+"\","+
			"	\"volsize\": \""+volsize+"\","+
			"}";
	
    
  
try {

	Client client = Client.create();

	WebResource webResource = client
			.resource("http://192.168.1.54/api/v1.0/storage/volume/tank/zvols/?format=json");
	
	
	ClientResponse response = webResource.type("application/json")
			.post(ClientResponse.class, input);

			if (response.getStatus() != 200) {
				System.out.println("----------Apps  Created  sucessfully---------"+response.getStatus());

	
	
			}

}catch(Exception e){
	e.printStackTrace();
	
	
	
}
return null;

}
/*public static   String  createNasVolume(Storage add) throws JsonGenerationException, JsonMappingException, IOException{
	
System.out.println("Inside  PostData  for  createNasVolume  Call");
	
	String volume_name=add.getServiceName();
	
	String volsize=add.getVolumeSize();
	
	System.out.println("volume_name"+volume_name);
	System.out.println("volsize"+volsize);
	
	ObjectMapper objectMapper = new ObjectMapper();
	

String input = "{"+
		"	\"name\": \""+volume_name+"\","+
		"	\"volsize\": \""+volsize+"\","+

"  }";
	

	
    
  
try {

	Client client = Client.create();
	WebResource webResource = client
			.resource("http://192.168.1.209/api/v1.0/storage/volume/tank/zvols/?format=json");
	
	
	ClientResponse response = webResource.type("application/json")
			.post(ClientResponse.class, input);

			if (response.getStatus() != 200) {
				System.out.println("----------Volume  Created  sucessfully---------"+response.getStatus());

	
	
			}

}catch(Exception e){
	e.printStackTrace();
	
	
	
}
return null;

}*/


	/*public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		AddService add = new AddService();
		List<Scale> scales = new ArrayList<Scale>();
		Scale sc = new Scale();
		sc.setContainerport("90");
		scales.add(sc);
		add.setScales(scales);
		
		MarathonRest.postData(add);
	
	}*/
	
}