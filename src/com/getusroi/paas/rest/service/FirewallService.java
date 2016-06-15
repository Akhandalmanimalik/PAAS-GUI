package com.getusroi.paas.rest.service;

import java.io.IOException;
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
import com.getusroi.paas.dao.FirewallDAO;
import com.getusroi.paas.rest.service.exception.FirewallServiceException;
import com.getusroi.paas.sdn.service.SDNInterface;
import com.getusroi.paas.sdn.service.impl.SDNServiceImplException;
import com.getusroi.paas.sdn.service.impl.SDNServiceWrapperImpl;
import com.getusroi.paas.vo.FirewallInbound;
import com.getusroi.paas.vo.FirewallOutbound;
import com.google.gson.Gson;

@Path("/firewallService")
public class FirewallService {
	static final Logger logger = LoggerFactory.getLogger(FirewallService.class);
	
	@POST
	@Path("/addFirewallOutbound")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addOutBoundFirewall(String outboundfirewallData) throws SDNServiceImplException, DataBaseOperationFailedException, FirewallServiceException{
		logger.debug(".addOutBoundFirewall method of FirewallService");
		FirewallDAO firewallDAO=new FirewallDAO();
		ObjectMapper mapper = new ObjectMapper();
		SDNInterface FirewallOutbound=new SDNServiceWrapperImpl();
		try {
			FirewallOutbound firewallOutbound=mapper.readValue(outboundfirewallData, FirewallOutbound.class);			
			FirewallOutbound.createFirewallRule(firewallOutbound.getOut_name(),firewallOutbound.getOut_name()+"openflow", firewallOutbound.getOut_ip(), firewallOutbound.getOut_portrange());
			firewallDAO.addOutboundFirewall(firewallOutbound);
		} catch (IOException e) {
			logger.error("Error in reading the data : "+outboundfirewallData+" using object mappper in addOutBoundFirewall");
			throw new FirewallServiceException("Error in reading the data : "+outboundfirewallData+" using object mappper in addOutBoundFirewall");
		}
		
	}//end of method addOutBoundFirewall
	
	
	@GET
	@Path("/getAllFirewallOutbound")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllOutboundFirewall() throws DataBaseOperationFailedException{
		logger.debug(".getAllOutboundFirewall method of FirewallService");
		FirewallDAO firewallDAO=new FirewallDAO();
		List<FirewallOutbound> firewallOutboundList=firewallDAO.getAllFirewallOutbound();
		Gson gson = new Gson();
		String firewallOutboundJsonString=gson.toJson(firewallOutboundList);
		logger.debug("firewall outbound json string : "+firewallOutboundJsonString);
		return firewallOutboundJsonString;
	}//end of method getAllOutboundFirewall
	
	@GET
	@Path("deleteOutboundFirewallByName/{outboundName}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteOutboundFirewallByName(@PathParam("outboundName") String outboundName) throws DataBaseOperationFailedException{
		logger.debug(".deleteOutboundFirewallByName method of FirewallService");
		FirewallDAO firewallDAO=new FirewallDAO();	
		firewallDAO.deleteFirewallOutboundByName(outboundName);
		return "Firewall outbound deleted used outbound name : "+outboundName;
	}//end of method deleteOutboundFirewallByName
	
	@POST
	@Path("/updateFirewallOutbound")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateFirewallOutbound(String outboundfirewallData) throws DataBaseOperationFailedException, FirewallServiceException{
		logger.debug(".updateFirewallOutbound method of FirewallService");
		FirewallDAO firewallDAO=new FirewallDAO();
		ObjectMapper mapper = new ObjectMapper();
		try {
			FirewallOutbound firewallOutbound=mapper.readValue(outboundfirewallData, FirewallOutbound.class);
			firewallDAO.updateOutboundFirewall(firewallOutbound);
		} catch (IOException e) {
			logger.error("Error in reading the data : "+outboundfirewallData+" using object mappper in updateFirewallOutbound");
			throw new FirewallServiceException("Error in reading the data : "+outboundfirewallData+" using object mappper in updateFirewallOutbound");

		}
		
	}//end of method updateFirewallOutbound
	
	@POST
	@Path("/addFirewallInbound")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addInBoundFirewall(String inboundfirewallData) throws SDNServiceImplException, DataBaseOperationFailedException, FirewallServiceException{
		logger.debug(".addInBoundFirewall method of FirewallService");
		FirewallDAO firewallDAO=new FirewallDAO();
		ObjectMapper mapper = new ObjectMapper();
		SDNInterface FirewallOutbound=new SDNServiceWrapperImpl();
		try {
			FirewallInbound firewallinbound=mapper.readValue(inboundfirewallData, FirewallInbound.class);			
			FirewallOutbound.createFirewallRule(firewallinbound.getIn_name(),firewallinbound.getIn_name()+"open-flow", firewallinbound.getIn_ip(), firewallinbound.getIn_portrange());
			firewallDAO.insertFirewallInbound(firewallinbound);
		} catch (IOException e) {
			logger.error("Error in reading the data : "+inboundfirewallData+" using object mappper in addOutBoundFirewall");
			throw new FirewallServiceException("Error in reading the data : "+inboundfirewallData+" using object mappper in addOutBoundFirewall");
		}
		
	}//end of method addOutBoundFirewall
	
	@GET
	@Path("/getAllInboundFirewall")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllInboundFirewall() throws DataBaseOperationFailedException{
		logger.debug(".getAllInboundFirewall method of FirewallService");
		FirewallDAO firewallDAO=new FirewallDAO();
		List<FirewallInbound> firewallInboundList=firewallDAO.getAllFirewallInbound();
		Gson gson = new Gson();
		String firewallInboundJsonString=gson.toJson(firewallInboundList);
		logger.debug("firewall inbound json string : "+firewallInboundJsonString);
		return firewallInboundJsonString;
	}//end of method getAllOutboundFirewall
	
	@GET
	@Path("deleteInboundFirewallByName/{outboundName}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteInboundFirewallByName(@PathParam("outboundName") String inboundName) throws DataBaseOperationFailedException{
		logger.debug(".deleteInboundFirewallByName method of FirewallService");
		FirewallDAO firewallDAO=new FirewallDAO();	
		firewallDAO.deleteFirewallInboundByName(inboundName);
		return "Firewall inbound deleted used inbound name : "+inboundName;
	}//end of method deleteOutboundFirewallByName
	
	@POST
	@Path("/updateFirewallOutbound")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateFirewallInbound(String inboundfirewallData) throws DataBaseOperationFailedException, FirewallServiceException{
		logger.debug(".updateFirewallOutbound method of FirewallService");
		FirewallDAO firewallDAO=new FirewallDAO();
		ObjectMapper mapper = new ObjectMapper();
		try {
			FirewallInbound firewallInbound=mapper.readValue(inboundfirewallData, FirewallInbound.class);
			firewallDAO.updateFirewallInbound(firewallInbound);
		} catch (IOException e) {
			logger.error("Error in reading the data : "+inboundfirewallData+" using object mappper in updateFirewallInbound");
			throw new FirewallServiceException("Error in reading the data : "+inboundfirewallData+" using object mappper in updateFirewallInbound");
		}
		
	}//end of method updateFirewallOutbound
	

}
