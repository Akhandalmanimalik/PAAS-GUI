package com.getusroi.paas.vo;

public class KibanaDashboard {
	private String userName;
	private String password;
	 
	public KibanaDashboard() {
		// TODO Auto-generated constructor stub
	}

	public KibanaDashboard(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	@Override
	public String toString() {
		return  userName +":"+ password ;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
