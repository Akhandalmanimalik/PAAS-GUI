package com.getusroi.paas.dao;

import static com.getusroi.paas.helper.PAASConstant.MYSQL_DB;

import java.io.IOException;
import java.sql.Connection;
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
import com.getusroi.paas.vo.ContainerTypes;
import com.mysql.jdbc.PreparedStatement;

/**
 * this class contains all DAO operation of Policies page
 * @author bizruntime
 *
 */
public class ContainerTypesDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContainerTypesDAO.class);
	
	private static final String INSERT_CONTAINER_TYPES_QUERY = "INSERT INTO container_type (container_type,memory,description,tenan_id,createdDTM) VALUES(?,?,?,?,NOW())";
	public static final String GET_CONTAINER_ID_BY_CONTAINER_NAME_QUERY = "SELECT id FROM container_type where container_type=?";
	public static final String GET_CONTAINERS_BY_CONTAINER_NAME_QUERY = "SELECT * FROM container_type where container_type=? and tenan_id=?";

	public static final String GET_CONTAINER_NAME_BY_CONTIANER_ID = "SELECT * FROM container_type where id=?";
	private static final String SELECT_ALL_CONTAINER_TYPE_QUERY = "SELECT * FROM container_type";
	private static final String GET_CONTAINER_TYPE_BY_TENANT_ID_QUERY = "SELECT * FROM container_type where tenan_id=?";
	private static final String GET_CONTAINER_TYPE_NAME_BY_ID_AND_TENANT_ID_QUERY = "SELECT container_type FROM container_type where id=? and tenan_id=?";
	public static final String UPDATE_CONTAINER_TYPE_BYID="UPDATE  container_type set container_type=?,description =?,memory=? where id=?";
	private static final String REMOVE_CONTAINER_TYPES_BY_ID = "DELETE FROM container_type WHERE id = ?";
	
	/**
	 * this method is used to get all data from container_type table
	 * @return : it return list of data from container_table
	 * @throws DataBaseOperationFailedException : Unable to fetch data from db
	 */
	public Integer getContainerTypeIdByContainerName(String containerName) throws DataBaseOperationFailedException {
		LOGGER.debug(".getContainerTypeIdByContainerName (.) of ContainerTypesDAO");
		DataBaseConnectionFactory dataBaseConnectionFactory = new DataBaseConnectionFactory();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Integer containerTypeId = null;
		try {
			connection = dataBaseConnectionFactory.getConnection(MYSQL_DB);
			preparedStatement = (PreparedStatement) connection.prepareStatement(GET_CONTAINER_ID_BY_CONTAINER_NAME_QUERY);
			preparedStatement.setString(1, containerName);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				containerTypeId=resultSet.getInt("id");
			}

		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Unable to get container types from db ");
			throw new DataBaseOperationFailedException("Unable to get container types from db",e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Unable to get container types from db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Unable to get container types from db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else {
				throw new DataBaseOperationFailedException("Unable to get container types from db ", e);
			}
		} finally {
			DataBaseHelper.dbCleanup(connection, preparedStatement, resultSet);
		}

		return containerTypeId;
	} // end of getAllContainerTypesData
	
	/**
	 * this method is used to get all data from container_type table
	 * @return : it return list of data from container_table
	 * @throws DataBaseOperationFailedException : Unable to fetch data from db
	 */
	public List<ContainerTypes> getContainerTypeIdByName(String containerName,int id) throws DataBaseOperationFailedException {
		LOGGER.debug(".getContainerTypeIdByName (.) of ContainerTypesDAO");
		DataBaseConnectionFactory dataBaseConnectionFactory = new DataBaseConnectionFactory();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<ContainerTypes> containerTypesList = new ArrayList<ContainerTypes>();

		try {
			connection = dataBaseConnectionFactory.getConnection(MYSQL_DB);
			preparedStatement = (PreparedStatement) connection.prepareStatement(GET_CONTAINERS_BY_CONTAINER_NAME_QUERY);
			preparedStatement.setString(1, containerName);
			preparedStatement.setInt(2, id);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				ContainerTypes containerTypes = new ContainerTypes();
				containerTypes.setId(resultSet.getInt("id"));
				containerTypes.setName(resultSet.getString("container_type"));
				containerTypes.setMemory(resultSet.getInt("memory"));
				containerTypes.setTenantId(resultSet.getInt("tenan_id"));
				containerTypes.setDescription(resultSet.getString("description"));
				containerTypesList.add(containerTypes);
			}

		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Unable to get container types from db ");
			throw new DataBaseOperationFailedException("Unable to get container types from db",e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Unable to get container types from db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Unable to get container types from db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else {
				throw new DataBaseOperationFailedException("Unable to get container types from db ", e);
			}
		} finally {
			DataBaseHelper.dbCleanup(connection, preparedStatement, resultSet);
		}

		return containerTypesList;
	} // end of getAllContainerTypesData
	
	/**
	 * to get container type name by 
	 * @param containerTypeID
	 * @return
	 * @throws DataBaseOperationFailedException
	 */
	public String getContainerNameByContainerId(int containerTypeID) throws DataBaseOperationFailedException {
		LOGGER.debug(".getContainerTypeIdByContainerName (.) of ContainerTypesDAO");
		DataBaseConnectionFactory dataBaseConnectionFactory = new DataBaseConnectionFactory();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String  containerType = "";
		try {
			connection = dataBaseConnectionFactory.getConnection(MYSQL_DB);
			preparedStatement = (PreparedStatement) connection.prepareStatement(GET_CONTAINER_NAME_BY_CONTIANER_ID);
			preparedStatement.setInt(1, containerTypeID);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				containerType=resultSet.getString("container_type");
			}

		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Unable to get container types from db ");
			throw new DataBaseOperationFailedException("Unable to get container types from db",e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Unable to get container types from db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Unable to get container types from db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else {
				throw new DataBaseOperationFailedException("Unable to get container types from db ", e);
			}
		} finally {
			DataBaseHelper.dbCleanup(connection, preparedStatement, resultSet);
		}

		return containerType;
	} // end of getAllContainerTypesData
	
	
	
	
	public int updateContainerType(ContainerTypes containerTypes) throws DataBaseOperationFailedException {
		LOGGER.debug("(.)updateContainerType method of ContainerTypesDAO");
		DataBaseConnectionFactory dataBaseConnectionFactory = new DataBaseConnectionFactory();
		Connection connection = null;
		PreparedStatement pStatement = null;
		int updatedContainer=0;
		try {
			connection = dataBaseConnectionFactory.getConnection(MYSQL_DB);
			pStatement = (PreparedStatement) connection.prepareStatement(UPDATE_CONTAINER_TYPE_BYID);
			pStatement.setString(1, containerTypes.getName());
			pStatement.setString(2, containerTypes.getDescription());
			pStatement.setInt(3, containerTypes.getMemory());
			pStatement.setInt(4, containerTypes.getId());

		 updatedContainer =	pStatement.executeUpdate();
		 LOGGER.debug("updatedContainer : "+updatedContainer);
		 LOGGER.debug("EnvironmentType Data is Updated");

		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Unable to update Container type into db with data: " + containerTypes);
			throw new DataBaseOperationFailedException(
					"Unable to update Container type into db with data : " + containerTypes, e);
		
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Unable to update data into Containertype because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Unable to update data into Containertype because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException(
						"Unable to update Container type into db with data : " + containerTypes, e);
		} finally {
			DataBaseHelper.dbCleanUp(connection, pStatement);
		}
		return updatedContainer;
	} // end of insertEnvironmentType method

	/**
	 * this is used to insert all values of container type into db
	 * @param containerTypes : list of values which is going to store into db
	 * @return : true if data inserted successfully
	 * @throws DataBaseOperationFailedException : Unable to store data into db
	 */
	public boolean insertContainerType(ContainerTypes containerTypes) throws DataBaseOperationFailedException {
		LOGGER.debug(".insertContainerType method of PoliciesDAO");
		DataBaseConnectionFactory dataBaseConnectionFactory = new DataBaseConnectionFactory();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataBaseConnectionFactory.getConnection(MYSQL_DB);
			preparedStatement = (PreparedStatement) connection.prepareStatement(INSERT_CONTAINER_TYPES_QUERY);
			preparedStatement.setString(1, containerTypes.getName());
			preparedStatement.setInt(2, containerTypes.getMemory());
			preparedStatement.setString(3, containerTypes.getDescription());
			preparedStatement.setInt(4, containerTypes.getTenantId());
			
			preparedStatement.executeUpdate();
						
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Unable to insert container type into db ");
			throw new DataBaseOperationFailedException("Unable to insert container type into db",e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Unable to insert container type into db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Unable to insert container type into db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else {
				throw new DataBaseOperationFailedException("Unable to insert container type into db ", e);
			}
		} finally {
			DataBaseHelper.dbCleanUp(connection, preparedStatement);
		}
		return true;
	} //end of insertContainerType
	
	
	/**
	 * this method is used to get all data from container_type table
	 * @return : it return list of data from container_table
	 * @throws DataBaseOperationFailedException : Unable to fetch data from db
	 */
	public List<ContainerTypes> getAllContainerTypesData() throws DataBaseOperationFailedException {
		LOGGER.debug(".getAllContainerTypesData method of PoliciesDAO");
		DataBaseConnectionFactory dataBaseConnectionFactory = new DataBaseConnectionFactory();
		List<ContainerTypes> containerTypesList = new ArrayList<ContainerTypes>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataBaseConnectionFactory.getConnection(MYSQL_DB);
			preparedStatement = (PreparedStatement) connection.prepareStatement(SELECT_ALL_CONTAINER_TYPE_QUERY);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				ContainerTypes containerTypes = new ContainerTypes();
				containerTypes.setId(resultSet.getInt("id"));
				containerTypes.setName(resultSet.getString("container_type"));
				containerTypes.setMemory(resultSet.getInt("memory"));
				containerTypes.setTenantId(resultSet.getInt("tenan_id"));
				containerTypes.setDescription(resultSet.getString("description"));
				containerTypesList.add(containerTypes);
			}

		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Unable to get container types from db ");
			throw new DataBaseOperationFailedException("Unable to get container types from db",e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Unable to get container types from db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Unable to get container types from db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else {
				throw new DataBaseOperationFailedException("Unable to get container types from db ", e);
			}
		} finally {
			DataBaseHelper.dbCleanup(connection, preparedStatement, resultSet);
		}

		return containerTypesList;
	} // end of getAllContainerTypesData
	
	
	public void removeContainerTypesByName(int id) throws DataBaseOperationFailedException {		
		LOGGER.debug(".removeContainerTypesByName of ContainerTypesDAO id : "+id);
		DataBaseConnectionFactory dataBaseConnectionFactory = new DataBaseConnectionFactory();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataBaseConnectionFactory.getConnection(MYSQL_DB);
			preparedStatement = (PreparedStatement) connection.prepareStatement(REMOVE_CONTAINER_TYPES_BY_ID);
			preparedStatement.setInt(1, id);
			preparedStatement.execute();
			
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Unable to remove container type from db ");
			throw new DataBaseOperationFailedException("Unable to remove container type from db",e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Unable to remove container type from db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Unable to remove container type from db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else {
				throw new DataBaseOperationFailedException("Unable to remove container type from db ", e);
			}
		} finally {
			DataBaseHelper.dbCleanUp(connection, preparedStatement);
		}
		
	} // end of removeContainerTypesByName
	
	
	
//	 = "SELECT  FROM container_type where id=? and tenan_id=?";
	/**
	 * this method is used to get all data from container_type table
	 * @return : it return list of data from container_table
	 * @throws DataBaseOperationFailedException : Unable to fetch data from db
	 */
	public String getContainerTypeNameById(int contnrID,int tenantId) throws DataBaseOperationFailedException {
		LOGGER.debug(".getAllContainerTypesByTenantId (.) of PoliciesDAO");
		DataBaseConnectionFactory dataBaseConnectionFactory = new DataBaseConnectionFactory();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String containerTypeName=null;
		try {
			connection = dataBaseConnectionFactory.getConnection(MYSQL_DB);
			preparedStatement = (PreparedStatement) connection.prepareStatement(GET_CONTAINER_TYPE_NAME_BY_ID_AND_TENANT_ID_QUERY);
			preparedStatement.setInt(1, contnrID);
			preparedStatement.setInt(2, tenantId);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet != null && resultSet.next()) {
				containerTypeName=resultSet.getString("container_type");
			}
		
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Unable to get container types from db ");
			throw new DataBaseOperationFailedException("Unable to get container types from db",e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Unable to get container types from db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Unable to get container types from db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else {
				throw new DataBaseOperationFailedException("Unable to get container types from db ", e);
			}
		} finally {
			DataBaseHelper.dbCleanup(connection, preparedStatement, resultSet);
		}

		return containerTypeName;
	} // end of getAllContainerTypesData
	
	/**
	 * this method is used to get all data from container_type table
	 * @return : it return list of data from container_table
	 * @throws DataBaseOperationFailedException : Unable to fetch data from db
	 */
	public List<ContainerTypes> getAllContainerTypesByTenantId(int tenantId) throws DataBaseOperationFailedException {
		LOGGER.debug(".getAllContainerTypesByTenantId (.) of PoliciesDAO");
		DataBaseConnectionFactory dataBaseConnectionFactory = new DataBaseConnectionFactory();
		List<ContainerTypes> containerTypesList = new ArrayList<ContainerTypes>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataBaseConnectionFactory.getConnection(MYSQL_DB);
			preparedStatement = (PreparedStatement) connection.prepareStatement(GET_CONTAINER_TYPE_BY_TENANT_ID_QUERY);
			preparedStatement.setInt(1, tenantId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				ContainerTypes containerTypes = new ContainerTypes();
				containerTypes.setName(resultSet.getString("container_type"));
				containerTypes.setMemory(resultSet.getInt("memory"));
				containerTypes.setDescription(resultSet.getString("description"));
				containerTypesList.add(containerTypes);
			}

		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Unable to get container types from db ");
			throw new DataBaseOperationFailedException("Unable to get container types from db",e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Unable to get container types from db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Unable to get container types from db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else {
				throw new DataBaseOperationFailedException("Unable to get container types from db ", e);
			}
		} finally {
			DataBaseHelper.dbCleanup(connection, preparedStatement, resultSet);
		}

		return containerTypesList;
	} // end of getAllContainerTypesData
	
	
}
























