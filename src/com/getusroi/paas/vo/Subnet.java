package com.getusroi.paas.vo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Subnet {
	private int id;
	private String subnetName;
	private String cidr;
	private String environmentName;
	private String vpcName;
    private int tenantId;
    private String aclName;
    
    public Subnet() {
		// TODO Auto-generated constructor stub
	}

	public Subnet(int id, String subnetName, String cidr,
			String environmentName, String vpcName, int tenantId, String aclName) {
		super();
		this.id = id;
		this.subnetName = subnetName;
		this.cidr = cidr;
		this.environmentName = environmentName;
		this.vpcName = vpcName;
		this.tenantId = tenantId;
		this.aclName = aclName;
	}

	@Override
	public String toString() {
		return "Subnet [id=" + id + ", subnetName=" + subnetName + ", cidr="
				+ cidr + ", environmentName=" + environmentName + ", vpcName="
				+ vpcName + ", tenantId=" + tenantId + ", aclName=" + aclName
				+ "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubnetName() {
		return subnetName;
	}

	public void setSubnetName(String subnetName) {
		this.subnetName = subnetName;
	}

	public String getCidr() {
		return cidr;
	}

	public void setCidr(String cidr) {
		this.cidr = cidr;
	}

	public String getEnvironmentName() {
		return environmentName;
	}

	public void setEnvironmentName(String environmentName) {
		this.environmentName = environmentName;
	}

	public String getVpcName() {
		return vpcName;
	}

	public void setVpcName(String vpcName) {
		this.vpcName = vpcName;
	}

	public int getTenantId() {
		return tenantId;
	}

	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}

	public String getAclName() {
		return aclName;
	}

	public void setAclName(String aclName) {
		this.aclName = aclName;
	}
    
    
}
