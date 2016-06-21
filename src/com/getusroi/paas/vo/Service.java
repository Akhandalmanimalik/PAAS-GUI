package com.getusroi.paas.vo;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Service {
	
	private int id;
	//Service
	private  String  serviceName;		//SERVICE NAME	
	private  String  type;				//CONTAINER TYPE
	
	//image resistry
	private  String  imageRegistry;		//IMAGE REGISTRY NAME
	private  String  imageRepository;	//IMAGE REPOSITORY URL
	private  String  tag;				//TAG
	
	//Run Setting
	private  String  run;			
	private  String  hostName;
	
	//Network policy
	private String portName;
	private String portType;
	private int hostPort;
	private int containerPort;
	
	//Health check routes
	private  String  typeName;
	private  int  envInterval;
	private  String  envPath;
	private  int  envThreshold;
	private  int  envIgnore;
	 
	//Volumes
	private  int  volume;	

	//Subnet Section
	private String subnetName;
	private String cidr;
	
	//Foreign key ids
	private String containerType;
	private int tenantId;
	//private int subnetId;
	//private int environmentId;
	//private int registryId;
	
	//For Environment variables
	private List<EnvironmentVariable> env = new ArrayList<>();
	private String applicantionName;
	
	//unused fields
	private String protocal;
	private String envirnament;
	private String envtimeout;
	private List<Scale> scales =new ArrayList<>();
	private List<Route> route =new ArrayList<>();
	
	
	private String applicationName; 		
	private int appsId;
	
	private String network_bridge;
	public String getNetwork_bridge() {
		return network_bridge;
	}

	public void setNetwork_bridge(String network_bridge) {
		this.network_bridge = network_bridge;
	}

	public Service() {
		// TODO Auto-generated constructor stub
	}

	public Service(int id, String serviceName, String type,
			String imageRegistry, String imageRepository, String tag,
			String run, String hostName, String portName, String portType,
			int hostPort, int containerPort, String typeName, int envInterval,
			String envPath, int envThreshold, int envIgnore, int volume,
			String subnetName, String cidr, String containerType, int tenantId,
			List<EnvironmentVariable> env, String applicantionName,
			String protocal, String envirnament, String envtimeout,
			List<Scale> scales, List<Route> route, String applicationName,int appsId) {
		super();
		this.id = id;
		this.serviceName = serviceName;
		this.type = type;
		this.imageRegistry = imageRegistry;
		this.imageRepository = imageRepository;
		this.tag = tag;
		this.run = run;
		this.hostName = hostName;
		this.portName = portName;
		this.portType = portType;
		this.hostPort = hostPort;
		this.containerPort = containerPort;
		this.typeName = typeName;
		this.envInterval = envInterval;
		this.envPath = envPath;
		this.envThreshold = envThreshold;
		this.envIgnore = envIgnore;
		this.volume = volume;
		this.subnetName = subnetName;
		this.cidr = cidr;
		this.containerType = containerType;
		this.tenantId = tenantId;
		this.env = env;
		this.applicantionName = applicantionName;
		this.protocal = protocal;
		this.envirnament = envirnament;
		this.envtimeout = envtimeout;
		this.scales = scales;
		this.route = route;
		this.applicationName = applicationName;
		this.appsId=appsId;
	}

	@Override
	public String toString() {
		return "Service [id=" + id + ", serviceName=" + serviceName + ", type="
				+ type + ", imageRegistry=" + imageRegistry
				+ ", imageRepository=" + imageRepository + ", tag=" + tag
				+ ", run=" + run + ", hostName=" + hostName + ", portName="
				+ portName + ", portType=" + portType + ", hostPort="
				+ hostPort + ", containerPort=" + containerPort + ", typeName="
				+ typeName + ", envInterval=" + envInterval + ", envPath="
				+ envPath + ", envThreshold=" + envThreshold + ", envIgnore="
				+ envIgnore + ", volume=" + volume + ", subnetName="
				+ subnetName + ", cidr=" + cidr + ", containerType="
				+ containerType + ", tenantId=" + tenantId + ", env=" + env
				+ ", applicantionName=" + applicantionName + ", protocal="
				+ protocal + ", envirnament=" + envirnament + ", envtimeout="
				+ envtimeout + ", scales=" + scales + ", route=" + route
				+ ", applicationName=" + applicationName +", appsId="+appsId+ "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	public String getPortType() {
		return portType;
	}

	public void setPortType(String portType) {
		this.portType = portType;
	}

	public int getHostPort() {
		return hostPort;
	}

	public void setHostPort(int hostPort) {
		this.hostPort = hostPort;
	}

	public int getContainerPort() {
		return containerPort;
	}

	public void setContainerPort(int containerPort) {
		this.containerPort = containerPort;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getEnvInterval() {
		return envInterval;
	}

	public void setEnvInterval(int envInterval) {
		this.envInterval = envInterval;
	}

	public String getEnvPath() {
		return envPath;
	}

	public void setEnvPath(String envPath) {
		this.envPath = envPath;
	}

	public int getEnvThreshold() {
		return envThreshold;
	}

	public void setEnvThreshold(int envThreshold) {
		this.envThreshold = envThreshold;
	}

	public int getEnvIgnore() {
		return envIgnore;
	}

	public void setEnvIgnore(int envIgnore) {
		this.envIgnore = envIgnore;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public String getSubnetName() {
		return subnetName;
	}

	public void setSubnetName(String subnetName) {
		this.subnetName = subnetName;
	}

	public String getCidr() {
		return cidr;
	}

	public void setCidr(String cidr) {
		this.cidr = cidr;
	}

	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public int getTenantId() {
		return tenantId;
	}

	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}

	public List<EnvironmentVariable> getEnv() {
		return env;
	}

	public void setEnv(List<EnvironmentVariable> env) {
		this.env = env;
	}

	public String getApplicantionName() {
		return applicantionName;
	}

	public void setApplicantionName(String applicantionName) {
		this.applicantionName = applicantionName;
	}

	public String getProtocal() {
		return protocal;
	}

	public void setProtocal(String protocal) {
		this.protocal = protocal;
	}

	public String getEnvirnament() {
		return envirnament;
	}

	public void setEnvirnament(String envirnament) {
		this.envirnament = envirnament;
	}

	public String getEnvtimeout() {
		return envtimeout;
	}

	public void setEnvtimeout(String envtimeout) {
		this.envtimeout = envtimeout;
	}

	public List<Scale> getScales() {
		return scales;
	}

	public void setScales(List<Scale> scales) {
		this.scales = scales;
	}

	public List<Route> getRoute() {
		return route;
	}

	public void setRoute(List<Route> route) {
		this.route = route;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public int getAppsId() {
		return appsId;
	}

	public void setAppsId(int appsId) {
		this.appsId = appsId;
	}

	
	
	
}
