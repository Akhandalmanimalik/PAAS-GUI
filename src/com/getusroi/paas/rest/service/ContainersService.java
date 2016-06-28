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

import com.getusroi.paas.dao.ContainerTypesDAO;
import com.getusroi.paas.dao.DataBaseOperationFailedException;
import com.getusroi.paas.dao.PoliciesDAO;
import com.getusroi.paas.marathon.service.IMarathonService;
import com.getusroi.paas.marathon.service.MarathonServiceException;
import com.getusroi.paas.marathon.service.impl.MarathonService;
import com.getusroi.paas.rest.RestServiceHelper;
import com.getusroi.paas.rest.service.exception.ImageRegistryServiceException;
import com.getusroi.paas.rest.service.exception.PoliciesServiceException;
import com.getusroi.paas.sdn.service.impl.SDNServiceImplException;
import com.getusroi.paas.vo.ContainerTypes;
import com.google.gson.Gson;

@Path("/containersService")
public class ContainersService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContainersService.class);
	
	private ContainerTypesDAO containerTypeDao = null;

	/**
	 * To get marathan service details
	 * @param request
	 * @return
	 * @throws MarathonServiceException
	 */
	@GET
	@Path("/selectMarathonRest")
	@Produces(MediaType.APPLICATION_JSON)
	public String selectMarathonRest(@Context HttpServletRequest request)
			throws MarathonServiceException {
		LOGGER.debug(".selectMarathonRest of ContainersService");

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
	
	/**
	 * To Update the container by using container id
	 * @param containerType
	 * @param req
	 * @return
	 * @throws DataBaseOperationFailedException
	 * @throws SDNServiceImplException
	 * @throws ImageRegistryServiceException
	 */
	@PUT
	@Path("/updateContainerType")
	@Produces(MediaType.TEXT_PLAIN)
	public String updateContainerType(String containerType,
			@Context HttpServletRequest req)
			throws DataBaseOperationFailedException, SDNServiceImplException,
			ImageRegistryServiceException {
		LOGGER.debug(".update updateContainerType method of ContainersService"
				+ containerType);
		containerTypeDao = new ContainerTypesDAO();
		ObjectMapper mapper = new ObjectMapper();
		int updateContainer = 0;
		try {

			ContainerTypes containerTypes = mapper.readValue(containerType,
					ContainerTypes.class);

			updateContainer = containerTypeDao.updateContainerType(containerTypes);
			String username = containerTypes.getName();
			int memmory = containerTypes.getMemory();
			String descp = containerTypes.getDescription();
			LOGGER.debug("username : " + username + " memmory : " + memmory
					+ " descption " + descp);
		} catch (IOException e) {
			LOGGER.error("Error in reading value from image registry  : "
					+ " using object mapper in Updage Container Type", e);
			throw new ImageRegistryServiceException(
					"Error in reading value from image registry  : "
							+ " using object mapper in addImageRegistry");
		}

		return String.valueOf(updateContainer == 1 ? "Success" : "failure");
	}

	/**
	 * To insert container type into the DB
	 * @param containerTypesData
	 * @param req
	 * @return
	 * @throws DataBaseOperationFailedException
	 * @throws PoliciesServiceException
	 */
	@POST
	@Path("/insertContainerTypes")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertContainerTypes(String containerTypesData,
			@Context HttpServletRequest req)
			throws DataBaseOperationFailedException, PoliciesServiceException {
		LOGGER.debug(".insertContainerTypes of PoliciesService s");
		containerTypeDao = new ContainerTypesDAO();
		ObjectMapper mapper = new ObjectMapper();
		ContainerTypes containerTypes = null;
		try {
			containerTypes = mapper.readValue(containerTypesData, ContainerTypes.class);
			HttpSession session = req.getSession(true);
			if (containerTypes != null) {
				containerTypes.setTenantId((int)session.getAttribute("id"));
				containerTypeDao.insertContainerType(containerTypes);
				return "Success";
			}
		} catch (IOException e) {
			LOGGER.error("Error in reading data : " + containerTypesData
					+ " using object mapper in insertScalingAndRecovery");
			throw new PoliciesServiceException("Error in reading data : "
					+ containerTypesData
					+ " using object mapper in insertScalingAndRecovery");
		}
		 
		return "failed";
	} // end of insertContainerTypes

	/**
	 * To get All ContainerTypes Data
	 * @return
	 * @throws DataBaseOperationFailedException
	 */
	@GET
	@Path("/getAllContainerTypesData")
	@Produces(MediaType.APPLICATION_JSON)
	public String selectContainerTypesData()
			throws DataBaseOperationFailedException {
		LOGGER.debug(".getAllContainerTypesData of PoliciesService");
		List<ContainerTypes> containerTypesList = new ArrayList<ContainerTypes>();
		containerTypeDao = new ContainerTypesDAO();
		containerTypesList = containerTypeDao.getAllContainerTypesData( );

		Gson gson = new Gson();
		String list = gson.toJson(containerTypesList);
		return list;

	} // end of getAllContainerTypesData

	/**
	 * To get Container Types by using Tenant id
	 * @return
	 * @throws DataBaseOperationFailedException
	 */
	@GET
	@Path("/getContainerTypesByTenantId")
	@Produces(MediaType.APPLICATION_JSON)
	public String getContainerTypesByTenantId(@Context HttpServletRequest request)
			throws DataBaseOperationFailedException {
		LOGGER.debug(".getContainerTypesByTenantId of PoliciesService");
		List<ContainerTypes> containerTypesList = new ArrayList<ContainerTypes>();
		containerTypeDao = new ContainerTypesDAO();
		HttpSession session = request.getSession(true);

		containerTypesList = containerTypeDao
				.getAllContainerTypesByTenantId((int)session
						.getAttribute("id"));
		LOGGER.debug(" LLKKJJ " + containerTypesList);
		Gson gson = new Gson();
		String list = gson.toJson(containerTypesList);
		return list;

	} // end of getAllContainerTypesData
	
	/** 
	 * To Delete the Container type using container name
	 * @param name
	 * @throws DataBaseOperationFailedException
	 */
	@GET
	@Path("/deleteContainerTypes/{id}")
	public void deleteContainerTypes(@PathParam("id") String containerId)
			throws DataBaseOperationFailedException {
		LOGGER.debug(".deleteContainerTypes of PoliciesService" + containerId);
		containerTypeDao = new ContainerTypesDAO();
		containerTypeDao.removeContainerTypesByName(new RestServiceHelper().convertStringToInteger(containerId));

	} // end of deleteContainerTypes

}
