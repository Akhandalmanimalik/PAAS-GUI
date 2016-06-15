package com.getusroi.paas.db.helper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * This class is for database helper class
 * @author bizruntime
 *
 */
public class DataBaseHelper {
	private static String URL = null;
	private static String DRIVER_CLASS = null;
	private static String USER = null;
	private static String PASSWORD = null;
	private static String MYSQL_CONFIG_FILE_KEY="paasConfigartionDB.properties";
	/**
	 * This method is used to get the MySQL Connection
	 * @return JDBCconnection
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public static Connection getMySQLConnection() throws ClassNotFoundException, SQLException, IOException {

		if (URL == null) {
			loadConfigrationDbPropertyFile(MYSQL_CONFIG_FILE_KEY);
		}
		Class.forName(DRIVER_CLASS);
		Connection connection = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
		return connection;
	}
	
	/**
	 * This method is used to get the Oracle Connection
	 * Note: This support is not yet provided
	 * @return JDBCconnection
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public static Connection getOrcaleConnection() throws ClassNotFoundException, SQLException, IOException {

		if (URL == null) {
			loadConfigrationDbPropertyFile("paasConfigartionDB.properties");
		}
		Class.forName(DRIVER_CLASS);
		Connection connection = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
		return connection;
	}

	public static void dbCleanup(Connection con, PreparedStatement ptst, ResultSet rs) {
		close(con);
		close(ptst);
		close(rs);
	}
	public static void dbCleanup(Connection con,Statement stmt, ResultSet rs) {
		close(con);
		close(stmt);
		close(rs);
	}

	public static void dbCleanUp(Connection conn, PreparedStatement ps) {
		close(conn);
		close(ps);
	}
	
	public static void dbCleanUp(Connection conn, Statement stmt) {
		close(conn);
		close(stmt);
	}

	public static void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException sqlexp) {
				sqlexp.printStackTrace();
			}
		}
	}

	public static void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException sqlexp) {
				sqlexp.printStackTrace();
			}
		}
	}		
	
	public static void close(PreparedStatement pStatement) {
		if (pStatement != null) {
			try {
				pStatement.close();
			} catch (SQLException sqlexp) {
				sqlexp.printStackTrace();
			}
		}
	}

	public static void close(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException sqlexp) {
				sqlexp.printStackTrace();
			}
		}
	}


	private synchronized static void loadConfigrationDbPropertyFile(String dbFileName) throws IOException {
		
		Properties properties = new Properties();
		properties.load(DataBaseHelper.class.getClassLoader().getResourceAsStream(dbFileName));
		URL = properties.getProperty("DB_URL");
		DRIVER_CLASS = properties.getProperty("DB_DRIVER_CLASS");
		USER = properties.getProperty("DB_USER");
		PASSWORD = properties.getProperty("DB_PASSWORD");

	}
	
}

