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

import com.getusroi.paas.dao.DataBaseOperationFailedException;
import com.getusroi.paas.dao.VpcDAO;
import com.getusroi.paas.marathon.service.MarathonServiceException;
import com.getusroi.paas.rest.RestServiceHelper;
import com.getusroi.paas.rest.service.exception.PAASNetworkServiceException;
import com.getusroi.paas.vo.VPC;
import com.google.gson.Gson;

@Path("/vpcService")
public class VpcService {
	static final Logger LOGGER = LoggerFactory.getLogger(VpcService.class);
	static final String TENANT = "tenant";

	VpcDAO vpcDAO = null;

	/**
	 * To insert vpc into the db
	 * @param vpcData
	 * @param req
	 * @throws DataBaseOperationFailedException
	 * @throws PAASNetworkServiceException
	 */
	@POST
	@Path("/addVPC")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addVPC(String vpcData, @Context HttpServletRequest req)
			throws DataBaseOperationFailedException,
			PAASNetworkServiceException {
		LOGGER.debug(".addVPC method of VpcService : VpcService "+vpcData);
		ObjectMapper mapper = new ObjectMapper();
		vpcDAO = new VpcDAO();
		RestServiceHelper restServiceHelper = new RestServiceHelper();
		try {
			VPC vpc = mapper.readValue(vpcData, VPC.class);
			if (vpc != null) {
				HttpSession session = req.getSession(true);
				vpc.setTenant_id(restServiceHelper
						.convertStringToInteger(session.getAttribute("id") + ""));
				// vpc.setVpcId(PAASGenericHelper.getCustomUUID(PAASConstant.VPC_PREFIX));
				vpcDAO.registerVPC(vpc);
			}
		} catch (IOException e) {
			LOGGER.error("Error in reading data : " + vpcData
					+ " using object mapper in addVPC", e);
			throw new PAASNetworkServiceException("Error in reading data : "
					+ vpcData + " using object mapper in addVPC");
		}

	}// end of method

	/**
	 * To get All vpc using tenant id
	 * @param req
	 * @return
	 * @throws DataBaseOperationFailedException
	 */
	@GET
	@Path("/getAllVPC")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllVPC(@Context HttpServletRequest req)
			throws DataBaseOperationFailedException {
		LOGGER.debug(".getAllVPC method of VpcService");
		HttpSession session = req.getSession(true);
		vpcDAO = new VpcDAO();
		List<VPC> vpcList = vpcDAO.getAllVPC((int) session.getAttribute("id"));
		Gson gson = new Gson();
		String vpcInJsonString = gson.toJson(vpcList);
		LOGGER.debug("vpcList SIZE " + vpcList.size() + "vpcList : " + vpcList);
		return vpcInJsonString;
	}// end of method getAllVPC

	/**
	 * To Delete Vpc by using Vpc name
	 * @param vpcName
	 * @return
	 * @throws DataBaseOperationFailedException
	 */
	@GET
	@Path("/deleteVPCById/{vpcId}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteVPCByName(@PathParam("vpcId") String vpcId)
			throws DataBaseOperationFailedException {
		LOGGER.debug(".deleteVPCByName method of VpcService : vpcId "+vpcId);
		vpcDAO = new VpcDAO();
		vpcDAO.deleteVPCById(new RestServiceHelper().convertStringToInteger(vpcId));
		return "vpc with name : " + vpcId + " is delete successfully";
	}// end of method deleteVPCByName

	@PUT
	@Path("/updateVPC")
	@Produces(MediaType.TEXT_PLAIN)
	public String updateAclById(String vpcObject,
			@Context HttpServletRequest req) throws MarathonServiceException {
		LOGGER.debug(".updateVPC method of VpcService : vpcObject " + vpcObject);
		ObjectMapper mapper = new ObjectMapper();
		vpcDAO = new VpcDAO();
		try {
			VPC vpc = mapper.readValue(vpcObject, VPC.class);
			vpcDAO.updateVPCByVPCId(vpc);
			LOGGER.debug("Updated Successfully.");
			return "Success";
		} catch (Exception e) {
			LOGGER.error("Error updating acl ", e);
		}
		return "failed";
	}

	/**
	 * 
	 * @param vpcData
	 * @throws DataBaseOperationFailedException
	 * @throws PAASNetworkServiceException
	 */
	
	/*
	 * @PUT
	 * 
	 * @Path("/updateVPC")
	 * 
	 * @Produces(MediaType.TEXT_PLAIN)
	 * 
	 * public String updateVPC(String vpcData,@Context HttpServletRequest req)
	 * throws DataBaseOperationFailedException, PAASNetworkServiceException {
	 * 
	 * LOGGER.debug(".updateVPC method of VpcService"); ObjectMapper mapper =
	 * new ObjectMapper(); vpcDAO = new VpcDAO(); try{ VPC vpc =
	 * mapper.readValue(vpcData, VPC.class); vpcDAO.updateVPCByVPCId(vpc);
	 * return "Success"; } catch (IOException e) {
	 * LOGGER.error("Error in reading data : " + vpcData +
	 * " using object mapper in updateVPC"); throw new
	 * PAASNetworkServiceException("Error in reading data : " + vpcData +
	 * " using object mapper in updateVPC"); }
	 * 
	 * }
	 */// end of method updateVPC

	/**
	 * To check vpc name exist or not in database
	 * @param vpcName
	 * @param req
	 * @return
	 * @throws DataBaseOperationFailedException
	 */
	@GET
	@Path("/checkVPC/{vpcName}")
	@Produces(MediaType.TEXT_PLAIN)
	public String checkVPCNameExist(@PathParam("vpcName") String vpcName, @Context HttpServletRequest req) throws DataBaseOperationFailedException {
		LOGGER.debug("Inside checkVPCNameExist (.)  of VpcServic : vpcName "+vpcName);
		HttpSession session = req.getSession(true);
		vpcDAO = new VpcDAO();
		int id = vpcDAO.getVPCIdByVPCNames(vpcName, (int) session.getAttribute("id"));
		LOGGER.debug("RETURN ID " + id);
		if (id > 0)
			return "success";
		else
			return "failure";
	}// end of method checkVPCByName validation

}
