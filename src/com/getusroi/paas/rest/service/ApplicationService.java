package com.getusroi.paas.rest.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.getusroi.paas.helper.UnableToLoadPropertyFileException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getusroi.paas.dao.ApplicationDAO;
import com.getusroi.paas.dao.ContainerTypesDAO;
import com.getusroi.paas.dao.DataBaseOperationFailedException;
import com.getusroi.paas.helper.ScriptService;
import com.getusroi.paas.marathon.service.IMarathonService;
import com.getusroi.paas.marathon.service.MarathonServiceException;
import com.getusroi.paas.marathon.service.impl.MarathonService;
import com.getusroi.paas.rest.RestServiceHelper;
import com.getusroi.paas.rest.service.exception.ApplicationServiceException;
import com.getusroi.paas.vo.Applications;
import com.getusroi.paas.vo.ContainerTypes;
import com.getusroi.paas.vo.MessosTaskInfo;
import com.getusroi.paas.vo.Service;
import com.google.gson.Gson;



@Path("/applicationService")
public class ApplicationService {
	 static final Logger LOGGER = LoggerFactory.getLogger(ApplicationService.class);
	 static final String TENANT="tenant";
	 static final String SUCESS="sucess";
	 static final String FAILUR="failed";

	 private ApplicationDAO applicationDAO=null;
	
	@POST
	@Path("/addService")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addService(String applicationServiceData,@Context HttpServletRequest request  ) throws DataBaseOperationFailedException, MarathonServiceException, InterruptedException, ApplicationServiceException{
		LOGGER.debug(".addService method of ApplicationService ");
		ObjectMapper mapper = new ObjectMapper();
		applicationDAO=new ApplicationDAO();
		IMarathonService marathonService=new MarathonService();
		ContainerTypesDAO contianer=new ContainerTypesDAO();
		//defulat value 
		int containerVlaue=500;
		try {
			HttpSession session=request.getSession(false);
			int userId=(int)session.getAttribute("id");
			Service service = mapper.readValue(applicationServiceData,Service.class);
			LOGGER.debug("service "+service);
			service.setTenantId(userId);
			applicationDAO.addService(service);	
		List<ContainerTypes> containerTypesList=	contianer.getContainerTypeIdByName(service.getType(),userId);
		if(!containerTypesList.isEmpty())
			containerVlaue=containerTypesList.get(0).getMemory();
			
			//create instance in marathon using service object
		String appID=	marathonService.postRequestToMarathon(service,containerVlaue);
		
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
		}
			LOGGER.debug("----------Network  script  called------------------------");
		} catch (IOException e) {
			LOGGER.error("Error in reading data "+applicationServiceData+" using object mapper in addService" + e);
			//throw new ApplicationServiceException("Error in reading data "+applicationServiceData+" using object mapper in addService");
		}catch (UnableToLoadPropertyFileException e) {
		
			LOGGER.error("Error in loading script  configuration file ",e);
		}
	}//end of method addService
	
	@GET
	@Path("/getAllServiceByAppsId")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllApplicationService(@Context HttpServletRequest request) throws DataBaseOperationFailedException{
		LOGGER.debug(".getAllApplicationService method of ApplicationService ");
		
		HttpSession session=request.getSession(false);
		int userId=(int)session.getAttribute("id");
	
		applicationDAO=new ApplicationDAO();
		List<Service> addServiceList = applicationDAO.getAllServiceByUserId(userId);
		 
		Gson gson = new Gson();
		String addServiceInJsonString=gson.toJson(addServiceList);
		return addServiceInJsonString;
		
	}//end of method getAllApplicationService
	
	
	@GET
	@Path("/getAllServiceByAppsId/{apps_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllServices(@PathParam("apps_id") int apps_id,@Context HttpServletRequest request) throws DataBaseOperationFailedException{
		LOGGER.debug(".getAllServices method of ApplicationService ");
		HttpSession session=request.getSession(false);
		applicationDAO = new ApplicationDAO();
		List<Service> applicantSummaryList=applicationDAO.getAllServiceByAppsIdAndTenantID(apps_id, (int)session.getAttribute("id"));
		Gson gson = new Gson();
		String applicantSummaryInJsonString=gson.toJson(applicantSummaryList);
		return applicantSummaryInJsonString;
	}//end of method getAllServices

	/*@GET
	@Path("/deleteServiceByName/{serviceName}/{appsid}")
	public void deleteServiceByName(@PathParam("serviceName") String serviceName,@PathParam("appsid") String appsid,@Context HttpServletRequest request) throws DataBaseOperationFailedException, MarathonServiceException{
		LOGGER.debug(".deleteServiceByName method of ApplicationService ");
		LOGGER.debug("ServiceNAme : "+serviceName  +" apps_id : "+appsid);
		applicationDAO=new ApplicationDAO();
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
	@Path("/deleteServiceById/{serviceId}")
	
	public void deleteServiceById(@PathParam("serviceId") String serviceId,@Context HttpServletRequest request) throws DataBaseOperationFailedException, MarathonServiceException{
		LOGGER.debug(".deleteServiceByName method of ApplicationService ");
		LOGGER.debug("ServiceNAme : "+serviceId  );
		applicationDAO = new ApplicationDAO();
		HttpSession session=request.getSession(false);
		//String appid=TENANT+user_id+"-"+envirnoment;
		//new MarathonService().deletInstanceformMarathan(appid);
		Service service = new Service();
		service.setId(Integer.parseInt(serviceId));
		service.setTenantId((int)session.getAttribute("id"));
		applicationDAO.deleteServiceByServiceId(service);
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
	@Path("/getApplications")
	@Produces(MediaType.APPLICATION_JSON)
	
	public String getApplications(@Context HttpServletRequest request) {

		LOGGER.info("Inside getAllApplications (.) of ApplicationService ");
		List<Applications> appList = new ArrayList<Applications>();
		String appsLista =null;
		try {
			applicationDAO = new ApplicationDAO();
			HttpSession session=request.getSession(false);

			appList = applicationDAO.getApplicationsDetailsBytTenantId((int)session.getAttribute("id"));
		Gson gson = new Gson();
		appsLista = gson.toJson(appList);
		//LOGGER.info("selectApplicantName : " + customersList);
		}catch(Exception e){
			LOGGER.error("Error when getting all data from Applications table");
		}
	return appsLista;
	}
	
	
	@GET
	@Path("/deleteApplication/{apps_id}")
	public void deleteApplicationById(@PathParam("apps_id") int apps_Id,@Context HttpServletRequest request) {
		LOGGER.info("Inside (.) deleteApplicationById of ApplicationService");
		
		applicationDAO = new ApplicationDAO();
		try{
			HttpSession session=request.getSession(false);

			applicationDAO.deleteApplicationById(apps_Id, (int)session.getAttribute("id"));
		}catch(Exception e){
			LOGGER.error("Error when deleting application ",e);
		}
	}
	
	/**
	 * 
	 * @param application
	 * @param req
	 * @return
	 * @throws JSONException
	 */
	@POST
	@Path("/storeApplication")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)

	public String storeApplicationsDetails(String application,@Context HttpServletRequest req) throws JSONException {
		LOGGER.info("Inside  (.)storeApplicationsDetails  ApplicationService");
		ObjectMapper mapper = new ObjectMapper();
		applicationDAO=new ApplicationDAO();
	
		try{
			HttpSession session=req.getSession(false);
			Applications apps=mapper.readValue(application, Applications.class);
				apps.setTenant_id( (int)session.getAttribute("id"));
				applicationDAO.addApplication(apps);
				return SUCESS;
		}catch(Exception e){
			LOGGER.error("Error when deleting application ",e);
		}
		
		return FAILUR;
	}
	
	@GET
	@Path("/checkApplication/{applictionName}")
	@Produces(MediaType.TEXT_PLAIN)
	public String checkApplicationExistOrNotByName(@PathParam("applictionName") String applicationName,@Context HttpServletRequest req) throws JSONException {
		LOGGER.info("Inside  (.)checkApplicationExistOrNotByName  ApplicationService");
		ObjectMapper mapper = new ObjectMapper();
		applicationDAO=new ApplicationDAO();
		int  id=0;
		try{
			HttpSession session=req.getSession(false);
		
				id=applicationDAO.getApplicationsIdByName(applicationName, (int)session.getAttribute("id"));
		 if(id==0)
			 return FAILUR;
			 
		 return SUCESS;
			 
		}catch(Exception e){
			LOGGER.error("Error when deleting application ",e);
		}
		
		return FAILUR;
	}
	
	/**
	 * Rest Service to  update Application by application id
	 * @param application
	 * @param req
	 * @return
	 * @throws MarathonServiceException
	 */
	@PUT
	@Path("/updateApplication")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String  updateApplication(String application,@Context HttpServletRequest req,@Context HttpServletResponse response) throws MarathonServiceException{
		LOGGER.debug(".updateApplication method of ApplicationService "+application);
		ObjectMapper mapper = new ObjectMapper();
		applicationDAO=new ApplicationDAO();
	
		
		try{
			HttpSession session=req.getSession(false);
			Applications apps=mapper.readValue(application, Applications.class);
				apps.setTenant_id( (int)session.getAttribute("id"));
				applicationDAO.updateApplication(apps);

				
				return SUCESS;
		}catch(Exception e){
			LOGGER.error("Error updating pplication ",e);
		}
		
	return FAILUR;
	}
	
	/**
	 * Rest Service to create Application by name only.
	 * @param msg
	 * @param req
	 * @return
	 * @throws JSONException
	 */
	@POST
	@Path("/createApplicationByName")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createApplicationByName(String msg,@Context HttpServletRequest req) throws JSONException {
		LOGGER.info("Inside storeApplicantUser (.) ApplicationService"+msg);
		/*userDAO = new userDAO();
		
		try {
			app = new ApplicantUser();
			app.setApplicantionName(msg);
			HttpSession session = req.getSession(true);
			app.setTenant_id((int)session.getAttribute("id"));
			userDAO.storeApplicant(app);
			
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		LOGGER.info("-----Json is -------"+msg);
 		 
		return null;
	}
	
	/**
	 * Rest Service to check application name exist or not
	 * @param applicationName
	 * @param request
	 * @return
	 * @throws DataBaseOperationFailedException
	 */
	@GET
	@Path("/checkingApplication/{applicationName}")
	@Produces(MediaType.TEXT_PLAIN)
	public String checkApplicationExistByNameAndTenantId(
			@PathParam("applicationName") String applicationName,
			@Context HttpServletRequest request)
			throws DataBaseOperationFailedException {
		LOGGER.debug(".getAllApplicationService method of ApplicationService ");
		int availability;
		HttpSession session = request.getSession(false);
		int userId = (int) session.getAttribute("id");

		applicationDAO = new ApplicationDAO();
		try {
			availability = applicationDAO
					.checkApplicationExistByNameAndTenantId(applicationName,
							userId);
			if (availability > 0)
				return "success";

		} catch (Exception e) {
			LOGGER.error("Erro in checking application by name and tenant Id ",
					e);
		}
		return "failure";
	}
	
	@PUT
	@Path("/updateService")
	@Produces(MediaType.TEXT_PLAIN)

	public String updateService(String serviceJsoObj,@Context HttpServletRequest req) throws MarathonServiceException{
		LOGGER.debug(".updateService method of ApplicationService "+serviceJsoObj);
		ObjectMapper mapper = new ObjectMapper();
		applicationDAO=new ApplicationDAO();
		try{
			HttpSession session=req.getSession(false);
			Service service = mapper.readValue(serviceJsoObj, Service.class);
			LOGGER.debug("service IS NULL "+service);
			if(service != null)
			service.setTenantId((int)session.getAttribute("id"));
			
			applicationDAO.updateServiceByServiceId(service);
			return SUCESS;
		}catch(Exception e){
			LOGGER.error("Error updating pplication ",e);
		}
		return FAILUR;
	}	
	
}
