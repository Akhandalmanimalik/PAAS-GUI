package com.getusroi.paas.rest.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getusroi.paas.dao.DataBaseOperationFailedException;
import com.getusroi.paas.dao.StorageDAO;
import com.getusroi.paas.rest.service.exception.StorageServiceException;
import com.getusroi.paas.vo.Storage;
import com.google.gson.Gson;

@Path("/storageService")
public class StorageService {
	
	private static Logger logger = LoggerFactory.getLogger(StorageService.class);
	
	@GET
	@Path("/getAllStorage")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllStorage() throws DataBaseOperationFailedException {
		
		logger.debug(".getAllStorage of StorageService");
		List<Storage> storageList = new ArrayList<>();
		StorageDAO storageDAO = new StorageDAO();
		storageList = storageDAO.getAllStorage();
		Gson gson = new Gson();
		String list = gson.toJson(storageList);
		return list;
		
	}
	
	@POST
	@Path("/updateStorage")
	@Consumes
	public void updateStorage(String storageData, String serviceName) throws StorageServiceException, DataBaseOperationFailedException {
		logger.debug(".getAllStorage of StorageService");
		ObjectMapper mapper = new ObjectMapper();
		StorageDAO storageDAO = new StorageDAO();
		try {
			Storage storage = mapper.readValue(storageData, Storage.class);
			storageDAO.updateStorage(storage, serviceName);
		} catch(IOException e) {
			logger.error("Error in reading data : "+storageData+" using object mapper in addApplicationSummary");
			throw new StorageServiceException("Error in reading data : "+storageData+" using object mapper in addApplicationSummary");
		}
	}
	
}














