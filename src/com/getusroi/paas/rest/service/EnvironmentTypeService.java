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

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getusroi.paas.dao.DataBaseOperationFailedException;
import com.getusroi.paas.dao.EnvironmentDAO;
import com.getusroi.paas.dao.ImageRegistryDAO;
import com.getusroi.paas.rest.RestServiceHelper;
import com.getusroi.paas.rest.service.exception.EnvironmentTypeServiceException;
import com.getusroi.paas.rest.service.exception.ImageRegistryServiceException;
import com.getusroi.paas.sdn.service.impl.SDNServiceImplException;
import com.getusroi.paas.vo.EnvironmentType;
import com.getusroi.paas.vo.Environments;
import com.getusroi.paas.vo.ImageRegistry;
import com.google.gson.Gson;

/**
 * this class handle all rest call request of environment page
 * @author Bizruntime
 *
 */
@Path("/environmentTypeService")
public class EnvironmentTypeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentTypeService.class);

	private EnvironmentDAO envDao= null;
	@POST
	@Path("/insertEnvironmentType")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertEnvironmentType(String environmentTypeData,@Context HttpServletRequest req)
			throws EnvironmentTypeServiceException, DataBaseOperationFailedException {
		LOGGER.debug(".insertEnvironmentType method of EnvironmentTypeService");
		ObjectMapper mapper = new ObjectMapper();
		envDao = new EnvironmentDAO();

		try {
			HttpSession session = req.getSession(true);
			EnvironmentType environmentTypes = mapper.readValue(environmentTypeData, EnvironmentType.class);
			if(environmentTypes != null)
				environmentTypes.setTenantId((int)session.getAttribute("id"));
			envDao.insertEnvironmentType(environmentTypes);
			return "success";
		} catch (IOException e) {
			LOGGER.error("Error in reading data : " + environmentTypeData + " using object mapper in environmentType");
			throw new EnvironmentTypeServiceException(
					"Error in reading data : " + environmentTypeData + " using object mapper in insertEnvironmentType");
		}
	} // end of insertEnvironmentType method

	
	@GET
	@Path("/getAllEnvironmentType")
	@Produces(MediaType.APPLICATION_JSON)
	public String getEnvironmentType(@Context HttpServletRequest req) throws EnvironmentTypeServiceException, DataBaseOperationFailedException {
		
		LOGGER.debug(".getEnvironmentType of EnvironmentTypeService");
		HttpSession session = req.getSession(true);
		List<EnvironmentType> environmentTypeList = new ArrayList<EnvironmentType>();
		envDao = new EnvironmentDAO();
		environmentTypeList = envDao.getAllEnvironmentType((int)session.getAttribute("id"));
		Gson gson = new Gson();
		String environmentList = gson.toJson(environmentTypeList);
		return environmentList;
	} // end of getEnvironmentType method

	@POST
	@Path("/deleteEnvironmentById/{envId}")
	public void deleteEnvironment(@PathParam("envId") String envId)
			throws DataBaseOperationFailedException, EnvironmentTypeServiceException {
		LOGGER.debug(".deleteEnvironment of EnvironmentTypeService envId "+envId);
		envDao = new EnvironmentDAO();
		envDao.deleteEnvironmentTypeById(new RestServiceHelper().convertStringToInteger(envId));
	} // end of deleteEnvironment method

	@GET
	@Path("/getAllEnvironamentList")
	@Produces(MediaType.APPLICATION_JSON)
	public String getEnvironmentList() throws DataBaseOperationFailedException {
		LOGGER.debug(".getEnvironmentList of EnvironmentTypeService");
		List<Environments> customers = new ArrayList<Environments>();
		envDao = new EnvironmentDAO();
		customers = envDao.getAllEnvironmentsList();
		Gson gson = new Gson();
		String customersList = gson.toJson(customers);
		return customersList;
	} // end of getEnvironmentList


	@POST
	@Path("/insertEnvironmentsData")
	@Produces(MediaType.APPLICATION_JSON)
	public String insertEnvironmentsData(String environmentData) throws DataBaseOperationFailedException {
		LOGGER.debug(".insertEnvironmentsData of EnvironmentsTypeService: " + environmentData);
		envDao = new EnvironmentDAO();
		List<Environments> environmentsList = new ArrayList<>();
		JSONObject jsonObject =new JSONObject(environmentData);
		jsonObject.put("containername", "dev.fuse");
		jsonObject.put("host", "192.168.1.219");
		jsonObject.put("ipadress", "w");
		jsonObject.put("state", "running");
		jsonObject.toString();
		
		Environments environments = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			environments = mapper.readValue(jsonObject.toString(), Environments.class);
			envDao.insertAllEnvironmentsData(environments);
		} catch (IOException e) {
			LOGGER.debug("Unable to read value");
		}
		Gson gson = new Gson();
		String environmentsLists = gson.toJson(environmentsList);
		return environmentsLists;
		
	} // end of insertEnvironmentsData method
	
	
	@PUT
	@Path("/updateEnvironmentType")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateEnvironmentType(String updateEnviromentType,@Context HttpServletRequest req) throws DataBaseOperationFailedException, SDNServiceImplException, ImageRegistryServiceException{
		LOGGER.debug(".update EnvironmentType method of EnvironmentTypeService"+updateEnviromentType);
		ObjectMapper mapper = new ObjectMapper();
		envDao = new EnvironmentDAO();
		try {
			//HttpSession session =req.getSession();
			EnvironmentType invironmentType = mapper.readValue(updateEnviromentType, EnvironmentType.class);
			envDao.updateEnvironmentType(invironmentType);
			String username = invironmentType.getName();
			String pass =invironmentType.getDescription();		
			LOGGER.debug("username : "+username+ " pass: "+pass);			
	return "Success";
		} catch (IOException e) {
			LOGGER.error("Error in reading value from image registry  : "+updateEnviromentType+" using object mapper in addImageRegistry",e);
			throw new ImageRegistryServiceException("Error in reading value from image registry  : "+updateEnviromentType+" using object mapper in addImageRegistry");
		}
	}
	
	
	/**
	 * To check environment name exist
	 * @param envName
	 * @param req
	 * @return
	 * @throws DataBaseOperationFailedException
	 */
	@GET
	@Path("/checknvironmentType/{envName}")
	@Produces(MediaType.TEXT_PLAIN)
	public String environmentValidation(@PathParam("envName") String envName,
			@Context HttpServletRequest req)
			throws DataBaseOperationFailedException {
		LOGGER.debug(" coming to check environment of pass network"+envName);
		envDao = new EnvironmentDAO();
		HttpSession session = req.getSession(true);
		LOGGER.debug("hhhhhh " + (int) session.getAttribute("id"));
		
		int id = envDao.getEnvironmentIdByEnvName(envName,
				(int) session.getAttribute("id"));

		if (id > 0)
			return "success";
		else
			return "failure";
	}// end of method environment

}
