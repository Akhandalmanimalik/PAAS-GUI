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
import com.getusroi.paas.dao.SubnetDAO;
import com.getusroi.paas.helper.PAASConstant;
import com.getusroi.paas.helper.ScriptService;
import com.getusroi.paas.rest.RestServiceHelper;
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
	RestServiceHelper restServHlper= null;	
	
	/**
	 * Rest Service to add new Subnet
	 * @param subnetData
	 * @param request
	 * @throws DataBaseOperationFailedException
	 * @throws SDNServiceImplException
	 * @throws PAASNetworkServiceException
	 */
	@POST
	 @Path("/addSubnet")
	 @Consumes(MediaType.APPLICATION_JSON)
	 public void addSubnet(String subnetData, @Context HttpServletRequest request) throws DataBaseOperationFailedException, SDNServiceImplException, PAASNetworkServiceException{
		 	LOGGER.debug(".addSubnet method of SubnetService"+subnetData);
			ObjectMapper mapper = new ObjectMapper();
			subnetDao= new SubnetDAO();
			ScriptService scriptService=new ScriptService();
			try {
				subnet = mapper.readValue(subnetData, Subnet.class);
				if (subnet != null) {
					HttpSession session = request.getSession(true);
					subnet.setTenantId((int)session.getAttribute("id"));
					int id=	subnetDao.addSubnet(subnet);
					//scriptService.createSubnetNetwork(subnet.getCidr(),PAASConstant.TENANT+subnet.getTenantId()+"-"+PAASConstant.SUBNET_PREFIX+id);
				} 
			} catch (IOException  e) {
				 LOGGER.error(""+e);
				 
				throw new PAASNetworkServiceException("Error in reading data : "
						+ subnetData + " using object mapper in addSubnet");
			}/*catch ( InterruptedException e) {
				LOGGER.error("Error In Excuting subnetCrearion Script ",e);
			}*/
		}//end of method addSubnet
	
	/**
	 * Rest service to Get Subnet name using Vpc
	 * @param subnetName
	 * @param request
	 * @return
	 * @throws DataBaseOperationFailedException
	 * @throws PAASNetworkServiceException
	 */
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
	
	/**
	 * Rest Service to get all Subnet
	 * @param request
	 * @return
	 * @throws DataBaseOperationFailedException
	 */
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
	 
	 /**
	  * Rest Service to delete Subnet by subnet id
	  * @param id
	  * @return
	  * @throws DataBaseOperationFailedException
	 * @throws IOException 
	 * @throws InterruptedException 
	  */
	 @GET
	 @Path("/deleteSubnetById/{id}")
	 @Produces(MediaType.TEXT_PLAIN)
	public String deleteSubnetById(@PathParam("id") String id,@Context HttpServletRequest request) throws DataBaseOperationFailedException, InterruptedException, IOException {
		 LOGGER.debug(".deleteSubnetById method of PAASNetworkService "+id);
		 restServHlper= new RestServiceHelper(); 
		 subnetDao = new SubnetDAO();
			HttpSession session = request.getSession(true);

	
				//new ScriptService().deleteSubnetNetwork(PAASConstant.TENANT+(int)session.getAttribute("id")+"-"+PAASConstant.SUBNET_PREFIX+id);
				 subnetDao.deleteSubnetById(restServHlper.convertStringToInteger(id));

		
			LOGGER.debug("Subnet  : "+id+" delete successubnetNamesfully");
		 return "subnet with name : "+id+" is delete successfully";
	 }//end of method deleteSubnetByName
	 
	 /**
	  * Rest Service to update Subnet by subnet id
	  * @param subnetData
	  * @return
	  * @throws DataBaseOperationFailedException
	  * @throws PAASNetworkServiceException
	  */
	 @PUT
	 @Path("/updateSubnet")
	 @Produces(MediaType.TEXT_PLAIN)
	public String updateSubnet(String subnetData) throws DataBaseOperationFailedException, PAASNetworkServiceException{
		LOGGER.debug(".updateSubnet method of SubnetService subnetData "+subnetData);
		ObjectMapper mapper = new ObjectMapper();
		subnetDao = new SubnetDAO();
		try {
			Subnet subnet=mapper.readValue(subnetData,Subnet.class);
			subnetDao.updateSubnetBySubnetID(subnet);
			return "Success";
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

