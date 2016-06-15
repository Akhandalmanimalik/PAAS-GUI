package com.getusroi.paas.vo;

public class VPC {
	private String vpcId;
	private String vpc_name;
	 
	private String acl;
	private int tenant_id;
	public VPC() {
		// TODO Auto-generated constructor stub
	}
	public VPC(String vpcId, String vpc_name,  String acl) {
		this.vpcId = vpcId;
		this.vpc_name = vpc_name;
		 
		this.acl = acl;
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
	 
	public String getAcl() {
		return acl;
	}
	public void setAcl(String acl) {
		this.acl = acl;
	}
	  
	
	public int getTenant_id() {
		return tenant_id;
	}
	public void setTenant_id(int tenant_id) {
		this.tenant_id = tenant_id;
	}
	
	@Override
	public String toString() {
		return "VPC [vpcId=" + vpcId + ", vpc_name=" + vpc_name
				+   ", acl="
				+ acl + ", tenant_id=" + tenant_id + "]";
	}
}
