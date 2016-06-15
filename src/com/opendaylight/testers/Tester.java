package com.opendaylight.testers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.opendaylight.managers.FlowManager;
import com.paas_gui.register.userDAO;
import com.paas_gui.vpc.ApplicantSummary;
import com.paas_gui.vpc.ImageRegistry;
import com.paas_gui.vpc.Storage;

public class Tester {

	public static void main(String[] args) throws JSONException, ClientProtocolException, IOException {
		
	//	Tester.createContoller("odc3", "10.0.1.1");
		//Tester.installFlow("test", "10.0.0.1", "10.0.0.2");
		
		//Tester.createOutBoundFirewallRule("test-01", "openflow-02", "10.0.0.2", "25");
	
		 }
		
	
	
	public  static String  installFlow(String vpcname,String  source,String  desti ,String  action) throws JSONException{
		
		JSONObject postData = new JSONObject();
		postData.put("name", vpcname);
		postData.put("nwSrc", source);
		postData.put("nwDst", desti);
		postData.put("installInHw", "true");
		postData.put("priority", "501");
		postData.put("etherType", "0x800");
		postData.put("actions", new JSONArray().put(action));

		// Node on which this flow should be installed
		JSONObject node = new JSONObject();
		node.put("id", "00:00:00:00:00:00:00:02");
		node.put("type", "OF");
		postData.put("node", node);

		// Actual flow install
		FlowManager.installFlow("00:00:00:00:00:00:00:02",
				vpcname, postData);

		
		
		
		
		return "Ok";
		
		
		
	}
public  static  String  createVolume(Storage add) throws JSONException{
		System.out.println("inside  Create  volume  data");
		JSONObject postData1 = new JSONObject();

		
		String volume_name=add.getServiceName();
		
		String volsize=add.getVolumeSize();
		
		System.out.println("volume_name"+volume_name);
		System.out.println("volsize"+volsize);
		postData1.put("name", volume_name);
		postData1.put("volsize", volsize);
		
	FlowManager.createVolume(postData1);
		return "Ok";
		}
	/*public static String createVtn(String  vtnName) throws JSONException{
		//Created  for  VTN
		JSONObject vtnpostData1 = new JSONObject();
		JSONObject vtnpostData2 = new JSONObject();
		vtnpostData1.put("vtn_name", vtnName);
		vtnpostData2.put("vtn", vtnpostData1);
		FlowManager.createVtn(vtnpostData2);
		return "Ok";
		
	}*/
	
	public  static  String  createContoller(String  contollerId,String  ipAddress) throws JSONException{
		
		JSONObject postData1 = new JSONObject();
		JSONObject postData12 = new JSONObject();

		postData1.put("controller_id", contollerId);
		postData1.put("ipaddr", ipAddress);
		postData1.put("type", "odc");
		postData1.put("version", "1.0");
		postData1.put("auditstatus", "enable");
		postData12.put("controller", postData1);
	FlowManager.createController(postData12);
		return "Ok";
		}
	public static String createVtn(String  vtnName) throws JSONException{
		//Created  for  VTN
		JSONObject vtnpostData1 = new JSONObject();
		JSONObject vtnpostData2 = new JSONObject();
		vtnpostData1.put("vtn_name", vtnName);
		vtnpostData2.put("vtn", vtnpostData1);
		FlowManager.createVtn(vtnpostData2);
		return "Ok";
		
	}
	public  static String createVBridge(String  vbName,String vtnName) throws JSONException{

		//For    VBridge
		JSONObject vBridgepostData1 = new JSONObject();
		JSONObject vBridgepostData2 = new JSONObject();

		vBridgepostData1.put("vbr_name", vbName);
		vBridgepostData1.put("controller_id","controllerone");
		vBridgepostData1.put("domain_id", "(DEFAULT)");
		vBridgepostData2.put("vbridge", vBridgepostData1);
		System.out.println("----------vBridgepostData2---------"+vBridgepostData2);
		
		FlowManager.createVBridge(vBridgepostData2,vtnName);
		
		return "OK";
		
		
		
	}
	public  static String  createInterfaces(String  intfaceName,String vtnName,String vBrigeName) throws JSONException{
		
		JSONObject interfacespostData1 = new JSONObject();
		JSONObject interfacespostData2 = new JSONObject();

		interfacespostData1.put("if_name", intfaceName);
		interfacespostData2.put("interface", interfacespostData1);
		FlowManager.createInterfaces(interfacespostData2,vtnName,vBrigeName);

		return "OK";
		
		
	}
	
public  static String  createMapping(String  intfaceName,String vtnName,String vBrigeName,String  logicalPortId) throws JSONException{
		
		JSONObject interfacespostData1 = new JSONObject();
		JSONObject interfacespostData2 = new JSONObject();
		

		interfacespostData1.put("logical_port_id", logicalPortId);
		interfacespostData1.put("vlan_id", "11");
		interfacespostData1.put("tagged", "true");
		
		
		interfacespostData2.put("portmap", interfacespostData1);
		
		
		System.out.println("---------interfacespostData2   is---------"+interfacespostData2);
		
		System.out.println("intfaceName"+intfaceName);
		System.out.println("vtnName"+vtnName);
		System.out.println("vBrigeName"+vBrigeName);
		System.out.println("logicalPortId"+logicalPortId);
		FlowManager.createMapping(interfacespostData2,vtnName,vBrigeName,intfaceName);

		return "OK";
		
		
	}
public  static String  createFlowList(String  flowName) throws JSONException{
	
	JSONObject interfacespostData1 = new JSONObject();
	JSONObject interfacespostData2 = new JSONObject();

	interfacespostData1.put("fl_name", flowName);
	interfacespostData1.put("ip_version", "IP");

	interfacespostData2.put("flowlist", interfacespostData1);
	
	System.out.println("---------interfacespostData2   is---------"+interfacespostData2);
	

	FlowManager.createFlowList(interfacespostData2);

	return "OK";
	
	
}
public  static String  createFlowListEntry(String  seqNumber,String srcIP,String  destIP ,String  flowList) throws JSONException{
	
	JSONObject interfacespostData1 = new JSONObject();
	JSONObject interfacespostData2 = new JSONObject();

	interfacespostData1.put("seqnum", seqNumber);
	interfacespostData1.put("macethertype", "0x800");
	
	
	interfacespostData1.put("ipdstaddr", destIP);
	interfacespostData1.put("ipdstaddrprefix", "24");
	

	interfacespostData1.put("ipsrcaddr", srcIP);
	interfacespostData1.put("ipsrcaddrprefix", "24");
	
	interfacespostData1.put("ipproto", "1");
	
	
	

	

	interfacespostData2.put("flowlistentry", interfacespostData1);
	
	System.out.println("---------interfacespostData2   is---------"+interfacespostData2);
	

	FlowManager.createFlowListEntry(interfacespostData2,flowList);

	return "OK";
	
	
}

public  static String  vBridgeInterfacesFlowFilter(String  vtnName ,String  vBridgeName,String  interFaces) throws JSONException{
	
	JSONObject interfacespostData1 = new JSONObject();
	JSONObject interfacespostData2 = new JSONObject();

	interfacespostData1.put("ff_type", "in");

	interfacespostData2.put("flowfilter", interfacespostData1);
	
	System.out.println("---------interfacespostData2   is---------"+interfacespostData2);
	
	FlowManager.vBridgeInterfacesFlowFilter(interfacespostData2,vtnName ,vBridgeName,interFaces);

	return "OK";
	
	
}

public  static String  pASSactiontype(String  seqNumber,String flowlist,String  actionType ,String  vtnName ,String  vBridgeName,String  interFaces) throws JSONException{
	
	JSONObject interfacespostData1 = new JSONObject();
	JSONObject interfacespostData2 = new JSONObject();

	interfacespostData1.put("seqnum", seqNumber);

	interfacespostData1.put("fl_name", flowlist);

	interfacespostData1.put("action_type", actionType);
	interfacespostData1.put("priority", "3");
	interfacespostData1.put("dscp", "55");
	
	interfacespostData2.put("flowfilterentry", interfacespostData1);
	
	System.out.println("---------interfacespostData2   is---------"+interfacespostData2);
	
	FlowManager.pASSactiontype(interfacespostData2,vtnName ,vBridgeName,interFaces);

	return "OK";
	
	
}
public  static String  dRopactiontype(String  seqNumber,String flowlist,String  actionType ,String  vtnName ,String  vBridgeName,String  interFaces) throws JSONException{
	
	JSONObject interfacespostData1 = new JSONObject();
	JSONObject interfacespostData2 = new JSONObject();

	interfacespostData1.put("seqnum", seqNumber);

	interfacespostData1.put("fl_name", flowlist);

	interfacespostData1.put("action_type", actionType);
	interfacespostData1.put("priority", "3");
	interfacespostData1.put("dscp", "55");
	
	interfacespostData2.put("flowfilterentry", interfacespostData1);
	
	System.out.println("---------interfacespostData2   is---------"+interfacespostData2);
	
	FlowManager.dropactiontype(interfacespostData2,vtnName ,vBridgeName,interFaces);

	return "OK";
	
	
}
public  static String  createInBoundFirewallRule(String ruleName,String nodename,String  inboundIP,String  port) throws JSONException{
	
	JSONObject postData = new JSONObject();
	postData.put("name", ruleName);
	postData.put("node", nodename);
	postData.put("ip-addr", inboundIP);
	postData.put("port", port);
	// Node on which this flow should be installed
	JSONObject node = new JSONObject();
	node.put("rule-registry-entry", postData);

	// Actual flow install
	FlowManager.createFirewallRule(node);
   return "Ok";
	
	
	
}
public  static String  createOutBoundFirewallRule(String ruleName,String nodename,String  inboundIP,String  port) throws JSONException{
	
	JSONObject postData = new JSONObject();
	postData.put("name", ruleName);
	postData.put("node", nodename);
	postData.put("ip-addr", inboundIP);
	postData.put("port", port);
	// Node on which this flow should be installed
	JSONObject node = new JSONObject();
	node.put("rule-registry-entry", postData);
    System.out.println("JSon  Flow  Data  for  firewall  is"+node);
	// Actual flow install
	FlowManager.createFirewallRule(node);
   return "Ok";
	
	
	
}
public static String getUserDetails(String user, String password,
        String baseURL) {

    StringBuffer result = new StringBuffer();
    try {

        /*if (!baseURL.contains("http")) {
            baseURL = "http://" + baseURL;
        }*/
        baseURL = "https://"+baseURL + "/v1/users/";
        System.out.println("base url  is"+baseURL);

        // Create URL = base URL + container
        URL url = new URL(baseURL);

        // Create authentication string and encode it to Base64
        String authStr = user + ":" + password;
        String encodedAuthStr = Base64.encodeBase64String(authStr
                .getBytes());

        // Create Http connection
        HttpURLConnection connection = (HttpURLConnection) url
                .openConnection();

        // Set connection properties
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Basic "
                + encodedAuthStr);
        connection.setRequestProperty("Accept", "application/json");
        
        System.out.println("Response  Code"+connection.getResponseCode());
        System.out.println("Response  Code"+connection.getResponseMessage());
        /*System.out.println("Response  Code"+connection.getContentType());
        System.out.println("Response  Code"+connection.getContentType());
        System.out.println("Response  Code"+connection.getContentType());
        System.out.println("Response  Code"+connection.getContentType());*/


       /* // Get the response from connection's inputStream
        InputStream content = (InputStream) connection.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                content));
        String line = "";
        while ((line = in.readLine()) != null) {
            result.append(line);
        }*/

        /*JSONObject nodes = new JSONObject(result.toString());
        System.out.println("List  of  node  is"+nodes);*/
        return "OK";
    } catch (Exception e) {
        e.printStackTrace();
    }

    return null;
}

public static String getUserDetailsRegistry(ImageRegistry user) {

    StringBuffer result = new StringBuffer();
    try {

        /*if (!baseURL.contains("http")) {
            baseURL = "http://" + baseURL;
        }*/
       String baseURL = "https://"+user.getLocation() + "/v1/users/";
        System.out.println("base url  is"+baseURL);

        // Create URL = base URL + container
        URL url = new URL(baseURL);

        // Create authentication string and encode it to Base64
        String authStr = user.getUser_name() + ":" + user.getPassword();
        String encodedAuthStr = Base64.encodeBase64String(authStr
                .getBytes());

        // Create Http connection
        HttpURLConnection connection = (HttpURLConnection) url
                .openConnection();

        // Set connection properties
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Basic "
                + encodedAuthStr);
        connection.setRequestProperty("Accept", "application/json");
        
        System.out.println("Response  Code"+connection.getResponseCode());
        System.out.println("Response  Code"+connection.getResponseMessage());
        
        return "OK";
    } catch (Exception e) {
        e.printStackTrace();
    }

    return null;
}

/*public static String getImageRegistry(ApplicantSummary summary) {

    StringBuffer result = new StringBuffer();
    try {
    	userDAO repoName=new userDAO();
    
    	repoName.selectImageRegistry(reponame);

        if (!baseURL.contains("http")) {
            baseURL = "http://" + baseURL;
        }
       String baseURL = "https://"+summary.getImageRegistry() + "/v1/repositories/"+summary.getImageRepository();
        System.out.println("base url  is"+baseURL);

        // Create URL = base URL + container
        URL url = new URL(baseURL);

        // Create authentication string and encode it to Base64
      String authStr = user.getUser_name() + ":" + user.getPassword();
        String encodedAuthStr = Base64.encodeBase64String(authStr.getBytes());

        // Create Http connection
        HttpURLConnection connection = (HttpURLConnection) url
                .openConnection();

        // Set connection properties
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Basic "
                + encodedAuthStr);
        connection.setRequestProperty("Accept", "application/json");
        
        System.out.println("Response  Code"+connection.getResponseCode());
        System.out.println("Response  Code"+connection.getResponseMessage());
        
        return "OK";
    } catch (Exception e) {
        e.printStackTrace();
    }

    return null;
}*/


}
