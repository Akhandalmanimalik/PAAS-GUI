package com.getusroi.paas.vo;

public class EnvironmentVariable {
	
	private int id;
	private String envkey;
	private String envvalue;
	private int appId;
	public EnvironmentVariable() {
		// TODO Auto-generated constructor stub
	}
	public EnvironmentVariable(int id, String envkey, String envvalue, int appId) {
		super();
		this.id = id;
		this.envkey = envkey;
		this.envvalue = envvalue;
		this.appId = appId;
	}
	@Override
	public String toString() {
		return "EnvironmentVariable [id=" + id + ", envkey=" + envkey
				+ ", envvalue=" + envvalue + ", appId=" + appId + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEnvkey() {
		return envkey;
	}
	public void setEnvkey(String envkey) {
		this.envkey = envkey;
	}
	public String getEnvvalue() {
		return envvalue;
	}
	public void setEnvvalue(String envvalue) {
		this.envvalue = envvalue;
	}
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}
	
	
	
	
}
