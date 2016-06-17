package com.getusroi.paas.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getusroi.paas.db.helper.DataBaseConnectionFactory;
import com.getusroi.paas.db.helper.DataBaseHelper;
import com.getusroi.paas.helper.PAASConstant;
import com.getusroi.paas.helper.PAASErrorCodeExceptionHelper;

/**
 * This class is used to control db operation for all network related setup like
 * creating VPC,Subnet,defining rule etc
 * 
 * @author bizruntime
 *
 */
public class VpcDAO {
	static final Logger LOGGER = LoggerFactory.getLogger(VpcDAO.class);
	private final String GET_VPC_NAME_USING_VPCID_TENANTID = "select vpc_name from vpc where vpc_id =? and tenant_id=?";
	private final String GET_VPCID_BY_VPCNAME_AND_TENANT_ID_QUERY="select vpc_id from vpc where vpc_name=? and tenant_id=?";
	
	/**
	 * This method is used to get vpc name by using vpcId and tenantId from db
	 * @param vpcid
	 * @param tenantId
	 * @return
	 * @throws DataBaseOperationFailedException
	 */
	public String getVPCNameByVpcIdAndTenantId(int vpcid, int tenantId)
			throws DataBaseOperationFailedException {
		LOGGER.debug(".getAllVPCRegionName method of NetworkDAO");
		DataBaseConnectionFactory connectionFactory = new DataBaseConnectionFactory();
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		String vpcName = null;
		try {
			connection = connectionFactory.getConnection("mysql");
			stmt = (PreparedStatement) connection
					.prepareStatement(GET_VPC_NAME_USING_VPCID_TENANTID);
			stmt.setInt(1, vpcid);
			stmt.setInt(2, tenantId);
			result = stmt.executeQuery();
			if (result != null && result.next()) {
				vpcName = result.getString("vpc_name");
				LOGGER.debug("comming vpc" + vpcName);

			} else {
				LOGGER.debug("No data available in vpc_region");
			}
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Error in getting the vpc region names from db");
			throw new DataBaseOperationFailedException(
					"Unable to fetch vpc region names from db", e);
		} catch (SQLException e) {
			if (e.getErrorCode() == 1064) {
				String message = "Error in getting the vpc region names because "
						+ PAASErrorCodeExceptionHelper
								.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if (e.getErrorCode() == 1146) {
				String message = "Error in getting the vpc region names because: "
						+ PAASErrorCodeExceptionHelper
								.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException(
						"Unable to fetch vpc region names from db", e);
		} finally {
			DataBaseHelper.dbCleanup(connection, stmt, result);
		}
		return vpcName;
	}// end of method getAllVPCRegionName

	
	/**
	 * This method is used to get vpc id using vpc name
	 * @return String : VPC Id in string
	 * @throws DataBaseOperationFailedException : Unable to get vpc id using vpc name
	 */
	public int getVPCIdByVPCNames(String vpcname,int tenant_id) throws DataBaseOperationFailedException{
		LOGGER.debug(".getVPCIdByVPCNames method of NetworkDAO vpcName: "+vpcname+" tenant_id : "+tenant_id);
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		Integer vpcId=null;
		Connection connection=null;
		PreparedStatement pstmt=null;
		ResultSet result=null;
		try {
			connection=connectionFactory.getConnection("mysql");
			pstmt=(PreparedStatement) connection.prepareStatement(GET_VPCID_BY_VPCNAME_AND_TENANT_ID_QUERY);
			 
			pstmt.setString(1, vpcname);
			pstmt.setInt(2, tenant_id);
			result=pstmt.executeQuery();
			
			if(result !=null){
				while(result.next()){
					 vpcId=result.getInt("vpc_id");
					LOGGER.debug(" vpcId : "+vpcId);					
				}
			}else{
				LOGGER.debug("No VPC available in db");
			}
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Error in getting the vpc detail from db using vpc name : "+vpcname);
			throw new DataBaseOperationFailedException("Error in fetching the vpcid from db using vpc name : "+vpcname,e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Error in getting the vpc detail from db using vpc name because " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Error in getting the vpc detail from db using vpc name because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Error in fetching the vpcid from db using vpc name : "+vpcname,e);
		} finally{
			DataBaseHelper.dbCleanup(connection, pstmt, result);
		}
		return vpcId;
	}//end of method getAllVPC
	
	
}
