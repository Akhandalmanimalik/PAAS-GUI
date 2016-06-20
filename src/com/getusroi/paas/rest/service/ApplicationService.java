package com.getusroi.paas.rest.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getusroi.paas.dao.ApplicationDAO;
import com.getusroi.paas.dao.DataBaseOperationFailedException;
import com.getusroi.paas.marathon.service.IMarathonService;
import com.getusroi.paas.marathon.service.MarathonServiceException;
import com.getusroi.paas.marathon.service.impl.MarathonService;
import com.getusroi.paas.rest.RestServiceHelper;
import com.getusroi.paas.rest.service.exception.ApplicationServiceException;
import com.getusroi.paas.vo.ApplicantSummary;
import com.getusroi.paas.vo.Service;
import com.google.gson.Gson;
import com.paas_gui.register.userDAO;
import com.paas_gui.vpc.ApplicantUser;



@Path("/applicationService")
public class ApplicationService {
	 static final Logger LOGGER = LoggerFactory.getLogger(ApplicationService.class);
	 static final String TENANT="tenant";
	 ApplicantUser app=null;
	 userDAO userDAO =  null; 
	 
	@POST
	@Path("/addApplicantSummary")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addApplicationSummary(String appSummaryData) throws ApplicationServiceException, DataBaseOperationFailedException{
		LOGGER.debug(".addApplicationSummary method of ApplicationService ");
		ObjectMapper mapper = new ObjectMapper();
		ApplicationDAO applicationDAO=new ApplicationDAO();
		try {
			ApplicantSummary applicantSummary=mapper.readValue(appSummaryData,ApplicantSummary.class);
			applicationDAO.insertApplicationSummary(applicantSummary);
		} catch (IOException e) {
			LOGGER.error("Error in reading data : "+appSummaryData+" using object mapper in addApplicationSummary");
			throw new ApplicationServiceException("Error in reading data : "+appSummaryData+" using object mapper in addApplicationSummary");
		}
	}//end of addApplicationSummary
	
	@POST
	@Path("/addService")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addService(String applicationServiceData,@Context HttpServletRequest request  ) throws DataBaseOperationFailedException, MarathonServiceException, InterruptedException, ApplicationServiceException{
		LOGGER.debug(".addService method of ApplicationService ");
		ObjectMapper mapper = new ObjectMapper();
		ApplicationDAO applicationDAO=new ApplicationDAO();
		IMarathonService marathonService=new MarathonService();
		try {
			HttpSession session=request.getSession(false);
			int userId=(int)session.getAttribute("id");
			Service service = mapper.readValue(applicationServiceData,Service.class);
			/*for(EnvironmentVariable env:service.getEnv()){
				LOGGER.debug("env key : "+env.getEnvkey()+"env value : "+env.getEnvvalue());
			}*/
			LOGGER.debug("service "+service);
			service.setTenantId(userId);
			applicationDAO.addService(service);			
			//create instance in marathon using service object
		/*String appID=	marathonService.postRequestToMarathon(service);
		
		LOGGER.debug("----------Before  ContianerScript  script  called------------------------");			
			Thread.sleep(60000);
		List<MessosTaskInfo>  listOfMessosTask=	 ScriptService.runSCriptGetMessosTaskId(appID);
		if(listOfMessosTask.isEmpty()){
			Thread.sleep(60000);
			listOfMessosTask=ScriptService.runSCriptGetMessosTaskId(appID);
			
		}
		for (Iterator iterator = listOfMessosTask.iterator(); 
				iterator .hasNext();) {
			MessosTaskInfo messosTaskInfo = (MessosTaskInfo) iterator.next();
			new ScriptService().updateSubnetNetworkInMessos(messosTaskInfo, service.getSubnetName());
		}*/
			LOGGER.debug("----------Network  script  called------------------------");
		} catch (IOException e) {
			LOGGER.error("Error in reading data "+applicationServiceData+" using object mapper in addService");
			throw new ApplicationServiceException("Error in reading data "+applicationServiceData+" using object mapper in addService");
		}
	}//end of method addService
	
	@GET
	@Path("/getAllApplicationService")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllApplicationService(@Context HttpServletRequest request) throws DataBaseOperationFailedException{
		LOGGER.debug(".getAllApplicationService method of ApplicationService ");
		
		HttpSession session=request.getSession(false);
		int userId=(int)session.getAttribute("id");
	
		ApplicationDAO applicationDAO=new ApplicationDAO();
		List<Service> addServiceList = applicationDAO.getAllServiceByUserId(userId);
		 
		Gson gson = new Gson();
		String addServiceInJsonString=gson.toJson(addServiceList);
		return addServiceInJsonString;
		
	}//end of method getAllApplicationService
	
	
	@GET
	@Path("/getAllApplicationSummary")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllApplicationSummary() throws DataBaseOperationFailedException{
		LOGGER.debug(".getAllApplicationSummary method of ApplicationService ");
		ApplicationDAO applicationDAO=new ApplicationDAO();
		List<ApplicantSummary> applicantSummaryList=applicationDAO.getAllApplicantSummary();
		Gson gson = new Gson();
		String applicantSummaryInJsonString=gson.toJson(applicantSummaryList);
		return applicantSummaryInJsonString;
	}//end of method getAllApplicationSummary

	/*@GET
	@Path("/deleteServiceByName/{serviceName}/{appsid}")
	public void deleteServiceByName(@PathParam("serviceName") String serviceName,@PathParam("appsid") String appsid,@Context HttpServletRequest request) throws DataBaseOperationFailedException, MarathonServiceException{
		LOGGER.debug(".deleteServiceByName method of ApplicationService ");
		LOGGER.debug("ServiceNAme : "+serviceName  +" apps_id : "+appsid);
		ApplicationDAO applicationDAO=new ApplicationDAO();
		HttpSession session=request.getSession(false);
		//String appid=TENANT+user_id+"-"+envirnoment;
		//new MarathonService().deletInstanceformMarathan(appid);
		Service service = new Service();
		service.setServiceName(serviceName);
		service.setTenantId((int)session.getAttribute("id"));
		service.setAppsId(new RestServiceHelper().convertStringToInteger(appsid));
		applicationDAO.deleteServiceByServiceName(service);
	}//end of method deleteServiceByName
*/	
	
	@GET
	@Path("/deleteServiceByName/{serviceName}")
	public void deleteServiceByName(@PathParam("serviceName") String serviceName,@Context HttpServletRequest request) throws DataBaseOperationFailedException, MarathonServiceException{
		LOGGER.debug(".deleteServiceByName method of ApplicationService ");
		String appsid="25";
		LOGGER.debug("ServiceNAme : "+serviceName  +" apps_id : "+appsid);
		ApplicationDAO applicationDAO=new ApplicationDAO();
		HttpSession session=request.getSession(false);
		//String appid=TENANT+user_id+"-"+envirnoment;
		//new MarathonService().deletInstanceformMarathan(appid);
		Service service = new Service();
		service.setServiceName(serviceName);
		service.setTenantId((int)session.getAttribute("id"));
		service.setAppsId(new RestServiceHelper().convertStringToInteger(appsid));
		applicationDAO.deleteServiceByServiceName(service);
	}//end of method deleteServiceByName
	
	@PUT
	@Path("/updateMarathonInstace")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateMarathonInstace(String data) throws MarathonServiceException{
		LOGGER.debug(".updateMarathonInstace method of ApplicationService ");
		IMarathonService marathonService=new MarathonService();
		marathonService.updateMarathonInsance(data);
		return data;
	}//end of method updateMarathonInstace
	
	/*All Below methods for the shake of Fectch.java i.e methods ass*/
	
	@GET
	@Path("/selectApplicantName")
	@Produces(MediaType.APPLICATION_JSON)
	public String selectApplicantName() {

		LOGGER.info("Inside selectApplicantName (.) of ApplicationService ");
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
	@Path("/deleteData/{data}")
	public void deleteData(@PathParam("data") String data) {
		LOGGER.info("Inside (.) deleteData of ApplicationService"+data);
		
		userDAO customerDao = new userDAO();
		try{
		customerDao.deleteData(data);
		}catch(Exception e){
			LOGGER.error("Error when deleting application ");
		}
	}
	
	@POST
	@Path("/storeApplicantUser")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response storeApplicantUser(String msg,@Context HttpServletRequest req) throws JSONException {
		LOGGER.info("Inside storeApplicantUser (.) ApplicationService");
		ObjectMapper mapper = new ObjectMapper();
		ApplicantUser user = null;
		try {
			user = mapper.readValue(msg, ApplicantUser.class);
			userDAO userDAO = new userDAO();
			HttpSession session = req.getSession(true);
			user.setTenant_id((int)session.getAttribute("id"));
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
	
	@POST
	@Path("/createApplicationByName")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createApplicationByName(String msg,@Context HttpServletRequest req) throws JSONException {
		LOGGER.info("Inside storeApplicantUser (.) ApplicationService"+msg);
		userDAO = new userDAO();
		
		try {
			app = new ApplicantUser();
			app.setApplicantionName(msg);
			HttpSession session = req.getSession(true);
			app.setTenant_id((int)session.getAttribute("id"));
			userDAO.storeApplicant(app);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("-----Json is -------"+msg);
 		 
		return null;
	}
	
	@GET
	@Path("/checkingApplication/{applicationName}")
	@Produces(MediaType.TEXT_PLAIN)
	public String checkApplicationExistByNameAndTenantId(@PathParam("applicationName") String applicationName,@Context HttpServletRequest request) throws DataBaseOperationFailedException{
		LOGGER.debug(".getAllApplicationService method of ApplicationService ");
	int availability;
	HttpSession session=request.getSession(false);
	int userId=(int)session.getAttribute("id");

	ApplicationDAO applicationDAO=new ApplicationDAO();
	try{
	availability=applicationDAO.checkApplicationExistByNameAndTenantId(applicationName,userId);
	if (availability > 0)
		return "success";
		
	}catch(Exception e){
		e.printStackTrace();
	}
	return "failure";
	}
	
}
