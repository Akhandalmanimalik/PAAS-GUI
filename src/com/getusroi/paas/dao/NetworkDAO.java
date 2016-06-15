package com.getusroi.paas.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getusroi.paas.db.helper.DataBaseConnectionFactory;
import com.getusroi.paas.db.helper.DataBaseHelper;
import com.getusroi.paas.helper.PAASConstant;
import com.getusroi.paas.helper.PAASErrorCodeExceptionHelper;
import com.getusroi.paas.helper.ScriptService;
import com.getusroi.paas.vo.ACL;
import com.getusroi.paas.vo.Subnet;
import com.getusroi.paas.vo.VPC;
import com.getusroi.paas.vo.VPCRegion;


/**
 * This class is used to control db operation for all network related setup like creating VPC,Subnet,defining rule etc
 * @author bizruntime
 *
 */
public class NetworkDAO {
	 static final Logger LOGGER = LoggerFactory.getLogger(SubnetDAO.class);
	 private final String REGISTER_VPC_QUERY="insert into vpc(vpc_name,tenant_id,createdDTM,acl) values(?,?,NOW(),?)";
	 private final String GET_VPCID_BY_VPCNAME_AND_TENANT_ID_QUERY="select vpc_id from vpc where vpc_name=? and tenant_id=?";
	 private final String GET_ALL_VPC_QUERY="select * from vpc where tenant_id=?";
	 private final String DELETE_VPC_BY_NAME_QUERY="delete from vpc where vpc_name=?";
	 private final String UPDATE_VPC_BY_NAME_AND_VPCID_QUERY="update vpc set vpc_region=? , cidr=?, acl=? where vpcId=? AND vpc_name=?";
	 private final String GET_VPC_NAME_USING_VPCID_TENANTID = "select vpc_name from vpc where vpc_id =? and tenant_id=?";
	 
	 private final String INSERT_VPC_REGION_QUERY="insert into vpc_region (region) values(?)";
	 private final String GET_VPC_REGION_NAME_QUERY="select region from vpc_region";
	 private final String DELETE_VPC_REGION_QUERY="delete from vpc_region where region=?";
	 
	 private final String INSERT_ACL_QUERY="insert into acl (acl_name,description,tenant_id,createdDTM) values(?,?,?,NOW())";
	 private final String GEL_ALL_ACL_NAMES_QUERY="select aclname from acl";
	 private final String GEL_ALL_ACL_QUERY="select * from acl where tenant_id=?";
	 private final String UPDATE_ACL_BY_NAME_QUERY="update acl set action=?, sourceip=?, destip=? where name=?";
	 private final String DELETE_ACL_BY_NAME_QUERY_Using_TenantId = "delete from acl where acl_name=? and tenant_id=?";
	 
	 private final String INSERT_SUBNET_QUERY = "insert into subnet(subnet_name,cidr,tenant_id,vpc_id ,envirnoment_id,createdDTM) values(?,?,?,?,?,now())";
	 
	 private final String GET_ALL_SUBNET_BY_TENANT_ID_QUERY="select subnet_name,cidr,vpc_id, envirnoment_id from subnet where tenant_id=?";
	 private final String DELETE_SUBNET_BY_SUBNET_NAME_QUERY="delete from subnet where subnet_name=?";
	 private final String UPDATE_SUBNET_BY_SUBNETID_AND_SUBNETNAME_QUERY="update subnet set vpc_name =? , cidr=?, acl=?, vpcId=? where subnetId=? AND subnet_name=?";
	 
	 private final String GET_ENVIRONMENT_NAME_USING_ID_AND_TENANTID="select environment_name from environments where id =? and tenant_id=?";
	/**
	 * This method is used to add VPC to database
	 * @param vpc : VPC object containg data need to be stored
	 * @throws DataBaseOperationFailedException : Unable to register vpc with db
	 */
	public void registerVPC(VPC vpc) throws DataBaseOperationFailedException{
		LOGGER.debug(".registerVPC method of NetworkDAO");
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		Connection connection=null;
		PreparedStatement pstmt=null;
		try {
			connection=connectionFactory.getConnection("mysql");
			pstmt=(PreparedStatement) connection.prepareStatement(REGISTER_VPC_QUERY);
			pstmt.setString(1, vpc.getVpc_name());
			pstmt.setInt(2, vpc.getTenant_id());
			pstmt.setString(3, vpc.getAcl());
			
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
	
	/**
	 * This method is used to get list of all the vpc store in db
	 * @return List<VPC> : list of VPC object containg vpc information
	 * @throws DataBaseOperationFailedException : Unable to get all the vpc stored in db
	 */
	public List<VPC> getAllVPC(int tenant_id) throws DataBaseOperationFailedException{
		LOGGER.debug(".getAllVPC method of NetworkDAO");
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		List<VPC> vpcList=new ArrayList<>();
		Connection connection=null;
		PreparedStatement pstmt=null;
		ResultSet result=null;
		try {
			connection=connectionFactory.getConnection("mysql");
			pstmt=(PreparedStatement) connection.prepareStatement(GET_ALL_VPC_QUERY);
			pstmt.setInt(1, tenant_id);
			result=pstmt.executeQuery();
			if(result !=null){
				while(result.next()){
					String vpc_name=result.getString("vpc_name");
					String acl=result.getString("acl");
					String vpcId=result.getString("vpc_id");
					LOGGER.debug("vpc name : "+vpc_name+", acl : "+acl+", vpcId : "+vpcId);
					VPC vpc = new VPC(vpcId, vpc_name, acl);
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
	public void deleteVPCByName(String vpcName) throws DataBaseOperationFailedException{
		LOGGER.debug(".deleteVPCByName method of NetworkDAO");
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		Connection connection=null;
		PreparedStatement pstmt=null;
		try {
			connection=connectionFactory.getConnection("mysql");
			pstmt=(PreparedStatement) connection.prepareStatement(DELETE_VPC_BY_NAME_QUERY);
			pstmt.setString(1, vpcName);
			pstmt.executeUpdate();
			LOGGER.debug("vpc : "+vpcName+" deleted from db successfully");
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Error in deleting the vpc from table using vpc name : "+vpcName);
			throw new DataBaseOperationFailedException("Error in deleting the vpc from table using vpc name : "+vpcName,e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Error in deleting the vpc from table using vpc name because " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Error in deleting the vpc from table using vpc name because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Error in deleting the vpc from table using vpc name : "+vpcName,e);
		} finally{
			DataBaseHelper.dbCleanUp(connection, pstmt);
		}
	}//end of method deleteVPCByName
	
	/**
	 * This method is used to update vpc based on vpcid and vpc name
	 * @param vpc : VPC object
	 * @throws DataBaseOperationFailedException : Unable to update the vpc error
	 */
	public void updateVPCByNameAndVPCId(VPC vpc) throws DataBaseOperationFailedException{
		LOGGER.debug(".updateVPCByNameAndVPCId method of NetworkDAO");
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		Connection connection=null;
		PreparedStatement pstmt=null;
		try {
			connection=connectionFactory.getConnection("mysql");
			pstmt=(PreparedStatement) connection.prepareStatement(UPDATE_VPC_BY_NAME_AND_VPCID_QUERY);
			
			pstmt.setString(3, vpc.getAcl());
			pstmt.setString(4,vpc.getVpcId());
			pstmt.setString(5,vpc.getVpc_name());
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
	
	/**
	 * This method is used to insert ACL in db
	 * @param acl : ACL object conating acl data
	 * @throws DataBaseOperationFailedException : Unable to insert ACL in db
	 */
	public void insertACL(ACL acl) throws DataBaseOperationFailedException{
		LOGGER.debug(".insertACL method of NetworkDAO");
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		Connection connection=null;
		PreparedStatement pstmt=null;
		try {
			connection=connectionFactory.getConnection("mysql");
			pstmt=(PreparedStatement) connection.prepareStatement(INSERT_ACL_QUERY);
			pstmt.setString(1,acl.getAclName());
			pstmt.setString(2,acl.getDescription());
			pstmt.setInt(3,acl.getTenantId());
			pstmt.executeUpdate();
			LOGGER.debug("ACL with data : "+acl+" executed successfully");
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Error in inserting acl in db");
			throw new DataBaseOperationFailedException("Error in inserting acl in db with data : "+acl,e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Error in inserting acl in db because " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Error in inserting acl in db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Error in inserting acl in db with data : "+acl,e);
		} 
	}//end of method insertACL
	
	/**
	 * This method is used to get all acl names from db
	 * @return List<String> : list of all ACL name in String
	 * @throws DataBaseOperationFailedException : Error in fetching data  for acl names in db
	 */
	public List<String> getAllACLNames() throws DataBaseOperationFailedException{
		LOGGER.debug(".getAllACLNames method of NetworkDAO");
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		List<String> aclList=new ArrayList<>();
		Connection connection=null;
		Statement stmt=null;
		ResultSet result=null;
		try {
			connection=connectionFactory.getConnection("mysql");
			stmt=connection.createStatement();
			result=stmt.executeQuery(GEL_ALL_ACL_NAMES_QUERY);
			if(result !=null){
				while(result.next()){				
					String aclname=result.getString("acl_name");
					LOGGER.debug("acl name : "+aclname);					
					aclList.add(aclname);
				}
			}else{
				LOGGER.debug("No data available for acl");
			}
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.debug("Error in getting the ACL names from db");
			throw new DataBaseOperationFailedException("Error in fetching the ACL names from db",e);
		}  catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Error in getting the ACL names from db because " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Error in getting the ACL names from db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Error in fetching the ACL names from db",e);
		} 
		return aclList;
	}//end of method getAllACL
	
	/**
	 * This method is used to get all acl from db
	 * @return List<ACL> : list of all ACL object conating acl data
	 * @throws DataBaseOperationFailedException : Error in fetching data  for acl in db
	 */
	public List<ACL> getAllACL(int tenantId) throws DataBaseOperationFailedException{
		LOGGER.debug(".getAllACL method of NetworkDAO");
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		List<ACL> aclList=new ArrayList<>();
		Connection connection=null;
		PreparedStatement pstmt=null;
		ResultSet result=null;
		try {
			connection=connectionFactory.getConnection("mysql");
			pstmt=(PreparedStatement) connection.prepareStatement(GEL_ALL_ACL_QUERY);
			pstmt.setInt(1, tenantId);
			result = pstmt.executeQuery();
			if(result !=null){
				while(result.next()){
					ACL acl = new ACL();
					acl.setAclName(result.getString("acl_name"));
					acl.setDescription(result.getString("description"));
					aclList.add(acl);
				}
			}else{
				LOGGER.debug("No data available for acl");
			}
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.debug("Error in getting the ACL from db");
			throw new DataBaseOperationFailedException("Error in fetching the ACL from db",e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Error in fetching the ACL from db because " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Error in fetching the ACL from db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Error in fetching the ACL from db",e);
		} 
		return aclList;
	}//end of method getAllACL
	
	/**
	 * This method is used to update acl based on name
	 * @param acl : ACL object contain data need to update based on name
	 * @throws DataBaseOperationFailedException : Error on updating data
	 */
	public void updateACLByName(ACL acl) throws DataBaseOperationFailedException{
		LOGGER.debug(".updateACLByName method of NetworkDAO");
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		Connection connection=null;
		PreparedStatement pstmt=null;
		try {
			connection=connectionFactory.getConnection("mysql");
			pstmt=(PreparedStatement) connection.prepareStatement(UPDATE_ACL_BY_NAME_QUERY);
			
//			pstmt.setString(2, acl.getSrcIp());
//			pstmt.setString(3, acl.getDestIP());
			pstmt.setString(5, acl.getAclName());
			LOGGER.debug("ACL update successfully with data : "+acl);
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Unable to update the ACL with data : "+acl);
			throw new DataBaseOperationFailedException("Unable to update the ACL with data : "+acl,e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Unable to update the ACL because " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Unable to update the ACL because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Unable to update the ACL with data : "+acl,e);
		} 
	}//end of method updateACLByName
	
	/**
	 * This method is used to insert vpc region in db
	 * @param vpcRegion : VPCRegionVO object contains data of vpc region
	 * @throws DataBaseOperationFailedException : Error in inserting vpc region in db
	 */
	public void insertVPCRegion(VPCRegion vpcRegion) throws DataBaseOperationFailedException{
		LOGGER.debug(".insertVPCRegion method of NetworkDAO");
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		Connection connection=null;
		PreparedStatement pstmt=null;
		try {
			connection=connectionFactory.getConnection("mysql");
			pstmt=(PreparedStatement) connection.prepareStatement(INSERT_VPC_REGION_QUERY);
			pstmt.setString(1, vpcRegion.getRegion());
			pstmt.execute();
			LOGGER.debug("VPC region is successfully added to db");
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Unable to insert data into vpc region with : "+vpcRegion);
			throw new DataBaseOperationFailedException("Unable to insert data into vpc region with : "+vpcRegion,e);
		}catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Unable to insert data into vpc region because " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Unable to insert data into vpc region because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Unable to insert data into vpc region with : "+vpcRegion,e);
		} finally{
			DataBaseHelper.dbCleanUp(connection, pstmt);
		}
	}//end of method insertVPCRegion
	
	/**
	 * This method is used to get region from db
	 * @return List<String> : list of all region defined in db in string
	 * @throws DataBaseOperationFailedException : Error in getting region from db
	 */
	public List<String> getAllVPCRegionName() throws DataBaseOperationFailedException{
		LOGGER.debug(".getAllVPCRegionName method of NetworkDAO");
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		List<String> vpcRegionNameList=new ArrayList<>();
		Connection connection=null;
		Statement stmt=null;
		ResultSet result=null;
		try {
			connection=connectionFactory.getConnection("mysql");
			stmt=connection.createStatement();
			result=stmt.executeQuery(GET_VPC_REGION_NAME_QUERY);
			if(result!=null){
				while(result.next()){
					String region=result.getString("region");
					vpcRegionNameList.add(region);
				}
			}else{
				LOGGER.debug("No data available in vpc_region");
			}
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Error in getting the vpc region names from db");
			throw new DataBaseOperationFailedException("Unable to fetch vpc region names from db",e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Error in getting the vpc region names because " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Error in getting the vpc region names because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Unable to fetch vpc region names from db",e);
		} finally{
			DataBaseHelper.dbCleanup(connection, stmt, result);
		}
		return vpcRegionNameList;
	}//end of method getAllVPCRegionName
	
	/**
	 * This method is used to delete vpc region from db
	 * @param region : region to be deleted in String
	 * @throws DataBaseOperationFailedException : Error in delete region from db
	 */
	public void deleteVPCRegion(String region) throws DataBaseOperationFailedException{
		LOGGER.debug(".deleteVPCRegion method of NetworkDAO");
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		Connection connection=null;
		PreparedStatement pstmt=null;
		try {
			connection=connectionFactory.getConnection("mysql");
			pstmt=(PreparedStatement) connection.prepareStatement(DELETE_VPC_REGION_QUERY);
			pstmt.setString(1, region);
			pstmt.executeUpdate();
			LOGGER.debug("Successfully delete the vpc region : "+region+" from db");
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Unable to delete vpc region "+region);
			throw new DataBaseOperationFailedException("Unble to delete vpc region : "+region,e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Unable to delete vpc region because " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Unable to delete vpc region because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Unble to delete vpc region : "+region,e);
		} finally{
			DataBaseHelper.dbCleanUp(connection, pstmt);
		}
	}//end of method deleteVPCRegion
	
	/**
	 * This method is used to add subnet to db
	 * @param subnet : Subnet Object need to be added
	 * @throws DataBaseOperationFailedException : Error in adding subnet to db
	 */
	public void addSubnet(Subnet subnet) throws DataBaseOperationFailedException{
		LOGGER.debug(".addSubnet method in NetworkDAO");
		LOGGER.info("indie addSubnetmethod with subnetName>>>>>>>>>>>>>>>>>>>>>>>." +subnet);
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		NetworkDAO networkDAO = new NetworkDAO();
		EnvironmentDAO envDAO = new EnvironmentDAO();
		Connection connection=null;
		PreparedStatement pstmt=null;
		try {
			connection=connectionFactory.getConnection("mysql");
			pstmt=(PreparedStatement) connection.prepareStatement(INSERT_SUBNET_QUERY);
			pstmt.setString(1, subnet.getSubnetName());
			pstmt.setString(2, subnet.getCidr());
			pstmt.setInt(3, subnet.getTenantId());//
			pstmt.setInt(4, networkDAO.getVPCIdByVPCNames(subnet.getVpcName(),subnet.getTenantId()));
			pstmt.setInt(5, envDAO.getEnvironmentIdByEnvName(subnet.getEnvironmentName(),subnet.getTenantId()));
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
		try {
			connection=connectionFactory.getConnection("mysql");
			stmt=connection.prepareStatement(GET_ALL_SUBNET_BY_TENANT_ID_QUERY);
			stmt.setInt(1, id);
			result=stmt.executeQuery();
			if(result != null){
				while(result.next()){
					Subnet subnet = new Subnet();
					
					int vpcId = result.getInt("vpc_id");
					int empId = result.getInt("envirnoment_id");
					subnet.setSubnetName(result.getString("subnet_name"));
					subnet.setCidr(result.getString("cidr"));
					subnet.setVpcName(getVPCNameByVpcIdAndTenantId(vpcId,id));
					subnet.setEnvironmentName(getEnvironmentNameByEnvIdAndTenantId(empId,id));
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
	
	/*
	 * This method is to delete acl using aclname and tenantid
	 */
	public void deleteACLByName(String aclName,int id)
			throws DataBaseOperationFailedException {
		LOGGER.debug(".deleating acl method of NetworkDAO");
		DataBaseConnectionFactory connectionFactory = new DataBaseConnectionFactory();
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = connectionFactory.getConnection("mysql");
			pstmt = (PreparedStatement) connection
					.prepareStatement(DELETE_ACL_BY_NAME_QUERY_Using_TenantId);
			pstmt.setString(1,aclName);
			pstmt.setInt(2,id);
			
			pstmt.executeUpdate();
			LOGGER.debug("acl : " + aclName + " deleted from db successfully");
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Error in deleting the acl from table using acl name : "
					+ aclName);
			throw new DataBaseOperationFailedException(
					"Error in deleting the acl from table using acl name : "
							+ aclName, e);
		} catch (SQLException e) {
			if (e.getErrorCode() == 1064) {
				String message = "Error in deleting the acl from table using acl name because "
						+ PAASErrorCodeExceptionHelper
								.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if (e.getErrorCode() == 1146) {
				String message = "Error in deleting the acl from table using acl name because: "
						+ PAASErrorCodeExceptionHelper
								.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException(
						"Error in deleting the acl from table using acl name : "
								+ aclName, e);
		} finally {
			DataBaseHelper.dbCleanUp(connection, pstmt);
		}
	}
	
	
	/**
	 * This method is used to get vpc name by using vpcId and tenantId from db
	 * @param vpcid
	 * @param tenantId
	 * @return
	 * @throws DataBaseOperationFailedException
	 */
	public String getVPCNameByVpcIdAndTenantId(int vpcid,int tenantId)
			throws DataBaseOperationFailedException {
		LOGGER.debug(".getAllVPCRegionName method of NetworkDAO");
		DataBaseConnectionFactory connectionFactory = new DataBaseConnectionFactory();
		 
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		String vpcName=null;
		
		try {
			connection = connectionFactory.getConnection("mysql");
			stmt = (PreparedStatement)connection.prepareStatement(GET_VPC_NAME_USING_VPCID_TENANTID);
			stmt.setInt(1, vpcid);
			stmt.setInt(2, tenantId);
			result = stmt.executeQuery( );
			if (result != null && result.next()) {
		    vpcName=result.getString("vpc_name");
		    LOGGER.debug("comming vpc"+vpcName);
		     
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

	public String getEnvironmentNameByEnvIdAndTenantId(int envid,int tenantId)
			throws DataBaseOperationFailedException {
		LOGGER.debug(".getAllVPCRegionName method of NetworkDAO");
		DataBaseConnectionFactory connectionFactory = new DataBaseConnectionFactory();
		 
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		String empName=null;
	
		try {
			connection = connectionFactory.getConnection("mysql");
			stmt =(PreparedStatement)connection.prepareStatement(GET_ENVIRONMENT_NAME_USING_ID_AND_TENANTID);
			stmt.setInt(1, envid);
			stmt.setInt(2, tenantId);
			result = stmt.executeQuery( );
			if (result != null && result.next()) {
				empName=result.getString("environment_name");
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
		return empName;
	}
	
	public static void main(String[] args) throws DataBaseOperationFailedException {
		LOGGER.debug("dd "+new NetworkDAO().getEnvironmentNameByEnvIdAndTenantId(5,7));
	}
	
}
