package com.getusroi.paas.dao;

import java.io.IOException;
import java.sql.Connection;
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
import com.getusroi.paas.vo.FirewallInbound;
import com.getusroi.paas.vo.FirewallOutbound;
import com.mysql.jdbc.PreparedStatement;
import static com.getusroi.paas.helper.PAASConstant.*;

/**
 * This class is used to controll DAO operation on network firewall
 * @author bizruntime
 *
 */
public class FirewallDAO {
	 static final Logger logger = LoggerFactory.getLogger(FirewallDAO.class);
	 private static final String ADD_FIREWALL_OUTBOUND_QUERY="insert into firewall_outbounds values(?,?,?,?,?,?)";	 
	 private static final String GET_ALL_FIREWALL_OUTBOUND_QUERY="select * from firewall_outbounds";
	 private static final String UPDATE_FIREWALL_OUTBOUND_BY_NAME_QUERY="update firewall_outbounds set out_type=?, out_protocol=?, out_portrange=?, out_source=?,out_ip=? where out_name=?";
	 private static final String DELETE_FIREWALL_OUTBOUND_BY_NAME_QUERY="delete from firewall_outbounds where out_name=?";
	 private static final String GET_ALL_FIREWALL_TYPES_QUERY="select type from firewall_type";
	 private static final String DELETE_FIREWALL_TYPE_QUERY="delete from firewall_type where type=?";
	 private static final String GET_ALL_FIREWALL_PROTOCOL_QUERY="select protocol_type from protocol";
	 private static final String INSERT_FIREWALL_INBOUND_QUERY="insert into firewall_inbounds values(?,?,?,?,?,?)";
	 private static final String GET_ALL_FIREWALL_INBOUND_QUERY="select * from firewall_inbounds";
	 private static final String UPDATE_FIREWALL_INBOUND_BY_NAME_QUERY="update firewall_inbounds set in_type=?, in_protocol=?, in_portrange=?, in_source=?,in_ip=? where in_name=?";
	 private static final String DELETE_FIREWALL_INBOUND_BY_NAME_QUERY="delete from firewall_inbounds where in_name=?";
	 
	 /**
	  * This method is used to add firewall outbound into db
	  * @param outbound : FirewallOutbound object contain data to be added into db
	  * @throws DataBaseOperationFailedException : Unable to add firewall outbound
	  */
	 public void addOutboundFirewall(FirewallOutbound outbound) throws DataBaseOperationFailedException{
		 logger.debug(".addOutboundFirewall method of FirewallDAO");
		 DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		 Connection connection=null;
		 PreparedStatement pstmt=null;
		 try {
			connection=connectionFactory.getConnection(MYSQL_DB);
			pstmt=(PreparedStatement) connection.prepareStatement(ADD_FIREWALL_OUTBOUND_QUERY);
			pstmt.setString(1, outbound.getOut_type());
			pstmt.setString(2, outbound.getOut_protocol());
			pstmt.setString(3, outbound.getOut_portrange());
			pstmt.setString(4, outbound.getOut_source());
			pstmt.setString(5, outbound.getOut_ip());
			pstmt.setString(6, outbound.getOut_name());
			pstmt.executeUpdate();
			logger.debug("Successfully added firewall outbound in db with data : "+outbound);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			logger.error("Unable to add firewall outbound into db : "+outbound);
			throw new DataBaseOperationFailedException("Unable to add firewall outbound into db : "+outbound,e);
		}finally{
			DataBaseHelper.dbCleanUp(connection, pstmt);
		}
	 }//end of addOutboundFirewall
	 
	 /**
	  * This method is used to get all the firewall outbound data
	  * @return List<FirewallOutbound> : List of all FirewallOutbound object to get outbound data
	 * @throws DataBaseOperationFailedException : Unable to get Firewall outbound data
	  */
	 public List<FirewallOutbound> getAllFirewallOutbound() throws DataBaseOperationFailedException{
		 logger.debug(".getAllFirewallOutbound method of FirewallDAO");
		 DataBaseConnectionFactory connectionFactory =new DataBaseConnectionFactory();
		 List<FirewallOutbound> outboundList=new ArrayList<>();
		 Connection connection=null;
		 Statement stmt=null;
		 ResultSet result=null;
		 try {
			connection=connectionFactory.getConnection(MYSQL_DB);
			stmt=connection.createStatement();
			result=stmt.executeQuery(GET_ALL_FIREWALL_OUTBOUND_QUERY);
			if(result !=null){
				while(result.next()){
					String out_type=result.getString("out_type");
					String out_protocol=result.getString("out_protocol");
					String out_portrange=result.getString("out_portrange");
					String out_source=result.getString("out_source");
					String out_ip=result.getString("out_ip");
					String out_name=result.getString("out_name");
					logger.debug("out firewall type : "+out_type+", out_protocol : "+out_protocol+", out port range : "+out_portrange);
					FirewallOutbound outbound=new FirewallOutbound(out_name, out_type, out_protocol, out_portrange, out_source, out_ip);
					outboundList.add(outbound);
				}
			}else{
				logger.debug("No firewall outbound data available in db");
			}
		} catch (ClassNotFoundException | IOException e) {
			logger.error("Unable to get firewall outbound from db ");
			throw new DataBaseOperationFailedException("Unable to get firewall outbound from db  ",e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Unable to get firewall outbound from db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Unable to get firewall outbound from db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Unable to get firewall outbound from db  ",e);
		}
		 return outboundList;
	 }//end of method getAllFirewallOutbound
	 
	 /**
	  * This method is used to update firewall outbound using name
	  * @param outbound : Firewall Outbound object need to be updated using name
	  * @throws DataBaseOperationFailedException : Unable to update Firewall Outbound using name
	  */
	 public void updateOutboundFirewall(FirewallOutbound outbound) throws DataBaseOperationFailedException{
		 logger.debug(".updateOutboundFirewall method of FirewallDAO");
		 DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		 Connection connection=null;
		 PreparedStatement pstmt=null;
		 try {
			connection=connectionFactory.getConnection(MYSQL_DB);
			pstmt=(PreparedStatement) connection.prepareStatement(UPDATE_FIREWALL_OUTBOUND_BY_NAME_QUERY);
			pstmt.setString(1, outbound.getOut_type());
			pstmt.setString(2, outbound.getOut_protocol());
			pstmt.setString(3, outbound.getOut_portrange());
			pstmt.setString(4, outbound.getOut_source());
			pstmt.setString(5, outbound.getOut_ip());
			pstmt.setString(6, outbound.getOut_name());
			pstmt.executeUpdate();
			logger.debug("Successfully updated firewall outbound in db with data : "+outbound);
		} catch (ClassNotFoundException | IOException e) {
			logger.error("Unable to update firewall outbound into db : "+outbound);
			throw new DataBaseOperationFailedException("Unable to update firewall outbound into db : "+outbound,e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Unable to update firewall outbound into db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Unable to update firewall outbound into db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Unable to update firewall outbound into db : "+outbound,e);
		} finally{
			DataBaseHelper.dbCleanUp(connection, pstmt);
		}
	 }//end of method updateOutboundFirewall
	 
	 /**
	  * This method is used to delete firewall from db using outbound name
	  * @param outboundName : Outbound name in String to delete data from db
	  * @throws DataBaseOperationFailedException : Error in deleting the firewall outbound from db using outbound name
	  */ 
	 public void deleteFirewallOutboundByName(String outboundName) throws DataBaseOperationFailedException{
		 logger.debug(".deleteFirewallOutboundByName method of FirewallDAO");
		 DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		 Connection connection=null;
		 PreparedStatement stmt=null;
		 try {
			connection=connectionFactory.getConnection(MYSQL_DB);
			stmt=(PreparedStatement) connection.prepareStatement(DELETE_FIREWALL_OUTBOUND_BY_NAME_QUERY);
		    stmt.setString(1,outboundName);
			stmt.executeUpdate();
			logger.debug("Firewall outbound is successfull deleted for : "+outboundName);
		} catch (ClassNotFoundException | IOException e) {
			logger.error("Unable to delete firewall outbound from db  for name : "+outboundName);
			throw new DataBaseOperationFailedException("Unable to get firewall outbound from db  for name : "+outboundName,e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Unable to delete firewall outbound from db  for name because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Unable to delete firewall outbound from db  for name because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Unable to get firewall outbound from db  for name : "+outboundName,e);
		} finally{
			DataBaseHelper.dbCleanUp(connection, stmt);
			
		}
	 }//end of method deleteFirewallOutboundByName
	 
	 /**
	  * This method is used to get all firewall types available in db 
	  * @return List<String> : List of all firewall types in String
	  * @throws DataBaseOperationFailedException : Unable to get all firewall types from db
	  */
	 public List<String> getFirewallTypes() throws DataBaseOperationFailedException{
		 logger.debug(".getFirewallTypes method of FirewallDAO");
		 DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		 List<String> firewallTypeList=new ArrayList<>();
		 Connection connection=null;
		 Statement stmt=null;
		 ResultSet result=null;
		  try {
			connection=connectionFactory.getConnection(MYSQL_DB);
			stmt=connection.createStatement();
			result=stmt.executeQuery(GET_ALL_FIREWALL_TYPES_QUERY);
			if(result!=null){
				while(result.next()){
					String type=result.getString("type");
					firewallTypeList.add(type);
				}
				logger.debug("list of firewall types : "+firewallTypeList);
			}else{
				logger.debug("No firewall types avaialble in database");
			}
		} catch (ClassNotFoundException | IOException e) {
			logger.error("Unable to fetch all firewall types");
			throw new DataBaseOperationFailedException("Unable to fetch all firewall types",e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Unable to delete firewall outbound from db  for name because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Unable to delete firewall outbound from db  for name because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Unable to fetch all firewall types",e);
		} finally{
			DataBaseHelper.dbCleanup(connection, stmt, result);
		}
		 return firewallTypeList;
	 }//end of method getFirewallTypes
	 
	 /**
	  * This method is used to delete firewall types available in db using type
	  * @param type : firewall type need to be delete
	  * @throws DataBaseOperationFailedException : Unable to delete firewall types from db
	  */
	 public List<String> deleteFirewallTypes(String type) throws DataBaseOperationFailedException{
		 logger.debug(".deleteFirewallTypes method of FirewallDAO");
		 DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		 List<String> firewallTypeList=new ArrayList<>();
		 Connection connection=null;
		 PreparedStatement pstmt=null;
		 try {
			connection=connectionFactory.getConnection(MYSQL_DB);
			pstmt=(PreparedStatement) connection.prepareStatement(DELETE_FIREWALL_TYPE_QUERY);
			pstmt.setString(1,type);
			pstmt.executeUpdate();
			logger.debug("firewalll type : "+type+" delete successfully from db");
		} catch (ClassNotFoundException | IOException e) {
			logger.error("Unable to delete  firewall types : "+type);
			throw new DataBaseOperationFailedException("Unable to fetch all firewall types "+type,e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Unable to delete  firewall types because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Unable to delete  firewall types because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Unable to fetch all firewall types "+type,e);
		} finally{
			DataBaseHelper.dbCleanUp(connection, pstmt);;
		}
		 return firewallTypeList;
	 }//end of method deleteFirewallTypes
	 
	 
	 /**
	  * This method is used to gell ALl firewall protocol from db
	  * @return List<String>: List of all firewall protocol in STring
	  * @throws DataBaseOperationFailedException : Unable to get protocol type from db
	  */
	 public  List<String> getALLProtocolTypes() throws DataBaseOperationFailedException{
		 logger.debug(".getALLProtocolTypes method of FirewallDAO");
		 DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		 List<String> protocolTypeList=new ArrayList<>();
		 Connection connection=null;
		 Statement stmt=null;
		 ResultSet result=null;
		 try {
			connection=connectionFactory.getConnection(MYSQL_DB);
			stmt=connection.createStatement();
			result=stmt.executeQuery(GET_ALL_FIREWALL_PROTOCOL_QUERY);
			if(result !=null){
				while(result.next()){
					String protocol_type=result.getString("protocol_type");
					protocolTypeList.add(protocol_type);
				}
			}else{
				logger.debug("No firewall protocol available in db");
			}
		} catch (ClassNotFoundException | IOException e) {
			logger.error("Error in getting the protocol from db");
			throw new DataBaseOperationFailedException("Error in getting the protocol from db",e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Error in getting the protocol from db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Error in getting the protocol from db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Error in getting the protocol from db",e);
		} finally{
			DataBaseHelper.dbCleanup(connection, stmt, result);
		}
		 return protocolTypeList;
	 }//end of method getALLProtocolTypes
	 
	 /**
	  * This method is used to delete firewall protocol from db
	  * @return protocolType: List of all firewall protocol in STring
	  * @throws DataBaseOperationFailedException : Unable to get protocol type from db
	  */
	 public  void deleteProtocolTypes(String protocolType) throws DataBaseOperationFailedException{
		 logger.debug(".deleteProtocolTypes method of FirewallDAO");
		 DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		 Connection connection=null;
		 Statement stmt=null;		 
		 try {
			connection=connectionFactory.getConnection(MYSQL_DB);
			stmt=connection.createStatement();
			stmt.executeUpdate(GET_ALL_FIREWALL_PROTOCOL_QUERY);			
			logger.debug("No firewall protocol available in db");			
		} catch (ClassNotFoundException | IOException e) {
			logger.error("Error in getting the protocol from db");
			throw new DataBaseOperationFailedException("Error in getting the protocol from db",e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Error in getting the protocol from db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Error in getting the protocol from db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Error in getting the protocol from db",e);
		} finally{
			DataBaseHelper.dbCleanUp(connection, stmt);
		}		
	 }//end of method deleteProtocolTypes
	 
	 
	 /**
	  * This method is used to insert firewall inbound in db
	  * @param inbound : Firewall Inbound Object contains data need to be inserted in db
	  * @throws DataBaseOperationFailedException
	  */
	 public void insertFirewallInbound(FirewallInbound inbound) throws DataBaseOperationFailedException{
		 logger.debug(".insertFirewallInbound method of FirewallDAO");
		 DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		 Connection connection=null;
		 PreparedStatement pstmt=null;
		 try {
			connection=connectionFactory.getConnection(MYSQL_DB);
			pstmt=(PreparedStatement) connection.prepareStatement(INSERT_FIREWALL_INBOUND_QUERY);
			pstmt.setString(1, inbound.getIn_type());
			pstmt.setString(2, inbound.getIn_protocol());
			pstmt.setString(3, inbound.getIn_portrange());
			pstmt.setString(4, inbound.getIn_source());
			pstmt.setString(5, inbound.getIn_ip());
			pstmt.setString(6, inbound.getIn_name());
			pstmt.executeUpdate();
			logger.debug("firewall Inbound : "+inbound+" inserted in db successfully");
		} catch (ClassNotFoundException | IOException e) {
			logger.error("Error in inserting firewall inbound in db "+inbound);
			throw new DataBaseOperationFailedException("Unable to insert firewall inbound in db "+inbound,e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Error in inserting firewall inbound in db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Error in inserting firewall inbound in db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Unable to insert firewall inbound in db "+inbound,e);
		} finally{
			DataBaseHelper.dbCleanUp(connection, pstmt);
		}

	 }//end of method insertFirewallInbound
	 
	 
	 /**
	  * This method is used to get all firewall inbound from db
	  * @return List<FirewallInbound> : List of Inbound Object containg data for inbounds
	  * @throws DataBaseOperationFailedException : Unable to get firewall inbounds data
	  */
	 public List<FirewallInbound>  getAllFirewallInbound() throws DataBaseOperationFailedException{
		 logger.debug(".getAllFirewallInbound method of FirewallDAO");
		 DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		 List<FirewallInbound> inboundList=new ArrayList<>();
		 Connection connection=null;
		 Statement stmt=null;
		 ResultSet result=null;
		 try {
			connection=connectionFactory.getConnection(MYSQL_DB);
			stmt=connection.createStatement();
			result=stmt.executeQuery(GET_ALL_FIREWALL_INBOUND_QUERY);
			if(result !=null){
				while(result.next()){
					String in_type=result.getString("in_type");
					String in_protocol=result.getString("in_protocol");
					String in_portrange=result.getString("in_portrange");
					String in_source=result.getString("in_source");
					String in_ip=result.getString("in_ip");
					String in_name=result.getString("in_name");
					logger.debug("type: "+in_type+", protocol : "+in_protocol+", port range : "+in_portrange+", source: "+in_source+", destination : "+in_ip+", name : "+in_name);
					FirewallInbound inbound=new FirewallInbound(in_name, in_type, in_protocol, in_portrange, in_source, in_ip);
					inboundList.add(inbound);
				}
			}else{
				logger.debug("No firewall Inbounds available in db");
			}
		} catch (ClassNotFoundException | IOException e) {
			logger.error("Error in getting firewall inbound data from db");
			throw new DataBaseOperationFailedException("Unable to fetch firewall inbound from db ",e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Error in getting firewall inbound data from db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Error in getting firewall inbound data from db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Unable to fetch firewall inbound from db ",e);
		} finally{
			DataBaseHelper.dbCleanup(connection, stmt, result);
		}
		return inboundList;		 
	 }//end of method GetAllFirewallInbound
	 
	 /**
	  * This method is used to update firewall inbound using name
	  * @param inbound : Firewall Inbound Object need to be updated in db using name
	  * @throws DataBaseOperationFailedException : Unable to update firewall inbound using name
	  */
	 public void updateFirewallInbound(FirewallInbound inbound) throws DataBaseOperationFailedException{
		 logger.debug(".updateFirewallInbound method of FirewallDAO");
		 DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		 Connection connection=null;
		 PreparedStatement pstmt=null;
		 try {
			connection=connectionFactory.getConnection(MYSQL_DB);
			pstmt=(PreparedStatement) connection.prepareStatement(UPDATE_FIREWALL_INBOUND_BY_NAME_QUERY);
			pstmt.setString(1, inbound.getIn_type());
			pstmt.setString(2, inbound.getIn_protocol());
			pstmt.setString(3, inbound.getIn_portrange());
			pstmt.setString(4, inbound.getIn_source());
			pstmt.setString(5, inbound.getIn_ip());
			pstmt.setString(6, inbound.getIn_name());
			pstmt.executeUpdate();
			logger.debug("firewall Inbound : "+inbound+" inserted in db successfully");
		} catch (ClassNotFoundException | IOException e) {
			logger.error("Error in updating firewall inbound in db "+inbound);
			throw new DataBaseOperationFailedException("Unable to updating firewall inbound in db "+inbound,e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Error in updating firewall inbound in db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Error in updating firewall inbound in db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Unable to updating firewall inbound in db "+inbound,e);
		} finally{
			DataBaseHelper.dbCleanUp(connection, pstmt);
		}
	 }//end of method updateFirewallInbound
	 
	 /**
	  * This method is used to delete firewall inbound from db using inbound name
	  * @param inboundName : inbound name in String to delete data from db
	  * @throws DataBaseOperationFailedException : Error in deleting the firewall inbound from db using inbound name
	  */ 
	 public void deleteFirewallInboundByName(String inboundName) throws DataBaseOperationFailedException{
		 logger.debug(".deleteFirewallInboundByName method of FirewallDAO");
		 DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		 Connection connection=null;
		 PreparedStatement stmt=null;
		 try {
			connection=connectionFactory.getConnection(MYSQL_DB);
			stmt=(PreparedStatement) connection.prepareStatement(DELETE_FIREWALL_INBOUND_BY_NAME_QUERY);
		    stmt.setString(1,inboundName);
			stmt.executeUpdate();
			logger.debug("Firewall inboundName is successfull deleted for : "+inboundName);
		} catch (ClassNotFoundException | IOException e) {
			logger.error("Unable to delete firewall inboundName from db  for name : "+inboundName);
			throw new DataBaseOperationFailedException("Unable to get firewall inboundName from db  for name : "+inboundName,e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Unable to delete firewall inboundName from db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Unable to delete firewall inboundName from db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Unable to get firewall inboundName from db  for name : "+inboundName,e);
		} finally{
			DataBaseHelper.dbCleanUp(connection, stmt);
			
		}
	 }//end of method deleteFirewallInboundByName

}
