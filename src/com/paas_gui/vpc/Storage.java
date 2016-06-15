package com.paas_gui.vpc;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Storage {
 
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
private String serviceName;
  private String tag;
  private String volumeSize;
 



  
}
