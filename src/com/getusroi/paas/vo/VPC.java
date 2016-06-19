package com.getusroi.paas.vo;

public class VPC {
	private String vpcId;
	private String vpc_name;
	private String aclName; 
	private String aclId;
	private int tenant_id;
	public VPC() {
		// TODO Auto-generated constructor stub
	}
	public VPC(String vpcId, String vpc_name, String aclName, String aclId,
			int tenant_id) {
		super();
		this.vpcId = vpcId;
		this.vpc_name = vpc_name;
		this.aclName = aclName;
		this.aclId = aclId;
		this.tenant_id = tenant_id;
	}
	@Override
	public String toString() {
		return "VPC [vpcId=" + vpcId + ", vpc_name=" + vpc_name + ", aclName="
				+ aclName + ", aclId=" + aclId + ", tenant_id=" + tenant_id
				+ "]";
	}
	public String getVpcId() {
		return vpcId;
	}
	public void setVpcId(String vpcId) {
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
	public String getAclId() {
		return aclId;
	}
	public void setAclId(String aclId) {
		this.aclId = aclId;
	}
	public int getTenant_id() {
		return tenant_id;
	}
	public void setTenant_id(int tenant_id) {
		this.tenant_id = tenant_id;
	}
	 
	
}
