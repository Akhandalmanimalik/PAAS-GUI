package com.getusroi.paas.vo;

public class ScalingAndRecovery {

	private String application;
	private String services;
	private String environmentTypes;
	private String desiredCount;
	private String autoRecovery;
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
	public String getDesiredCount() {
		return desiredCount;
	}
	public void setDesiredCount(String desiredCount) {
		this.desiredCount = desiredCount;
	}
	public String getAutoRecovery() {
		return autoRecovery;
	}
	public void setAutoRecovery(String autoRecovery) {
		this.autoRecovery = autoRecovery;
	}

	
}
