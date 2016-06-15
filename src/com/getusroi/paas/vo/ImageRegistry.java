package com.getusroi.paas.vo;

public class ImageRegistry {

	private String name;
	private String location;
	private String version;
	private String user_name;
	private String password;
	private int tenant_id;
	
	 

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
 
	public int getTenant_id() {
		return tenant_id;
	}

	public void setTenant_id(int tenant_id) {
		this.tenant_id = tenant_id;
	}

	public ImageRegistry(String name, String location, String version,
			String user_name, String password, int tenant_id) {
		super();
		this.name = name;
		this.location = location;
		this.version = version;
		this.user_name = user_name;
		this.password = password;
		this.tenant_id = tenant_id;
	}

	@Override
	public String toString() {
		return "ImageRegistry [name=" + name + ", location=" + location
				+ ", version=" + version + ", user_name=" + user_name
				+ ", password=" + password + ", tenant_id=" + tenant_id + "]";
	}

	public ImageRegistry() {
		super();
	}
  

}
