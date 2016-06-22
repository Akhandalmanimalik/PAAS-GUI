package com.getusroi.paas.vo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InOutBoundRule {
	private int id;
	private String type;
	// inoutbound_type
	private String ruleType;
	private String protocol;
	private String protocolRange;
	private String sourceIp;
	private String aclName;
	private int aclId;
	private String action;

	public InOutBoundRule() {
		// TODO Auto-generated constructor stub
	}

	public InOutBoundRule(int id, String type, String ruleType,
			String protocol, String protocolRange, String sourceIp,
			String aclName, int aclId, String action) {
		super();
		this.id = id;
		this.type = type;
		this.ruleType = ruleType;
		this.protocol = protocol;
		this.protocolRange = protocolRange;
		this.sourceIp = sourceIp;
		this.aclName = aclName;
		this.aclId = aclId;
		this.action = action;
	}

	@Override
	public String toString() {
		return "InOutBoundRule [id=" + id + ", type=" + type + ", ruleType="
				+ ruleType + ", protocol=" + protocol + ", protocolRange="
				+ protocolRange + ", sourceIp=" + sourceIp + ", aclName="
				+ aclName + ", aclId=" + aclId + ", action=" + action + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
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

	public String getAclName() {
		return aclName;
	}

	public void setAclName(String aclName) {
		this.aclName = aclName;
	}

	public int getAclId() {
		return aclId;
	}

	public void setAclId(int aclId) {
		this.aclId = aclId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
