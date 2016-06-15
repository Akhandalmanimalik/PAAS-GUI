package com.paas_gui.vpc;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicantSummary {
	

	private String applicantionName;
	private String description;
	private String imageRegistry;
	private String imageRepository;
	private String  tag;
	

	public ApplicantSummary() {

	}

	public ApplicantSummary(String applicantionName, String description, String imageRegistry, String imageRepository,String tag ) {
		super();
		this.applicantionName = applicantionName;
		this.description = description;
		this.imageRegistry = imageRegistry;
		this.imageRepository = imageRepository;
		this.tag = tag;

	}
	
	public String getApplicantionName() {
		return applicantionName;
	}

	public void setApplicantionName(String applicantionName) {
		this.applicantionName = applicantionName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageRegistry() {
		return imageRegistry;
	}

	public void setImageRegistry(String imageRegistry) {
		this.imageRegistry = imageRegistry;
	}

	public String getImageRepository() {
		return imageRepository;
	}

	public void setImageRepository(String imageRepository) {
		this.imageRepository = imageRepository;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	
}
