package com.getusroi.paas.rest.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getusroi.paas.marathon.service.IMarathonService;
import com.getusroi.paas.marathon.service.MarathonServiceException;
import com.getusroi.paas.marathon.service.impl.MarathonService;

@Path("/containersService")
public class ContainersService {
	
	private Logger logger = LoggerFactory.getLogger(ContainersService.class);

	@GET
	@Path("/selectMarathonRest")
	@Produces(MediaType.APPLICATION_JSON)
	public String selectMarathonRest(@Context HttpServletRequest  request) throws MarathonServiceException {
		logger.debug(".selectMarathonRest of ContainersService");
		
		HttpSession session=request.getSession(false);
		int userId=(int)session.getAttribute("id");
	
		IMarathonService iMarathonService = new MarathonService();
		int dev = iMarathonService.getInstanceCount(userId,"dev");
		int prod = iMarathonService.getInstanceCount(userId,"prod");
		int qa = iMarathonService.getInstanceCount(userId,"qa");
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("dev", dev);
		jsonObj.put("prod", prod);
		jsonObj.put("qa", qa);
		return jsonObj.toString();
	}
	
}
