package com.getusroi.paas.vo;

public class ServiceAffinities {
	private String application;
	private String services;
	private String environmentTypes;
	private String affinity;

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getServices() {
		return services;
	}

	public void setServices(String services) {
		this.services = services;
	}

	
	public String getEnvironmentTypes() {
		return environmentTypes;
	}

	public void setEnvironmentTypes(String environmentTypes) {
		this.environmentTypes = environmentTypes;
	}

	public String getAffinity() {
		return affinity;
	}

	public void setAffinity(String affinity) {
		this.affinity = affinity;
	}

}
