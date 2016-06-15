package com.getusroi.paas.vo;

public class MessosTaskInfo {
	
	private String hostIp;
	private String containerId;
	private String messo_Task_Id;
	public String getHostIp() {
		return hostIp;
	}
	@Override
	public String toString() {
		return "MessosTaskInfo [hostIp=" + hostIp + ", containerId="
				+ containerId + ", messo_Task_Id=" + messo_Task_Id + "]";
	}
	public String getContainerId() {
		return containerId;
	}
	public String getMesso_Task_Id() {
		return messo_Task_Id;
	}
	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}
	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}
	public void setMesso_Task_Id(String messo_Task_Id) {
		this.messo_Task_Id = messo_Task_Id;
	}
	
	

}
