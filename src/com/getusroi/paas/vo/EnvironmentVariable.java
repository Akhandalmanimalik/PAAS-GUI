package com.getusroi.paas.vo;

public class EnvironmentVariable {
	
	private String envkey;
	private String envvalue;
	
	public EnvironmentVariable() {
		// TODO Auto-generated constructor stub
	}

	public EnvironmentVariable(String envkey, String envvalue) {
		super();
		this.envkey = envkey;
		this.envvalue = envvalue;
	}

	@Override
	public String toString() {
		return "EnvironmentVariable [envkey=" + envkey + ", envvalue="
				+ envvalue + "]";
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
}
