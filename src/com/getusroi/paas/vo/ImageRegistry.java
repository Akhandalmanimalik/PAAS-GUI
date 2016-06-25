package com.getusroi.paas.vo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageRegistry {
	private int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public ImageRegistry(int id, String name, String location, String version,
			String user_name, String password, int tenant_id) {
		super();
		this.id=id;
		this.name = name;
		this.location = location;
		this.version = version;
		this.user_name = user_name;
		this.password = password;
		this.tenant_id = tenant_id;
	}

	

	@Override
	public String toString() {
		return "ImageRegistry [id=" + id + ", name=" + name + ", location=" + location + ", version=" + version
				+ ", user_name=" + user_name + ", password=" + password + ", tenant_id=" + tenant_id + "]";
	}

	public ImageRegistry() {
		super();
	}
  

}
