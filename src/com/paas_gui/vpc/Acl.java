package com.paas_gui.vpc;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Acl {
 
  private String srcIp;
  private String destIP;
  private String action;
  private String aclName;



public String getAclName() {
	return aclName;
}
public void setAclName(String aclName) {
	this.aclName = aclName;
}
public String getSrcIp() {
	return srcIp;
}
public void setSrcIp(String srcIp) {
	this.srcIp = srcIp;
}
public String getDestIP() {
	return destIP;
}
public void setDestIP(String destIP) {
	this.destIP = destIP;
}
public String getAction() {
	return action;
}
public void setAction(String action) {
	this.action = action;
}
  

  
}
