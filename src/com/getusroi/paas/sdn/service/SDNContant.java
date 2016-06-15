package com.getusroi.paas.sdn.service;

public class SDNContant {
	
	//contants for NAS storage
	public static final String NAS_URL="nasURL";
	public static final String NAS_USERNAME="nasUserName";
	public static final String NAS_PASSWORD="nasPassword";
	public static final String NAS_PROPERTY_FILE_KEY="nasStorage.properties";
	
	//constants for SDN Controller
	public static final String SDN_CONTROLLER_URL="sdnControllerURL";
	public static final String SDN_CONTROLLER_USERNAME="sdnControllerUserName";
	public static final String SDN_CONTROLLER_PASSWORD="sdnControllerPassword";
	public static final String SDN_CONTROLLER_PROPERTY_FILE_KEY="sdnController.properties";
	public static final String SDN_GET_CONTROLLERS_URL_KEY="sdnControllerRestAPIURL";
	public static final String SDN_CONTROLLER_VTN_URL_KEY="sdnControllerVTNRestAPIURL";
	public static final String SDN_VTNS_URI_KEY="sdnVtnsURI";
	public static final String SDN_BRIDGE_URL_KEY="sdnBridgeURL";
	public static final String SDN_BRIDGE_URI_KEY="sdnBridgeURI";
	public static final String SDN_INTERFACE_URL_KEY="sdnInterfaceURL";
	public static final String SDN_INTERFACE_URI_KEY="sdnInetrfaceURI";
	public static final String SDN_PORT_URL_KEY="sdnPortURL";
	public static final String SDN_FLOWLIST_URL_KEY="sdnFlowListURL";
	public static final String SDN_FLOWLIST_URI_KEY="sdnFlowListURI";
	public static final String SDN_FLOWLIST_ENTRY_URL_KEY="sdnFlowListEntryURL";
	public static final String SDN_Flow_ACTION_TYPE_KEY="sdnFlowActionType";
	public static final String SDN_FLOW_DROP_ACTION_KEY="sdnFlowDropAction";
	public static final String SDN_FLOW_FILTER_KEY="sdnFlowFilter";
	
	//constants for SDN Firewall
	public static final String SDN_FIREWALL_URL="sdnFirewallURL";
	public static final String SDN_FIREWALL_URI="sdnFirewallURI";
	public static final String SDN_FIREWALL_USERNAME="sdnFirewallUserName";
	public static final String SDN_FIREWALL_PASSWORD="sdnFirewallPassword";
	public static final String SDN_FIREWALL_PROPERTY_FILE_KEY="sdnFirewall.properties";
	
	
	//contants for flow manager
	public static final String SDN_FLOW_URL="sdnFlowURL";
	public static final String SDN_FLOW_DEFAULT_NODE_URI_KEY="sdnFlowDefaultNodeURI";
	public static final String SDN_FLOW_STATIC_FLOW_URI_KEY="sdnFlowStaticFlowURI";
	public static final String SDN_FLOW_USERNAME_KEY="sdnFlowUserName";
	public static final String SDN_FLOW_PASSWORD_KEY="sdnFlowPassword";
	public static final String SDN_FLOW_PROPERTY_FILE_KEY="sdnFlowManager.Properties";
	
	//contansts for docker 
	public static final String SDN_DOCKER_URL_KEY="sdnDockerURL";
	public static final String SDN_DOCKER_CONTAINER_KEY="sdnDockerContainer";
	public static final String SDN_DOCKER_PROPERTY_FILE_KEY="dockerConfig.Properties";

	
	//http status code constants
	public static final int NO_CONTENT = 204;
	public static final int CREATED = 201;
	public static final int OK = 200;

}
