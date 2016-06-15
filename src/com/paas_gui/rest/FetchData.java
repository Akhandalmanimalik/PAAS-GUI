package com.paas_gui.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.json.JSONObject;

import com.getusroi.paas.rest.RestServiceHelper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.opendaylight.testers.Tester;
import com.paas.network.PostService;
import com.paas_gui.register.CheckLoginDAO;
import com.paas_gui.register.Employee;
import com.paas_gui.register.userDAO;
import com.paas_gui.vpc.Acl;
import com.paas_gui.vpc.AddService;
import com.paas_gui.vpc.ApplicantSummary;
import com.paas_gui.vpc.ApplicantUser;
import com.paas_gui.vpc.CloudProviders;
import com.paas_gui.vpc.ContainerTypes;
import com.paas_gui.vpc.EnvironmentTypes;
import com.paas_gui.vpc.Environments;
import com.paas_gui.vpc.FirewallInbounds;
import com.paas_gui.vpc.FirewallOutbounds;
import com.paas_gui.vpc.HostScalingPolicy;
import com.paas_gui.vpc.ImageRegistry;
import com.paas_gui.vpc.MarathonRest;
import com.paas_gui.vpc.ResourceSelection;
import com.paas_gui.vpc.ScalingAndRecovery;
import com.paas_gui.vpc.ServiceAffinities;
import com.paas_gui.vpc.Storage;
import com.paas_gui.vpc.Subnet;
import com.paas_gui.vpc.Vpc_pozo;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Path("/fetchData")
public class FetchData {

	private static final Logger LOGGER = Logger.getLogger(FetchData.class);

	
                 /*for registration*/
	
	
	@POST
	@Path("/signup")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postMsg(String msg) {
		
		ObjectMapper mapper = new ObjectMapper();

		LOGGER.info(msg);
		Employee user = null;
		try {
			user = mapper.readValue(msg, Employee.class);
			userDAO s = new userDAO();
			s.viewdata(user);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GET
	@Path("{Param1}/{Param2}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getUnique(@PathParam("Param1") String username,@PathParam("Param2") String password) {
		String name;
		LOGGER.info(password);
		CheckLoginDAO sa = new CheckLoginDAO();
		boolean flag = sa.viewdata(password, username);
		LOGGER.info("returnd : " + flag);
		if (flag == false) {
			name = "try another";

		} else {
			name = password;
		}
		return name;
	}

	@GET
	@Path("{Param1}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getUnique(@PathParam("Param1") String username) {
		String name;
		CheckEmail sa = new CheckEmail();
		boolean flag = sa.viewdata(username);
		LOGGER.info("returnd : " + flag);
		if (flag == false) {
			name = username;

		} else {
			name = "Email Already Exists";
		}
		return name;
	}
                    
	    /*===========ending of registration==============*/
	
	
	@GET
	@Path("/selectVpc")
	@Produces(MediaType.APPLICATION_JSON)
	public String selectVpc() {

		LOGGER.info("--------------VPC   Selected--------");
		List<Vpc_pozo> customers = new ArrayList<Vpc_pozo>();

		userDAO customerDao = new userDAO();
		customers = customerDao.selectVpc();
		
		
		Gson gson = new Gson();
	
		
		String customersList = gson.toJson(customers);
		
		LOGGER.info("jsonCartList: " + customersList);
	
	return customersList;

	}
	@GET
	@Path("/selectServices")
	@Produces(MediaType.APPLICATION_JSON)
	public String selectServices() throws InterruptedException, IOException {

		LOGGER.info("--------------selectServices   Selected--------");
		List<AddService> customers = new ArrayList<AddService>();

		userDAO customerDao = new userDAO();
		customers = customerDao.selectServices();
		
		
		Gson gson = new Gson();
	
		
		String customersList = gson.toJson(customers);
		
		LOGGER.info("----------selectServicsubnetes---------: " + customersList);
		
		/*List<Subnet> subnetlist = new ArrayList<Subnet>();

		userDAO subnetdao = new userDAO();
		subnetlist = subnetdao.selectSubnet();*/
		/*String cidr="";
		
		
		for(Subnet sub:subnetlist){
			cidr=sub.getCidr();
			System.out.println("CIDR  is"+cidr);
			
		}
		String newCider= getIncrementValue(cidr);
		
		
		
		LOGGER.info(customers);
		System.out.println("----------CIDR  is  ---------"+cidr);
		System.out.println("----------Before  Network  script  called------------------------");
		
		String containerId=MarathonRest.getContainer();
		PostService.callScript(containerId, newCider);
		System.out.println("----------Network  script  called------------------------");
*/		
		
		
		
	
	return customersList;

	}
	
	
	
	
	private String getIncrementValue(String vlue) {
		String ad [] = vlue.split("/");
		System.out.println("array lengh "+ad.length);
		
		String s=ad[0];
		System.out.println("s value "+s);
		      String[] s2 = s.split("\\.");
		    int value=Integer.parseInt(s2[s2.length-1]);
		 System.out.println("value "+value);
		 value= value+1;
		 s2[s2.length-1]=value+"";
		 String data="";
		 for (int i = 0; i < s2.length-1; i++) {
			 data=data+s2[i]+".";
		}
		 data=data+value+"/"+ad[1];
		 System.out.println("value "+data );
		return data;
	}

	@GET
	@Path("/selectEnvirnamentList")
	@Produces(MediaType.APPLICATION_JSON)
	public String selectEnvirnamentList() {

		LOGGER.info("--------------selectEnvirnamentList   Selected--------");
		List<Environments> customers = new ArrayList<Environments>();

		userDAO customerDao = new userDAO();
		customers = customerDao.selectEnvirnamentList();
		
		
		Gson gson = new Gson();
	
		
		String customersList = gson.toJson(customers);
		
		LOGGER.info("jsonCartList: " + customersList);
	
	return customersList;

	}
	
	
	
	
	@GET
	@Path("/selectStoragelist")
	@Produces(MediaType.APPLICATION_JSON)
	public String selectStoragelist() {

		LOGGER.info("--------------selectStoragelist   Selected--------");
		List<Storage> customers = new ArrayList<Storage>();

		userDAO customerDao = new userDAO();
		customers = customerDao.selectStoragelist();
		
		
		Gson gson = new Gson();
	
		
		String customersList = gson.toJson(customers);
		
		LOGGER.info("selectStoragelist : " + customersList);
	
	return customersList;

	} 
	@POST
	@Path("/storestorage")
	@Produces(MediaType.APPLICATION_JSON)
	public void storage(String data) throws JSONException {

		LOGGER.info("--------------storage------------"+data);
		
		JSONObject jsonObject =new JSONObject(data);
		
		LOGGER.info("--------------storage  Data-----------"+jsonObject.getString("volumeSize"));
		
		String   volumeSize=jsonObject.getString("volumeSize");
		String  serviceName=jsonObject.getString("serviceName");
		
		LOGGER.info("--------------volumeSize-----------"+jsonObject.getString("volumeSize"));
		LOGGER.info("--------------serviceName-----------"+jsonObject.getString("serviceName"));
		Storage user = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			user = mapper.readValue(jsonObject.toString(), Storage.class);
			
			userDAO s = new userDAO();
			
			s.storeStorage(user);
			LOGGER.info("Calling  FreeNas  Rest  Api...........");
			Tester.createVolume(user);
			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		Gson gson = new Gson();
	
		
		
		
		
	
	

	}
	
	@POST
	@Path("/storeEnvironments")
	@Produces(MediaType.APPLICATION_JSON)
	public String storeEnvironments(String data) {

		LOGGER.info("--------------storeEnvironments   --------"+data);
		List<Environments> customers = new ArrayList<Environments>();
		JSONObject jsonObject =new JSONObject(data);
		jsonObject.put("containername", "dev.fuse");
		jsonObject.put("host", "192.168.1.219");
		jsonObject.put("ipadress", "w");
		jsonObject.put("state", "running");
		jsonObject.toString();
		System.out.println("dfdssdfsfsdfsdf"+jsonObject.toString());
		
		Environments user = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			user = mapper.readValue(jsonObject.toString(), Environments.class);
			
			userDAO s = new userDAO();
			
			s.storeEnvironments(user);
			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		Gson gson = new Gson();
	
		
		String customersList = gson.toJson(customers);
		
		LOGGER.info("jsonCartList: " + customersList);
	
	return customersList;

	}
	@PUT
	@Path("/updateInstance")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateInstace(String data) {
		System.out.println("update Instance "+data);
		
		
		try {

			Client client = Client.create();
			String id="10/"+"102";
			WebResource webResource = client
					.resource("http://192.168.1.46:8080/v2/apps/"+id);
			
			String input = "{"+
					"	\"id\": \""+id+"\","+
					"	\"instances\": "+data+"}";
			System.out.println(input);
			ClientResponse response = webResource.type("application/json")
					.put(ClientResponse.class, input);
			System.out.println("----------Apps stat---------"+response.getStatus());

					if (response.getStatus() != 200) {
						System.out.println("----------Apps  Created  sucessfully---------"+response.getStatus());

			
			
					}

		}catch(Exception e){
			e.printStackTrace();
			
			
			
		}
		return data;	
	}
	
	@POST
	@Path("/selectRepo")
	@Produces(MediaType.APPLICATION_JSON)
	public String selectSummary(    String reponame) {

/*		List<ApplicantSummary> customers = new ArrayList<ApplicantSummary>();
*/
		JSONObject jsonObject =new JSONObject(reponame);
		
		LOGGER.info("--------------selectRepo   Selected--------"+jsonObject);

		
		StringBuffer result = new StringBuffer();
	    try {
	    	userDAO repoName=new userDAO();
	    
	    ImageRegistry  summary=	repoName.selectImageRegistry(jsonObject.getString("imageRepository"));
	    System.out.println("---------summary------------------"+summary);

	    if(summary==null)
	    	return null;
	       String baseURL = "https://"+summary.getLocation() + "/v1/repositories/"+summary.getName()+"/tags";
	        System.out.println("base url  is"+baseURL);

	        // Create URL = base URL + container
	        URL url = new URL(baseURL);

	        // Create authentication string and encode it to Base64
	      String authStr = summary.getUser_name() + ":" + summary.getPassword();
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
		
	        InputStream content = (InputStream) connection.getInputStream();
	        BufferedReader in = new BufferedReader(new InputStreamReader(
	                content));
	        String line = "";
	        while ((line = in.readLine()) != null) {
	            result.append(line);
	        }

	        System.out.println("result"+result);
JSONArray josArray=new JSONArray(result.toString());
System.out.println("node "+josArray );

	    }catch(Exception e){
	    	System.out.println("error"+e);
	    }
		
	return  result.toString() ;
	    }
	
	@POST
	@Path("/selectRepoName")
	@Produces(MediaType.APPLICATION_JSON)
	public String selectRepoApp(    String appName) {
System.out.println("selectRepoName > "+appName);
/*		List<ApplicantSummary> customers = new ArrayList<ApplicantSummary>();
*/ApplicantSummary sum=new  ApplicantSummary();
	
		

		
		StringBuffer result = new StringBuffer();
	    try {
	    	userDAO repoName=new userDAO();
	    	sum=repoName.selectImageRegistryfromsummary(appName);
	    	
	    	
	    ImageRegistry  summary=	repoName.selectImageRegistry(sum.getImageRepository());
	    System.out.println("---------summary------------------"+summary);

	    if(summary==null)
	    	return null;
	       String baseURL = "https://"+summary.getLocation() + "/v1/repositories/"+summary.getName()+"/tags";
	        System.out.println("base url  is"+baseURL);

	        // Create URL = base URL + container
	        URL url = new URL(baseURL);

	        // Create authentication string and encode it to Base64
	      String authStr = summary.getUser_name() + ":" + summary.getPassword();
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
		
	        InputStream content = (InputStream) connection.getInputStream();
	        BufferedReader in = new BufferedReader(new InputStreamReader(
	                content));
	        String line = "";
	        while ((line = in.readLine()) != null) {
	            result.append(line);
	        }

	        System.out.println("result"+result);
JSONArray josArray=new JSONArray(result.toString());
System.out.println("node "+josArray );





		
		
		
		
		
	
		
		
		
	    }catch(Exception e){
	    	System.out.println("error"+e);
	    }
		
	return  result.toString() ;
	    }
	
	
	
	@POST
	@Path("/storeVpc")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postVpc(String msg) throws JSONException {
		LOGGER.info("-----------vpc Selected  Data...-----------------");
		ObjectMapper mapper = new ObjectMapper();

		
		Vpc_pozo user = null;
		try {
			user = mapper.readValue(msg, Vpc_pozo.class);
			user.setVpcId(getuuid());
			userDAO s = new userDAO();
			
			s.viewVpc(user);
			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		LOGGER.info("-----Json is -------"+msg);
		JSONObject  jobj=new JSONObject(msg);
		
		String  vpc_name=(String) jobj.get("vpc_name");
		
		System.out.println("VPcName  is "+vpc_name);
		String  cidr=(String) jobj.get("cidr");
	
		
		
         
             
           
            Tester.createVtn(vpc_name);
           
             
             
     		LOGGER.info("-----VPC with  VTN Created  using  SDN -------"+msg);

		
		
		
		
		
		return null;
	}
	@POST
	@Path("/storeServices")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response storeServices(String msg) throws JSONException, JsonGenerationException, JsonMappingException, IOException, InterruptedException {
		LOGGER.info("-----------storeServices selected-----------------"+msg);
		ObjectMapper mapper = new ObjectMapper();

		
		AddService user = null;
		try {
			user = mapper.readValue(msg, AddService.class);
			LOGGER.info("AFTER MAPPING "+user.toString());
					
			LOGGER.info("-----------storeServices selected-----------------"+msg);
			
			userDAO s = new userDAO();
			
			s.storeServices(user);
			

			
			//sleep(60);
			
			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*JSONObject  jobj=new JSONObject(msg);
	

		String  serviceName=jobj.get("serviceName").toString();
		String  type=jobj.get("type").toString();
		String  imageRegistry=jobj.get("imageRegistry").toString();
		String  imageRepository=jobj.get("imageRepository").toString();
		String  run=jobj.get("run").toString();
		String  typename=jobj.get("typename").toString();
		String  envirnament=jobj.get("envirnament").toString();
		String  envinterval=jobj.get("envinterval").toString();
		String  envtimeout=jobj.get("envtimeout").toString();
	    
		String  tag=jobj.get("tag").toString();
		JSONObject  jobjtag=new JSONObject(tag);
		String  tagname=jobjtag.get("name").toString();
		
		System.out.println("----serviceName----------"+serviceName);
		System.out.println("----tagname----------"+tagname);*/
		
		MarathonRest.postData(user);
		System.out.println("----------posting  to  marathons  -------------");
		List<Subnet> subnetlist = new ArrayList<Subnet>();
		userDAO subnetdao = new userDAO();
		subnetlist = subnetdao.selectSubnet();
		String cidr="";
		
		
		for(Subnet sub:subnetlist){
			cidr=sub.getCidr();
			System.out.println("CIDR  is"+cidr);
			
		}
		System.out.println("----------CIDR  is  ---------"+cidr);
		System.out.println("----------Before  Network  script  called------------------------");
		
		Thread.sleep(60000);
		String containerId=MarathonRest.getContainer();
		String newCider= getIncrementValue(cidr);
		//PostService.callScript(containerId, newCider);
		PostService.executeFile(containerId, newCider);
		System.out.println("----------Network script called------------------------");
		
		
		
		return null;
	}
	
	@POST
	@Path("/storeApplicantUser")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response storeApplicantUser(String msg,@Context HttpServletRequest req) throws JSONException {
		LOGGER.info("-----------storeApplicantUserss...-----------------");
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("0");
		RestServiceHelper restHelper = new RestServiceHelper();
		System.out.println("1");
		ApplicantUser user = null;
		try {
				LOGGER.debug("COMMING AFTER TRY");
			user = mapper.readValue(msg, ApplicantUser.class);
			userDAO userDAO = new userDAO();
			LOGGER.debug("COMMING BEFORE TRY");
			HttpSession session = req.getSession(true);
			LOGGER.debug("AFTER SESSION OBJECT>>><<<<>"+session);
			user.setTenant_id(restHelper.convertStringToInteger(session.getAttribute("id")+""));
			userDAO.storeApplicant(user);
			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		LOGGER.info("-----Json is -------"+msg);
		JSONObject  jobj=new JSONObject(msg);
      		LOGGER.info("-----VPC with  VTN Created  using  SDN -------"+msg);
 		 
		return null;
	}
	@GET
	@Path("/selectApplicantName")
	@Produces(MediaType.APPLICATION_JSON)
	public String selectApplicantName() {

		LOGGER.info("--------------VPC   Selected--------");
		List<ApplicantUser> customers = new ArrayList<ApplicantUser>();
		String customersList =null;
		try {
		userDAO customerDao = new userDAO();
		customers = customerDao.selectApplicantName();
		Gson gson = new Gson();
		customersList = gson.toJson(customers);
		//LOGGER.info("selectApplicantName : " + customersList);
		}catch(Exception e){
			LOGGER.error("Error when getting all data from Applications table");
		}
	return customersList;
	}
	
	@GET
	@Path("/getApplicationName")
	@Produces(MediaType.APPLICATION_JSON)
	public String getApplicationName() {

		LOGGER.info("--------------getApplicationName   Selected--------");
		List<ApplicantSummary> customers = new ArrayList<ApplicantSummary>();

		userDAO customerDao = new userDAO();
		customers = customerDao.getApplicationNameSummary();
		
		
		Gson gson = new Gson();
	
		
		String customersList = gson.toJson(customers);
		
		//LOGGER.info("selectApplicantName : " + customersList);
	
	return customersList;

	}
	
	@GET
	@Path("/selectSummary")
	@Produces(MediaType.APPLICATION_JSON)
	public String selectSummaryapp() {

		LOGGER.info("--------------selectSummaryapp   Selected--------");
		List<ApplicantSummary> customers = new ArrayList<ApplicantSummary>();

		userDAO customerDao = new userDAO();
		customers = customerDao.selectSummary();
		
		
		Gson gson = new Gson();
	
		
		String customersList = gson.toJson(customers);
		
		LOGGER.info("selectSummaryapp : " + customersList);
	
	return customersList;

	}
	@POST
	@Path("/storeSummary")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postApplicationSummary(String msg) throws JSONException {
		LOGGER.info("-----------storeSummary Selected  Data...-----------------"+msg);
		ObjectMapper mapper = new ObjectMapper();
		JSONObject obj = new JSONObject(msg);
		System.out.println("objject "+obj.get("tag"));
		
		JSONObject jsonObject=(JSONObject)obj.get("tag");

		
		System.out.println("tag >>>>>>>  "+jsonObject);
		System.out.println("tag >>>>>>>  "+jsonObject.getString("name"));
		obj.put("tag", jsonObject.getString("name"));
		ApplicantSummary summary = null;
		try {
			summary = mapper.readValue(obj.toString(), ApplicantSummary.class);
			userDAO s = new userDAO();
			s.storeSummary(summary);
			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		LOGGER.info("-----Json is -------"+msg);
		
		
		
		
		
		
	
		
		
            // Tester.installFlow(vpc_name, cidr, cidr);
             
           
            // Tester.createVtn(vpc_name);
             
             
     		//LOGGER.info("-----VPC with  VTN Created  using  SDN -------"+msg);

		
		
		
		
		
		return null;
	}
	 
	
	@GET
	@Path("/deleteData/{data}")
	public void deleteData(@PathParam("data") String data) {
		LOGGER.info("Inside (.) deleteData of Rest Service"+data);
		
		userDAO customerDao = new userDAO();
		try{
		customerDao.deleteData(data);
		}catch(Exception e){
			LOGGER.error("Error when deleting application ");
		}
	}
	           
	          /*UPDATE VPC DATA*/
	   
	 /*=====================pending ?????????==========================*/

        /*==================ending of vpc=================*/
	

	
	@GET
	@Path("/selectSubnet")
	@Produces(MediaType.APPLICATION_JSON)
	public String selectSubnet() {

		LOGGER.info("------------------------------Rest.. selectSubnet----------------------------");
		List<Subnet> customers = new ArrayList<Subnet>();

		userDAO customerDao = new userDAO();
		customers = customerDao.selectSubnet();
		LOGGER.info(customers);
		Gson gson = new Gson();
		String customersList = gson.toJson(customers);
		LOGGER.info("jsonCartList: " + customersList);
		return customersList;

	}
	
	@GET
	@Path("/selectMarathonRest")
	@Produces(MediaType.APPLICATION_JSON)
	public String selectMarathonRest() {

		LOGGER.info("------------------------------Rest.. selectMarathonRest----------------------------");
		
		
		int dev = MarathonRest.marathoncall(10,"dev");
		int prod = MarathonRest.marathoncall(10,"prod");
		int qa = MarathonRest.marathoncall(10,"qa");
		//String rest1 = String.valueOf(rest);
		
		JSONObject obj = new JSONObject();
		obj.put("dev", dev);
		obj.put("prod", prod);
		obj.put("qa", qa);
		
		System.out.println(obj.toString());
	return obj.toString();
		

	}
	
	/*@POST
	@Path("/callMarathonsLaunch")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response callMarathonsLaunch(String aclData) throws JSONException, JsonGenerationException, JsonMappingException, IOException {
		LOGGER.info("----------inside Call  Marathons  Launch ");
		AddService add=new  AddService();
		add.setApplicantionName(applicantionName);
		
		MarathonRest.postData(add);

		return null;
	}
	*/
	
	
	@POST
	@Path("/storeAcl")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postStoreAcl(String aclData) throws JSONException {
		LOGGER.info("----------Post  ACL  data ...--------"+aclData);
		ObjectMapper mapper = new ObjectMapper();
		JSONObject  job=null;
		try{
		  job=new  JSONObject(aclData);
		  String  destIP=job.get("destIP").toString();
		  
		}catch(org.json.JSONException ex){
			
			LOGGER.error("Json  error"+ex.getMessage());			
		}
		
		
	     String  aclName=job.get("aclName").toString();
		String  destIP=job.get("destIP").toString();
		String  srcIp=job.get("srcIp").toString();
		String  action=job.get("action").toString();
		
		
		LOGGER.info("------action--------"+action);
		LOGGER.info("--------------destip-------"+srcIp);
		LOGGER.info("--------------srcIP-------"+destIP);
		
		if(action.equalsIgnoreCase("pass")){
			Tester.installFlow(aclName, srcIp, destIP,"OUTPUT=2");
			
		}else{
			
			Tester.installFlow(aclName, srcIp, destIP,"DROP");
		}
		 
		//Tester.pASSactiontype("233", vpc_name, action,vpc_name,vpc_name+"vb", vpc_name+"if1");
		 
       Acl user = null;
		try {
			user = mapper.readValue(aclData, Acl.class);
			LOGGER.info("USER VALUE "+user);
			userDAO s = new userDAO();
			s.viewAcl(user);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//LOGGER.info("---------subnet  is ------"+msg);
		JSONObject  jobj=new JSONObject(aclData);
		LOGGER.debug("Json  Data  is  for  ACL"+jobj);
		       
             

		return null;
	}
	@GET
	@Path("/selectAcl")
	@Produces(MediaType.APPLICATION_JSON)
	public String selectAcl() {

		LOGGER.info("-----------------------------SElect Acl----------------------------");
		List<Acl> customers = new ArrayList<Acl>();

		userDAO customerDao = new userDAO();
		customers = customerDao.selectAcl();
		LOGGER.info(customers);
		Gson gson = new Gson();
		String customersList = gson.toJson(customers);
		LOGGER.info("jsonCartList: " + customersList);
		return customersList;

	}

	@GET
	@Path("/getListAcl")
	@Produces(MediaType.APPLICATION_JSON)
	public String getListAcl() {

		LOGGER.info("-----------------------------getListAcl ----------------------------");
		List<Acl> customers = new ArrayList<Acl>();

		userDAO customerDao = new userDAO();
		customers = customerDao.getAclList();
		LOGGER.info(customers);
		Gson gson = new Gson();
		String customersList = gson.toJson(customers);
		LOGGER.info("AclName  List: " + customersList);
		return customersList;

	}

	@POST
	@Path("/storeSubnet")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postSubnet(String msg) throws JSONException, InterruptedException, IOException {
		LOGGER.info("----------Post  Subnet  data ..."+msg);
		ObjectMapper mapper = new ObjectMapper();
		JSONObject  job=new  JSONObject(msg);
		System.out.println("job value "+job);
		
		
		//String  message1=job.get("vpc_name").toString();
		//  destip=job.get("cidr").toString();
		//String  srcip=job.getJSONObject("vpc_name").getString("cidr").toString();
		
		//String  srcPrefix[]=srcip.split("/");
		//String  destPrefix[]=destip.split("/");
	//	System.out.println("------vpc_name--------"+message1);
	 
	String vpcname=	job.get("vpc_name").toString();
		 
		System.out.println("---------------Vpc  Name  for  Json-----------"+vpcname);
 		//System.out.println("--------------destip-------"+destip);
		//System.out.println("--------------srcIP-------"+srcip);
		 Subnet user = null;
		try {
			user = mapper.readValue(msg, Subnet.class);
			System.out.println("USER VALUE "+user);
			userDAO s = new userDAO();
			String vpcID =s.getvpcId(job.get("vpc_name").toString());
			System.out.println("vpc ID "+vpcID);
			user.setVpcId(vpcID);
			user.setSubnetId(getsubnetuuid());
			s.viewSubnet(user);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		LOGGER.info("---------subnet  is ------"+msg);
		
		//System.out.println("Json  Data  is  for  Subnet"+jobj);
		String  vpc_name=job.get("vpc_name").toString();
		//String  subnet_name=(String) jobj.get("subnet_name");
		//String  cidr=(String) jobj.get("cidr");
		
		
		
		
	Tester.createVBridge(vpc_name+"vb", vpc_name);
	Tester.createInterfaces(vpc_name+"if1", vpc_name, vpc_name+"vb");
	
	Tester.createMapping(vpc_name+"if1",vpc_name, vpc_name+"vb", "PP-OF:00:00:00:00:00:00:00:03-s3-eth2");
	
	Tester.createInterfaces(vpc_name+"if2", vpc_name, vpc_name+"vb");
	
	Tester.createMapping(vpc_name+"if2",vpc_name, vpc_name+"vb", "PP-OF:00:00:00:00:00:00:00:02-s2-eth1");
	
	Tester.createFlowList(vpc_name);
	
	
//	Tester.createFlowListEntry("233", srcip , destip, vpc_name);
	Tester.vBridgeInterfacesFlowFilter(vpc_name, vpc_name+"vb", vpc_name+"if1");

	//Tester.pASSactiontype("233", vpc_name, "pass",vpc_name,vpc_name+"vb", vpc_name+"if1");
    //Tester.dRopactiontype("233", vpc_name, "drop",vpc_name,vpc_name+"vb", vpc_name+"if1");
             
             

		return null;
	}
	

	@GET
	@Path("/deleteSubnet/{data}")
	public void deleteSubnet(@PathParam("data") String data) {
		
		userDAO customerDao = new userDAO();
		customerDao.deleteSubnet(data);
	}

	
	                    /*==================ending of subnet===================*/
	
/*================scaling and recovery starts===================*/
	

	@GET
	@Path("/selectSAR")
	@Produces(MediaType.APPLICATION_JSON)
	public String selectSAR() {

		LOGGER.info("Rest.. Select");
		List<ScalingAndRecovery> customers = new ArrayList<ScalingAndRecovery>();

		userDAO customerDao = new userDAO();
		customers = customerDao.selectSAR();
		LOGGER.info(customers);
		Gson gson = new Gson();
		String customersList = gson.toJson(customers);
		LOGGER.info("jsonCartList: " + customersList);
		return customersList;

	}
	
	@POST
	@Path("/storeSAR")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postSAR(String msg) {
		LOGGER.info("hello...");
		ObjectMapper mapper = new ObjectMapper();

		LOGGER.info(msg);
		ScalingAndRecovery user = null;
		try {
			user = mapper.readValue(msg, ScalingAndRecovery.class);
			userDAO s = new userDAO();
			s.viewSAR(user);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	@GET
	@Path("/deleteSAR/{data}")
	public void deleteSAR(@PathParam("data") String data) {
		LOGGER.info("Inside (.) deleteSAR ");
		userDAO customerDao = new userDAO();
		customerDao.deleteSAR(data);
	}
	          /*============END================*/
	
/*================HOST SCALING POLICY===================*/
	

	@GET
	@Path("/selectHSP")
	@Produces(MediaType.APPLICATION_JSON)
	public String selectHSP() {

		LOGGER.info("Rest.. Select");
		List<HostScalingPolicy> customers = new ArrayList<HostScalingPolicy>();

		userDAO customerDao = new userDAO();
		customers = customerDao.selectHSP();
		LOGGER.info(customers);
		Gson gson = new Gson();
		String customersList = gson.toJson(customers);
		LOGGER.info("jsonCartList: " + customersList);
		return customersList;

	}
	
	@POST
	@Path("/storeHSP")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postHSP(String msg) {
		LOGGER.info("hello...");
		ObjectMapper mapper = new ObjectMapper();

		LOGGER.info(msg);
		HostScalingPolicy user = null;
		try {
			user = mapper.readValue(msg, HostScalingPolicy.class);
			userDAO s = new userDAO();
			s.viewHSP(user);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	@GET
	@Path("/deleteHSP/{data}")
	public void deleteHSP(@PathParam("data") String data) {
		
		userDAO customerDao = new userDAO();
		customerDao.deleteHSP(data);
	}
	          /*============END================*/
	
	
	
/*================SERVICE AFFINITY STARTS===================*/
	

	@GET
	@Path("/selectServiceAffinities")
	@Produces(MediaType.APPLICATION_JSON)
	public String selectServiceAffinities() {

		LOGGER.info("Rest.. Select");
		List<ServiceAffinities> customers = new ArrayList<ServiceAffinities>();

		userDAO customerDao = new userDAO();
		customers = customerDao.selectServiceAffinities();
		LOGGER.info(customers);
		Gson gson = new Gson();
		String customersList = gson.toJson(customers);
		LOGGER.info("jsonCartList: " + customersList);
		return customersList;

	}
	
	@POST
	@Path("/storeServiceAffinities")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postServiceAffinities(String msg) {
		LOGGER.info("hello...");
		ObjectMapper mapper = new ObjectMapper();

		LOGGER.info(msg);
		ServiceAffinities user = null;
		try {
			user = mapper.readValue(msg, ServiceAffinities.class);
			userDAO s = new userDAO();
			s.viewServiceAffinities(user);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	@GET
	@Path("/deleteServiceAffinities/{data}")
	public void deleteServiceAffinities(@PathParam("data") String data) {
		
		userDAO customerDao = new userDAO();
		customerDao.deleteServiceAffinities(data);
	}
	          /*============END================*/
	
	
	
	
/*================RESOURCE SELECTION STARTS===================*/
	

	@GET
	@Path("/selectResourceSelection")
	@Produces(MediaType.APPLICATION_JSON)
	public String selectResourceSelection() {

		LOGGER.info("Rest.. Select");
		List<ResourceSelection> customers = new ArrayList<ResourceSelection>();

		userDAO customerDao = new userDAO();
		customers = customerDao.selectResourceSelection();
		LOGGER.info(customers);
		Gson gson = new Gson();
		String customersList = gson.toJson(customers);
		LOGGER.info("jsonCartList: " + customersList);
		return customersList;

	}
	
	@POST
	@Path("/storeResourceSelection")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postResourceSelection(String msg) {
		//LOGGER.info("hello...");
		ObjectMapper mapper = new ObjectMapper();

		LOGGER.info(msg);
		ResourceSelection user = null;
		try {
			user = mapper.readValue(msg, ResourceSelection.class);
			userDAO s = new userDAO();
			s.viewResourceSelection(user);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	@GET
	@Path("/deleteResourceSelection/{data}")
	public void deleteResourceSelection(@PathParam("data") String data) {
		
		userDAO customerDao = new userDAO();
		customerDao.deleteResourceSelection(data);
	}
	          /*============END================*/
	
	
	
/*================CONTAINER TYPES STARTS===================*/

	@GET
	@Path("/selectContainerTypes")
	@Produces(MediaType.APPLICATION_JSON)
	public String selectContainerTypes() {

		LOGGER.info("Rest.. Select");
		List<ContainerTypes> customers = new ArrayList<ContainerTypes>();

		userDAO customerDao = new userDAO();
		customers = customerDao.selectContainerTypes();
		LOGGER.info(customers);
		Gson gson = new Gson();
		String customersList = gson.toJson(customers);
		LOGGER.info("jsonCartList: " + customersList);
		return customersList;

	}
	
	@POST
	@Path("/storeContainerTypes")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postContainerTypes(String msg) {
		//LOGGER.info("hello...");
		ObjectMapper mapper = new ObjectMapper();

		LOGGER.info(msg);
		ContainerTypes user = null;
		try {
			user = mapper.readValue(msg, ContainerTypes.class);
			userDAO s = new userDAO();
			s.viewContainerTypes(user);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	@GET
	@Path("/deleteContainerTypes/{data}")
	public void deleteContainerTypes(@PathParam("data") String data) {
		
		userDAO customerDao = new userDAO();
		customerDao.deleteContainerTypes(data);
	}
	          /*============END================*/
	
	
	/*================CLOUD PROVIDRTS STARTS===================*/

	@GET
	@Path("/selectCloudProviders")
	@Produces(MediaType.APPLICATION_JSON)
	public String selectCloudProviders() {

		LOGGER.info("Rest.. Select");
		List<CloudProviders> customers = new ArrayList<CloudProviders>();

		userDAO customerDao = new userDAO();
		customers = customerDao.selectCloudProviders();
		LOGGER.info(customers);
		Gson gson = new Gson();
		String customersList = gson.toJson(customers);
		LOGGER.info("jsonCartList: " + customersList);
		return customersList;

	}
	
	@POST
	@Path("/storeCloudProviders")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postCloudProviders(String msg) {
		//LOGGER.info("hello...");
		ObjectMapper mapper = new ObjectMapper();

		LOGGER.info(msg);
		CloudProviders user = null;
		try {
			user = mapper.readValue(msg, CloudProviders.class);
			userDAO s = new userDAO();
			s.viewCloudProviders(user);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	@GET
	@Path("/deleteCloudProviders/{data}")
	public void deleteCloudProviders(@PathParam("data") String data) {
		
		userDAO customerDao = new userDAO();
		customerDao.deleteCloudProviders(data);
	}
	          /*============END================*/
	
	
	/*================IMAGE REGISTRY STARTS===================*/

	@GET
	@Path("/selectImageRegistry")
	@Produces(MediaType.APPLICATION_JSON)
	public String selectImageRegistry() {

		LOGGER.info("Rest.. Select");
		List<ImageRegistry> customers = new ArrayList<ImageRegistry>();

		userDAO customerDao = new userDAO();
		customers = customerDao.selectImageRegistry();
		LOGGER.info(customers);
		Gson gson = new Gson();
		String customersList = gson.toJson(customers);
		LOGGER.info("jsonCartList: " + customersList);
		return customersList;

	}
	
	@POST
	@Path("/storeImageRegistry")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postImageRegistry(String msg) {
		//LOGGER.info("hello...");
		ObjectMapper mapper = new ObjectMapper();

		LOGGER.info("Image  Registry Response"+msg);
		
		
		
		
	
		
		
		ImageRegistry user = null;
		try {
			user = mapper.readValue(msg, ImageRegistry.class);
			userDAO s = new userDAO();
			s.viewImageRegistry(user);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String username = user.getUser_name();
		String pass = user.getPassword();
		String url = user.getLocation();
		LOGGER.info("username : "+username+ " pass: "+pass+ "url : "+url);
		
		
		
		Tester.getUserDetailsRegistry(user);
		
		
		return null;
	}
	

	@GET
	@Path("/deleteImageRegistry/{data}")
	public void deleteImageRegistry(@PathParam("data") String data) {
		
		userDAO customerDao = new userDAO();
		customerDao.deleteImageRegistry(data);
	}
	          /*============END================*/
	
	
	/*================ENVIRONMENT TYPES STARTS===================*/

	@GET
	@Path("/selectEnvironmentTypes")
	@Produces(MediaType.APPLICATION_JSON)
	public String selectEnvironmentTypes() {

		LOGGER.info("Rest.. Select");
		List<EnvironmentTypes> customers = new ArrayList<EnvironmentTypes>();
           
		userDAO customerDao = new userDAO();
		customers = customerDao.selectEnvironmentTypes();
		LOGGER.info(customers);
		Gson gson = new Gson();
		String customersList = gson.toJson(customers);
		LOGGER.info("jsonCartList: " + customersList);
		return customersList;

	}
	
	@POST
	@Path("/storeEnvironmentTypes")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postEnvironmentTypes(String msg) {
		//LOGGER.info("hello...");
		ObjectMapper mapper = new ObjectMapper();

		LOGGER.info(msg);
		EnvironmentTypes user = null;
		try {
			user = mapper.readValue(msg, EnvironmentTypes.class);
			userDAO s = new userDAO();
			s.viewEnvironmentTypes(user);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	@GET
	@Path("/deleteEnvironmentTypes/{data}")
	public void deleteEnvironmentTypes(@PathParam("data") String data) {
		
		userDAO customerDao = new userDAO();
		customerDao.deleteEnvironmentTypes(data);
	}
	          /*============END================*/
	

	
	/*================FIREWALL OUTBOUNDS STARTS===================*/

	@GET
	@Path("/selectFirewallOutbounds")
	@Produces(MediaType.APPLICATION_JSON)
	public String selectFirewallOutbounds() {

		LOGGER.info("Rest.. Select  firewall  selectFirewallOutbounds ");
		List<FirewallOutbounds> customers = new ArrayList<FirewallOutbounds>();
           
		userDAO customerDao = new userDAO();
		customers = customerDao.selectFirewallOutbounds();
		LOGGER.info(customers);
		Gson gson = new Gson();
		String customersList = gson.toJson(customers);
		LOGGER.info("jsonCartList: " + customersList);
		
		
		
		return customersList;

	}
	
	@POST
	@Path("/storeFirewallOutbounds")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postFirewallOutbounds(String msg) throws JSONException {
		LOGGER.info("store  firwall  data...");
		ObjectMapper mapper = new ObjectMapper();

		LOGGER.info("-----------------Firewall  Rules----------------------"+msg);
		
		
		JSONObject  job=new  JSONObject(msg);
		LOGGER.info("----------------firewall   values  is---------- "+job);
		
	
		String  out_name=job.get("out_name").toString();
		String  out_ip=job.get("out_ip").toString();
		String  out_portrange=job.get("out_portrange").toString();
		
		LOGGER.info("----------------out_name---------- "+out_name);
		LOGGER.info("----------------out_ip---------- "+out_ip);
		LOGGER.info("----------------out_portrange---------- "+out_portrange);
		
		
		Tester.createOutBoundFirewallRule(out_name, out_name+"openflow", out_ip, out_portrange);
		
		FirewallOutbounds user = null;
		try {
			user = mapper.readValue(msg, FirewallOutbounds.class);
			userDAO s = new userDAO();
			s.viewFirewallOutbounds(user);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	@GET
	@Path("/deleteFirewallOutbounds/{data}")
	public void deleteFirewallOutbounds(@PathParam("data") String data) {
		
		userDAO customerDao = new userDAO();
		customerDao.deleteFirewallOutbounds(data);
	}
	          /*============END================*/
	
	
	/*================FIREWALL INBOUNDS STARTS===================*/

	@GET
	@Path("/selectFirewallInbounds")
	@Produces(MediaType.APPLICATION_JSON)
	public String selectFirewallInbounds() {

		LOGGER.info("--------------selectFirewallInbounds.. ---------------------");
		List<FirewallInbounds> customers = new ArrayList<FirewallInbounds>();
           
		userDAO customerDao = new userDAO();
		customers = customerDao.selectFirewallInbounds();
		LOGGER.info(customers);
		Gson gson = new Gson();
		String customersList = gson.toJson(customers);
		LOGGER.info("jsonCartList: " + customersList);
		return customersList;

	}
	
	@POST
	@Path("/storeFirewallInbounds")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postFirewallInbounds(String msg) throws JSONException {
		//LOGGER.info("hello...");
		ObjectMapper mapper = new ObjectMapper();

		LOGGER.info("-------------inboundFirewallInbounds----------"+msg);
		

		
		
		JSONObject  job=new  JSONObject(msg);
		LOGGER.debug("----------------firewall   values  is---------- "+job);
		
	
		String  in_name=job.get("in_name").toString();
		String  in_ip=job.get("in_ip").toString();
		String  in_portrange=job.get("in_portrange").toString();
		
		LOGGER.debug("----------------in_name---------- "+in_name);
		LOGGER.debug("----------------in_ip---------- "+in_ip);
		LOGGER.debug("----------------in_portrange---------- "+in_portrange);
		
		
	Tester.createInBoundFirewallRule(in_name, in_name+"open-flow", in_ip, in_portrange);
		
		FirewallInbounds user = null;
		try {
			user = mapper.readValue(msg, FirewallInbounds.class);
			userDAO s = new userDAO();
			s.viewFirewallInbounds(user);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	@GET
	@Path("/deleteFirewallInbounds/{data}")
	public void deleteFirewallInbounds(@PathParam("data") String data) {
		
		userDAO customerDao = new userDAO();
		customerDao.deleteFirewallInbounds(data);
	}
	          /*============END================*/
	
	private String getuuid(){
		  UUID uuid = UUID.randomUUID();
		  return "vpc-"+uuid.toString().substring(0, 8);
		 }
	private String getsubnetuuid(){
		  UUID uuid = UUID.randomUUID();
		  return "subnet-"+uuid.toString().substring(0, 8);
		 }
	private String getacluuid(){
		  UUID uuid = UUID.randomUUID();
		  return "acl-"+uuid.toString().substring(0, 8);
		 }
	
	@GET
	@Path("/deleteServiceByName/{data}")
	public void deleteServiceByName(@PathParam("data") String data) {
		LOGGER.info("Inside deleteServiceByName method of FetchData class"+data);
		System.out.println("Inside deleteServiceByName method of FetchData class"+data);
		userDAO customerDao = new userDAO();
		try{
			customerDao.deleteServiceByName(data);	
		}catch(Exception e){
			LOGGER.error("Error when deleting service from the DB...");
		}
		
	}
}