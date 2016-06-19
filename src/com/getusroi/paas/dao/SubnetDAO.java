package com.getusroi.paas.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getusroi.paas.db.helper.DataBaseConnectionFactory;
import com.getusroi.paas.helper.PAASConstant;
import com.getusroi.paas.helper.PAASErrorCodeExceptionHelper;
import com.getusroi.paas.vo.Subnet;

/**
 * This class is used to control db operation for all Subnet related setup like creating VPC,Subnet,defining rule etc
 * @author bizruntime
 *
 */
public class SubnetDAO {
	 static final Logger LOGGER = LoggerFactory.getLogger(SubnetDAO.class);
	 private final String GET_SUBNET_ID_BY_SUBNET_NAME_ID_QUERY = "select subnet_id from subnet where subnet_name=? and tenant_id=?";
	 private final String  GET_ALL_SUBNET_BY_VPC_ID_TENANT_ID_QUERY = "select subnet_name from subnet where vpc_id = ? and tenant_id = ?";
	 
	 private Subnet subnet = null;
	 private VpcDAO vpcDao = null;
	/**
	 * This method is used to get all the subnet data from db
	 * @return List<Subnet> : List of all subnet Object contain details of subnet
	 * @throws DataBaseOperationFailedException : Error in fetching all subnet data from db
	 */
	public int getSubnetIdBySubnetName(String subnetName,int tenantId) throws DataBaseOperationFailedException{
		LOGGER.debug(".getSubnetIdBySubnetName method in SubnetDAO");
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		Connection connection=null;
		PreparedStatement stmt=null;
		ResultSet result=null;
		int subnetId=0;
		try {
			connection=connectionFactory.getConnection("mysql");
			stmt=connection.prepareStatement(GET_SUBNET_ID_BY_SUBNET_NAME_ID_QUERY);
			stmt.setString(1, subnetName);
			stmt.setInt(2,tenantId);
			result = stmt.executeQuery();
			
			if(result != null){
				while(result.next()){
					subnetId = result.getInt("subnet_id");
					LOGGER.debug("subnet_id : " +subnetId);
				}
			}else{
				LOGGER.debug("No subnet data available in db");
			}
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Error in getting all the subnet from db");
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
	
	
	/**
	 * This method is used to get all  subnet by vpcId and tenantid data from db
	 * @return List<Subnet> : List of all subnet Object contain details of subnet
	 * @throws DataBaseOperationFailedException : Error in fetching all subnet data from db
	 */
	public List<Subnet> getAllSubnetByVpcNameAndTenantId(String vpcName,int tenantId) throws DataBaseOperationFailedException{
		LOGGER.debug(".getAllSubnetByVpcIdAndTenantId method in SubnetDAO");
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		Connection connection=null;
		PreparedStatement stmt=null;
		ResultSet result=null;
		List<Subnet> subnetList = new ArrayList<Subnet>();
		vpcDao =new VpcDAO();
		try {
			connection = connectionFactory.getConnection("mysql");
			stmt=connection.prepareStatement(GET_ALL_SUBNET_BY_VPC_ID_TENANT_ID_QUERY);
			stmt.setInt(1, vpcDao.getVPCIdByVPCNames(vpcName, tenantId));
			stmt.setInt(2, tenantId);
			result = stmt.executeQuery();
			if(result != null){
				while(result.next()){
					subnet = new Subnet();
					subnet.setSubnetName(result.getString("subnet_name"));
					subnetList.add(subnet);
				}
			}else{
				LOGGER.debug("No subnet data available in db");
			}
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Error in getting all the subnet from db");
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
		
		return subnetList;
	}//end of method getAllSubnet
	
	public static void main(String[] args) throws DataBaseOperationFailedException {
		LOGGER.debug(">>>> "+ new SubnetDAO().getAllSubnetByVpcNameAndTenantId("vpcname17", 7));
	}
	
}
