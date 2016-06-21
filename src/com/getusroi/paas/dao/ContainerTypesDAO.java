package com.getusroi.paas.dao;

import static com.getusroi.paas.helper.PAASConstant.MYSQL_DB;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getusroi.paas.db.helper.DataBaseConnectionFactory;
import com.getusroi.paas.db.helper.DataBaseHelper;
import com.getusroi.paas.helper.PAASConstant;
import com.getusroi.paas.helper.PAASErrorCodeExceptionHelper;
import com.mysql.jdbc.PreparedStatement;

/**
 * this class contains all DAO operation of Policies page
 * @author bizruntime
 *
 */
public class ContainerTypesDAO {

	private static final Logger logger = LoggerFactory.getLogger(ContainerTypesDAO.class);
	public static final String GET_CONTAINER_ID_BY_CONTAINER_NAME_QUERY = "SELECT id FROM container_type where container_type=?";
	
	public static final String GET_CONTAINER_NAME_BY_CONTIANER_ID = "SELECT * FROM container_type where id=?";

	
	/**
	 * this method is used to get all data from container_type table
	 * @return : it return list of data from container_table
	 * @throws DataBaseOperationFailedException : Unable to fetch data from db
	 */
	public Integer getContainerTypeIdByContainerName(String containerName) throws DataBaseOperationFailedException {
		logger.debug(".getContainerTypeIdByContainerName (.) of ContainerTypesDAO");
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
			logger.error("Unable to get container types from db ");
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
	 * to get container type name by 
	 * @param containerTypeID
	 * @return
	 * @throws DataBaseOperationFailedException
	 */
	public String getContainerNameByContainerId(int containerTypeID) throws DataBaseOperationFailedException {
		logger.debug(".getContainerTypeIdByContainerName (.) of ContainerTypesDAO");
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
			logger.error("Unable to get container types from db ");
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
}
























