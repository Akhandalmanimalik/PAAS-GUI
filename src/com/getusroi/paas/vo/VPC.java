package com.getusroi.paas.vo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class VPC {
	private int vpcId;
	private String vpc_name;
	private String aclName; 
	private int aclId;
	private int tenant_id;
	private String cidr;
	
	public VPC() {
		// TODO Auto-generated constructor stub
	}
	public VPC(int vpcId, String vpc_name, String aclName, int aclId,
			int tenant_id,String cidr) {
		super();
		this.vpcId = vpcId;
		this.vpc_name = vpc_name;
		this.aclName = aclName;
		this.aclId = aclId;
		this.tenant_id = tenant_id;
		this.cidr=cidr;
	}
	public String getCidr() {
		return cidr;
	}
	public void setCidr(String cidr) {
		this.cidr = cidr;
	}
	@Override
	public String toString() {
		return "VPC [vpcId=" + vpcId + ", vpc_name=" + vpc_name + ", aclName="
				+ aclName + ", aclId=" + aclId + ", tenant_id=" + tenant_id
				+ ", cidr=" + cidr + "]";
	}
	public int getVpcId() {
		return vpcId;
	}
	public void setVpcId(int vpcId) {
		this.vpcId = vpcId;
	}
	public String getVpc_name() {
		return vpc_name;
	}
	public void setVpc_name(String vpc_name) {
		this.vpc_name = vpc_name;
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
	public int getTenant_id() {
		return tenant_id;
	}
	public void setTenant_id(int tenant_id) {
		this.tenant_id = tenant_id;
	}
	 
	
}
