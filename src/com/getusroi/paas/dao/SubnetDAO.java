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
import com.getusroi.paas.helper.ScriptService;
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
	 private final String INSERT_SUBNET_QUERY = "insert into subnet(subnet_name,cidr,tenant_id,vpc_id ,envirnoment_id,createdDTM,acl_id) values(?,?,?,?,?,now(),?)";	 
	 private final String GET_ALL_SUBNET_BY_TENANT_ID_QUERY="select subnet_name,cidr,vpc_id, envirnoment_id from subnet where tenant_id=?";
	 private final String DELETE_SUBNET_BY_SUBNET_NAME_QUERY="delete from subnet where subnet_name=?";
	 private final String UPDATE_SUBNET_BY_SUBNETID_AND_SUBNETNAME_QUERY="update subnet set vpc_name =? , cidr=?, acl=?, vpcId=? where subnetId=? AND subnet_name=?";
	 
	 private Subnet subnet = null;
	 private VpcDAO vpcDao = null;
	 private AclDAO netwrkDAO = null;
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
	
	/**
	 * This method is used to add subnet to db
	 * @param subnet : Subnet Object need to be added
	 * @throws DataBaseOperationFailedException : Error in adding subnet to db
	 */
	public void addSubnet(Subnet subnet) throws DataBaseOperationFailedException{
		LOGGER.debug(".addSubnet method in NetworkDAO");
		LOGGER.info("indie addSubnetmethod with subnetName>>>>>>>>>>>>>>>>>>>>>>>." +subnet);
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		EnvironmentDAO envDAO = new EnvironmentDAO();
		VpcDAO vpcDao = new VpcDAO();
		Connection connection=null;
		PreparedStatement pstmt=null;
		netwrkDAO = new AclDAO(); 
		try {
			connection=connectionFactory.getConnection("mysql");
			pstmt=(PreparedStatement) connection.prepareStatement(INSERT_SUBNET_QUERY);
			pstmt.setString(1, subnet.getSubnetName());
			pstmt.setString(2, subnet.getCidr());
			pstmt.setInt(3, subnet.getTenantId());
			LOGGER.debug("vpc id for subnet "+vpcDao.getVPCIdByVPCNames(subnet.getVpcName(),subnet.getTenantId()));
			pstmt.setInt(4, vpcDao.getVPCIdByVPCNames(subnet.getVpcName(),subnet.getTenantId()));
			pstmt.setInt(5, envDAO.getEnvironmentIdByEnvName(subnet.getEnvironmentName(),subnet.getTenantId()));
			pstmt.setInt(6,netwrkDAO.getACLIdByACLNames(subnet.getAclName(), subnet.getTenantId()));
			pstmt.executeUpdate();
			LOGGER.debug("Inserting subnet : "+subnet+" is successfull");
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Error in inserting subnet : "+subnet+" in db");
			throw new DataBaseOperationFailedException("Error in inserting subnet : "+subnet+" in db",e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Error in inserting subnet because " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Error in inserting subnet because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Error in inserting subnet : "+subnet+" in db",e);
		} finally{
			DataBaseHelper.dbCleanUp(connection, pstmt);
		}
	}//end of method addSubnet
	
	
	/**
	 * This method is used to get all the subnet data from db
	 * @return List<Subnet> : List of all subnet Object contain details of subnet
	 * @throws DataBaseOperationFailedException : Error in fetching all subnet data from db
	 */
	public List<Subnet> getAllSubnetByTenantId(int id) throws DataBaseOperationFailedException{
		LOGGER.debug(".getAllSubnet method in NetworkDAO");
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		List<Subnet> subnetList=new ArrayList<>();
		Connection connection=null;
		PreparedStatement stmt=null;
		ResultSet result=null;
		VpcDAO vpcDao = new VpcDAO();
		EnvironmentDAO envDao = new EnvironmentDAO();
		try {
			connection=connectionFactory.getConnection("mysql");
			stmt=connection.prepareStatement(GET_ALL_SUBNET_BY_TENANT_ID_QUERY);
			stmt.setInt(1, id);
			result=stmt.executeQuery();
			if(result != null){
				while(result.next()){
					Subnet subnet = new Subnet();
				 
					subnet.setSubnetName(result.getString("subnet_name"));
					subnet.setCidr(result.getString("cidr"));
					subnet.setVpcName(vpcDao.getVPCNameByVpcIdAndTenantId(result.getInt("vpc_id"),id));
					subnet.setEnvironmentName(envDao.getEnvironmentNameByEnvIdAndTenantId(result.getInt("envirnoment_id"),id));
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
	
	
	/**
	 * This method is used to update subnet based on subnetId and subnetName
	 * @param subnet : Subnet Object contains data need to be updated using subnetId and subnetName
	 * @throws DataBaseOperationFailedException : Unable to udate subnet using subnetId and subnetName
	 */
	public void updateSubnetBySubnetIDAndSubnetName(Subnet subnet) throws DataBaseOperationFailedException{
		LOGGER.debug(".updateSubnetBySubnetIDAndSubnetName method of NetworkDAO");
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		Connection connection=null;
		PreparedStatement pstmt=null;
		try {
			connection=connectionFactory.getConnection("mysql");
			pstmt=(PreparedStatement) connection.prepareStatement(UPDATE_SUBNET_BY_SUBNETID_AND_SUBNETNAME_QUERY);
			/*pstmt.setString(1, subnet.getVpc_name());
			pstmt.setString(2, subnet.getCidr());
			pstmt.setString(3, subnet.getAcl());
			pstmt.setString(4, subnet.getVpcId() );
			pstmt.setString(5, subnet.getSubnetId());
			pstmt.setString(5, subnet.getSubnet_name());*/
			LOGGER.debug("subnet : "+subnet+" is updated successfully");
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.debug("Unable to update subnet with data : "+subnet);
			throw new DataBaseOperationFailedException("Unable to update subnet with data : "+subnet,e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Unable to update subnet because " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Unable to update subnet because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Unable to update subnet with data : "+subnet,e);
		} finally{
			DataBaseHelper.dbCleanUp(connection, pstmt);
		}

	}//end of method updateSubnetBySubnetIDAndSubnetName
	
	/**
	 * This method is used to delete the subnet based on the  subnet name
	 * @param subnetName : subnet name in String
	 * @throws DataBaseOperationFailedException 
	 */
	public void deleteSubnetBySubnetName(String subnetName) throws DataBaseOperationFailedException{
		LOGGER.debug(".deleteSubnet method of NetworkDAO");
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		Connection connection=null;
		java.sql.PreparedStatement stmt=null;
		try {
			connection=connectionFactory.getConnection("mysql");
			stmt=connection.prepareStatement(DELETE_SUBNET_BY_SUBNET_NAME_QUERY);
			stmt.setString(1,subnetName);
			stmt.executeUpdate();
			new ScriptService().deleteSubnetNetwork(subnetName);
			LOGGER.debug("Subnet  : "+subnetName+" delete successfully");
		} catch (ClassNotFoundException | IOException | InterruptedException e) {
			LOGGER.error("Error in deleteing the subnet using subnet ID : "+subnetName);
			throw new DataBaseOperationFailedException("Error in deleteing the subnet using subnet name : "+subnetName,e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Error in deleteing the subnet because " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Error in deleteing the subnet because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Error in deleteing the subnet using subnet id : "+subnetName,e);
		} finally{
			DataBaseHelper.close(stmt);
			DataBaseHelper.close(connection);
		}		
	}//end of method deleteSubnetBySubnetName
	
	
	
}
