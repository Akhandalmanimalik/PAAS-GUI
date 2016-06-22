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
import com.getusroi.paas.dao.SubnetDAO;
import com.getusroi.paas.rest.service.exception.PAASNetworkServiceException;
import com.getusroi.paas.sdn.service.impl.SDNServiceImplException;
import com.getusroi.paas.vo.Subnet;
import com.google.gson.Gson;

@Path("/subnetService")
public class SubnetService {
	private Logger LOGGER = LoggerFactory.getLogger(SubnetService.class);
	SubnetDAO subnetDao = null;
	ObjectMapper mapper = null;
	Subnet subnet = null;
	
	
	@POST
	 @Path("/addSubnet")
	 @Consumes(MediaType.APPLICATION_JSON)
	 public void addSubnet(String subnetData, @Context HttpServletRequest request) throws DataBaseOperationFailedException, SDNServiceImplException, PAASNetworkServiceException{
		 	LOGGER.debug(".addSubnet method of PAASNetworkService"+subnetData);
			ObjectMapper mapper = new ObjectMapper();
			subnetDao= new SubnetDAO();
			try {
				subnet = mapper.readValue(subnetData, Subnet.class);
				if (subnet != null) {
					HttpSession session = request.getSession(true);
					subnet.setTenantId((int)session.getAttribute("id"));
					subnetDao.addSubnet(subnet);
				} 
			} catch (IOException e) {
				 LOGGER.error(""+e);
				 LOGGER.debug("Error in reading data : " + subnetData
						+ " using object mapper in addSubnet");
				throw new PAASNetworkServiceException("Error in reading data : "
						+ subnetData + " using object mapper in addSubnet");
			}

		}//end of method addSubnet
	
	@POST
	@Path("/getSubnetNameByVpc")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getAllSubnetByVpcName(String subnetName, @Context HttpServletRequest  request) throws DataBaseOperationFailedException, PAASNetworkServiceException {
		LOGGER.debug(".getAllSubnet method of PAASNetworkService "+subnetName);
		subnetDao = new SubnetDAO();
		String subnetJsonString = null;
		try {
			HttpSession session = request.getSession(true);
			List<Subnet> subnetList = subnetDao.getAllSubnetByVpcNameAndTenantId(subnetName, (int)session.getAttribute("id"));
			LOGGER.debug("comming"+subnetList);
			Gson gson = new Gson();
			subnetJsonString = gson.toJson(subnetList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subnetJsonString;
	}
	
	 @GET
	 @Path("/getAllSubnet")
	 @Produces(MediaType.APPLICATION_JSON)
	 public String getAllSubnet(@Context HttpServletRequest request) throws DataBaseOperationFailedException{

			LOGGER.debug(".getAllSubnet method of PAASNetworkService");
			subnetDao = new SubnetDAO();
			String subnetJsonString = null;
			try {
				HttpSession session = request.getSession(true);
				List<Subnet> subnetList = subnetDao.getAllSubnetByTenantId((int)session.getAttribute("id"));
				LOGGER.debug("comming"+subnetList);
				Gson gson = new Gson();
				subnetJsonString = gson.toJson(subnetList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return subnetJsonString;
	 }//end of method getAllSubnet
	 
	 
	 @GET
	 @Path("/deleteSubnetByName/{subnetName}")
	 @Produces(MediaType.TEXT_PLAIN)
	public String deleteSubnetByName(@PathParam("subnetName") String subnetName) throws DataBaseOperationFailedException {
		 LOGGER.debug(".deleteVPCByName method of PAASNetworkService");
		 subnetDao = new SubnetDAO();
		 subnetDao.deleteSubnetBySubnetName(subnetName);
		 return "subnet with name : "+subnetName+" is delete successfully";
	 }//end of method deleteSubnetByName
	 
	 @POST
	 @Path("/updateSubnet")
	 @Consumes(MediaType.APPLICATION_JSON)
	public void updateSubnet(String subnetData) throws DataBaseOperationFailedException, PAASNetworkServiceException{
		LOGGER.debug(".updateSubnet method of PAASNetworkService");
		ObjectMapper mapper = new ObjectMapper();
		subnetDao = new SubnetDAO();
		try {
			Subnet subnet=mapper.readValue(subnetData,Subnet.class);			
			subnetDao.updateSubnetBySubnetIDAndSubnetName(subnet);
		} catch (IOException e) {
			LOGGER.error("Error in reading data : "+subnetData+" using object mapper in updateSubnet");
			throw new PAASNetworkServiceException("Error in reading data : "+subnetData+" using object mapper in updateSubnet");
		}		
	}//end of method updateSubnet
	 
	 
	 /**
		 * To check subnet name exist or not
		 * @param subName
		 * @param req
		 * @return
		 * @throws DataBaseOperationFailedException
		 */
		@GET
		@Path("/checkSubnet/{subName}")
		@Produces(MediaType.TEXT_PLAIN)
		public String subnetValidation(@PathParam("subName") String subName,
				@Context HttpServletRequest req)
				throws DataBaseOperationFailedException {
			LOGGER.debug(" coming to check acl of pass network");
			HttpSession session = req.getSession(true);
			SubnetDAO networkDAO = new SubnetDAO();
			int id = networkDAO.getSubnetIdBySubnetName(subName,
					(int) session.getAttribute("id"));
			if (id > 0)
				return "success";
			else
				return "failure";
		}// end of method aClByName
}

