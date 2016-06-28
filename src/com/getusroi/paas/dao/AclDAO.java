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
import com.getusroi.paas.vo.ACL;
import com.getusroi.paas.vo.InOutBoundRule;


/**
 * This class is used to control db operation for all network related setup like creating VPC,Subnet,defining rule etc
 * @author bizruntime
 *
 */
public class AclDAO {
	 static final Logger LOGGER = LoggerFactory.getLogger(SubnetDAO.class);
	 private final String INSERT_ACL_QUERY="insert into acl (acl_name,description,tenant_id,createdDTM) values(?,?,?,NOW())";
	 private final String GEL_ALL_ACL_NAMES_QUERY = "select aclname from acl where tenant_id=?";
	 private final String GEL_ALL_ACL_QUERY="select * from acl where tenant_id=?";
	 private final String GET_ACL_ID_BY_ACLNAME_TENANT_ID="select acl_id from acl where acl_name=? and tenant_id=?";
	 private final String UPDATE_ACL_BY_ID_QUERY="update acl set acl_name=?, tenant_id=?, description=? where acl_id=?";
	 private final String DELETE_ACL_BY_ACL_ID = "delete from acl where acl_id=?";
	 private final String GET_ACL_NAME_BY_ACL_ID_AND_TENANT_ID="select acl_name from acl where acl_id=? and tenant_id=?";
	  
	 private final String INSERT_INTO_INOUT_BOUND_RULE_QUERY = "insert into inoutbound_rule (type,protocol,protocol_range,inoutbound_type,source_ip,createDTM,acl_id,action) values(?,?,?,?,?,NOW(),?,?)"; 
	 private final String GET_ALL_INOUT_BOUND_RULES_BY_ALC_ID = "select * from inoutbound_rule where acl_id=?";
	 private final String DELETE_INOUT_BOUND_RULE_BY_ID_QUERY = "delete from inoutbound_rule where id=?";
	 private final String  UPDATE_INOUT_BOUND_RULE_BY_ID_QUERY="update inoutbound_rule set type=?, protocol=?, protocol_range=?, inoutbound_type=?, source_ip=?, acl_id=? where id=?";
	 
	 InOutBoundRule inOutBoundRule = null;
	/**
	 * This method is used to insert ACL in db
	 * @param acl : ACL object conating acl data
	 * @throws DataBaseOperationFailedException : Unable to insert ACL in db
	 */
	public void insertACL(ACL acl) throws DataBaseOperationFailedException{
		LOGGER.debug(".insertACL method of NetworkDAO"+acl);
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
	public List<String> getAllACLNames(int tenantId) throws DataBaseOperationFailedException{
		LOGGER.debug(".getAllACLNames method of NetworkDAO");
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		List<String> aclList=new ArrayList<>();
		Connection connection=null;
		PreparedStatement pstmt=null;
		ResultSet result=null;
		try {
			connection=connectionFactory.getConnection("mysql");
			pstmt=(PreparedStatement) connection.prepareStatement(GEL_ALL_ACL_NAMES_QUERY);
			pstmt.setInt(1, tenantId);
			result=pstmt.executeQuery();
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
					acl.setId(result.getInt(1));
					acl.setTenantId(result.getInt("tenant_id"));
					acl.setId(result.getInt("acl_id"));
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
	 * This method is used to update acl based on Id
	 * @param acl : ACL object contain data need to update based on name
	 * @throws DataBaseOperationFailedException : Error on updating data
	 */
	public void updateACLById(ACL acl) throws DataBaseOperationFailedException{
		LOGGER.debug(".updateACLById method of AclServices");
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		Connection connection=null;
		PreparedStatement pstmt=null;
		try {
			connection=connectionFactory.getConnection("mysql");
			pstmt=(PreparedStatement) connection.prepareStatement(UPDATE_ACL_BY_ID_QUERY);
			//="update acl set acl_name=?, tenant_id=?, description=? where acl_id=?";
			pstmt.setString(1, acl.getAclName());
			pstmt.setInt(2, acl.getTenantId());
			pstmt.setString(3, acl.getDescription());
			pstmt.setInt(4, acl.getId());
			pstmt.execute();
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
	}//end of method updateACLById
	
	
	
	/*
	 * This method is to delete acl using aclname and tenantid
	 */
	/**
	 * To delete acl by aclId
	 * @param aclName
	 * @param id
	 * @throws DataBaseOperationFailedException
	 */
	public void deleteACLByaclId(int aclId)
			throws DataBaseOperationFailedException {
		LOGGER.debug(".deleating acl method of NetworkDAO");
		DataBaseConnectionFactory connectionFactory = new DataBaseConnectionFactory();
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = connectionFactory.getConnection("mysql");
			pstmt = (PreparedStatement) connection
					.prepareStatement(DELETE_ACL_BY_ACL_ID);
			pstmt.setInt(1,aclId);
			
			pstmt.executeUpdate();
			LOGGER.debug("acl : " + aclId + " deleted from db successfully");
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Error in deleting the acl from table using acl id : "
					+ aclId);
			throw new DataBaseOperationFailedException(
					"Error in deleting the acl from table using acl aclId : "
							+ aclId, e);
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
						"Error in deleting the acl from table using acl aclId : "
								+ aclId, e);
		} finally {
			DataBaseHelper.dbCleanUp(connection, pstmt);
		}
	}
	
	
	
	 /**
	  * To get acl_id by acl name
	  * @param aclname
	  * @param tenant_id
	  * @return
	  * @throws DataBaseOperationFailedException
	  */
	public int getACLIdByACLNames(String aclname,int tenant_id) throws DataBaseOperationFailedException{
		LOGGER.debug(".getaClIdByVPCNames method of NetworkDAO aclname: "+aclname+" tenant_id : "+tenant_id);
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		
		int aclId=0;
		Connection connection=null;
		PreparedStatement pstmt=null;
		ResultSet result=null;
		try {
			connection=connectionFactory.getConnection("mysql");
			pstmt=(PreparedStatement) connection.prepareStatement(GET_ACL_ID_BY_ACLNAME_TENANT_ID);
			 
			pstmt.setString(1, aclname);
			pstmt.setInt(2, tenant_id);
			result=pstmt.executeQuery();
			
			if(result !=null){
				while(result.next()){
					 aclId=result.getInt("acl_id");
					LOGGER.debug(" aclId : "+aclId);					
				}
			}else{
				LOGGER.debug("No aCl available in db");
			}
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Error in getting the acl detail from db using vpc name : "+aclname);
			throw new DataBaseOperationFailedException("Error in fetching the aclid from db using acl name : "+aclname,e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Error in getting the acl detail from db using acl name because " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Error in getting the acl detail from db using acl name because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Error in fetching the aclid from db using vpc name : "+aclname,e);
		} finally{
			DataBaseHelper.dbCleanup(connection, pstmt, result);
		}
		return aclId;
	}//end of method acl validation
	
	public String getACLNameByAclIdAndTenantId(int aclId,int tenant_id) throws DataBaseOperationFailedException{
		LOGGER.debug(".getACLNameByAclIdAndTenantId method of NetworkDAO aclId>>>>>>>>>>>>>>>>: "+aclId+" tenant_id : "+tenant_id);
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		
		String aclName=null;
		Connection connection=null;
		PreparedStatement pstmt=null;
		ResultSet result=null;
		try {
			connection=connectionFactory.getConnection("mysql");
			pstmt=(PreparedStatement) connection.prepareStatement(GET_ACL_NAME_BY_ACL_ID_AND_TENANT_ID);
			 
			pstmt.setInt(1, aclId);
			pstmt.setInt(2, tenant_id);
			result=pstmt.executeQuery();
			
			if(result !=null){
				while(result.next()){
					aclName = result.getString("acl_name");
					LOGGER.debug(" aclName : "+aclName);					
				}
			}else{
				LOGGER.debug("No aCl available in db");
			}
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Error in getting the acl detail from db using vpc name : "+aclId);
			throw new DataBaseOperationFailedException("Error in fetching the aclid from db using acl name : "+aclId,e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Error in getting the acl detail from db using acl name because " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Error in getting the acl detail from db using acl name because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Error in fetching the aclid from db using vpc name : "+aclId,e);
		} finally{
			DataBaseHelper.dbCleanup(connection, pstmt, result);
		}
		return aclName;
	}//end of method acl validation
	
	/**
	 * This method is used to insert ACL in db
	 * @param acl : ACL object conating acl data
	 * @throws DataBaseOperationFailedException : Unable to insert ACL in db
	 */
//"(type,protocol,protocol_range,inoutbound_type,source_ip,createDTM,acl_id,action) values(?,?,?,?,?,NOW(),?,?)";
	
	public void insertInOutBoundRule(InOutBoundRule inOutBndRule) throws DataBaseOperationFailedException{
		LOGGER.debug(".insertInOutBoundRule method of AclDAO "+inOutBndRule);
		
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		Connection connection=null;
		PreparedStatement pstmt=null;
		try {
			connection=connectionFactory.getConnection("mysql");
			pstmt=(PreparedStatement) connection.prepareStatement(INSERT_INTO_INOUT_BOUND_RULE_QUERY);
			pstmt.setString(1,inOutBndRule.getType());
			pstmt.setString(2,inOutBndRule.getProtocol());
			pstmt.setString(3,inOutBndRule.getProtocolRange());
			pstmt.setString(4,inOutBndRule.getRuleType());
			pstmt.setString(5,inOutBndRule.getSourceIp());
			pstmt.setInt(6,inOutBndRule.getAclId());
			pstmt.setString(7,inOutBndRule.getAction());
			pstmt.executeUpdate();
			LOGGER.debug("InOutBoundRule with data : "+inOutBndRule+" executed successfully");
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Error in inserting InOutBoundRule in db");
			throw new DataBaseOperationFailedException("Error in inserting InOutBoundRule in db with data : "+inOutBndRule,e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Error in inserting InOutBoundRule in db because " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Error in inserting InOutBoundRule in db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Error in inserting acl in db with data : "+inOutBndRule, e);
		} 
	}//end of method insertACL
	 
	
	/**
	 * This method is used to get all InOutBound rule from db
	 * @return List<ACL> : list of all ACL object conating acl data
	 * @throws DataBaseOperationFailedException : Error in fetching data  for acl in db
	 */
	public List<InOutBoundRule> getAllInOutBoundRules(int aclId,int tenantId) throws DataBaseOperationFailedException{
		LOGGER.debug(".getAllACL method of AclDAO aclId "+aclId);
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		List<InOutBoundRule> aclList=new ArrayList<InOutBoundRule>();
		Connection connection=null;
		PreparedStatement pstmt=null;
		ResultSet result=null;
		inOutBoundRule = new InOutBoundRule();
		try {
			connection=connectionFactory.getConnection("mysql");
			pstmt=(PreparedStatement) connection.prepareStatement(GET_ALL_INOUT_BOUND_RULES_BY_ALC_ID);
			pstmt.setInt(1, aclId);
			result = pstmt.executeQuery();
			if(result !=null){
				while(result.next()){
					inOutBoundRule = new InOutBoundRule();
					inOutBoundRule.setId(result.getInt("id"));
					inOutBoundRule.setType(result.getString("type"));
					inOutBoundRule.setProtocol(result.getString("protocol"));
					inOutBoundRule.setProtocolRange(result.getString("protocol_range"));
					inOutBoundRule.setRuleType(result.getString("inoutbound_type"));
					inOutBoundRule.setSourceIp(result.getString("source_ip"));
					inOutBoundRule.setAclId(result.getInt("acl_id"));
					inOutBoundRule.setAclName(getACLNameByAclIdAndTenantId(result.getInt("acl_id"), tenantId));
					inOutBoundRule.setAction(result.getString("action"));
					aclList.add(inOutBoundRule);
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
	 * This method is to delete InOutBoundRule by id.
	 * @param id
	 * @throws DataBaseOperationFailedException
	 */
	public void deleteInOutBoundRuleById(int id)
			throws DataBaseOperationFailedException {
		LOGGER.debug("Inside .deleteInOutBoundRuleById of AclDAO id "+id);
		DataBaseConnectionFactory connectionFactory = new DataBaseConnectionFactory();
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = connectionFactory.getConnection("mysql");
			pstmt = (PreparedStatement) connection
					.prepareStatement(DELETE_INOUT_BOUND_RULE_BY_ID_QUERY);
			pstmt.setInt(1,id);
			pstmt.executeUpdate();
			LOGGER.debug("InOutboundRule : " + id + " deleted from db successfully");
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Error in deleting the acl from table using acl name : "
					+ id);
			throw new DataBaseOperationFailedException(
					"Error in deleting the InOutboundRule from table using acl name : "
							+ id, e);
		} catch (SQLException e) {
			if (e.getErrorCode() == 1064) {
				String message = "Error in deleting the InOutboundRule from table using acl name because "
						+ PAASErrorCodeExceptionHelper
								.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if (e.getErrorCode() == 1146) {
				String message = "Error in deleting the InOutboundRule from table using acl name because: "
						+ PAASErrorCodeExceptionHelper
								.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException(
						"Error in deleting the InOutboundRule from table using acl name : "
								+ id, e);
		} finally {
			DataBaseHelper.dbCleanUp(connection, pstmt);
		}
	}
	
	
	/**
	 * This method is used to update InOutBoundRule based on id
	 * @param acl : ACL object contain data need to update based on name
	 * @throws DataBaseOperationFailedException : Error on updating data
	 */
	public void updateInOutBoundRuleById(InOutBoundRule inoutBndrule) throws DataBaseOperationFailedException{
		LOGGER.debug(". updateInOutBoundRuleById method of AclDAO "+inoutBndrule);
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		Connection connection=null;
		PreparedStatement pstmt=null;
		LOGGER.debug(">>>>>>>>>>>>>> "+ inoutBndrule.getAction());
		try {
			connection=connectionFactory.getConnection("mysql");
			pstmt=(PreparedStatement) connection.prepareStatement(UPDATE_INOUT_BOUND_RULE_BY_ID_QUERY);
			
			pstmt.setString(1, inoutBndrule.getType());
			pstmt.setString(2, inoutBndrule.getProtocol());
			pstmt.setString(3, inoutBndrule.getProtocolRange());
			pstmt.setString(4, inoutBndrule.getRuleType());
			pstmt.setString(5, inoutBndrule.getSourceIp());
			pstmt.setInt(6, inoutBndrule.getAclId());
			pstmt.setInt(7, inoutBndrule.getId());
			pstmt.execute();
			LOGGER.debug("InOutBoundrule update successfully with data : "+inoutBndrule);
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Unable to update the InOutBoundrule with data : "+inoutBndrule);
			throw new DataBaseOperationFailedException("Unable to update the ACL with data : "+inoutBndrule,e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Unable to update the InOutBoundrule because " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Unable to update the InOutBoundrule because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Unable to update the InOutBoundrule with data : "+inoutBndrule,e);
		} 
	}//end of method updateInOutBoundRuleById

	
}
