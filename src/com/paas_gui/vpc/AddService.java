package com.paas_gui.vpc;

import java.util.ArrayList;
import java.util.List;

public class AddService {

	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	@Override
	public String toString() {
		return "AddService [serviceName=" + serviceName + ", type=" + type + ", applicantionName=" + applicantionName
				+ ", imageRegistry=" + imageRegistry + ", imageRepository=" + imageRepository + ", tag=" + tag
				+ ", run=" + run + ", hostname=" + hostname + ", typename=" + typename + ", envirnament=" + envirnament
				+ ", envpath=" + envpath + ", envinterval=" + envinterval + ", envtimeout=" + envtimeout
				+ ", envthresold=" + envthresold + ", envignore=" + envignore + ", volume=" + volume + ", scales="
				+ scales + ", env=" + env + ", route=" + route + "]";
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getApplicantionName() {
		return applicantionName;
	}
	public void setApplicantionName(String applicantionName) {
		this.applicantionName = applicantionName;
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
	public String getRun() {
		return run;
	}
	public void setRun(String run) {
		this.run = run;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getEnvirnament() {
		return envirnament;
	}
	public void setEnvirnament(String envirnament) {
		this.envirnament = envirnament;
	}
	public String getEnvpath() {
		return envpath;
	}
	public void setEnvpath(String envpath) {
		this.envpath = envpath;
	}
	public String getEnvinterval() {
		return envinterval;
	}
	public void setEnvinterval(String envinterval) {
		this.envinterval = envinterval;
	}
	public String getEnvtimeout() {
		return envtimeout;
	}
	public void setEnvtimeout(String envtimeout) {
		this.envtimeout = envtimeout;
	}
	public String getEnvthresold() {
		return envthresold;
	}
	public void setEnvthresold(String envthresold) {
		this.envthresold = envthresold;
	}
	public String getEnvignore() {
		return envignore;
	}
	public void setEnvignore(String envignore) {
		this.envignore = envignore;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public List<Scale> getScales() {
		return scales;
	}
	public void setScales(List<Scale> scales) {
		this.scales = scales;
	}
	public List<EnvironmentVariable> getEnv() {
		return env;
	}
	public void setEnv(List<EnvironmentVariable> env) {
		this.env = env;
	}
	public List<Route> getRoute() {
		return route;
	}
	public void setRoute(List<Route> route) {
		this.route = route;
	}
	private  String  serviceName;
	private  String  type;
	private  String  applicantionName;
	
	private  String  imageRegistry;
	private  String  imageRepository;
	
	private  String  tag;
	private  String  run;
	private  String  hostname;
	
	private  String  typename;
	private  String  envirnament;
	private  String  envpath;
	
	private  String  envinterval;
	private  String  envtimeout;
	private  String  envthresold;
	private  String  envignore;

	
	
	
	private  String  volume;
	
	
	private List<Scale> scales =new ArrayList<>();
	private List<EnvironmentVariable> env= new ArrayList<>();
	private List<Route> route =new ArrayList<>();
	
		
	
	
}
