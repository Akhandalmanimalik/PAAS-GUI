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
import com.getusroi.paas.db.helper.DataBaseHelper;
import com.getusroi.paas.helper.PAASConstant;
import com.getusroi.paas.helper.PAASErrorCodeExceptionHelper;
import com.getusroi.paas.vo.VPC;

/**
 * This class is used to control db operation for all network related setup like
 * creating VPC,Subnet,defining rule etc
 * 
 * @author bizruntime
 *
 */
public class VpcDAO {
	static final Logger LOGGER = LoggerFactory.getLogger(VpcDAO.class);
	
	private final String REGISTER_VPC_QUERY="insert into vpc(vpc_name,tenant_id,createdDTM,acl_id,cidr) values(?,?,NOW(),?,?)";
	private final String GET_ALL_VPC_QUERY="select * from vpc where tenant_id=?";
	private final String GET_VPC_NAME_USING_VPCID_TENANTID = "select vpc_name from vpc where vpc_id =? and tenant_id=?";
	private final String GET_VPCID_BY_VPCNAME_AND_TENANT_ID_QUERY="select vpc_id from vpc where vpc_name=? and tenant_id=?";
	private final String DELETE_VPC_BY_NAME_QUERY="delete from vpc where vpc_id=?";
	private final String UPDATE_VPC_BY_VPCID_QUERY="update vpc set vpc_name=?,tenant_id=?,acl_id=?,cidr=? where vpc_id=?";
	
	AclDAO aclDAO = null;
	
	/**
	 * This method is used to get vpc name by using vpcId and tenantId from db
	 * @param vpcid
	 * @param tenantId
	 * @return
	 * @throws DataBaseOperationFailedException
	 */
	public String getVPCNameByVpcIdAndTenantId(int vpcid, int tenantId)
			throws DataBaseOperationFailedException {
		LOGGER.debug(".getVPCNameByVpcIdAndTenantId method of VpckDAO");
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
		int vpcId=0;
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

	/**
	 * This method is used to add VPC to database
	 * @param vpc : VPC object containg data need to be stored
	 * @throws DataBaseOperationFailedException : Unable to register vpc with db
	 */
	public void registerVPC(VPC vpc) throws DataBaseOperationFailedException{
		LOGGER.debug(".registerVPC method of NetworkDAO vpc"+vpc);
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		Connection connection=null;
		PreparedStatement pstmt=null;
		aclDAO = new AclDAO();   
		try {
			connection=connectionFactory.getConnection("mysql");
			pstmt=(PreparedStatement) connection.prepareStatement(REGISTER_VPC_QUERY);
			pstmt.setString(1, vpc.getVpc_name());
			pstmt.setInt(2, vpc.getTenant_id());
			pstmt.setInt(3, aclDAO.getACLIdByACLNames(vpc.getAclName(), vpc.getTenant_id()));
			pstmt.setString(4, vpc.getCidr());
			pstmt.executeUpdate();
			LOGGER.debug("VPC registerd successfully with data : "+vpc);
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Unable to register vpc to database with details : "+vpc);
			throw new DataBaseOperationFailedException("Unable to register vpc to database with details : "+vpc,e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Unable to register vpc to database because " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Unable to register vpc to database because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Unable to register vpc to database with details : "+vpc,e);
		}finally{
			DataBaseHelper.dbCleanUp(connection, pstmt);
		}
	}//end of method registerVPC
	
	
	 
		/**
		 * This method is used to get list of all the vpc store in db
		 * @return List<VPC> : list of VPC object containg vpc information
		 * @throws DataBaseOperationFailedException : Unable to get all the vpc stored in db
		 */
		public List<VPC> getAllVPC(int tenant_id) throws DataBaseOperationFailedException{
			LOGGER.debug(".getAllVPC method of NetworkDAO");
			DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
			List<VPC> vpcList=new ArrayList<VPC>();
			Connection connection=null;
			PreparedStatement pstmt=null;
			ResultSet result=null;
			aclDAO = new AclDAO();
			try {
				connection=connectionFactory.getConnection("mysql");
				pstmt=(PreparedStatement) connection.prepareStatement(GET_ALL_VPC_QUERY);
				pstmt.setInt(1, tenant_id);
				result=pstmt.executeQuery();
				if(result !=null){
					while(result.next()){
						VPC vpc = new VPC();
						vpc.setVpcId(result.getInt(1));
						vpc.setVpc_name(result.getString("vpc_name"));
						vpc.setTenant_id(result.getInt("tenant_id"));
						vpc.setAclName(aclDAO.getACLNameByAclIdAndTenantId(result.getInt("acl_id"), tenant_id));
						vpc.setCidr(result.getString("cidr"));
						vpc.setVpcId(result.getInt("vpc_id"));
						vpcList.add(vpc);
					}
				}else{
					LOGGER.debug("No VPC available in db");
				}
			} catch (ClassNotFoundException | IOException e) {
				LOGGER.error("Error in getting the vpc detail from db");
				throw new DataBaseOperationFailedException("Error in fetching the vpc from db",e);
			} catch(SQLException e) {
				if(e.getErrorCode() == 1064) {
					String message = "Error in getting the vpc detail from db because " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
					throw new DataBaseOperationFailedException(message, e);
				} else if(e.getErrorCode() == 1146) {
					String message = "Error in getting the vpc detail from db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
					throw new DataBaseOperationFailedException(message, e);
				} else
					throw new DataBaseOperationFailedException("Error in fetching the vpc from db",e);
			} finally{
				DataBaseHelper.dbCleanup(connection, pstmt, result);
			}
			return vpcList;
		}//end of method getAllVPC
		
		
		/**
		 * This method is used to delete vpc from db using vpc name
		 * @param vpcName : name of the vpc to be delete in String
		 * @throws DataBaseOperationFailedException : Unable to delete the vpc by vpc name
		 */
		public void deleteVPCById(int vpcId) throws DataBaseOperationFailedException{
			LOGGER.debug(".deleteVPCByName method of NetworkDAO");
			DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
			Connection connection=null;
			PreparedStatement pstmt=null;
			try {
				connection=connectionFactory.getConnection("mysql");
				pstmt=(PreparedStatement) connection.prepareStatement(DELETE_VPC_BY_NAME_QUERY);
				pstmt.setInt(1, vpcId);
				pstmt.executeUpdate();
				LOGGER.debug("vpc : "+vpcId+" deleted from db successfully");
			} catch (ClassNotFoundException | IOException e) {
				LOGGER.error("Error in deleting the vpc from table using vpc name : "+vpcId);
				throw new DataBaseOperationFailedException("Error in deleting the vpc from table using vpc name : "+vpcId,e);
			} catch(SQLException e) {
				if(e.getErrorCode() == 1064) {
					String message = "Error in deleting the vpc from table using vpc name because " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
					throw new DataBaseOperationFailedException(message, e);
				} else if(e.getErrorCode() == 1146) {
					String message = "Error in deleting the vpc from table using vpc name because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
					throw new DataBaseOperationFailedException(message, e);
				} else
					throw new DataBaseOperationFailedException("Error in deleting the vpc from table using vpc name : "+vpcId,e);
			} finally{
				DataBaseHelper.dbCleanUp(connection, pstmt);
			}
		}//end of method deleteVPCByName

		
		/**
		 * This method is used to update vpc based on vpcid.
		 * @param vpc
		 * @throws DataBaseOperationFailedException
		 */
		public void updateVPCByVPCId(VPC vpc) throws DataBaseOperationFailedException{
			LOGGER.debug(".updateVPCByVPCId method of VpckDAO");
			DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
			Connection connection=null;
			PreparedStatement pstmt=null;
			aclDAO = new AclDAO();
			try {
				connection=connectionFactory.getConnection("mysql");
				pstmt=(PreparedStatement) connection.prepareStatement(UPDATE_VPC_BY_VPCID_QUERY);
				pstmt.setString(1, vpc.getVpc_name());
				pstmt.setInt(2,vpc.getTenant_id());
				pstmt.setInt(3,aclDAO.getACLIdByACLNames(vpc.getAclName(), vpc.getTenant_id()));
				pstmt.setString(4, vpc.getCidr());
				pstmt.setInt(5, vpc.getVpcId());
				pstmt.execute();
				LOGGER.debug("VPC update with data : "+vpc+" successfully");
			} catch (ClassNotFoundException | IOException e) {
				LOGGER.error("Unable to update VPC with data :  "+ vpc);
				throw new DataBaseOperationFailedException("Unable to update VPC with data :  "+ vpc,e);
			} catch(SQLException e) {
				if(e.getErrorCode() == 1064) {
					String message = "Unable to update VPC with data because " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
					throw new DataBaseOperationFailedException(message, e);
				} else if(e.getErrorCode() == 1146) {
					String message = "Unable to update VPC with data because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
					throw new DataBaseOperationFailedException(message, e);
				} else
					throw new DataBaseOperationFailedException("Unable to update VPC with data :  "+ vpc,e);
			} finally{
				DataBaseHelper.dbCleanUp(connection, pstmt);
			}
		}//end of method updateVPCByNameAndVPCId
		
	}
