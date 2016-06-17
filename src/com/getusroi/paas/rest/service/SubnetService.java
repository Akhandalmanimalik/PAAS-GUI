package com.getusroi.paas.rest.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getusroi.paas.dao.DataBaseOperationFailedException;
import com.getusroi.paas.dao.SubnetDAO;
import com.getusroi.paas.rest.service.exception.PAASNetworkServiceException;
import com.getusroi.paas.vo.Subnet;
import com.google.gson.Gson;

@Path("/subnetService")
public class SubnetService {
	private Logger LOGGER = LoggerFactory.getLogger(SubnetService.class);
	SubnetDAO subnetDao = null;
	ObjectMapper mapper = null;
	Subnet subnet = null;
	
	@POST
	@Path("/getSubnetNameByVpc")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getAllSubnetByVpcName(String subnetName, @Context HttpServletRequest  request) throws DataBaseOperationFailedException, PAASNetworkServiceException {
		LOGGER.debug(".getAllSubnet method of PAASNetworkService "+subnetName);
		subnetDao = new SubnetDAO();
		//mapper = new ObjectMapper();
		subnet = new Subnet();
		String subnetJsonString = null;
		try {
			//subnet = mapper.readValue(subnetName, Subnet.class);
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
}

