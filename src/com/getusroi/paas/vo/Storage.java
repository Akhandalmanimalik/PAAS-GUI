package com.getusroi.paas.vo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Storage {
	private String serviceName;
	private String tag;
	private String volumeSize;
	
	public Storage() {}
	public Storage(String serviceName, String tag, String volumeSize) {
		super();
		this.serviceName = serviceName;
		this.tag = tag;
		this.volumeSize = volumeSize;
	}

  public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getVolumeSize() {
		return volumeSize;
	}
	public void setVolumeSize(String volumeSize) {
		this.volumeSize = volumeSize;
	}

	@Override
	public String toString() {
		return "Storage [serviceName=" + serviceName + ", tag=" + tag
				+ ", volumeSize=" + volumeSize + "]";
	}
	
}
