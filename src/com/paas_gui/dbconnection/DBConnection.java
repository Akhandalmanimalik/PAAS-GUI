package com.paas_gui.dbconnection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

public class DBConnection {

	private static final Logger log = Logger.getLogger(DBConnection.class);
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			log.info(e.getMessage());
			
		}
	}

	public static Connection getConnection() throws SQLException {
		//String url = "jdbc:mysql://192.168.1.200:3306/bizruntime";
		String url = "jdbc:mysql://localhost:3306/pass_overlay";
		Connection conn = DriverManager.getConnection(url, "root", "root");
		return conn;
	}

	public static void cleanup(Statement st, Connection conn) {
		try {
			if (st != null)
				st.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void cleanup(ResultSet rs, Statement st, Connection conn) {
		try {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		
		
		try {
			DBConnection.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}