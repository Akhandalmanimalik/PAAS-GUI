package com.getusroi.paas.vo;

public class Rule {
private int id;
private String ruleType;						//inoutbound_type
private String type;							
private String protocol;
private String protocolRange;
private String sourceIp;
private String action;
private int aclId;

 
public Rule() {
	// TODO Auto-generated constructor stub
}


public Rule(int id, String ruleType, String type, String protocol,
		String protocolRange, String sourceIp, String action, int aclId) {
	super();
	this.id = id;
	this.ruleType = ruleType;
	this.type = type;
	this.protocol = protocol;
	this.protocolRange = protocolRange;
	this.sourceIp = sourceIp;
	this.action = action;
	this.aclId = aclId;
}


@Override
public String toString() {
	return "Rule [id=" + id + ", ruleType=" + ruleType + ", type=" + type
			+ ", protocol=" + protocol + ", protocolRange=" + protocolRange
			+ ", sourceIp=" + sourceIp + ", action=" + action + ", aclId="
			+ aclId + "]";
}


public int getId() {
	return id;
}


public void setId(int id) {
	this.id = id;
}


public String getRuleType() {
	return ruleType;
}


public void setRuleType(String ruleType) {
	this.ruleType = ruleType;
}


public String getType() {
	return type;
}


public void setType(String type) {
	this.type = type;
}


public String getProtocol() {
	return protocol;
}


public void setProtocol(String protocol) {
	this.protocol = protocol;
}


public String getProtocolRange() {
	return protocolRange;
}


public void setProtocolRange(String protocolRange) {
	this.protocolRange = protocolRange;
}


public String getSourceIp() {
	return sourceIp;
}


public void setSourceIp(String sourceIp) {
	this.sourceIp = sourceIp;
}


public String getAction() {
	return action;
}


public void setAction(String action) {
	this.action = action;
}


public int getAclId() {
	return aclId;
}


public void setAclId(int aclId) {
	this.aclId = aclId;
}



}
