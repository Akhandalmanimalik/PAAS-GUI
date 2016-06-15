package com.getusroi.paas.vo;

public class ResourceSelection {

	private String rank;
	private String name;
	private String containerTypes;
	private String environmentTypes;
	private String hostGroups;
	private String placement;
	private String minimum;

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContainerTypes() {
		return containerTypes;
	}

	public void setContainerTypes(String containerTypes) {
		this.containerTypes = containerTypes;
	}

	public String getEnvironmentTypes() {
		return environmentTypes;
	}

	public void setEnvironmentTypes(String environmentTypes) {
		this.environmentTypes = environmentTypes;
	}

	public String getHostGroups() {
		return hostGroups;
	}

	public void setHostGroups(String hostGroups) {
		this.hostGroups = hostGroups;
	}

	public String getPlacement() {
		return placement;
	}

	public void setPlacement(String placement) {
		this.placement = placement;
	}

	public String getMinimum() {
		return minimum;
	}

	public void setMinimum(String minimum) {
		this.minimum = minimum;
	}

}
