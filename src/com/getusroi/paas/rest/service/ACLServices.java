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

import com.getusroi.paas.dao.AclDAO;
import com.getusroi.paas.dao.DataBaseOperationFailedException;
import com.getusroi.paas.marathon.service.MarathonServiceException;
import com.getusroi.paas.rest.RestServiceHelper;
import com.getusroi.paas.rest.service.exception.PAASNetworkServiceException;
import com.getusroi.paas.sdn.service.impl.SDNServiceImplException;
import com.getusroi.paas.vo.ACL;
import com.getusroi.paas.vo.InOutBoundRule;
import com.google.gson.Gson;


@Path("/aclService")
public class ACLServices {
	 static final Logger LOGGER = LoggerFactory.getLogger(ACLServices.class);
	 static final String TENANT="tenant";
	 
	 AclDAO aclDAO = null;
	 InOutBoundRule inOutBoundRule = null;
	 
	/**
	 * Rest Service to Get all Acl		 
	 * @param req
	 * @return
	 * @throws DataBaseOperationFailedException
	 */
	 @GET
	 @Path("/getAllACL")
	 @Produces(MediaType.APPLICATION_JSON)
	 public String getAllACL(@Context HttpServletRequest req) throws DataBaseOperationFailedException{
			LOGGER.debug("Inside (.)  getAllACL of ACLServices ");
			aclDAO = new AclDAO();
			String aclInJsonString = null;
 			try {
				HttpSession session = req.getSession(true);
				List<ACL> aclList = aclDAO.getAllACL((int)session.getAttribute("id"));
				Gson gson = new Gson();
				aclInJsonString = gson.toJson(aclList);
				LOGGER.debug(""+aclInJsonString);
				}
			 catch (Exception e) {
					e.printStackTrace();
				}
			return aclInJsonString;
		}//end of method getAllACL
	  
	 /**
	  * Rest Service to add new Acl into Db
	  * @param aclData
	  * @param req
	  * @return
	  * @throws SDNServiceImplException
	  * @throws DataBaseOperationFailedException
	  * @throws PAASNetworkServiceException
	  */
	 @POST
	 @Path("/addACLRule")
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces(MediaType.TEXT_PLAIN)
	 public String addACLRule(String aclData,@Context HttpServletRequest req) throws SDNServiceImplException, DataBaseOperationFailedException, PAASNetworkServiceException{
		 LOGGER.debug(".addACLRule method of ACLServices");
		 ObjectMapper mapper = new ObjectMapper();
		  aclDAO=new AclDAO();
		 /*SDNInterface sdnService = new SDNServiceWrapperImpl();
		 boolean flowFlag=false;*/
		 try {
			  
			ACL acl = mapper.readValue(aclData, ACL.class);			
//			flowFlag = sdnService.installFlow(acl.getAclName(), acl.getSourceIp(), acl.getDestinationIp(),PAASConstant.ACL_PASS_ACTION_KEY);
			HttpSession session = req.getSession(true);
			if(acl != null){
				acl.setTenantId((int)session.getAttribute("id"));
				aclDAO.insertACL(acl);
				return "Success";
			}
			
		} catch (IOException e) {
			LOGGER.error("Error in reading data : "+aclData+" using object mapper in addACLRule");
			throw new PAASNetworkServiceException("Error in reading data : "+aclData+" using object mapper in addACLRule");
		}
		 return "failed";
	 }//end of method addACLRule
	 
	 /**
	  * Rest Service to Get All Acl names
	  * @return
	  * @throws DataBaseOperationFailedException
	  */
	 @GET
	 @Path("/getAllACLNames")
	 @Produces(MediaType.APPLICATION_JSON)
	 public String getAllACLNames(@Context HttpServletRequest req) throws DataBaseOperationFailedException{
		 LOGGER.debug(".getAllACL method of ACLServices");
		 HttpSession session = req.getSession(true);
		 aclDAO=new AclDAO();
		 List<String> aclList=aclDAO.getAllACLNames((int)session.getAttribute("id"));
		 Gson gson=new Gson();
		 String aclInJsonString=gson.toJson(aclList);
		 return aclInJsonString;
	 }//end of method getAllACLNames
	 
	 	@GET
		@Path("/deleteACLByaclId/{aclId}")
		@Produces(MediaType.TEXT_PLAIN)
		public String deleteACLByaclId(@PathParam("aclId") String aclId,@Context HttpServletRequest req)
				throws DataBaseOperationFailedException {
	 		LOGGER.debug(".deleteAclByName method of ACLServices aclId "+aclId);
			aclDAO = new AclDAO();
			aclDAO.deleteACLByaclId(new RestServiceHelper().convertStringToInteger(aclId));
			return "acl with aclId : " + aclId + " is delete successfully";
		}// end 
	 
	 	/**
	 	 * To update Acl By using Id
	 	 * @param acl
	 	 * @param req
	 	 * @return
	 	 * @throws MarathonServiceException
	 	 */
	 	@PUT
		@Path("/updateAclById")
		@Produces(MediaType.TEXT_PLAIN)

		public String updateAclById(String acl,@Context HttpServletRequest req) throws MarathonServiceException{
			LOGGER.debug(".updateAclById method of AclServices "+acl);
			ObjectMapper mapper = new ObjectMapper();
			aclDAO = new AclDAO();
			try{
				ACL inOutBoundRule=mapper.readValue(acl, ACL.class);
				aclDAO.updateACLById(inOutBoundRule);
				return "Success";
			}catch(Exception e){
				LOGGER.error("Error updating acl ",e);
			}
			return "failed";
		}
	 	
	 	
		/**
		 * To chckAcl name exist or not
		 * @param aclName
		 * @param req
		 * @return
		 * @throws DataBaseOperationFailedException
		 */
		@GET
		@Path("/checkAcl/{aclName}")
		@Produces(MediaType.TEXT_PLAIN)
		public String aclValidation(@PathParam("aclName") String aclName,
				@Context HttpServletRequest req)
				throws DataBaseOperationFailedException {
			LOGGER.debug(" Inside (.) aclValidation of ACLServices");
			HttpSession session = req.getSession(true);
			aclDAO = new AclDAO();
			int id = aclDAO.getACLIdByACLNames(aclName,
					(int) session.getAttribute("id"));
			if (id > 0)
				return "success";
			else
				return "failure";
		}// end of method aClByName validation
		
		
	/**
	 * Rest Service to Insert In Out Bound rule into the DB	
	 * @param aclData
	 * @param req
	 * @throws SDNServiceImplException
	 * @throws DataBaseOperationFailedException
	 * @throws PAASNetworkServiceException
	 */
	@POST
	@Path("/addInOutBoundRule")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String addInOutBoundRule(String aclData,
			@Context HttpServletRequest req) throws SDNServiceImplException,
			DataBaseOperationFailedException, PAASNetworkServiceException {
		LOGGER.debug("Inside (.) addInOutBoundRule of ACLServices");
		ObjectMapper mapper = new ObjectMapper();
		aclDAO = new AclDAO();
		try {
			inOutBoundRule = mapper.readValue(aclData, InOutBoundRule.class);
			if (inOutBoundRule != null)
				aclDAO.insertInOutBoundRule(inOutBoundRule);
				return "Success";
		} catch (IOException e) {
			LOGGER.error("Error in reading data : " + aclData
					+ " using object mapper in addACLRule");
			throw new PAASNetworkServiceException("Error in reading data : "
					+ aclData + " using object mapper in addACLRule");
		}
		
	}// end of method addACLRule
		
		
		/**
		 * To get all Inoutbound rule From db by acl id
		 * @param aclName
		 * @param req
		 * @return
		 * @throws DataBaseOperationFailedException
		 */
		@GET
		@Path("/getAllInOutBoundRuleByAclId/{selectedAclId}")
		@Produces(MediaType.APPLICATION_JSON)
		public String getAllInOutBoundRuleByAclId(@PathParam("selectedAclId") String selectedAclId,@Context HttpServletRequest req)
				throws DataBaseOperationFailedException {
			LOGGER.debug("Inside (.) getAllInOutBoundRuleByAclId of ACLServices "+selectedAclId);
			aclDAO = new AclDAO();
			String aclInJsonString = null;
			try {
				 
				HttpSession session = req.getSession(true);
				List<InOutBoundRule> aclList = aclDAO.getAllInOutBoundRules(Integer.parseInt(selectedAclId), (int) session.getAttribute("id"));
				Gson gson = new Gson();
				aclInJsonString = gson.toJson(aclList);
				LOGGER.debug("" + aclInJsonString);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return aclInJsonString;
		}// end of method getAllACL
		
		/**
		 * This method is to call delete In Out bound rule mehtod of jdbc.
		 * @param id
		 * @param req
		 * @return
		 * @throws DataBaseOperationFailedException
		 */
		@GET
		@Path("/deleteInOutBoundRuleById/{id}")
		@Produces(MediaType.TEXT_PLAIN)
		public String deleteInOutBoundRuleById(@PathParam("id") int id,@Context HttpServletRequest req)
				throws DataBaseOperationFailedException {
	 		LOGGER.debug(".deleteInOutBoundRuleById method of AclServices id "+id);
			aclDAO = new AclDAO();
			aclDAO.deleteInOutBoundRuleById(id);
			return "acl with name : " + id + " is delete successfully";
		}// end 
		
		/**
		 * To update In Out Bound rule By using id
		 * @param inOutBndRule
		 * @param req
		 * @return
		 * @throws MarathonServiceException
		 */
		
		@PUT
		@Path("/updateInOutBoundRuleById")
		@Produces(MediaType.TEXT_PLAIN)
		public String updateInOutBoundRuleById(String inOutBndRule,@Context HttpServletRequest req) throws MarathonServiceException{
			LOGGER.debug(".updateInOutBoundRuleById method of AclServices "+inOutBndRule);
			ObjectMapper mapper = new ObjectMapper();
			aclDAO = new AclDAO();
			try{
				
				InOutBoundRule inOutBoundRule=mapper.readValue(inOutBndRule, InOutBoundRule.class);
				LOGGER.debug(">>>>>>>>>>>>>> "+ inOutBoundRule.getAction());
				aclDAO.updateInOutBoundRuleById(inOutBoundRule);
				return "Success";
			}catch(Exception e){
				LOGGER.error("Error updating InOutBoundRule ",e);
			}
			return "failed";
		}
		
}
