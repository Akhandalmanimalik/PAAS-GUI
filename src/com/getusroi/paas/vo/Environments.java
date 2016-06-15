package com.getusroi.paas.vo;

public class Environments {

	private String containerName;
	private String service;
	private String tag;
	private String host;
	private String ipadress;
	private String state;

	public String getContainerName() {
		return containerName;
	}

	public void setContainerName(String containername) {
		this.containerName = containername;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getIpadress() {
		return ipadress;
	}

	public void setIpadress(String ipadress) {
		this.ipadress = ipadress;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
