package com.getusroi.paas.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getusroi.paas.db.helper.DataBaseConnectionFactory;
import com.getusroi.paas.helper.PAASConstant;
import com.getusroi.paas.helper.PAASErrorCodeExceptionHelper;

/**
 * This class is used to control db operation for all Subnet related setup like creating VPC,Subnet,defining rule etc
 * @author bizruntime
 *
 */
public class SubnetDAO {
	 static final Logger logger = LoggerFactory.getLogger(NetworkDAO.class);
	 private final String GET_SUBNET_ID_BY_SUBNET_NAME_ID_QUERY = "select subnet_id from subnet where subnet_name=?";
		
	/**
	 * This method is used to get all the subnet data from db
	 * @return List<Subnet> : List of all subnet Object contain details of subnet
	 * @throws DataBaseOperationFailedException : Error in fetching all subnet data from db
	 */
	public Integer getSubnetIdBySubnetName(String subnetName) throws DataBaseOperationFailedException{
		logger.debug(".getSubnetIdBySubnetName method in SubnetDAO");
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		
		Connection connection=null;
		java.sql.PreparedStatement stmt=null;
		ResultSet result=null;
		Integer subnetId=null;
		try {
			connection=connectionFactory.getConnection("mysql");
			stmt=connection.prepareStatement(GET_SUBNET_ID_BY_SUBNET_NAME_ID_QUERY);
			stmt.setString(1, subnetName);
			result = stmt.executeQuery();
			
			if(result != null){
				while(result.next()){
					subnetId = result.getInt("subnet_id");
					logger.debug("subnet_id : " +subnetId);
				}
			}else{
				logger.debug("No subnet data available in db");
			}
		} catch (ClassNotFoundException | IOException e) {
			logger.error("Error in getting all the subnet from db");
			throw new DataBaseOperationFailedException("Unable to fetch all subnet data from db ",e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Unable to fetch all subnet data from db because " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Unable to fetch all subnet data from db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Unable to fetch all subnet data from db ",e);
		}
		return subnetId;
	}//end of method getAllSubnet
	
	
	
}
