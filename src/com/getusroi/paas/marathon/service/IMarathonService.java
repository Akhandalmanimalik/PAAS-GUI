package com.getusroi.paas.marathon.service;

import com.getusroi.paas.vo.Service;



public interface IMarathonService {

	public int getInstanceCount(int id,String env) throws MarathonServiceException;
	public void updateMarathonInsance(String data) throws MarathonServiceException;
	public String getDockerContainerID() throws MarathonServiceException;
	public String  getGatewayRoute() throws MarathonServiceException;
	public String postRequestToMarathon(Service addService,int memomry) throws MarathonServiceException;
	public void  attachNasStorage(Service addService,String  containerdisk) throws MarathonServiceException;
}
