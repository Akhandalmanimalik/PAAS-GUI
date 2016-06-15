package com.getusroi.paas.dao;

import static com.getusroi.paas.helper.PAASConstant.MYSQL_DB;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getusroi.paas.db.helper.DataBaseConnectionFactory;
import com.getusroi.paas.db.helper.DataBaseHelper;
import com.getusroi.paas.helper.PAASConstant;
import com.getusroi.paas.helper.PAASErrorCodeExceptionHelper;
import com.getusroi.paas.vo.Storage;
import com.mysql.jdbc.PreparedStatement;

public class StorageDAO {

	private static Logger logger = LoggerFactory.getLogger(StorageDAO.class);
	private static final String GET_ALL_STORAGE_QUERY = "SELECT * FROM storage";
	private static final String UPDATE_STORAGE_QUERY = "UPDATE storage SET servicename = ?, tage = ?, volumesize = ? WHERE servicename = ? ";
	
	/**
	 * this method is used to get all data from storage table
	 * @return : it return the list of of storage object
	 * @throws DataBaseOperationFailedException : Unable to fetch data from db
	 */
	public List<Storage> getAllStorage() throws DataBaseOperationFailedException {
		logger.debug(".getAllStorage of StorageDAO");
		DataBaseConnectionFactory connectionFactory = new DataBaseConnectionFactory();
		List<Storage> storageList = new LinkedList<Storage>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = connectionFactory.getConnection(MYSQL_DB);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(GET_ALL_STORAGE_QUERY);
			while(resultSet != null) {
				Storage storage = new Storage();
				storage.setServiceName(resultSet.getString("servicename"));
				storage.setTag(resultSet.getString("tag"));
				storage.setVolumeSize(resultSet.getString("volumesize"));
				storageList.add(storage);
			}
			
		} catch (ClassNotFoundException | IOException e) {
			logger.error("Unable to fetch storage data from db ");
			throw new DataBaseOperationFailedException("Unable to fetch storage data from db ", e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Unable to fetch storage data from db because " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Unable to fetch storage data from db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Unable to fetch storage data from db ", e);
		}finally {
			DataBaseHelper.dbCleanup(connection, statement, resultSet);
		}
		return storageList;
	} // end of getAllStorage
	
	/**
	 * this method is used to update the storage table 
	 * @param storage : it contains all values of storage table
	 * @param serviceName : based on this key data will be updated
	 * @throws DataBaseOperationFailedException : Unable to update data into db
	 */
	public void updateStorage(Storage storage, String serviceName) throws DataBaseOperationFailedException {
		logger.debug(".getAllStorage of StorageDAO");
		DataBaseConnectionFactory connectionFactory = new DataBaseConnectionFactory();
		Connection connection = null;
		PreparedStatement pStatement = null;
		
		try {
			connection = connectionFactory.getConnection(MYSQL_DB);
			pStatement = (PreparedStatement) connection.prepareStatement(UPDATE_STORAGE_QUERY);
			pStatement.setString(1, storage.getServiceName());
			pStatement.setString(2, storage.getTag());
			pStatement.setString(3, storage.getVolumeSize());
			pStatement.setString(4, serviceName);
			
			pStatement.execute();
			
		}  catch (ClassNotFoundException | IOException e) {
			logger.error("Unable to update storage data into db ");
			throw new DataBaseOperationFailedException("Unable to update storage data into db ", e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Unable to update storage data into db because " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Unable to update storage data into db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Unable to update storage data into db ", e);
		} finally {
			DataBaseHelper.dbCleanUp(connection, pStatement);
		}
		
	}
	
}

















