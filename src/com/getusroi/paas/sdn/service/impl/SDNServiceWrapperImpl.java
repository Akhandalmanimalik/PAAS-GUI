package com.getusroi.paas.sdn.service.impl;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getusroi.paas.sdn.service.SDNInterface;
import com.getusroi.paas.vo.ImageRegistry;
import com.getusroi.paas.vo.Storage;




import static com.getusroi.paas.sdn.service.SDNServiceConstant.*;


public class SDNServiceWrapperImpl implements SDNInterface{
	 static final Logger logger = LoggerFactory.getLogger(SDNServiceWrapperImpl.class);

	/**
	 * This method is used to create volume 
	 * @param storage : Storage Object
	 * @return boolean : true if created else false
	 * @throws SDNServiceImplException : Error in creating volume
	 */
	public boolean createVolume(Storage storage) throws SDNServiceImplException{
		logger.debug(".createVolume method of SDNServiceWrapperImpl");
		SDNServiceImpl serviceImpl=new SDNServiceImpl();
		boolean response=false;
		JSONObject postData1 = new JSONObject();		
		String volume_name=storage.getServiceName();		
		String volsize=storage.getVolumeSize();		
		logger.debug("volume_name : "+volume_name+", volsize : "+volsize);
		try {
			postData1.put(NAME_KEY, volume_name);
			postData1.put(VOLUME_SIZE_KEY, volsize);
			response=serviceImpl.createVolume(postData1);
		} catch (JSONException e) {
			logger.error("Unable to create JSONObject for creating volume "+postData1);
			throw new SDNServiceImplException("Unable to create JSONObject for creating volume "+postData1,e);
		}
		return response;		
	}//end of method createVolume
	
	/**
	 * This method is used to create controller for sdn
	 * @param contollerId : controllerId in String
	 * @param ipAddress : ip address in String
	 * @return boolean : true if created else false
	 * @throws SDNServiceImplException : Error in creating Controller
	 */
	public boolean createController(String  contollerId,String  ipAddress) throws SDNServiceImplException{
		logger.debug(".createController method of SDNServiceWrapperImpl");
		SDNServiceImpl serviceImpl=new SDNServiceImpl();
		boolean response=false;
		JSONObject controllerConfig = new JSONObject();
		JSONObject ControllerJson = new JSONObject();
		try {
			controllerConfig.put(CONTROLLER_ID_KEY, contollerId);
			controllerConfig.put(CONTROLLER_IP_KEY, ipAddress);
			controllerConfig.put(CONTROLLER_TYPE_KEY, CONTROLLER_TYPE_VALUE);
			controllerConfig.put(CONTROLLER_VESRION_KEY,CONTROLLER_VESRION_VALUE);
			controllerConfig.put(CONTROLLER_AUDIT_STATUS__KEY, CONTROLLER_AUDIT_STATUS__VALUE);
			ControllerJson.put("controller", controllerConfig);
			response=serviceImpl.createController(ControllerJson);
		} catch (JSONException e) {
			logger.error("Unable to create JSONObject for createController "+ControllerJson);
			throw new SDNServiceImplException("Unable to create JSONObject for  createController "+ControllerJson,e);
		}		
		return response;
		
	}//end of method createContrller
	
	/**
	 * This method is used to creat vtn
	 * @param vtnName : vtn name in String
	 * @return boolean : true if created else false
	 * @throws SDNServiceImplException : Error in creating VTN
	 */
	public boolean createVtn(String  vtnName) throws SDNServiceImplException{
		logger.debug(".createVtn method of SDNServiceWrapperImpl");
		SDNServiceImpl serviceImpl=new SDNServiceImpl();
		boolean response=false;
		//Created  for  VTN
		JSONObject vtnConfig = new JSONObject();
		JSONObject vtnConfigJson = new JSONObject();
		try {
			vtnConfig.put(VTN_NAME_KEY, vtnName);
			vtnConfigJson.put(VTN_KEY, vtnConfig);
			response=serviceImpl.createVtn(vtnConfigJson);
		} catch (JSONException e) {
			logger.error("Unable to create JSONObject for createVtn "+vtnConfigJson);
			throw new SDNServiceImplException("Unable to create JSONObject for  createVtn "+vtnConfigJson,e);
		}
		return response;
	}//end of method createVtn
	
	/**
	 * This method is used to create virtual bridge
	 * @param virtualBridgeName : virtual bridge name in String
	 * @param vtnName : vtn name in String
	 * @return boolean : true if created else false
	 * @throws SDNServiceImplException : Error in creating createVirtualBridge
	 */
	public boolean createVirtualBridge(String  virtualBridgeName,String vtnName,String controllerId) throws SDNServiceImplException{
		logger.debug(".createVirtualBridge method of SDNServiceWrapperImpl");
		SDNServiceImpl serviceImpl=new SDNServiceImpl();
		boolean response=false;
		JSONObject virtualBridgeConfig = new JSONObject();
		JSONObject virtualBridgeConfigJson = new JSONObject();

		try {
			virtualBridgeConfig.put(VIRTUAL_BRIDGE_NAME_KEY, virtualBridgeName);
			virtualBridgeConfig.put(CONTROLLER_ID_KEY,controllerId);
			virtualBridgeConfig.put(DOMAIN_ID_KEY, "(DEFAULT)");
			virtualBridgeConfigJson.put(VIRTUAL_BRIDGE_KEY, virtualBridgeConfig);
			logger.debug("----------virtualBridgeConfigJson---------"
					+ virtualBridgeConfigJson);
			response=serviceImpl.createVirtualBridge(virtualBridgeConfigJson, vtnName);
		} catch (JSONException e) {
			logger.error("Unable to create JSONObject for createVirtualBridge "+virtualBridgeConfigJson);
			throw new SDNServiceImplException("Unable to create JSONObject for  createVirtualBridge "+virtualBridgeConfigJson,e);
		}
		return response;
	}//end of method createVirtualBridge
	
	
	/**
	 * This method is used to create Interface for sdn
	 * @param intfaceName : interfacename in String
	 * @param vtnName : vtn name in String
	 * @param vBrigeName : virtual bridge name String
	 * @return boolean : true if created else false
	 * @throws SDNServiceImplException : Error in creating Interface
	 */
	public boolean  createInterfaces(String  intfaceName,String vtnName,String vBrigeName) throws SDNServiceImplException{
		logger.debug(".createInterfaces method of SDNServiceWrapperImpl");
		SDNServiceImpl serviceImpl=new SDNServiceImpl();
		boolean response=false;
		JSONObject interfaceConfig = new JSONObject();
		JSONObject interfaceConfigJson = new JSONObject();
		try {
			interfaceConfig.put(INTERFACE_NAME_KEY, intfaceName);
			interfaceConfigJson.put(INTERFACE_KEY, interfaceConfig);
			response=serviceImpl.createInterfaces(interfaceConfigJson,vtnName,vBrigeName);
		} catch (JSONException e) {
			logger.error("Unable to create JSONObject for createInterfaces "+interfaceConfigJson);
			throw new SDNServiceImplException("Unable to create JSONObject for  createInterfaces "+interfaceConfigJson,e);
		}
		return response;
	}//end of method createInterfaces
	
	/**
	 * This method is used to create mapping
	 * @param intfaceName : Interface name in String
	 * @param vtnName : vtn name in String
	 * @param vBrigeName : virtual bridge name String
	 * @param logicalPortId : logical port id in String
	 * @param vlanId : vlan id in STring
	 * @return boolean : true if created else false
	 * @throws SDNServiceImplException :Error in creating mapping
	 */
	public boolean createMapping(String  intfaceName,String vtnName,String vBrigeName,String  logicalPortId,String vlanId) throws SDNServiceImplException{
		logger.debug(".createMapping method of SDNServiceWrapperImpl");
		SDNServiceImpl serviceImpl=new SDNServiceImpl();
		boolean response=false;
		JSONObject mappingConfig = new JSONObject();
		JSONObject mappingConfigJson = new JSONObject();
		try {
			mappingConfig.put(LOGICAL_PORT_ID_KEY, logicalPortId);
			mappingConfig.put(VLAN_ID_KEY,vlanId);
			mappingConfig.put(TAGGED_KEY,TAGGED_VALUE);
			mappingConfigJson.put(PORT, mappingConfig);
			logger.debug("---------mappingConfigJson   is---------"+mappingConfigJson);
			logger.debug("intfaceName"+intfaceName+", vtnName"+vtnName+", vBrigeName"+vBrigeName+", logicalPortId"+logicalPortId);			
			response=serviceImpl.createMapping(mappingConfigJson,vtnName,vBrigeName,intfaceName);
		} catch (JSONException e) {
			logger.error("Unable to create JSONObject for createMapping "+mappingConfigJson);
			throw new SDNServiceImplException("Unable to create JSONObject for  createMapping "+mappingConfigJson,e);
		}		
		return response;
	}//end of method createMapping
	
	/**
	 * This method is used to create flow in sdn
	 * @param flowName : flow name in String
	 * @return boolean : true if created else false
	 * @throws SDNServiceImplException : Error in creating FlowList
	 */
	public boolean createFlowList(String  flowName) throws SDNServiceImplException{
		logger.debug(".createFlowList method of SDNServiceWrapperImpl");
		SDNServiceImpl serviceImpl=new SDNServiceImpl();
		boolean response=false;
		JSONObject flowListConfig = new JSONObject();
		JSONObject flowListConfigJson = new JSONObject();

		try {
			flowListConfig.put(FLOW_NAME_KEY, flowName);
			flowListConfig.put(IP_VERSION_KEY,IP_VERSION_VALUE);
			flowListConfigJson.put(FLOWLIST_KEY, flowListConfig);			
			logger.debug("---------interfacespostData2   is---------"+flowListConfigJson);
			response=serviceImpl.createFlowList(flowListConfigJson);
		} catch (JSONException e) {
			logger.error("Unable to create JSONObject for createFlowList "+flowListConfigJson);
			throw new SDNServiceImplException("Unable to create JSONObject for  createFlowList "+flowListConfigJson,e);
		}		
		return response;
	}//end of method createFlowList
	
	/**
	 * This method is used to create flow list entry for sdn
	 * @param seqNumber : sequence number in String
	 * @param srcIP : source IP in String
	 * @param destIP : destination IP in String
	 * @param flowList : flow List in String
	 * @return boolean : true if created else false
	 * @throws SDNServiceImplException : Error in creating FlowListEntry
	 */
	public boolean createFlowListEntry(String  seqNumber,String srcIP,String  destIP ,String  flowList) throws SDNServiceImplException{
		logger.debug(".createFlowListEntry method of SDNServiceWrapperImpl");
		SDNServiceImpl serviceImpl=new SDNServiceImpl();
		boolean response=false;
		JSONObject flowListEntryConfig = new JSONObject();
		JSONObject flowListEntryConfigJson = new JSONObject();

		try {
			flowListEntryConfig.put(SEQUENCE_NUMBER_KEY, seqNumber);
			flowListEntryConfig.put(MAC_ETHER_TYPE_KEY, MAC_ETHER_TYPE_VALUE);
			flowListEntryConfig.put(IP_DESTINATION_KEY, destIP);
			flowListEntryConfig.put(IP_DESTINATION_PREFIX_KEY,IP_DESTINATION_PREFIX_VALUE);
			flowListEntryConfig.put(IP_SOURCE_KEY, srcIP);
			flowListEntryConfig.put(IP_SOURCE_PREFIX_KEY,IP_SOURCE_PREFIX_VALUE);		
			flowListEntryConfig.put(IP_PROTO_KEY,IP_PROTO_VALUE);
			flowListEntryConfigJson.put(FLOWLIST_ENTRY_KEY, flowListEntryConfig);			
			logger.debug("---------flowListEntryConfigJson   is---------"+flowListEntryConfigJson);
			response=serviceImpl.createFlowListEntry(flowListEntryConfigJson,flowList);
		} catch (JSONException e) {
			logger.error("Unable to create JSONObject for createFlowListEntry "+flowListEntryConfigJson);
			throw new SDNServiceImplException("Unable to create JSONObject for  createFlowListEntry "+flowListEntryConfigJson,e);
		}		
		return response;
	}//end of method createFlowListEntry
	
	/**
	 * This method is used to create  Firewall Rule
	 * @param ruleName : rule name in String
	 * @param nodename : node name in String
	 * @param inboundIP : Inbound IP in String
	 * @param port : port in String
	 * @return boolean : true if created else false
	 * @throws SDNServiceImplException : Error in creating Firewall Rule
	 */
	public boolean createFirewallRule(String ruleName,String nodename,String  inboundIP,String  port) throws SDNServiceImplException{
		logger.debug(".createFirewallRule method of SDNServiceWrapperImpl");
		SDNServiceImpl serviceImpl=new SDNServiceImpl();
		boolean response=false;
		// Node on which this flow should be installed
		JSONObject nodeConfigJson = new JSONObject();
		JSONObject nodeConfig = new JSONObject();
		try {
			nodeConfig.put(NAME_KEY, ruleName);
			nodeConfig.put(NODE_KEY, nodename);
			nodeConfig.put(IP_ADDRESS_KEY, inboundIP);
			nodeConfig.put(PORT_KEY, port);			
			nodeConfigJson.put(REGISTRY_ENTRY_RULE_KEY, nodeConfig);
			// Actual flow install
			response=serviceImpl.createFirewallRule(nodeConfigJson);
		} catch (JSONException e) {
			logger.error("Unable to create JSONObject for createFirewallRule "+nodeConfigJson);
			throw new SDNServiceImplException("Unable to create JSONObject for  createFirewallRule "+nodeConfigJson,e);
		}
		
		return response;
	}//end of method createFirewallRule
	
	
	/**
	 * This method is used to install flow 
	 * @param vpcname : vpc name in String
	 * @param source : source name in String
	 * @param desti : desti name in String
	 * @param action : action name in String
	 * @return boolean : true if created else false
	 * @throws SDNServiceImplException : Error in installFlow 
	 */
	public boolean installFlow(String vpcname,String  source,String  desti ,String  action) throws SDNServiceImplException{
		logger.debug(".installFlow method of SDNServiceWrapperImpl");
		SDNServiceImpl serviceImpl=new SDNServiceImpl();
		boolean response=false;
		JSONObject flowConfigJson = new JSONObject();
		// Node on which this flow should be installed
		JSONObject flowConfig = new JSONObject();
		try {
			flowConfigJson.put(NAME_KEY, vpcname);
			flowConfigJson.put(SOURCE_KEY, source);
			flowConfigJson.put(DESTINATION_KEY, desti);
			flowConfigJson.put(INSTALL_IN_HARDWARE_KEY,INSTALL_IN_HARDWARE_VALUE);
			flowConfigJson.put(PRIORITY_KEY,PRIORITY_VALUE);
			flowConfigJson.put(ETHER_TYPE_KEY,ETHER_TYPE_VALUE);
			flowConfigJson.put(ACTION_KEY, new JSONArray().put(action));			
			flowConfig.put(ID_KEY,ID_VALUE);
			flowConfig.put(TYPE_KEY,TYPE_VALUE);
			flowConfigJson.put(NODE_KEY, flowConfig);
			// Actual flow install
			response=serviceImpl.installFlow(ID_VALUE,vpcname, flowConfigJson);
		} catch (JSONException e) {
			logger.error("Unable to create JSONObject for createFirewallRule "+flowConfigJson);
			throw new SDNServiceImplException("Unable to create JSONObject for  createFirewallRule "+flowConfigJson,e);
		}		
		return response;
	}//end of method installFlow
	
	/**
	 * This method is used to add action type
	 * @param seqNumber : sequence number in String
	 * @param flowlist : flow List in String
	 * @param actionType : action type in String
	 * @param vtnName : vtn name in String
	 * @param vBridgeName : bridge name in String
	 * @param interFaces : insteface name in String
	 * @return boolean : true if created else false
	 * @throws SDNServiceImplException : Error in paasActionType
	 */
	public boolean paasActionType(String  seqNumber,String flowlist,String  actionType ,String  vtnName ,String  vBridgeName,String  interFaces) throws SDNServiceImplException{
		logger.debug(".paasActionType method of SDNServiceWrapperImpl");
		SDNServiceImpl serviceImpl=new SDNServiceImpl();
		boolean response=false;
		JSONObject actionTypeConfig = new JSONObject();
		JSONObject actionTypeConfigJson = new JSONObject();
		try {
			actionTypeConfig.put(SEQUENCE_NUMBER_KEY, seqNumber);
			actionTypeConfig.put(FLOW_NAME_KEY, flowlist);
			actionTypeConfig.put(ACTION_TYPE_KEY, actionType);
			actionTypeConfig.put(ACTION_PRIORITY_KEY,ACTION_PRIORITY_VALUE);
			actionTypeConfig.put(ACTION_DSCP_KEY,ACTION_DSCP_VALUE);			
			actionTypeConfigJson.put(FLOW_FILTER_ENTRY_KEY, actionTypeConfig);			
			logger.debug("---------actionTypeConfigJson   is---------"+actionTypeConfigJson);			
			response=serviceImpl.paasActionType(actionTypeConfigJson,vtnName ,vBridgeName,interFaces);
		} catch (JSONException e) {
			logger.error("Error in setting the config for action type in JSON");
			throw new SDNServiceImplException("Error in setting the config for action type in JSON "+actionTypeConfigJson,e);
		}
		return response;
	}//end of method paasActionType
	
	/**
	 * This method is used to drop action type
	 * @param seqNumber : sequence number in String
	 * @param flowlist : flow List in String
	 * @param actionType : action type in String
	 * @param vtnName : vtn name in String
	 * @param vBridgeName : bridge name in String
	 * @param interFaces : insteface name in String
	 * @return boolean : true if created else false
	 * @throws SDNServiceImplException : Error in dropActionType
	 */
	public boolean dropActionType(String  seqNumber,String flowlist,String  actionType ,String  vtnName ,String  vBridgeName,String  interFaces) throws SDNServiceImplException{
		logger.debug(".dropActionType method of SDNServiceWrapperImpl");
		SDNServiceImpl serviceImpl=new SDNServiceImpl();
		boolean response=false;
		JSONObject actionTypeConfig = new JSONObject();
		JSONObject actionTypeConfigJson = new JSONObject();
		try {
			actionTypeConfig.put(SEQUENCE_NUMBER_KEY, seqNumber);
			actionTypeConfig.put(FLOW_NAME_KEY, flowlist);
			actionTypeConfig.put(ACTION_TYPE_KEY, actionType);
			actionTypeConfig.put(ACTION_PRIORITY_KEY,ACTION_PRIORITY_VALUE);
			actionTypeConfig.put(ACTION_DSCP_KEY,ACTION_DSCP_VALUE);			
			actionTypeConfigJson.put(FLOW_FILTER_ENTRY_KEY, actionTypeConfig);			
			logger.debug("---------actionTypeConfigJson   is---------"+actionTypeConfigJson);			
			response=serviceImpl.dropActionType(actionTypeConfigJson,vtnName ,vBridgeName,interFaces);
		} catch (JSONException e) {
			logger.error("Error in setting the config for drop  action type in JSON");
			throw new SDNServiceImplException("Error in setting the config for drop  action type in JSON "+actionTypeConfigJson,e);

		}
		return response;
	}//end of method dropActionType
	
	/**
	 * This method is used to create virtual bridge interface flow filter for sdn
	 * @param vtnName : vtn name in String
	 * @param vBridgeName : virtual bridge name in String
	 * @param interFaces : interface name in String
	 * @return boolean : true if created else false
	 * @throws SDNServiceImplException : Error in virtual bridge interface flow filter
	 */
	public boolean virtualBridgeInterfacesFlowFilter(String  vtnName ,String  vBridgeName,String  interFaces) throws SDNServiceImplException{
		logger.debug(".virtualBridgeInterfacesFlowFilter method of SDNServiceWrapperImpl");
		SDNServiceImpl serviceImpl=new SDNServiceImpl();
		boolean response=false;
		JSONObject bridgeInterfacesFlowFilterConfig = new JSONObject();
		JSONObject bridgeInterfacesFlowFilterConfigJson = new JSONObject();
		try {
			bridgeInterfacesFlowFilterConfig.put(FLOWFILETR_TYPE_KEY, "in");
			bridgeInterfacesFlowFilterConfigJson.put(FILETR_TYPE_KEY, bridgeInterfacesFlowFilterConfig);			
			logger.debug("---------bridgeInterfacesFlowFilterConfigJson   is---------"+bridgeInterfacesFlowFilterConfigJson);			
			response=serviceImpl.virtualBridgeInterfacesFlowFilter(bridgeInterfacesFlowFilterConfigJson,vtnName ,vBridgeName,interFaces);
		} catch (JSONException e) {
			logger.error("Error in setting json object for virtual bridge interface flow filter");
			throw new SDNServiceImplException("Error in setting json object for virtual bridge interface flow filter "+bridgeInterfacesFlowFilterConfigJson,e);

		}		
		return response;
	}//end of method virtualBridgeInterfacesFlowFilter
	
	/**
	 * This method is used to get User details
	 * @param username : username in String
	 * @param password : password in String
	 * @param baseURL : baseUrl in String
	 * @return boolean : true if created else false
	 * @throws SDNServiceImplException 
	 */
	public boolean getUserDetails(String username, String password,String baseURL) throws SDNServiceImplException{
		logger.debug(".getUserDetails method of SDNServiceWrapperImpl");
		SDNServiceImpl serviceImpl=new SDNServiceImpl();
		boolean response=false;
		response=serviceImpl.getUserDetails(username, password, baseURL);
		return response;
	}//end of method getUserDetails
	
	
	/**
	 * This method is used to regsiter user
	 * @param imageRegistry : Image Registry Object
	 * @return boolean : true if created else false
	 * @throws SDNServiceImplException 
	 */
	public boolean getUserDetailsRegistry(ImageRegistry imageRegistry) throws SDNServiceImplException{
		logger.debug(".getUserDetailsRegistry method of SDNServiceWrapperImpl");
		SDNServiceImpl serviceImpl=new SDNServiceImpl();
		boolean response=false;
		response=serviceImpl.getUserDetailsRegistry(imageRegistry);
		return response;
	}//end of method getUserDetailsRegistry

}
