package com.getusroi.paas.sdn.service;

public class SDNServiceConstant {
	//constant for volume
	public static final String NAME_KEY="name";
	public static final String VOLUME_SIZE_KEY="volsize";
	
	//contant for controller
	public static final String CONTROLLER_ID_KEY="controller_id";
	public static final String CONTROLLER_IP_KEY="ipaddr";
	public static final String CONTROLLER_TYPE_KEY="type";
	public static final String CONTROLLER_TYPE_VALUE="odc";
	public static final String CONTROLLER_VESRION_KEY="version";
	public static final String CONTROLLER_VESRION_VALUE="1.0";
	public static final String CONTROLLER_AUDIT_STATUS__KEY="auditstatus";
	public static final String CONTROLLER_AUDIT_STATUS__VALUE="enabled";
	public static final String CONTROLLER_KEY="controller";
	
	//contant for vtn
	public static final String VTN_NAME_KEY="vtn_name";
	public static final String VTN_KEY="vtn";
	
	//contant for virtual bridge
	public static final String VIRTUAL_BRIDGE_NAME_KEY="vbr_name";
	public static final String DOMAIN_ID_KEY="domain_id";
	public static final String DOMAIN_ID_VALUE="(DEFAULT)";
	public static final String VIRTUAL_BRIDGE_KEY="vbridge";
	
	//contant for Interface
	public static final String INTERFACE_NAME_KEY="if_name";
	public static final String INTERFACE_KEY="interface";
	
	//constant for mapping 
	public static final String LOGICAL_PORT_ID_KEY="logical_port_id";
	public static final String VLAN_ID_KEY="vlan_id";
	public static final String TAGGED_KEY="tagged";
	public static final String TAGGED_VALUE="true";
	public static final String PORT="portmap";
	
	//constant for flowList
	public static final String FLOW_NAME_KEY="fl_name";
	public static final String IP_VERSION_KEY="ip_version";
	public static final String IP_VERSION_VALUE="IP";
	public static final String FLOWLIST_KEY="flowlist";
	
	//constant for flowListEntry
	public static final String SEQUENCE_NUMBER_KEY="seqnum";
	public static final String MAC_ETHER_TYPE_KEY="macethertype";
	public static final String MAC_ETHER_TYPE_VALUE="0x800";
	public static final String IP_DESTINATION_KEY="ipdstaddr";
	public static final String IP_DESTINATION_PREFIX_KEY="ipdstaddrprefix";
	public static final String IP_DESTINATION_PREFIX_VALUE="24";
	public static final String IP_SOURCE_KEY="ipsrcaddr";
	public static final String IP_SOURCE_PREFIX_KEY="ipsrcaddrprefix";
	public static final String IP_SOURCE_PREFIX_VALUE="24";
	public static final String IP_PROTO_KEY="ipproto";
	public static final String IP_PROTO_VALUE="1";
	public static final String FLOWLIST_ENTRY_KEY="flowlistentry";
	
	//constant for FireWall rule
	public static final String NODE_KEY="node";
	public static final String IP_ADDRESS_KEY="ip-addr";
	public static final String PORT_KEY="port";
	public static final String REGISTRY_ENTRY_RULE_KEY="rule-registry-entry";
	
	//constant for flow
	public static final String SOURCE_KEY="nwSrc";
	public static final String DESTINATION_KEY="nwDst";
	public static final String INSTALL_IN_HARDWARE_KEY="installInHw";
	public static final String INSTALL_IN_HARDWARE_VALUE="true";
	public static final String PRIORITY_KEY="priority";
	public static final String PRIORITY_VALUE="501";
	public static final String ETHER_TYPE_KEY="etherType";
	public static final String ETHER_TYPE_VALUE="0x800";
	public static final String ACTION_KEY="action";
	public static final String ID_KEY="id";
	public static final String ID_VALUE="00:00:00:00:00:00:00:02";
	public static final String TYPE_KEY="type";
	public static final String TYPE_VALUE="OF";
	
	//constant of action
	public static final String ACTION_TYPE_KEY="action_type";
	public static final String ACTION_PRIORITY_KEY="priority";
	public static final String ACTION_PRIORITY_VALUE="3";
	public static final String ACTION_DSCP_KEY="dscp";
	public static final String ACTION_DSCP_VALUE="55";
	public static final String FLOW_FILTER_ENTRY_KEY="flowfilterentry";
	
	//constant of virtualBridgeInterface filter
	public static final String FLOWFILETR_TYPE_KEY="ff_type";
	public static final String FILETR_TYPE_KEY="flowfilter";

}
