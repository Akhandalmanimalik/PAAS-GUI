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
import com.getusroi.paas.dao.NetworkDAO;
import com.getusroi.paas.rest.RestServiceHelper;
import com.getusroi.paas.rest.service.exception.PAASNetworkServiceException;
import com.getusroi.paas.sdn.service.impl.SDNServiceImplException;
import com.getusroi.paas.vo.ACL;
import com.getusroi.paas.vo.Subnet;
import com.getusroi.paas.vo.VPC;
import com.google.gson.Gson;


@Path("/networkservice")
public class PAASNetworkService {
	 static final Logger LOGGER = LoggerFactory.getLogger(PAASNetworkService.class);

	 static final String TENANT="tenant";
	 @POST
	 @Path("/addVPC")
	 @Consumes(MediaType.APPLICATION_JSON)
	public void addVPC(String vpcData,@Context HttpServletRequest req) throws DataBaseOperationFailedException, PAASNetworkServiceException{
		LOGGER.debug(".addVPC method of PAASNetworkService");
		ObjectMapper mapper = new ObjectMapper();
		NetworkDAO networkDAO=new NetworkDAO();
		RestServiceHelper restServiceHelper = new RestServiceHelper();
		try {
			
			VPC vpc=mapper.readValue(vpcData,VPC.class);
			if (vpc != null) {
			HttpSession session = req.getSession(true);
			vpc.setTenant_id(restServiceHelper.convertStringToInteger( session.getAttribute("id")+""));
			//vpc.setVpcId(PAASGenericHelper.getCustomUUID(PAASConstant.VPC_PREFIX));
			networkDAO.registerVPC(vpc);
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
		 NetworkDAO networkDAO=new NetworkDAO();
		 List<VPC> vpcList=networkDAO.getAllVPC((int)session.getAttribute("id"));
		 Gson gson=new Gson();
		 String vpcInJsonString=gson.toJson(vpcList);
		 return vpcInJsonString;
	 }//end of method getAllVPC
	 
	 @GET
	 @Path("/getAllACL")
	 @Produces(MediaType.APPLICATION_JSON)
	 public String getAllACL(@Context HttpServletRequest req) throws DataBaseOperationFailedException{
			LOGGER.debug("coming inside pass network of get all aclS");
			NetworkDAO networkDAO = new NetworkDAO();
			String aclInJsonString = null;
 			try {
 				LOGGER.debug("BEFORE SESSION");
				HttpSession session = req.getSession(true);
				LOGGER.debug("coming before dao call"+(int)session.getAttribute("id"));
				List<ACL> aclList = networkDAO.getAllACL((int)session.getAttribute("id"));
				LOGGER.debug("COMMING AFTER DAO CALL");
				Gson gson = new Gson();
				aclInJsonString = gson.toJson(aclList);
				LOGGER.debug(""+aclInJsonString);
				}
			 catch (Exception e) {
					e.printStackTrace();
				}
			return aclInJsonString;
		}//end of method getAllACL
	 
	 @GET
	 @Path("/deleteVPCByName/{vpcName}")
	 @Produces(MediaType.TEXT_PLAIN)
	public String deleteVPCByName(@PathParam("vpcName") String vpcName) throws DataBaseOperationFailedException {
		 LOGGER.debug(".deleteVPCByName method of PAASNetworkService");
		 NetworkDAO networkDAO=new NetworkDAO();
		 networkDAO.deleteVPCByName(vpcName);
		 return "vpc with name : "+vpcName+" is delete successfully";
	 }//end of method deleteVPCByName
	 
	 @POST
	 @Path("/updateVPC")
	 @Consumes(MediaType.APPLICATION_JSON)
	public void updateVPC(String vpcData) throws DataBaseOperationFailedException, PAASNetworkServiceException{
		LOGGER.debug(".updateVPC method of PAASNetworkService");
		ObjectMapper mapper = new ObjectMapper();
		NetworkDAO networkDAO=new NetworkDAO();
		try {
			VPC vpc=mapper.readValue(vpcData,VPC.class);			
			networkDAO.updateVPCByNameAndVPCId(vpc);
		} catch (IOException e) {
			LOGGER.error("Error in reading data : "+vpcData+" using object mapper in updateVPC");
			throw new PAASNetworkServiceException("Error in reading data : "+vpcData+" using object mapper in updateVPC");
		}		
	}//end of method updateVPC
	 
	 @POST
	 @Path("/addSubnet")
	 @Consumes(MediaType.APPLICATION_JSON)
	 public void addSubnet(String subnetData, @Context HttpServletRequest request) throws DataBaseOperationFailedException, SDNServiceImplException, PAASNetworkServiceException{
		 	LOGGER.debug(".addSubnet method of PAASNetworkService"+subnetData);
			ObjectMapper mapper = new ObjectMapper();
			NetworkDAO networkDAO = new NetworkDAO();
			try {
				Subnet subnet = mapper.readValue(subnetData, Subnet.class);
				if (subnet != null) {
					HttpSession session = request.getSession(true);
					subnet.setTenantId((int)session.getAttribute("id"));
					networkDAO.addSubnet(subnet);
				} 
			} catch (IOException e) {
				 LOGGER.error(""+e);
				 LOGGER.debug("Error in reading data : " + subnetData
						+ " using object mapper in addSubnet");
				throw new PAASNetworkServiceException("Error in reading data : "
						+ subnetData + " using object mapper in addSubnet");
			}

		}//end of method addSubnet
	 
	 @GET
	 @Path("/getAllSubnet")
	 @Produces(MediaType.APPLICATION_JSON)
	 public String getAllSubnet(@Context HttpServletRequest request) throws DataBaseOperationFailedException{

			LOGGER.debug(".getAllSubnet method of PAASNetworkService");
			NetworkDAO networkDAO = new NetworkDAO();
			String subnetJsonString = null;
			try {
				HttpSession session = request.getSession(true);
				List<Subnet> subnetList = networkDAO.getAllSubnetByTenantId((int)session.getAttribute("id"));
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
		 NetworkDAO networkDAO=new NetworkDAO();
		 networkDAO.deleteSubnetBySubnetName(subnetName);
		 return "subnet with name : "+subnetName+" is delete successfully";
	 }//end of method deleteSubnetByName
	 
	 @POST
	 @Path("/updateSubnet")
	 @Consumes(MediaType.APPLICATION_JSON)
	public void updateSubnet(String subnetData) throws DataBaseOperationFailedException, PAASNetworkServiceException{
		LOGGER.debug(".updateSubnet method of PAASNetworkService");
		ObjectMapper mapper = new ObjectMapper();
		NetworkDAO networkDAO=new NetworkDAO();
		try {
			Subnet subnet=mapper.readValue(subnetData,Subnet.class);			
			networkDAO.updateSubnetBySubnetIDAndSubnetName(subnet);
		} catch (IOException e) {
			LOGGER.error("Error in reading data : "+subnetData+" using object mapper in updateSubnet");
			throw new PAASNetworkServiceException("Error in reading data : "+subnetData+" using object mapper in updateSubnet");
		}		
	}//end of method updateSubnet
	 
	 @POST
	 @Path("/addACLRule")
	 @Consumes(MediaType.APPLICATION_JSON)
	 public void addACLRule(String aclData,@Context HttpServletRequest req) throws SDNServiceImplException, DataBaseOperationFailedException, PAASNetworkServiceException{
		 LOGGER.debug(".addACLRule method of PAASNetworkService");
		 ObjectMapper mapper = new ObjectMapper();
		 NetworkDAO networkDAO=new NetworkDAO();
		 /*SDNInterface sdnService = new SDNServiceWrapperImpl();
		 boolean flowFlag=false;*/
		 try {
			ACL acl = mapper.readValue(aclData, ACL.class);			
//			flowFlag = sdnService.installFlow(acl.getAclName(), acl.getSourceIp(), acl.getDestinationIp(),PAASConstant.ACL_PASS_ACTION_KEY);
			HttpSession session = req.getSession(true);
			if(acl != null)
				acl.setTenantId((int)session.getAttribute("id"));
				networkDAO.insertACL(acl);
			
		} catch (IOException e) {
			LOGGER.error("Error in reading data : "+aclData+" using object mapper in addACLRule");
			throw new PAASNetworkServiceException("Error in reading data : "+aclData+" using object mapper in addACLRule");
		}
	 }//end of method addACLRule
	 
	 @GET
	 @Path("/getAllACLNames")
	 @Produces(MediaType.APPLICATION_JSON)
	 public String getAllACLNames() throws DataBaseOperationFailedException{
		 LOGGER.debug(".getAllACL method of PAASNetworkService");
		 NetworkDAO networkDAO=new NetworkDAO();
		 List<String> aclList=networkDAO.getAllACLNames();
		 Gson gson=new Gson();
		 String aclInJsonString=gson.toJson(aclList);
		 return aclInJsonString;
	 }//end of method getAllACLNames
	 
	 	@GET
		@Path("/deleteACLByNameUsingTenantId/{aclName}")
		@Produces(MediaType.TEXT_PLAIN)
		public String deleteACLByNameUsingTenantId(@PathParam("aclName") String aclName,@Context HttpServletRequest req)
				throws DataBaseOperationFailedException {
	 		LOGGER.debug(".deleteAclByName method of PAASNetworkService");
	 		LOGGER.debug("Name is"+aclName);
			 
			NetworkDAO networkDAO = new NetworkDAO();
			RestServiceHelper restServiceHelper = new RestServiceHelper();
			HttpSession session = req.getSession(true);
			
			int tenant_id = restServiceHelper.convertStringToInteger(session
					.getAttribute("id") + "");
			
			networkDAO.deleteACLByName(aclName,tenant_id);
			return "acl with name : " + aclName + " is delete successfully";
		}// end 
	 
}
