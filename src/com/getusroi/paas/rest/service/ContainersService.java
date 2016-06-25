package com.getusroi.paas.rest.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getusroi.paas.dao.ContainerTypesDAO;
import com.getusroi.paas.dao.DataBaseOperationFailedException;
import com.getusroi.paas.marathon.service.IMarathonService;
import com.getusroi.paas.marathon.service.MarathonServiceException;
import com.getusroi.paas.marathon.service.impl.MarathonService;
import com.getusroi.paas.rest.service.exception.ImageRegistryServiceException;
import com.getusroi.paas.sdn.service.impl.SDNServiceImplException;
import com.getusroi.paas.vo.ContainerTypes;

@Path("/containersService")
public class ContainersService {

	private Logger logger = LoggerFactory.getLogger(ContainersService.class);

	@GET
	@Path("/selectMarathonRest")
	@Produces(MediaType.APPLICATION_JSON)
	public String selectMarathonRest(@Context HttpServletRequest request) throws MarathonServiceException {
		logger.debug(".selectMarathonRest of ContainersService");

		HttpSession session = request.getSession(false);
		int userId = (int) session.getAttribute("id");

		IMarathonService iMarathonService = new MarathonService();
		int dev = iMarathonService.getInstanceCount(userId, "dev");
		int prod = iMarathonService.getInstanceCount(userId, "prod");
		int qa = iMarathonService.getInstanceCount(userId, "qa");
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("dev", dev);
		jsonObj.put("prod", prod);
		jsonObj.put("qa", qa);
		return jsonObj.toString();
	}

	@PUT
	@Path("/updateContainerType")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateContainerType(String containerType, @Context HttpServletRequest req)
			throws DataBaseOperationFailedException, SDNServiceImplException, ImageRegistryServiceException {
		logger.debug(".update updateContainerType method of ContainersService" + containerType);

		ContainerTypesDAO containerDao = new ContainerTypesDAO();

		ObjectMapper mapper = new ObjectMapper();
		int updateContainer=0;
		try {

			ContainerTypes containerTypes = mapper.readValue(containerType, ContainerTypes.class);

			 updateContainer = containerDao.updateContainerType(containerTypes);
			String username = containerTypes.getName();
			int memmory = containerTypes.getMemory();
			String descp = containerTypes.getDescription();

			logger.debug("username : " + username + " memmory : " + memmory + " descption " + descp);

			
		} catch (IOException e) {
			logger.error(
					"Error in reading value from image registry  : " + " using object mapper in Updage Container Type",
					e);
			throw new ImageRegistryServiceException(
					"Error in reading value from image registry  : " + " using object mapper in addImageRegistry");
		}
		
		return String.valueOf(updateContainer==1?"success":"failure");
	}

}
