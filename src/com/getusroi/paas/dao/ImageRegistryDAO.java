package com.getusroi.paas.dao;

import static com.getusroi.paas.helper.PAASConstant.MYSQL_DB;

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
import com.getusroi.paas.vo.ImageRegistry;

/**
 * This class  is used to get,add,delete and update image registry
 * @author bizruntime
 *
 */
public class ImageRegistryDAO {
	 private static final Logger LOGGER = LoggerFactory.getLogger(ImageRegistryDAO.class);
	 private final String INSERT_IMAGEREGISTRY_QUERY="insert into image_registry (registory_name,registory_url,version,user_name,password,tenant_id,createDTM) VALUES (?,?,?,?,?,?,NOW())";
	 private final String GET_ALL_IMAGEREGISTRY_QUERY="select * from image_registry where tenant_id=?";
	 private final String GET_IMAGE_REGISTRY_BY_NAME_AND_TENANT_ID="select * from image_registry where registory_name=? and tenant_id=?";
	 
	 private final String GET_IMAGE_REGISTRY_ID_BY_NAME = "select id from image_registry where registory_name=?";
	 private final String GET_IMAGE_REGISTRY_NAME_BY_ID_AND_TENANT_ID = "select registory_name from image_registry where tenant_id=? and id=?";
	 private final String GET_IMAGE_REGISTRY_ID_BY_UserNAME = "select id from image_registry where  user_name=? and tenant_id=?";
	 private final String UPDATE_IMAGE_REGISTRY_BY_ID="UPDATE  image_registry set registory_name=?,registory_url =?,version=?,user_name=?,password=?,tenant_id=? where id=?";
	 private final String DELETE_IMAGEREGISTRY_BY_IMAGEID_AND_USERNAME_QUERY="delete from image_registry where id=? AND user_name=?";
	 /**
	  * This method is used to store imageRegistry in db
	  * @param imageRegistryVO : ImageRegistryVO 
	  * @throws DataBaseOperationFailedException : Unable to store image regsitry in db
	  */
	public void addImageRegistry(ImageRegistry imageRegistryVO) throws DataBaseOperationFailedException{
		LOGGER.debug(".addImageRegistry method of ImageRegistryDAO");
		LOGGER.debug("name "+imageRegistryVO.getName()+" url "+imageRegistryVO.getLocation()+" Version "+imageRegistryVO.getVersion()+" user name "+imageRegistryVO.getUser_name()+" password "+imageRegistryVO.getPassword()+" tenant id "+imageRegistryVO.getTenant_id());
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
			
			LOGGER.debug("image registry data inserted successfully : "+imageRegistryVO);
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Unable to store the image registry in db : "+imageRegistryVO.toString(),e);
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
	
public int updateImageRegistry(ImageRegistry imageRegistryVO)throws DataBaseOperationFailedException{

	
	LOGGER.debug(".updateImageRegistry method of ImageRegistryDAO");
	LOGGER.debug("Image DAO"+imageRegistryVO.getId()+"name "+imageRegistryVO.getName()+" url "+imageRegistryVO.getLocation()+" Version "+imageRegistryVO.getVersion()+" user name "+imageRegistryVO.getUser_name()+" password "+imageRegistryVO.getPassword()+" tenant id "+imageRegistryVO.getTenant_id());
	DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
	Connection connection=null;
	PreparedStatement pstmt=null;
	int updateImage;
	 try {
		connection=connectionFactory.getConnection(MYSQL_DB);
		pstmt = (PreparedStatement) connection.prepareStatement(UPDATE_IMAGE_REGISTRY_BY_ID);
		pstmt.setString(1, imageRegistryVO.getName());
		pstmt.setString(2, imageRegistryVO.getLocation());
		pstmt.setString(3, imageRegistryVO.getVersion());
		pstmt.setString(4, imageRegistryVO.getUser_name());
		pstmt.setString(5, imageRegistryVO.getPassword());
		pstmt.setInt(6, imageRegistryVO.getTenant_id());
		pstmt.setInt(7, imageRegistryVO.getId());
		
		 updateImage =pstmt.executeUpdate()>0 ? 1 : 0;
		
		LOGGER.debug("image registry data updates successfully : "+imageRegistryVO);
	} catch (ClassNotFoundException | IOException e) {
		LOGGER.error("Unable to update the image registry in db : "+imageRegistryVO.toString(),e);
		throw new DataBaseOperationFailedException("Unable to update the image registry in db : "+imageRegistryVO.toString(),e);
	}catch(SQLException e) {
		if(e.getErrorCode() == 1064) {
			String message = "Unable to store the image registry in db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
			throw new DataBaseOperationFailedException(message, e);
		} else if(e.getErrorCode() == 1146) {
			String message = "Unable to update the image registry in db because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
			throw new DataBaseOperationFailedException(message, e);
		} else
			throw new DataBaseOperationFailedException("Unable to update the image registry in db : "+imageRegistryVO.toString(),e);
	} finally{
		DataBaseHelper.close(pstmt);
		DataBaseHelper.close(connection);
	}
return updateImage;
}
	
	
	/**
	 * This method is used to get all image registry from db
	 * @return List<ImageRegistryVO> : List of all image registry from db
	 * @throws DataBaseOperationFailedException : unable to fetch image regsitry from db
	 */
	public List<ImageRegistry> getAllImageRegistry(int tenantId) throws DataBaseOperationFailedException{
		LOGGER.debug(".getAllImageRegistry method of ImageRegistryDAO");
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
					int imageId = result.getInt("id");
					String name=result.getString("registory_name");
					String location=result.getString("registory_url");
					String version=result.getString("version");
					//String private_cloud=result.getString("private_cloud");
					String user_name=result.getString("user_name");
					String password=result.getString("password");
					int tenant_id = result.getInt("tenant_id");
					LOGGER.debug("name : "+name+", location : "+location+", version : "+version+", user name : "+user_name+", password : "+password+"Imageid : "+imageId+",");
					ImageRegistry imageRegistry=new ImageRegistry(imageId,name, location, version, user_name, password,tenant_id);
					imageRegistryList.add(imageRegistry);
				}//end of while
				LOGGER.debug("element in image registry list are : "+imageRegistryList);
			}else{
				LOGGER.debug("No data available in image_registry table");
			}
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Unable to fetch the data from image_registry");
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
		LOGGER.debug(".getAllImageRegistry method of ImageRegistryDAO");
		
		Connection connection=null;
		PreparedStatement pstmt=null;
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
					int iamgeId =result.getInt("id");
					String name = result.getString("registory_name");
					String location = result.getString("registory_url");
					 
					
					String user_name = result.getString("user_name");
					String password = result.getString("password");
					 
					
					 imageRegistry=new ImageRegistry(iamgeId,name, location, "", user_name, password,7);
					
				}//end of while
				LOGGER.debug("element in image registry list are : "+imageRegistry);
			}else{
				LOGGER.debug("No data available in image_registry table");
			}
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Unable to fetch the data from image_registry");
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
	public void deleteImageRegistryById(int imageId,String userName) throws DataBaseOperationFailedException{
		LOGGER.debug(".deleteImageRegistryByNameAndUserName method of ImageRegistryDAO");
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		Connection connection=null;
		PreparedStatement pstmt=null;
		try {
			connection=connectionFactory.getConnection(MYSQL_DB);
			pstmt=(PreparedStatement) connection.prepareStatement(DELETE_IMAGEREGISTRY_BY_IMAGEID_AND_USERNAME_QUERY);
			pstmt.setInt(1,imageId);
			pstmt.setString(2,userName);
			pstmt.executeUpdate();
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Unable to delete image registry from db using image name : "+imageId+" and user name : "+userName);
			throw new DataBaseOperationFailedException("Unable to delete image registry from db using image name : "+imageId+" and user name : "+userName,e);
		} catch(SQLException e) {
			if(e.getErrorCode() == 1064) {
				String message = "Unable to delete image registry from db using image name because " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if(e.getErrorCode() == 1146) {
				String message = "Unable to delete image registry from db using image name because: " + PAASErrorCodeExceptionHelper.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException("Unable to delete image registry from db using image name : "+imageId+" and user name : "+userName,e);
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
	public int getImageRegistryIdByName(String imageRegistryName, int tenant_id) throws DataBaseOperationFailedException{
		LOGGER.debug(".getImageRegistryIdByName method of ImageRegistryDAO");
		
		Connection connection=null;
		PreparedStatement pstmt=null;
		ResultSet result=null;
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		int imageRegistryId=0;
		try {
			connection=connectionFactory.getConnection(MYSQL_DB);
			pstmt=connection.prepareStatement(GET_IMAGE_REGISTRY_ID_BY_NAME);
			pstmt.setString(1,imageRegistryName);
			result=pstmt.executeQuery();
			if(result !=null){
				while(result.next()){
					imageRegistryId = result.getInt("id");
				}//end of while
				LOGGER.debug("Image id with given image Registry : "+imageRegistryId);
			}else{
				LOGGER.debug("No data available in image_registry table");
			}
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Unable to fetch the data from image_registry");
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
	
	
	//GET_IMAGE_REGISTRY_NAME_BY_ID_AND_TENANT_ID
	
	/**
	 * This method is used to get  image registry name from db by id
	 * @param imageRegistryName : image registry name in String
	 * @param tenant_id TODO
	 * @return ImageRegistry : image registry from db based on name
	 * @throws DataBaseOperationFailedException : unable to fetch image regsitry from db
	 */
	public String getImageRegistryNameById(int imageRegistryId, int tenant_id) throws DataBaseOperationFailedException{
		LOGGER.debug("getImageRegistryNameById (.) of ImageRegistryDAO");
		
		Connection connection=null;
		PreparedStatement pstmt=null;
		ResultSet result=null;
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		String imageRegistryName = null;
		try {
			connection=connectionFactory.getConnection(MYSQL_DB);
			pstmt=connection.prepareStatement(GET_IMAGE_REGISTRY_NAME_BY_ID_AND_TENANT_ID);
			pstmt.setInt(1,tenant_id);
			pstmt.setInt(2,imageRegistryId);
			result=pstmt.executeQuery();
			if(result !=null){
				while(result.next()){
					imageRegistryName = result.getString("registory_name");
				}//end of while
				LOGGER.debug("Image id with given image Registry : "+imageRegistryId);
			}else{
				LOGGER.debug("No data available in image_registry table");
			}
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Unable to fetch the data from image_registry");
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
		return imageRegistryName;
	}//end of method getAllImageRegistry
	
public int getImgRegistryIdByUser_NameandTenant_Id(String userName, int tenant_id) throws DataBaseOperationFailedException{
		
	LOGGER.debug(".getImageRegistryIdByName method of ImageRegistryDAO");
		
		Connection connection=null;
		java.sql.PreparedStatement pstmt=null;
		ResultSet result=null;
		DataBaseConnectionFactory connectionFactory=new DataBaseConnectionFactory();
		Integer imageRegistryId=0;
		try {
			connection=connectionFactory.getConnection(MYSQL_DB);
			pstmt=connection.prepareStatement(GET_IMAGE_REGISTRY_ID_BY_UserNAME);
			pstmt.setString(1,userName);
			pstmt.setInt(2,tenant_id);
			result=pstmt.executeQuery();
			if(result !=null){
				while(result.next()){
					imageRegistryId = result.getInt("id");
				}//end of while
				
			}else{
				LOGGER.debug("No data available in image_registry table");
			}
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Unable to fetch the data from image_registry");
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
	}//end of method getAllId by using userName

}
