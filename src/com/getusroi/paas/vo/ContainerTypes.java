package com.getusroi.paas.vo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class ContainerTypes {
	
	private int id;
	private String name;
	private int memory;
	private String description;
	private int tenantId;
	public ContainerTypes() {
		// TODO Auto-generated constructor stub
	}
	public ContainerTypes(int id, String name, int memory, String description,
			int tenantId) {
		super();
		this.id = id;
		this.name = name;
		this.memory = memory;
		this.description = description;
		this.tenantId = tenantId;
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
	public int getMemory() {
		return memory;
	}
	public void setMemory(int memory) {
		this.memory = memory;
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
	@Override
	public String toString() {
		return "ContainerTypes [id=" + id + ", name=" + name + ", memory="
				+ memory + ", description=" + description + ", tenantId="
				+ tenantId + "]";
	}
	
	
}
