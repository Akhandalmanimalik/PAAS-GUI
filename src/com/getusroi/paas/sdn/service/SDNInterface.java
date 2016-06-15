package com.getusroi.paas.sdn.service;

import com.getusroi.paas.sdn.service.impl.SDNServiceImplException;
import com.getusroi.paas.vo.ImageRegistry;
import com.getusroi.paas.vo.Storage;

public interface SDNInterface {
	public boolean createVolume(Storage storage) throws SDNServiceImplException;
	public boolean createController(String  contollerId,String  ipAddress) throws SDNServiceImplException;
	public boolean createVtn(String  vtnName) throws SDNServiceImplException;
	public boolean createVirtualBridge(String  virtualBridgeName,String vtnName,String controllerId) throws SDNServiceImplException;
	public boolean  createInterfaces(String  intfaceName,String vtnName,String vBrigeName) throws SDNServiceImplException;
	public boolean createMapping(String  intfaceName,String vtnName,String vBrigeName,String  logicalPortId,String vlanId) throws SDNServiceImplException;
	public boolean createFlowList(String  flowName) throws SDNServiceImplException;
	public boolean createFlowListEntry(String  seqNumber,String srcIP,String  destIP ,String  flowList) throws SDNServiceImplException;
	public boolean createFirewallRule(String ruleName,String nodename,String  inboundIP,String  port) throws SDNServiceImplException;
	public boolean installFlow(String vpcname,String  source,String  desti ,String  action) throws SDNServiceImplException;
	public boolean paasActionType(String  seqNumber,String flowlist,String  actionType ,String  vtnName ,String  vBridgeName,String  interFaces) throws SDNServiceImplException;
	public boolean dropActionType(String  seqNumber,String flowlist,String  actionType ,String  vtnName ,String  vBridgeName,String  interFaces) throws SDNServiceImplException;
	public boolean virtualBridgeInterfacesFlowFilter(String  vtnName ,String  vBridgeName,String  interFaces) throws SDNServiceImplException;
	public boolean getUserDetails(String username, String password,String baseURL) throws SDNServiceImplException;
	public boolean getUserDetailsRegistry(ImageRegistry imageRegistry) throws SDNServiceImplException;

}
