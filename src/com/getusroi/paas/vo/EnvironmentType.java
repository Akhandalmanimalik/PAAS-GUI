package com.getusroi.paas.vo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnvironmentType {
	private int id;
	private String name;
	private String description;
	private int tenantId;

	 public EnvironmentType() {
		// TODO Auto-generated constructor stub
	}

	public EnvironmentType(int id, String name, String description, int tenantId) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.tenantId = tenantId;
	}

	@Override
	public String toString() {
		return "EnvironmentType [id=" + id + ", name=" + name
				+ ", description=" + description + ", tenantId=" + tenantId
				+ "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getTenantId() {
		return tenantId;
	}

	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}
	 

}
