package com.getusroi.paas.vo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ACL {
	 private int id;
	 private String aclName;
	 private int tenantId; 
	 private String description;
	 public ACL() {
		// TODO Auto-generated constructor stub
	 }
	public ACL(int id, String aclName, int tenantId, String description) {
		super();
		this.id = id;
		this.aclName = aclName;
		this.tenantId = tenantId;
		this.description = description;
	}
	@Override
	public String toString() {
		return "ACL [id=" + id + ", aclName=" + aclName + ", tenantId="
				+ tenantId + ", description=" + description + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAclName() {
		return aclName;
	}
	public void setAclName(String aclName) {
		this.aclName = aclName;
	}
	public int getTenantId() {
		return tenantId;
	}
	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

		
	  
}
