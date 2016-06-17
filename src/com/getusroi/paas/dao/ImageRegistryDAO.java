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
import com.getusroi.paas.vo.ImageRegistry;
import com.mysql.jdbc.PreparedStatement;
import static com.getusroi.paas.helper.PAASConstant.*;

/**
 * This class  is used to get,add,delete and update image registry
 * @author bizruntime
 *
 */
public class ImageRegistryDAO {
	 static final Logger logger = LoggerFactory.getLogger(ImageRegistryDAO.class);
	 private final String INSERT_IMAGEREGISTRY_QUERY="insert into image_registry (registory_name,registory_url,version,user_name,password,tenant_id,createDTM) VALUES (?,?,?,?,?,?,NOW())";
	 private final String GET_ALL_IMAGEREGISTRY_QUERY="select * from image_registry where tenant_id=?";
	 private final String GET_IMAGE_REGISTRY_BY_NAME_AND_TENANT_ID="select * from image_registry where registory_name=? and tenant_id=?";
	 private final String DELETE_IMAGEREGISTRY_BY_IMAGENAME_AND_USERNAME_QUERY="delete from image_registry where registory_name=? AND user_name=?";
	 private final String GET_IMAGE_REGISTRY_ID_BY_NAME = "select id from image_registry where registory_name=?";
	 
	 /**
	  * This method is used to store imageRegistry in db
	  * @param imageRegistryVO : ImageRegistryVO 
	  * @throws DataBaseOperationFailedException : Unable to store image regsitry in db
	  */
	public void addImageRegistry(ImageRegistry imageRegistryVO) throws DataBaseOperationFailedException{
		logger.debug(".addImageRegistry method of ImageRegistryDAO");
		logger.debug("name "+imageRegistryVO.getName()+" url "+imageRegistryVO.getLocation()+" Version "+imageRegistryVO.getVersion()+" user name "+imageRegistryVO.getUser_name()+" password "+imageRegistryVO.getPassword()+" tenant id "+imageRegistryVO.getTenant_id());
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		Connection connection=null;
		PreparedStatement pstmt=null;
		 try {
			connection=connectionFactory.getConnection(MYSQL_DB);
			pstmt = (PreparedStatement) connection.prepareStatement(INSERT_IMAGEREGISTRY_QUERY);
			pstmt.setString(1, imageRegistryVO.getName());
			pstmt.setString(2, imageRegistryVO.getLocation());
			pstmt.setString(3, imageRegistryVO.getVersion());
			pstmt.setString(4, imageRegistryVO.getUser_name());
			pstmt.setString(5, imageRegistryVO.getPassword());
			pstmt.setInt(6, imageRegistryVO.getTenant_id());
			
			pstmt.executeUpdate();
			
			logger.debug("image registry data inserted successfully : "+imageRegistryVO);
		} catch (ClassNotFoundException | IOException e) {
			logger.error("Unable to store the image registry in db : "+imageRegistryVO.toString(),e);
			throw new DataBaseOperationFailedException("Unable to store the image registry in db : "+imageRegistryVO.toString(),e);
		}catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Unable to store the image registry in db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Unable to store the image registry in db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Unable to store the image registry in db : "+imageRegistryVO.toString(),e);
		} finally{
			DataBaseHelper.close(pstmt);
			DataBaseHelper.close(connection);
		}
	}//end of method addImageRegistry
	
	/**
	 * This method is used to get all image registry from db
	 * @return List<ImageRegistryVO> : List of all image registry from db
	 * @throws DataBaseOperationFailedException : unable to fetch image regsitry from db
	 */
	public List<ImageRegistry> getAllImageRegistry(int tenantId) throws DataBaseOperationFailedException{
		logger.debug(".getAllImageRegistry method of ImageRegistryDAO");
		Connection connection=null;
		PreparedStatement stmt=null;
		ResultSet result=null;
		List<ImageRegistry> imageRegistryList=new ArrayList<>();
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		try {
			connection=connectionFactory.getConnection(MYSQL_DB);
			stmt = (PreparedStatement) connection.prepareStatement(GET_ALL_IMAGEREGISTRY_QUERY);
			stmt.setInt(1, tenantId);
			result=stmt.executeQuery();
			if(result !=null){
				while(result.next()){
					String name=result.getString("registory_name");
					String location=result.getString("registory_url");
					String version=result.getString("version");
					//String private_cloud=result.getString("private_cloud");
					String user_name=result.getString("user_name");
					String password=result.getString("password");
					int tenant_id = result.getInt("tenant_id");
					logger.debug("name : "+name+", location : "+location+", version : "+version+", user name : "+user_name+", password : "+password);
					ImageRegistry imageRegistry=new ImageRegistry(name, location, version, user_name, password,tenant_id);
					imageRegistryList.add(imageRegistry);
				}//end of while
				logger.debug("element in image registry list are : "+imageRegistryList);
			}else{
				logger.debug("No data available in image_registry table");
			}
		} catch (ClassNotFoundException | IOException e) {
			logger.error("Unable to fetch the data from image_registry");
			throw new DataBaseOperationFailedException("Error in getting all data from image_registry ",e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Unable to fetch the data from image_registry because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Unable to fetch the data from image_registry db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Error in getting all data from image_registry ",e);
		} finally{
			DataBaseHelper.dbCleanup(connection, stmt, result);
		}
		return imageRegistryList;
	}//end of method getAllImageRegistry
	
	
	/**
	 * This method is used to get  image registry from db by name
	 * @param imageRegistryName : image registry name in String
	 * @return ImageRegistry : image registry from db based on name
	 * @throws DataBaseOperationFailedException : unable to fetch image regsitry from db
	 */
	public ImageRegistry getImageRegistryByName(String imageRegistryName,int id) throws DataBaseOperationFailedException{
		logger.debug(".getAllImageRegistry method of ImageRegistryDAO");
		
		Connection connection=null;
		java.sql.PreparedStatement pstmt=null;
		ResultSet result=null;
		ImageRegistry imageRegistry=null;
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		try {
			connection=connectionFactory.getConnection(MYSQL_DB);
			pstmt=connection.prepareStatement(GET_IMAGE_REGISTRY_BY_NAME_AND_TENANT_ID);
			pstmt.setString(1,imageRegistryName);
			pstmt.setInt(2, id);
			result=pstmt.executeQuery();
			if(result !=null){
				while(result.next()){
					String name = result.getString("registory_name");
					String location = result.getString("registory_url");
					 
					
					String user_name = result.getString("user_name");
					String password = result.getString("password");
					 
					
					 imageRegistry=new ImageRegistry(name, location, "", user_name, password,7);
					
				}//end of while
				logger.debug("element in image registry list are : "+imageRegistry);
			}else{
				logger.debug("No data available in image_registry table");
			}
		} catch (ClassNotFoundException | IOException e) {
			logger.error("Unable to fetch the data from image_registry");
			throw new DataBaseOperationFailedException("Error in getting all data from image_registry ",e);
		}catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Error in getting all data from image_registry: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Error in getting all data from image_registry because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Error in getting all data from image_registry ",e);
		} finally{
			DataBaseHelper.dbCleanup(connection, pstmt, result);
		}
		return imageRegistry;
	}//end of method getAllImageRegistry
	
	/**
	 * This method is used to delete image registry based on image name ans user_name
	 * @param imageName  : name of image in String
	 * @param userName : name of user in String
	 * @throws DataBaseOperationFailedException 
	 */
	public void deleteImageRegistryByNameAndUserName(String imageName,String userName) throws DataBaseOperationFailedException{
		logger.debug(".deleteImageRegistryByNameAndUserName method of ImageRegistryDAO");
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		Connection connection=null;
		PreparedStatement pstmt=null;
		try {
			connection=connectionFactory.getConnection(MYSQL_DB);
			pstmt=(PreparedStatement) connection.prepareStatement(DELETE_IMAGEREGISTRY_BY_IMAGENAME_AND_USERNAME_QUERY);
			pstmt.setString(1,imageName);
			pstmt.setString(2,userName);
			pstmt.executeUpdate();
		} catch (ClassNotFoundException | IOException e) {
			logger.error("Unable to delete image registry from db using image name : "+imageName+" and user name : "+userName);
			throw new DataBaseOperationFailedException("Unable to delete image registry from db using image name : "+imageName+" and user name : "+userName,e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Unable to delete image registry from db using image name because " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Unable to delete image registry from db using image name because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Unable to delete image registry from db using image name : "+imageName+" and user name : "+userName,e);
		} finally{
			DataBaseHelper.dbCleanUp(connection, pstmt);			
		}
	}//end of method
	
	/**
	 * This method is used to get  image registry from db by name
	 * @param imageRegistryName : image registry name in String
	 * @param tenant_id TODO
	 * @return ImageRegistry : image registry from db based on name
	 * @throws DataBaseOperationFailedException : unable to fetch image regsitry from db
	 */
	public Integer getImageRegistryIdByName(String imageRegistryName, int tenant_id) throws DataBaseOperationFailedException{
		logger.debug(".getImageRegistryIdByName method of ImageRegistryDAO");
		
		Connection connection=null;
		java.sql.PreparedStatement pstmt=null;
		ResultSet result=null;
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		Integer imageRegistryId=null;
		try {
			connection=connectionFactory.getConnection(MYSQL_DB);
			pstmt=connection.prepareStatement(GET_IMAGE_REGISTRY_ID_BY_NAME);
			pstmt.setString(1,imageRegistryName);
			result=pstmt.executeQuery();
			if(result !=null){
				while(result.next()){
					imageRegistryId = result.getInt("id");
				}//end of while
				logger.debug("Image id with given image Registry : "+imageRegistryId);
			}else{
				logger.debug("No data available in image_registry table");
			}
		} catch (ClassNotFoundException | IOException e) {
			logger.error("Unable to fetch the data from image_registry");
			throw new DataBaseOperationFailedException("Error in getting all data from image_registry ",e);
		}catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Error in getting all data from image_registry: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Error in getting all data from image_registry because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Error in getting all data from image_registry ",e);
		} finally{
			DataBaseHelper.dbCleanup(connection, pstmt, result);
		}
		return imageRegistryId;
	}//end of method getAllImageRegistry
	

}
