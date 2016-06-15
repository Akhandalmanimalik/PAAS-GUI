package com.getusroi.paas.rest.service;

import java.io.IOException;
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

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getusroi.paas.dao.ApplicationDAO;
import com.getusroi.paas.dao.DataBaseOperationFailedException;
import com.getusroi.paas.marathon.service.IMarathonService;
import com.getusroi.paas.marathon.service.MarathonServiceException;
import com.getusroi.paas.marathon.service.impl.MarathonService;
import com.getusroi.paas.rest.service.exception.ApplicationServiceException;
import com.getusroi.paas.vo.ApplicantSummary;
import com.getusroi.paas.vo.Service;
import com.google.gson.Gson;



@Path("/applicationService")
public class ApplicationService {
	 static final Logger logger = LoggerFactory.getLogger(ApplicationService.class);
	 static final String TENANT="tenant";

	@POST
	@Path("/addApplicantSummary")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addApplicationSummary(String appSummaryData) throws ApplicationServiceException, DataBaseOperationFailedException{
		logger.debug(".addApplicationSummary method of ApplicationService ");
		ObjectMapper mapper = new ObjectMapper();
		ApplicationDAO applicationDAO=new ApplicationDAO();
		try {
			ApplicantSummary applicantSummary=mapper.readValue(appSummaryData,ApplicantSummary.class);
			applicationDAO.insertApplicationSummary(applicantSummary);
		} catch (IOException e) {
			logger.error("Error in reading data : "+appSummaryData+" using object mapper in addApplicationSummary");
			throw new ApplicationServiceException("Error in reading data : "+appSummaryData+" using object mapper in addApplicationSummary");
		}
	}//end of addApplicationSummary
	
	@POST
	@Path("/addService")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addService(String applicationServiceData,@Context HttpServletRequest request  ) throws DataBaseOperationFailedException, MarathonServiceException, InterruptedException, ApplicationServiceException{
		logger.debug(".addService method of ApplicationService ");
		ObjectMapper mapper = new ObjectMapper();
		ApplicationDAO applicationDAO=new ApplicationDAO();
		IMarathonService marathonService=new MarathonService();
		try {
			
			HttpSession session=request.getSession(false);
			int userId=(int)session.getAttribute("id");
			
			Service service = mapper.readValue(applicationServiceData,Service.class);
			/*for(EnvironmentVariable env:service.getEnv()){
				logger.debug("env key : "+env.getEnvkey()+"env value : "+env.getEnvvalue());
			}*/
			logger.debug("service "+service);
			service.setTenantId(userId);
			applicationDAO.addService(service);			
			//create instance in marathon using service object
		/*String appID=	marathonService.postRequestToMarathon(service);
		
		logger.debug("----------Before  ContianerScript  script  called------------------------");			
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
			logger.debug("----------Network  script  called------------------------");
		} catch (IOException e) {
			logger.error("Error in reading data "+applicationServiceData+" using object mapper in addService");
			throw new ApplicationServiceException("Error in reading data "+applicationServiceData+" using object mapper in addService");
		}
	}//end of method addService
	
	@GET
	@Path("/getAllApplicationService")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllApplicationService(@Context HttpServletRequest request) throws DataBaseOperationFailedException{
		logger.debug(".getAllApplicationService method of ApplicationService ");
		
		HttpSession session=request.getSession(false);
		int userId=(int)session.getAttribute("id");
	
		ApplicationDAO applicationDAO=new ApplicationDAO();
		List<Service> addServiceList=applicationDAO.getAllServiceByUserId(userId);
		Gson gson = new Gson();
		String addServiceInJsonString=gson.toJson(addServiceList);
		return addServiceInJsonString;
		
	}//end of method getAllApplicationService
	
	
	@GET
	@Path("/getAllApplicationSummary")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllApplicationSummary() throws DataBaseOperationFailedException{
		logger.debug(".getAllApplicationSummary method of ApplicationService ");
		ApplicationDAO applicationDAO=new ApplicationDAO();
		List<ApplicantSummary> applicantSummaryList=applicationDAO.getAllApplicantSummary();
		Gson gson = new Gson();
		String applicantSummaryInJsonString=gson.toJson(applicantSummaryList);
		return applicantSummaryInJsonString;
	}//end of method getAllApplicationSummary
	
	@GET
	@Path("/deleteServiceByName/{serviceName}/{envirnoment}")
	public void deleteServiceByName(@PathParam("serviceName") String serviceName,@PathParam("envirnoment") String envirnoment,@Context HttpServletRequest request) throws DataBaseOperationFailedException, MarathonServiceException{
		logger.debug(".deleteServiceByName method of ApplicationService ");
		logger.debug("ServiceNAme : "+serviceName  +" environement : "+envirnoment);
		ApplicationDAO applicationDAO=new ApplicationDAO();
		HttpSession session=request.getSession(false);
		int user_id=(int)session.getAttribute("id");
		String appid=TENANT+user_id+"-"+envirnoment;
		//new MarathonService().deletInstanceformMarathan(appid);
		
		applicationDAO.deleteServiceByServiceName(serviceName,user_id);
	}//end of method deleteServiceByName
	
	@PUT
	@Path("/updateMarathonInstace")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateMarathonInstace(String data) throws MarathonServiceException{
		logger.debug(".updateMarathonInstace method of ApplicationService ");
		IMarathonService marathonService=new MarathonService();
		marathonService.updateMarathonInsance(data);
		return data;
	}//end of method updateMarathonInstace
	
	
}
