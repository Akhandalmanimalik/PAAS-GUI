package com.getusroi.paas.rest.service;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
import com.getusroi.paas.rest.RestServiceHelper;
import com.getusroi.paas.rest.service.exception.PAASNetworkServiceException;
import com.getusroi.paas.vo.VPC;
import com.google.gson.Gson;


@Path("/vpcService")
public class VpcService {
	 static final Logger LOGGER = LoggerFactory.getLogger(VpcService.class);
	 static final String TENANT="tenant";
	 
	 VpcDAO vpcDAO =null;
	 
	 @POST
	 @Path("/addVPC")
	 @Consumes(MediaType.APPLICATION_JSON)
	 public void addVPC(String vpcData,@Context HttpServletRequest req) throws DataBaseOperationFailedException, PAASNetworkServiceException{
		LOGGER.debug(".addVPC method of PAASNetworkService");
		ObjectMapper mapper = new ObjectMapper();
		vpcDAO = new VpcDAO();
		RestServiceHelper restServiceHelper = new RestServiceHelper();
		try {
			VPC vpc=mapper.readValue(vpcData,VPC.class);
			if (vpc != null) {
				LOGGER.debug("VPC "+vpc);
			HttpSession session = req.getSession(true);
			vpc.setTenant_id(restServiceHelper.convertStringToInteger( session.getAttribute("id")+""));
			//vpc.setVpcId(PAASGenericHelper.getCustomUUID(PAASConstant.VPC_PREFIX));
			vpcDAO.registerVPC(vpc);
			}
		} catch (IOException e) {
			LOGGER.error("Error in reading data : "+vpcData+" using object mapper in addVPC",e);
			throw new PAASNetworkServiceException("Error in reading data : "+vpcData+" using object mapper in addVPC");
		}
		
	}//end of method 
	 
	 @GET
	 @Path("/getAllVPC")
	 @Produces(MediaType.APPLICATION_JSON)
	 public String getAllVPC(@Context HttpServletRequest req) throws DataBaseOperationFailedException{
		 LOGGER.debug(".getAllVPC method of PAASNetworkService");
		 HttpSession session = req.getSession(true);
		 vpcDAO = new VpcDAO();
		 List<VPC> vpcList = vpcDAO.getAllVPC((int)session.getAttribute("id"));
		 Gson gson=new Gson();
		 String vpcInJsonString=gson.toJson(vpcList);
		 return vpcInJsonString;
	 }//end of method getAllVPC
	 
	 /**
	  * 
	  * @param vpcName
	  * @return
	  * @throws DataBaseOperationFailedException
	  */
	@GET
	@Path("/deleteVPCByName/{vpcName}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteVPCByName(@PathParam("vpcName") String vpcName)
			throws DataBaseOperationFailedException {
		LOGGER.debug(".deleteVPCByName method of PAASNetworkService");
		vpcDAO = new VpcDAO();
		vpcDAO.deleteVPCByName(vpcName);
		return "vpc with name : " + vpcName + " is delete successfully";
	}// end of method deleteVPCByName

	/**
	 * 
	 * @param vpcData
	 * @throws DataBaseOperationFailedException
	 * @throws PAASNetworkServiceException
	 */
	@POST
	@Path("/updateVPC")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateVPC(String vpcData)
			throws DataBaseOperationFailedException,
			PAASNetworkServiceException {
		LOGGER.debug(".updateVPC method of PAASNetworkService");
		ObjectMapper mapper = new ObjectMapper();
		vpcDAO = new VpcDAO();
		try {
			VPC vpc = mapper.readValue(vpcData, VPC.class);
			vpcDAO.updateVPCByNameAndVPCId(vpc);
		} catch (IOException e) {
			LOGGER.error("Error in reading data : " + vpcData
					+ " using object mapper in updateVPC");
			throw new PAASNetworkServiceException("Error in reading data : "
					+ vpcData + " using object mapper in updateVPC");
		}
	}// end of method updateVPC
	
	 	/**
	 	 * To check vpc name exist or not in database.
	 	 * @param vpcName
	 	 * @param req
	 	 * @return
	 	 * @throws DataBaseOperationFailedException
	 	 */
		@GET
		@Path("/checkVPC/{vpcName}")
		@Produces(MediaType.TEXT_PLAIN)
		public String checkVPCNameExist(@PathParam("vpcName") String vpcName,
				
		@Context HttpServletRequest req)
				throws DataBaseOperationFailedException {
			
			HttpSession session = req.getSession(true);
			vpcDAO = new VpcDAO();
			int id = vpcDAO.getVPCIdByVPCNames(vpcName,
					(int) session.getAttribute("id"));
				LOGGER.debug("RETURN ID "+id);
	
			if (id > 0)
				return "success";
			else
				return "failure";
		}// end of method checkVPCByName validation

		
		
		
}
