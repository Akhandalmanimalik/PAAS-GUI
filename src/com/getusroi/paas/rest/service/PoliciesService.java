package com.getusroi.paas.rest.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getusroi.paas.dao.DataBaseOperationFailedException;
import com.getusroi.paas.dao.PoliciesDAO;
import com.getusroi.paas.rest.service.exception.PoliciesServiceException;
import com.getusroi.paas.vo.HostScalingPolicy;
import com.getusroi.paas.vo.ResourceSelection;
import com.getusroi.paas.vo.ScalingAndRecovery;
import com.getusroi.paas.vo.ServiceAffinities;
import com.google.gson.Gson;


/**
 * this class handle the all rest call of Policies page
 * @author BizRuntime
 *
 */
@Path("/policiesService")
public class PoliciesService {
	
	private static final Logger logger = LoggerFactory.getLogger(PoliciesService.class);
	@POST
	@Path("/addScalingAndRecovery")
	@Produces(MediaType.APPLICATION_JSON)
	public void insertScalingAndRecovery(String scalingAndRecoveryData) throws PoliciesServiceException, DataBaseOperationFailedException {
		logger.debug(".insertScalingAndRecovery of PoliciesService");
		ObjectMapper mapper = new ObjectMapper();		
		try {
			ScalingAndRecovery scalingAndRecovery = mapper.readValue(scalingAndRecoveryData, ScalingAndRecovery.class);
			PoliciesDAO policiesDAO = new PoliciesDAO();
			policiesDAO.insertScalingAndRecovery(scalingAndRecovery);
		} catch (IOException e) {
			logger.error("Error in reading data : " + scalingAndRecoveryData + " using object mapper in insertScalingAndRecovery");
			throw new PoliciesServiceException("Error in reading data : " + scalingAndRecoveryData + " using object mapper in insertScalingAndRecovery");
		}
	} // end of insertScalingAndRecovery
	
	@GET
	@Path("/getAllScalingAndRecovery")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllScalingAndRecovery() throws DataBaseOperationFailedException {
		logger.debug(".getAllScalingAndRecovery of PoliciesService");
		List<ScalingAndRecovery> scalingAndRecoveryList = new ArrayList<ScalingAndRecovery>();
		PoliciesDAO policiesDAO = new PoliciesDAO();
		scalingAndRecoveryList = policiesDAO.getAllScalingAndRecovery();
		Gson gson = new Gson();
		String list = gson.toJson(scalingAndRecoveryList);
		return list;
	} // end of getAllScalingAndRecovery method
	
	@GET
	@Path("/removeScalingAndRecovery/{data}")
	public void removeScalingAndRecovery(@PathParam("data") String data) throws DataBaseOperationFailedException {
		logger.debug(".removeScalingAndRecovery of PoliciesService");
		PoliciesDAO policiesDAO = new PoliciesDAO();
		policiesDAO.removeScalingAndRecoveryByApplication(data);
	} // end of removeScalingAndRecovery
	
	@POST
	@Path("/insertHostingScalingPolicy")
	@Consumes(MediaType.APPLICATION_JSON)
	public void insertHostingScalingPolicy(String data) throws PoliciesServiceException, DataBaseOperationFailedException {
		logger.debug(".insertHostingScalingPolicy of PoliciesService");
		ObjectMapper objectMapper = new ObjectMapper();		
		try {
			HostScalingPolicy hostScalingPolicy = objectMapper.readValue(data, HostScalingPolicy.class);
			PoliciesDAO policiesDAO = new PoliciesDAO();
			policiesDAO.insertAllHostScalingPolicy(hostScalingPolicy);
		} catch (IOException e) {
			throw new PoliciesServiceException("Error in reading data : "+data+" using object mapper in insertScalingAndRecovery");
		}
		
	} // end of insertHostingScalingPolicy
	
	@GET
	@Path("/getAllHostScalingPolicy")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllHostScalingPolicy() throws DataBaseOperationFailedException {
		logger.debug(".getAllHostScalingPolicy of PoliciesService");
		List<HostScalingPolicy> hostPolicyList = new ArrayList<HostScalingPolicy>();
		PoliciesDAO policiesDAO = new PoliciesDAO();
		hostPolicyList = policiesDAO.selectAllHostScalingPolicy();
		Gson gson = new Gson();
		String hostPolicies = gson.toJson(hostPolicyList);
		return hostPolicies;
	} // end of getAllHostScalingPolicy
	
	@GET
	@Path("/removeHostScalingPolicy/{name}")
	public void removeHostScalingPolicy(@PathParam("name") String name) throws DataBaseOperationFailedException {
		PoliciesDAO policiesDAO = new PoliciesDAO();
		policiesDAO.removeHostScalingPolicyByName(name);
	} // end of removeHostScalingPolicy
	
	@POST
	@Path("/insertServiceAffinities")
	@Consumes(MediaType.APPLICATION_JSON)
	public void insertServiceAffinities(String serviceAffinitiesData) throws PoliciesServiceException, DataBaseOperationFailedException {
		logger.debug(".insertServiceAffinities of PoliciesService");
		ObjectMapper mapper = new ObjectMapper();
		try {
			ServiceAffinities serviceAffinities = mapper.readValue(serviceAffinitiesData, ServiceAffinities.class);
			PoliciesDAO policiesDAO = new PoliciesDAO();
			policiesDAO.insertAllServiceAffinities(serviceAffinities);
		} catch (IOException e) {
			logger.error("Error in reading data : " + serviceAffinitiesData + " using object mapper in insertScalingAndRecovery");
			throw new PoliciesServiceException("Error in reading data : " + serviceAffinitiesData + " using object mapper in insertScalingAndRecovery");
		}
	} // end of insertServiceAffinities

	@GET
	@Path("/getServiceAffinities")
	@Produces(MediaType.APPLICATION_JSON)
	public String selectServiceAffinities() throws DataBaseOperationFailedException {
		logger.debug(".selectServiceAffinities of PoliciesService");
		List<ServiceAffinities> serviceAffinitiesList = new ArrayList<ServiceAffinities>();
		PoliciesDAO policiesDAO = new PoliciesDAO();
		serviceAffinitiesList = policiesDAO.getAllServiceAffinities();
		Gson gson = new Gson();
		String serviceAffinities = gson.toJson(serviceAffinitiesList);
		return serviceAffinities;
	} // end of selectServiceAffinities
	
	@GET
	@Path("/removeAllServiceAffinities/{application}")
	public void removeAllServiceAffinities(@PathParam("application") String application) throws DataBaseOperationFailedException {
		logger.debug(".removeAllServiceAffinities of PoliciesService");
		PoliciesDAO policiesDAO = new PoliciesDAO();
		policiesDAO.removeServiceAffinitiesByApplication(application);
	} // end of removeAllServiceAffinities
	
	@POST
	@Path("/insertResourceSelection")
	@Consumes(MediaType.APPLICATION_JSON)
	public void insertResourceSelection(String resourceSelectionData) throws PoliciesServiceException, DataBaseOperationFailedException {
		logger.debug(".insertResourceSelection of PoliciesService");
		ObjectMapper mapper = new ObjectMapper();
		try {
			ResourceSelection resourceSelection = mapper.readValue(resourceSelectionData, ResourceSelection.class);
			PoliciesDAO policiesDAO = new PoliciesDAO();
			policiesDAO.insertResourceSelection(resourceSelection);
		} catch (IOException e) {
			logger.error("Error in reading data : " + resourceSelectionData + " using object mapper in insertScalingAndRecovery");
			throw new PoliciesServiceException("Error in reading data : " + resourceSelectionData + " using object mapper in insertScalingAndRecovery");
		}
		
	}
	
	@GET
	@Path("/getAllResourceSelection")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllResourceSelection() throws DataBaseOperationFailedException {		
		logger.debug(".getAllResourceSelection of PoliciesService");
		List<ResourceSelection> resourceSelectionList = new ArrayList<ResourceSelection>();
		PoliciesDAO policiesDAO = new PoliciesDAO();
		resourceSelectionList = policiesDAO.getAllResourceSelection();
		Gson gson = new Gson();
		String list = gson.toJson(resourceSelectionList);
		return list;
		
	} // end of getAllResourceSelection
	
	@GET
	@Path("/removeResourceSelectionByRank/{rank}")
	public void removeResourceSelection(String rank) throws DataBaseOperationFailedException {
		logger.debug(".removeResourceSelection of PoliciesService");
		PoliciesDAO policiesDAO = new PoliciesDAO();
		policiesDAO.removeResourceSelectionByRank(rank);
	} // end of removeResourceSelectionByRank
	
	
	
}

















