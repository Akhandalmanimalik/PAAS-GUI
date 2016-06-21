package com.paas_gui.vpc;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Application {
	

	private String applicantionName;
	private String description;
	private int tenant_id;
	

	public Application() {

	}
 
	public Application(String applicantionName, String description,
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
