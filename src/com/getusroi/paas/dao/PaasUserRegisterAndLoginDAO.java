package com.getusroi.paas.dao;

import static com.getusroi.paas.helper.PAASConstant.MYSQL_DB;

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
import com.getusroi.paas.security.MD5PasswordEncryption;
import com.getusroi.paas.vo.PaasUserRegister;
import com.mysql.jdbc.PreparedStatement;


/**
 * This is DAO Class to register paas user,get detail of Paas user, update and delete the paas user
 * @author bizruntime
 *
 */
public class PaasUserRegisterAndLoginDAO {
	
	 static final Logger LOGGER = LoggerFactory.getLogger(PaasUserRegisterAndLoginDAO.class);
	 private static final String REGISTER_USER_QUERY="insert into tenant(tenant_name,tenant_email,company_name,company_address,createdDTM) values(?,?,?,?,NOW())";
	 private static final String ALL_REGISTERED_USER_QUERY="select * from tenant";
	 private static final String DELETE_RESGISTERED_USER_BY_EMAIL_COMPANYNAME="delete from tenant where tenant_email=? AND company_name=?";
	 private static final String LOGIN_QUERY="select * from tenant where tenant_email=? AND password=?";
	 private static final String CHECKEMAIL_EXIST_QUERY="select tenant_email from tenant where tenant_email=?";
	 private static final String CHECKEMAIl_AND_PASSWORD_EXIST_QUERY="select * from login where email_id=? and password=?";
	 private static final String INSERT_USER_DETAILS_INTO_LOGIN = "insert into login(tenant_id,admin_id,email_id,password,role_id,createdDTM) values(?,?,?,?,?,NOW())";
	 private static final int ROLE_ID=1;
	 
	public String matchPassward(PaasUserRegister paasUserRegister)
			throws DataBaseOperationFailedException {
		LOGGER.debug(".registerPaasUser method of PaasUserRegisterDAO");
		final String PASSWORD_EXIST_QUERY = "select password from login where password='"
				+ paasUserRegister.getPassword() + "'";
		DataBaseConnectionFactory connectionFactory = new DataBaseConnectionFactory();
		Connection connection = null;
		Statement stmt = null;
		String dbPassword = null;
		try {
			connection = connectionFactory.getConnection(MYSQL_DB);
			stmt = connection.createStatement();

			ResultSet rs = stmt.executeQuery(PASSWORD_EXIST_QUERY);

			System.out.println("rs " + rs);
			while (rs.next()) {
				dbPassword = rs.getString("password");
			}
		} catch (Exception e) {

		}
		return dbPassword;
	}
	/**
	 * This method is used to  register user to paas
	 * @param paasUserRegister : PaasUserRegisterVO Object type contain details require to register the user
	 * @throws DataBaseOperationFailedException 
	 */
	public void registerPaasUser(PaasUserRegister paasUserRegister) throws DataBaseOperationFailedException{
		LOGGER.debug(".registerPaasUser method of PaasUserRegisterDAO");
		DataBaseConnectionFactory connectionFactory = new DataBaseConnectionFactory();
		Connection connection=null;
		PreparedStatement pstmt = null;
		
		int last_inserted_id = 0;
		try {
			connection = connectionFactory.getConnection(MYSQL_DB);			
			pstmt = (PreparedStatement) connection.prepareStatement(REGISTER_USER_QUERY,Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setString(1, paasUserRegister.getTenant_name());
			pstmt.setString(2, paasUserRegister.getEmail());
			pstmt.setString(3, paasUserRegister.getCompany_name());
			pstmt.setString(4, paasUserRegister.getCompany_address());
			 
			pstmt.executeUpdate();
			
			//NEED	 TO USE TRANSACTION FOR CONSISTENCY INSERTION IN BOTH TABLE (tenant,login)
			
			ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next()) {
                 last_inserted_id = rs.getInt(1);
                 LOGGER.debug("last_inserted_id "+last_inserted_id);
            }
           
            pstmt.close();
            pstmt =null;
            //MD5PasswordEncryption md5Encrypt=new MD5PasswordEncryption();
    		
			pstmt = (PreparedStatement) connection.prepareStatement(INSERT_USER_DETAILS_INTO_LOGIN);
			pstmt.setInt(1, last_inserted_id);
			pstmt.setInt(2, last_inserted_id);
			pstmt.setString(3, paasUserRegister.getEmail());
			//String password = md5Encrypt.getMD5EncryptedPassword(paasUserRegister.getPassword());
			//pstmt.setString(4, password);
			pstmt.setString(4, paasUserRegister.getPassword());
			pstmt.setInt(5	, ROLE_ID);
			
			pstmt.executeUpdate();
			LOGGER.debug("Data Inserted into tenant and login successfully");
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Unable to register user to paas");
			throw new DataBaseOperationFailedException("Unable to register user to paas with detail : "+paasUserRegister.toString(),e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Unable to register user to paas because " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Unable to register user to paas because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Unable to register user to paas with detail : "+paasUserRegister.toString(),e);
		} finally{
			DataBaseHelper.dbCleanUp(connection, pstmt);			
		}		

	}// end of method registerPaasUser
	
	/**
	 * This method is used to  get all user registered to paas
	 * @return List<PaasUserRegisterVO> : List of all Pass User Register
	 * @throws DataBaseOperationFailedException 
	 */
	public List<PaasUserRegister> getAllPaasUser() throws DataBaseOperationFailedException{
		LOGGER.debug(".getAllPaasUser method of PaasUserRegisterDAO");
		DataBaseConnectionFactory connectionFactory = new DataBaseConnectionFactory();
		List<PaasUserRegister> paasUserList=new ArrayList<>();
		Connection connection=null;
		Statement stmt = null;
		ResultSet result=null;
		try {
			 connection = connectionFactory.getConnection(MYSQL_DB);
			 stmt= connection.createStatement();
			 result=stmt.executeQuery(ALL_REGISTERED_USER_QUERY);
			 if(result!=null){
				 while(result.next()){
					 String tenant_name = result.getString("tenant_name");
					 String company_name = result.getString("company_name");
					 String company_address = result.getString("company_address");
					 String email = result.getString("tenant_email");
					 String password = result.getString("password");
					 int id = result.getInt("id");
					 PaasUserRegister paasUser=new PaasUserRegister(company_name, company_address, email, password,id,tenant_name);
					 paasUserList.add(paasUser);
				 }
			 }else{
				 LOGGER.debug("No data available in tenant table");
			 }
			 LOGGER.debug("Paas user List : "+paasUserList);				 
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Unable to get All users registered to paas ");
			throw new DataBaseOperationFailedException("Unable to get All users registered to paas",e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Unable to get All users registered to paas because " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Unable to get All users registered to paas because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Unable to get All users registered to paas",e);
		} finally{
			DataBaseHelper.dbCleanup(connection, stmt, result);			
		}
		return paasUserList;

	}// end of method registerPaasUser
	
	/**
	 * This method is used to  delete registered user based
	 * @param paasUserRegister : PaasUserRegisterVO Object type contain details require to register the user
	 * @throws DataBaseOperationFailedException 
	 */
	public void deleteRegisteredUser(PaasUserRegister paasUserRegister) throws DataBaseOperationFailedException{
		LOGGER.debug(".deleteRegisteredUser method of PaasUserRegisterDAO");
		DataBaseConnectionFactory connectionFactory = new DataBaseConnectionFactory();
		Connection connection=null;
		PreparedStatement pstmt = null;
		try {
			 connection = connectionFactory.getConnection(MYSQL_DB);
			 pstmt = (PreparedStatement) connection.prepareStatement(DELETE_RESGISTERED_USER_BY_EMAIL_COMPANYNAME);
			 pstmt.setString(1,paasUserRegister.getEmail());
			 pstmt.setString(2,paasUserRegister.getCompany_name());
			 pstmt.executeUpdate();			 
			 LOGGER.debug("registered user with email : "+paasUserRegister.getEmail()+"and companyName+"+paasUserRegister.getCompany_name()+" deleted successfully");				 
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Unable to delete regsitered user with email : "+paasUserRegister.getEmail()+"and companyName+"+paasUserRegister.getCompany_name());
			throw new DataBaseOperationFailedException("Unable to delete regsitered user with email : "+paasUserRegister.getEmail()+"and companyName+"+paasUserRegister.getCompany_name(),e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Unable to delete regsitered user because " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Unable to delete regsitered user because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Unable to delete regsitered user with email : "+paasUserRegister.getEmail()+"and companyName+"+paasUserRegister.getCompany_name(),e);
		} finally{
			DataBaseHelper.dbCleanUp(connection, pstmt);			
		}

	}// end of method registerPaasUser
	
	/**
	 * This method is used to login to pass 
	 * @param email : String used to login
	 * @param password : String used to login
	 * @return PaasUserRegister : PaasUserRegister
	 * @throws DataBaseOperationFailedException
	 */
	public PaasUserRegister loginToPaas(String email,String password) throws DataBaseOperationFailedException{
		LOGGER.debug(".loginToPaas method of PaasUserRegisterDAO");
		DataBaseConnectionFactory connectionFactory = new DataBaseConnectionFactory();
		Connection connection=null;
		PreparedStatement pstmt = null;
		 ResultSet result=null;
		  PaasUserRegister paasUser=null;

		try {
			 connection = connectionFactory.getConnection(MYSQL_DB);
			 pstmt = (PreparedStatement) connection.prepareStatement(LOGIN_QUERY);
			 pstmt.setString(1,email);
			 pstmt.setString(2,password);			 
			  result=pstmt.executeQuery();	
			 if(result.next()){
				 String tenant_name = result.getString("tenant_name");
				 String company_name=result.getString("company_name");
				 String company_address=result.getString("company_address");
				 email=result.getString("tenant_email");
				 password=result.getString("password");
				 int id=result.getInt("id");
				 paasUser=new PaasUserRegister(company_name, company_address, email, password,id,tenant_name);
				 LOGGER.debug("Logged in   with email : "+email+"and password+"+password+" is  successfull");	
			 }
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Unable to loggedIn  with email : "+email+"and password+"+password);
			throw new DataBaseOperationFailedException("Unable to loggedIn  with email : "+email+"and password+"+password,e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Unable to loggedIn because " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Unable to loggedIn because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Unable to loggedIn  with email : "+email+"and password+"+password,e);
		} finally{
			DataBaseHelper.dbCleanUp(connection, pstmt);			
		}		
		return paasUser;
	}//end of method loginToPaas
	
	/**
	 * This method is used to check if email already exist in tenant table or not
	 * @param email : String , check if email already exist
	 * @return boolean : true if exist and false if not
	 * @throws DataBaseOperationFailedException 
	 */
	public boolean checkEmailExist(String email) throws DataBaseOperationFailedException{
		LOGGER.debug(".checkEmailExist method of PaasUserRegisterDAO");
		DataBaseConnectionFactory connectionFactory = new DataBaseConnectionFactory();
		Connection connection=null;
		PreparedStatement pstmt = null;
		 ResultSet result=null;
		boolean emailFlag=false;
		try {
			 connection = connectionFactory.getConnection(MYSQL_DB);
			 pstmt = (PreparedStatement) connection.prepareStatement(CHECKEMAIL_EXIST_QUERY);
			 pstmt.setString(1,email);			 			 
			  result=pstmt.executeQuery();	
			 if(result.next()){
				 emailFlag=true;
				 LOGGER.debug("Email already exist with value : "+email);	
			 }
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Unable to fetch email from tenant table with value : "+email);
			throw new DataBaseOperationFailedException("Unable to fetch email from regsiter table with value : "+email,e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Unable to fetch email from tenant table " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Unable to fetch email from regsiter table because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Unable to fetch email from regsiter table with value : "+email,e);
		} finally{
			DataBaseHelper.dbCleanUp(connection, pstmt);			
		}		
		return emailFlag;
	}//end of method checkEmailExist
	
	/**
	 * This method is used to check if email and password already exist in tenant table or not
	 * @param email : String , check if email already exist
	 * @param password : String, check if password exist
	 * @return boolean : true if exist and false if not
	 * @throws DataBaseOperationFailedException 
	 */
	public PaasUserRegister userWithEmailPasswordExist(String email,String password) throws DataBaseOperationFailedException{
		
		LOGGER.debug(".userWithEmailPasswordExist method of PaasUserRegisterDAO");
		DataBaseConnectionFactory connectionFactory = new DataBaseConnectionFactory();
		Connection connection=null;
		PreparedStatement pstmt = null;
		ResultSet result=null;

		PaasUserRegister paasUser=null;
		try {
			connection = connectionFactory.getConnection(MYSQL_DB);
			
			pstmt = (PreparedStatement) connection.prepareStatement(CHECKEMAIl_AND_PASSWORD_EXIST_QUERY);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			result = pstmt.executeQuery();
			if (result.next()) {
				
				email = result.getString("email_id");
				password = result.getString("password");
				int id = result.getInt("tenant_id");
				paasUser = new PaasUserRegister();
				paasUser.setEmail(email);
				paasUser.setId(id);
				LOGGER.debug("Email and password already exist with value : "+ email + " and " + password);
			}
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Unable to fetch data from regsiter table with value : "+email);
			throw new DataBaseOperationFailedException("Unable to fetch data from regsiter table with value : "+email+" and pass : "+password,e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Unable to fetch data from regsiter table because " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Unable to fetch data from regsiter table because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Unable to fetch data from regsiter table with value : "+email+" and pass : "+password,e);
		} finally{
			DataBaseHelper.dbCleanUp(connection, pstmt);			
		}		
		return paasUser;
	}//end of method userWithEmailPasswordExist

	
}
