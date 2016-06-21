package com.getusroi.paas.vo;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Applications {
	

	@Override
	public String toString() {
		return "Applications [apps_id=" + apps_id + ", applicantionName="
				+ applicantionName + ", description=" + description
				+ ", tenant_id=" + tenant_id + "]";
	}

	public int getApps_id() {
		return apps_id;
	}

	public void setApps_id(int apps_id) {
		this.apps_id = apps_id;
	}



	private int apps_id;
	private String applicantionName;
	private String description;
	private int tenant_id;
	

	public Applications() {

	}
 
	public Applications(String applicantionName, String description,
			int tenant_id) {
		super();
		this.applicantionName = applicantionName;
		this.description = description;
		this.tenant_id = tenant_id;
	}



	public String getApplicantionName() {
		return applicantionName;
	}

	public void setApplicantionName(String applicantionName) {
		this.applicantionName = applicantionName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



	public int getTenant_id() {
		return tenant_id;
	}



	public void setTenant_id(int tenant_id) {
		this.tenant_id = tenant_id;
	}
 
	
}
