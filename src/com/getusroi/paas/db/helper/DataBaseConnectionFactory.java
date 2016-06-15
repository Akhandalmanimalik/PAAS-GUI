package com.getusroi.paas.db.helper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is get the connection based on the database type passed
 * @author bizruntime
 *
 */
public class DataBaseConnectionFactory {
	 static final Logger logger = LoggerFactory.getLogger(DataBaseConnectionFactory.class);
	public Connection getConnection(String databaseType) throws ClassNotFoundException, SQLException, IOException{
		logger.debug(".getConnection method of DataBaseConnectionFactory");
		Connection conn=null;
		switch (databaseType) {
		case "mysql":conn=DataBaseHelper.getMySQLConnection();
			
			break;
		case "oracle":conn=DataBaseHelper.getOrcaleConnection();
		
		break;
		default:conn=DataBaseHelper.getMySQLConnection();
			break;
		}
		return conn;
	}//end of method getConnection

	
}
