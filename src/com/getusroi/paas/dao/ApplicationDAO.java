package com.getusroi.paas.dao;

import static com.getusroi.paas.helper.PAASConstant.MYSQL_DB;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getusroi.paas.db.helper.DataBaseConnectionFactory;
import com.getusroi.paas.db.helper.DataBaseHelper;
import com.getusroi.paas.helper.PAASConstant;
import com.getusroi.paas.helper.PAASErrorCodeExceptionHelper;
import com.getusroi.paas.vo.ApplicantSummary;
import com.getusroi.paas.vo.EnvironmentVariable;
import com.getusroi.paas.vo.Service;
import com.mysql.jdbc.PreparedStatement;

public class ApplicationDAO {
	static final Logger LOGGER = LoggerFactory.getLogger(ApplicationDAO.class);

	private static final String INSERT_APPLICATION_SUMMARY_QUERY = "insert into appsummary values(?,?,?,?,?)";
	private static final String GET_ALL_APPLICATION_SUMMARY_QUERY = "select * from appsummary";
	
	private static final String INSERT_APPLICATION_SERVICE_QUERY = "insert into application (service_name,registry_url,tag,run,host_name,host_port,container_port,protocol_type,port_index,path,interval_seconds,timeout_seconds,max_consecutive_failures,grace_period_seconds,ignore_http1xx,instance_count,host_path,container_path,volume,subnet_id,createdDTM,tenant_id,registry_id,container_id,apps_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?,?,?,?)";
	private static final String GET_SERVICE_BY_NAME_AND_USERID = "select * from application where service_name=? && tenant_id=?";	
	private static final String GET_ALL_APPLICATION_SERVICE_BY_TENANT_ID_QUERY_ = "select app_id,service_name,registry_id,tag,container_id from application where tenant_id=? and apps_id=?";
	private static final String DELETE_SERVICE_BY_SERVICENAME_USER_ID_AND_APPS_ID_QUERY = "delete from application where service_name=? and tenant_id=?";	/*and apps_id=?*/
	
	private static final String GET_APPLICATION_ID_BY_NAME_AND_TENANT_ID = "select apps_id from applications where applications_name=? && tenant_id=?";
	private static final String INSERT_ENVIRONMENT_VARIABLE_DETAILS__QUERY = "insert into application_variable (varible_name,varible_value,app_id,createdDTM) values (?,?,?,NOW())";
	
	
	private static final String GET_ENVIRONMENT_VARIABLE_BY_SERVICENAME = "select * from environment_variable where serviceName =?";
	
	DataBaseConnectionFactory connectionFactory = null;
	
	/**
	 * This method is used to add application summary
	 * 
	 * @param appSummary
	 *            : ApplicantSummary Object contains data need for inserting
	 *            data
	 * @throws DataBaseOperationFailedException
	 *             : Unable to insert ApplicantSummary
	 */
	public void insertApplicationSummary(ApplicantSummary appSummary)
			throws DataBaseOperationFailedException {
		LOGGER.debug(".insertApplicationSummary method of ApplicationDAO");
		DataBaseConnectionFactory connectionFactory = new DataBaseConnectionFactory();
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = connectionFactory.getConnection(MYSQL_DB);
			pstmt = (PreparedStatement) connection
					.prepareStatement(INSERT_APPLICATION_SUMMARY_QUERY);
			pstmt.setString(1, appSummary.getApplicantionName());
			pstmt.setString(2, appSummary.getDescription());
			pstmt.setString(3, appSummary.getImageRegistry());
			pstmt.setString(4, appSummary.getImageRepository());
			pstmt.setString(5, appSummary.getTag());
			pstmt.executeUpdate();
			LOGGER.debug("apps summary data : " + appSummary
					+ " inserted successfully");
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Unable to add applicant summary into db with data : "
					+ appSummary);
			throw new DataBaseOperationFailedException(
					"Unable to add applicant summary into db with data : "
							+ appSummary, e);
		} catch (SQLException e) {
			if (e.getErrorCode() == 1064) {
				String message = "Unable to add applicant summary into db because: "
						+ PAASErrorCodeExceptionHelper
								.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if (e.getErrorCode() == 1146) {
				String message = "Unable to add applicant summary into db because: "
						+ PAASErrorCodeExceptionHelper
								.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException(
						"Unable to add applicant summary into db with data : "
								+ appSummary, e);
		} finally {
			DataBaseHelper.dbCleanUp(connection, pstmt);
		}

	}// end of method insertApplicationSummary

	/**
	 * This method is used to get All applicant summary from db
	 * 
	 * @return List<ApplicantSummary> : List of ApplicantSummary Object
	 * @throws DataBaseOperationFailedException
	 *             : Unable to get all applicant summary
	 */
	public List<ApplicantSummary> getAllApplicantSummary()
			throws DataBaseOperationFailedException {
		LOGGER.debug(".getAllApplicantSummary method of ApplicationDAO");
		DataBaseConnectionFactory connectionFactory = new DataBaseConnectionFactory();
		List<ApplicantSummary> applicantSummaryList = new LinkedList<ApplicantSummary>();
		Connection connection = null;
		Statement stmt = null;
		ResultSet result = null;
		try {
			connection = connectionFactory.getConnection(MYSQL_DB);
			stmt = connection.createStatement();
			result = stmt.executeQuery(GET_ALL_APPLICATION_SUMMARY_QUERY);
			if (result != null) {
				while (result.next()) {
					ApplicantSummary applicantSummary = new ApplicantSummary();
					applicantSummary = new ApplicantSummary();
					applicantSummary.setApplicantionName(result
							.getString("applicantName"));
					applicantSummary.setDescription(result
							.getString("description"));
					applicantSummary.setImageRegistry(result
							.getString("imageRegistry"));
					applicantSummary.setImageRepository(result
							.getString("imageRepository"));
					applicantSummary.setTag(result.getString("tag"));
					applicantSummaryList.add(applicantSummary);
				}
			} else {
				LOGGER.debug("No data avilable in apps summary table");
			}
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Unable to fetch applicant summary into db ");
			throw new DataBaseOperationFailedException(
					"Unable to fetch applicant summary ", e);
		} catch (SQLException e) {
			if (e.getErrorCode() == 1064) {
				String message = "Unable to fetch applicant summary into db because: "
						+ PAASErrorCodeExceptionHelper
								.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if (e.getErrorCode() == 1146) {
				String message = "Unable to fetch applicant summary into db because: "
						+ PAASErrorCodeExceptionHelper
								.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException(
						"Unable to fetch applicant summary ", e);
		} finally {
			DataBaseHelper.dbCleanup(connection, stmt, result);
		}
		return applicantSummaryList;
	}// end of method getAllApplicantSummary

	/**
	 * This method is used to add service to db
	 * 
	 * @param addService
	 *            : addService Object
	 * @throws DataBaseOperationFailedException
	 *             : Error in adding service to db
	 */
	public void addService(Service service)
			throws DataBaseOperationFailedException {
		LOGGER.debug(".addService method of ApplicationDAO");
		DataBaseConnectionFactory connectionFactory = new DataBaseConnectionFactory();
		Connection connection = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		int last_inserted_id =0;
		try {
			connection = connectionFactory.getConnection(MYSQL_DB);

		if(checkServiceNameExist(connection, service))
		throw new DataBaseOperationFailedException("given service name exit so Unable to insert data for service into db with data :  "+ service);
			pstmt = (PreparedStatement) connection
					.prepareStatement(INSERT_APPLICATION_SERVICE_QUERY,Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setString(1, service.getServiceName());
			pstmt.setString(2, service.getImageRepository());
			pstmt.setString(3, service.getTag());
			pstmt.setString(4, service.getRun());
			pstmt.setString(5, service.getHostName());
			pstmt.setInt(6, service.getHostPort());
			pstmt.setInt(7, service.getContainerPort());
			pstmt.setString(8, service.getProtocal());		//HARDCODE
			pstmt.setInt(9, 111);							//HARDCODE
			pstmt.setString(10, service.getEnvPath());	
			pstmt.setInt(11, service.getEnvInterval());
			pstmt.setInt(12, 60);							//HARDCODE
			pstmt.setInt(13, service.getEnvThreshold());
			pstmt.setInt(14, 120);							//HARDCODE
			pstmt.setInt(15, service.getEnvIgnore());
			pstmt.setInt(16, 11);							//HARDCODE
			pstmt.setString(17, "hostpath1");				//HARDCODE
			pstmt.setString(18, "contnrpath1");				//HARDCODE	
			pstmt.setInt(19, service.getVolume());
			pstmt.setInt(20, new SubnetDAO().getSubnetIdBySubnetName(service.getSubnetName(),service.getTenantId()));
			pstmt.setInt(21, service.getTenantId());
			pstmt.setInt(22, new ImageRegistryDAO().getImageRegistryIdByName(service.getImageRegistry(), service.getTenantId()));
			pstmt.setInt(23, new ContainerTypesDAO().getContainerTypeIdByContainerName(service.getType()));
			pstmt.setInt(24, getApplicationsIdByName(service.getApplicantionName(), service.getTenantId()));
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next()) {
                 last_inserted_id = rs.getInt(1);
                 LOGGER.debug("last_inserted_id "+last_inserted_id);
            }
            
            pstmt2 = (PreparedStatement) connection
					.prepareStatement(INSERT_ENVIRONMENT_VARIABLE_DETAILS__QUERY);
            List<EnvironmentVariable> listOfEnvrnmtVar = service.getEnv(); 
            LOGGER.debug("Number of environment variabl "+listOfEnvrnmtVar.size());
            EnvironmentVariable env= null;
            for(int i=0; i < listOfEnvrnmtVar.size(); i++){
            	 env=listOfEnvrnmtVar.get(i);
            	LOGGER.debug("Environmentvariable object  "+env);
            	pstmt2.setString(1, env.getEnvkey());
    			pstmt2.setString(2, env.getEnvvalue());
    			pstmt2.setInt(3, last_inserted_id);
    			pstmt2.executeUpdate();
            }
            pstmt2.close();
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error( "Unable to insert data for service into db with data : " + service, e);
			throw new DataBaseOperationFailedException(
					"Unable to insert data for service into db with data :  "
							+ service, e);
		} catch (SQLException e) {
			e.printStackTrace();
			if (e.getErrorCode() == 1064) {
				String message = "Unable to insert data for service into db with because: "
						+ PAASErrorCodeExceptionHelper
								.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if (e.getErrorCode() == 1146) {
				String message = "Unable to insert data for service into db with because: "
						+ PAASErrorCodeExceptionHelper
								.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException(
						"Unable to insert data for service into db with data :  "
								+ service, e);
		} finally {
			DataBaseHelper.dbCleanUp(connection, pstmt);
		}
	}// end of method addService

	/**
	 * This method is used to get all data available in addservice
	 * 
	 * @return List<AddService> : List of addService
	 * @throws DataBaseOperationFailedException
	 *             : Unable to get the all service from db
	 */
	public List<Service> getAllServiceByUserId(int user_id)
			throws DataBaseOperationFailedException {
		LOGGER.debug(".getAllService method of ApplicationDAO");
		DataBaseConnectionFactory connectionFactory = new DataBaseConnectionFactory();
		List<Service> addServiceList = new LinkedList<Service>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet result = null;
		
		try {
			connection = connectionFactory.getConnection(MYSQL_DB);
			pstmt = (PreparedStatement) connection.prepareStatement(GET_ALL_APPLICATION_SERVICE_BY_TENANT_ID_QUERY_);
			pstmt.setInt(1, user_id);
			pstmt.setInt(2, 26);//apps_id name is not available so hardcoded
			result = pstmt.executeQuery();
			if (result != null) {
				while (result.next()) {
					Service service = new Service();
					service.setId(result.getInt("app_id"));
					service.setServiceName(result.getString("service_name"));
					service.setImageRegistry(new ImageRegistryDAO().getImageRegistryNameById(result.getInt("registry_id"), user_id));
					service.setTag(result.getString("tag"));
					service.setType(new PoliciesDAO().getContainerTypeNameById(result.getInt("container_id"), 7));					
					addServiceList.add(service);
				}
			} else {
				LOGGER.debug("No data avilable in add service table");
			}
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Unable to fetch  data for service from db");
			throw new DataBaseOperationFailedException(
					"Unable to fetch  data for service from db", e);
		} catch (SQLException e) {
			if (e.getErrorCode() == 1064) {
				String message = "Unable to fetch  data for service from db because: "
						+ PAASErrorCodeExceptionHelper
								.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if (e.getErrorCode() == 1146) {
				String message = "Unable to fetch  data for service from db because: "
						+ PAASErrorCodeExceptionHelper
								.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException(
						"Unable to fetch  data for service from db", e);
		} finally {
			DataBaseHelper.dbCleanup(connection, pstmt, result);
		}
		return addServiceList;
	}// end of method getAllService

	/**
	 * This method is used to delete service from db by service name
	 * 
	 * @param serviceName
	 *            : service name in String
	 * @throws DataBaseOperationFailedException
	 *             : Unable to delete service from db using service name
	 */
	public void deleteServiceByServiceName(Service service)
			throws DataBaseOperationFailedException {
		LOGGER.debug(".deleteServiceByServiceName method of ApplicationDAO");
		DataBaseConnectionFactory connectionFactory = new DataBaseConnectionFactory();
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = connectionFactory.getConnection(MYSQL_DB);
			// "delete from application where service_name=? and tenant_id=? and apps_id=?";
			pstmt = (PreparedStatement) connection.prepareStatement(DELETE_SERVICE_BY_SERVICENAME_USER_ID_AND_APPS_ID_QUERY);
			pstmt.setString(1, service.getServiceName());
			pstmt.setInt(2, service.getTenantId());
			//pstmt.setInt(3, service.getAppsId());
			pstmt.executeUpdate();
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Unable to delete   data for service from db : "
					+ service.getServiceName());
			throw new DataBaseOperationFailedException(
					"Unable to delete  data for service from db " + service.getServiceName(),
					e);
		} catch (SQLException e) {
			if (e.getErrorCode() == 1064) {
				String message = "Unable to delete   data for service from db because: "
						+ PAASErrorCodeExceptionHelper
								.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if (e.getErrorCode() == 1146) {
				String message = "Unable to delete   data for service from db because: "
						+ PAASErrorCodeExceptionHelper
								.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException(
						"Unable to delete  data for service from db "
								+ service.getServiceName(), e);
		}
	}// end of method deleteServiceByServiceName

	/**
	 * This method is used to get environment varibale based on service name
	 * 
	 * @param connection
	 *            : COnnection Object
	 * @param serviceName
	 *            : service name in String
	 * @return List<EnvironmentVariable> : List of EnvironmentVariable
	 * @throws DataBaseOperationFailedException
	 *             : Unable to fetch environment variable by service name
	 * @throws SQLException
	 *             : Unable to close the resources
	 */
	@SuppressWarnings("unused")
	private List<EnvironmentVariable> getAllEnvironment(Connection connection,
			String serviceName) throws DataBaseOperationFailedException,
			SQLException {
		LOGGER.debug(".getAllEnvironment method of ApplicationDAO");
		List<EnvironmentVariable> listOfEnvs = new ArrayList<>();
		ResultSet envResultSet = null;
		PreparedStatement envPreparedStatement = null;
		try {
			envPreparedStatement = (PreparedStatement) connection
					.prepareStatement(GET_ENVIRONMENT_VARIABLE_BY_SERVICENAME);
			envPreparedStatement.setString(1, serviceName);
			envResultSet = envPreparedStatement.executeQuery();
			if (envResultSet != null) {
				while (envResultSet.next()) {
					EnvironmentVariable envVar = new EnvironmentVariable();
					envVar.setEnvkey(envResultSet.getString(1));
					envVar.setEnvvalue(envResultSet.getString(2));
					listOfEnvs.add(envVar);
				}
			} else {
				LOGGER.debug("No data available for environment varible for service name : "
						+ serviceName);
			}
		} catch (SQLException e) {
			LOGGER.error("Unable to fetch  EnvironmentVariable for service"
					+ serviceName + " from db");
			throw new DataBaseOperationFailedException(
					"Unable to fetch  EnvironmentVariable for service"
							+ serviceName + " from db", e);
		} finally {
			envResultSet.close();
			envPreparedStatement.close();
		}
		return listOfEnvs;
	}// end of method getAllEnvironment

	

	
	/**
	 * This method used for check servicename is exist for given userId or not 
	 * @param connection
	 * @param addService
	 * @return Boolean
	 * @throws DataBaseOperationFailedException
	 * @throws SQLException
	 */
	private boolean checkServiceNameExist(Connection connection,
			Service addService) throws DataBaseOperationFailedException,
			SQLException {
		LOGGER.debug(".checkServiceNameExist  method of ApplicationDAO with Service Deatsil : "+ addService);
		boolean isServiceExist = false;

		java.sql.PreparedStatement statement = null;
		ResultSet reSet = null;

		try {
			statement = connection
					.prepareStatement(GET_SERVICE_BY_NAME_AND_USERID);
			statement.setString(1, addService.getServiceName());
			statement.setInt(2, addService.getTenantId());
			reSet = statement.executeQuery();
			if(reSet != null){
				while (reSet.next()) {
					isServiceExist = true;
				}
			}
		} catch (SQLException e) {
			LOGGER.error("Unable to fetch   service:"
					+ addService.getServiceName() + "   from db with user_id="
					+ addService.getTenantId());
			throw new DataBaseOperationFailedException(
					"Unable to fetch   service" + addService.getServiceName()
							+ " from db", e);
		} finally {
			if(reSet != null)
			reSet.close();
			statement.close();
		}
		return isServiceExist;
	}
	
	/**
	 * This method used for check servicename is exist for given userId or not 
	 * @param connection
	 * @param addService
	 * @return Boolean
	 * @throws DataBaseOperationFailedException
	 * @throws SQLException
	 */
	private int getApplicationsIdByName(String applicationName, int tenantId) throws DataBaseOperationFailedException,
			SQLException {
		LOGGER.debug(".getApplicationsIdByName (.) of ApplicationDAO   : applicationName "+ applicationName+" tenantId  "+tenantId);
		connectionFactory = new DataBaseConnectionFactory();
		int apps_id = 0;
		PreparedStatement pstmt = null;
		ResultSet reSet = null;
		Connection connection = null; 
		try {
			connection=connectionFactory.getConnection("mysql");
			pstmt=(PreparedStatement) connection.prepareStatement(GET_APPLICATION_ID_BY_NAME_AND_TENANT_ID);
			pstmt.setString(1, applicationName);
			pstmt.setInt(2, tenantId);
			reSet = pstmt.executeQuery();
			if(reSet != null){
				while (reSet.next()) {
					apps_id =  reSet.getInt("apps_id");
				}
			}
		}catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Error in getting the vpc detail from db");
			throw new DataBaseOperationFailedException("Error in fetching the vpc from db",e);
		} 
		catch (SQLException e) {
			LOGGER.error("Unable to fetch   applications details with:"
					+ applicationName + "   from db with tenant_id="
					+ tenantId);
			throw new DataBaseOperationFailedException(
					"Unable to fetch   applications id" + applicationName
							+ " from db", e);
		} finally {
			if(reSet != null)
			reSet.close();
			pstmt.close();
		}
		return apps_id;
	}
	
	public static void main(String[] args) throws DataBaseOperationFailedException {
		LOGGER.debug(">>>>>>> "+new ApplicationDAO().getAllServiceByUserId(7));
	}
	

}
