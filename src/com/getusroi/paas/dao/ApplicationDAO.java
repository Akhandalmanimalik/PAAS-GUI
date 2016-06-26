package com.getusroi.paas.dao;

import static com.getusroi.paas.helper.PAASConstant.MYSQL_DB;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getusroi.paas.db.helper.DataBaseConnectionFactory;
import com.getusroi.paas.db.helper.DataBaseHelper;
import com.getusroi.paas.helper.PAASConstant;
import com.getusroi.paas.helper.PAASErrorCodeExceptionHelper;
import com.getusroi.paas.vo.Applications;
import com.getusroi.paas.vo.EnvironmentVariable;
import com.getusroi.paas.vo.Service;
import com.paas_gui.dbconnection.DBConnection;

public class ApplicationDAO {
	static final Logger LOGGER = LoggerFactory.getLogger(ApplicationDAO.class);
	private static final String NETWORK_TYPE = "BRIDGE";

	private static final String INSERT_INTO_APPLICATION = "insert into applications(applications_name,description,tenant_id) values(?,?,?)";
	private static final String GET_ALL_APPLICATIONS_BY_TENANT_ID = "select * from applications where tenant_id=?";
	private static final String CHECK_APPLICATION_EXIST_WITH_GIVEN_APPL_NAME="select apps_id from applications where applications_name=? and tenant_id=?";
	private static final String GET_APPLICATION_ID_BY_NAME_AND_TENANT_ID = "select apps_id from applications where applications_name=? && tenant_id=?";
	private static final String UPDATE_APPLICATION_BY_ALL_DETAILS_FOR_GIVE_ID = "UPDATE  applications set applications_name=?,description =? where apps_id=?";
	private static final String DELETE_APPLICATION_BY_ID_AND_BY_TENANT_ID = "delete from applications where apps_id=? and tenant_id=?";	/*and apps_id=?*/

	private static final String INSERT_APPLICATION_SERVICE_QUERY = "insert into application (service_name,registry_url,tag,run,host_name,host_port,container_port,protocol_type,port_index,path,interval_seconds,timeout_seconds,max_consecutive_failures,grace_period_seconds,ignore_http1xx,instance_count,host_path,container_path,volume,subnet_id,createdDTM,tenant_id,registry_id,container_id,apps_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?,?,?,?)";
	private static final String GET_SERVICE_BY_NAME_AND_USERID = "select * from application where service_name=? && tenant_id=?";	
	//BOTH ARE SAME QUERY
	private static final String GET_ALL_SERVICES_BY_TENANTID_BY_SERVICE_ID = "select * from application where apps_id=? and tenant_id=?"; //CURRENTLY USED
	private static final String GET_ALL_APPLICATION_SERVICE_BY_TENANT_ID_QUERY_ = "select * from application where tenant_id=? and apps_id=?";	//CURRENTLY  NOT USED
	private static final String DELETE_SERVICE_BY_SERVICE_ID = "delete from application where app_id=?";	/*and apps_id=?*/
	private static final String UPDATE_SERVICE_BY_SERVICE_ID = "update application set service_name=?,registry_url=?,tag=?,run=?,host_name=?,host_port=?,container_port=?,protocol_type=?,port_index=?,path=?,interval_seconds=?,timeout_seconds=?,max_consecutive_failures=?,grace_period_seconds=?,ignore_http1xx=?,instance_count=?,host_path=?,container_path=?,volume=?,subnet_id=?,tenant_id=?,registry_id=?,container_id=?,apps_id=? where app_id=?";
	
	
	//Should be create EnvironmentVariableDAO and move the below query there.
	private static final String GET_ENVIRONMENT_VARIABLE_BY_SERVICENAME = "select * from environment_variable where serviceName =?";
	
	private static final String INSERT_ENVIRONMENT_VARIABLE_DETAILS__QUERY = "insert into application_variable (varible_name,varible_value,app_id,createdDTM) values (?,?,?,NOW())";	
	private static final String GET_APPLICATION_VARIABLE_BY_SERVICE_ID= "select * from application_variable where app_id =?";
	private static final String UPDATE_APPLICATION_VARIABLE_BY_SERVICE_ID ="update application_variable set varible_name=?, varible_value=? where id=?";
	
	DataBaseConnectionFactory connectionFactory = null;
	SubnetDAO subnetDao = null;	
	PoliciesDAO policiesDAO = null;
	ImageRegistryDAO imRegistryDAO = null;
	ContainerTypesDAO  containerTypesDAO=null; 

	/**
	 * This method is used to add Application to db
	 * 
	 * @param addApplication
	 *            : addApplication Object
	 * @throws DataBaseOperationFailedException
	 *             : Error in adding application to db
	 */
	public void updateApplication(Applications applications)
			throws DataBaseOperationFailedException {
		LOGGER.debug(".updateApplication method of ApplicationDAO");
		JSONObject jsonObject=new JSONObject();
		DataBaseConnectionFactory connectionFactory = new DataBaseConnectionFactory();
		Connection connection = null;
		PreparedStatement pstmt = null;
		LOGGER.debug(" Applications Details =  "+applications);
		try {
			connection = connectionFactory.getConnection(MYSQL_DB);
		pstmt = (PreparedStatement) connection
					.prepareStatement(UPDATE_APPLICATION_BY_ALL_DETAILS_FOR_GIVE_ID);
			
			pstmt.setString(1, applications.getApplicantionName());
			pstmt.setString(2, applications.getDescription());
			pstmt.setInt(3, applications.getApps_id());
			pstmt.executeUpdate();
			
			LOGGER.debug("Application updated sucessfull with Id="+applications.getApps_id());		
			} catch (ClassNotFoundException | IOException e) {
			LOGGER.error( "Unable to update data for applications into db with data : " + applications, e);
			throw new DataBaseOperationFailedException(
					"Unable to update data for applications into db with data :  "
							+ applications, e);
		} catch (SQLException e) {
			jsonObject.put("id", "error");

			e.printStackTrace();
			if (e.getErrorCode() == 1064) {
				String message = "Unable to update data for applications into db with because: "
						+ PAASErrorCodeExceptionHelper
								.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if (e.getErrorCode() == 1146) {

				String message = "Unable to update data for applications into db with because: "
						+ PAASErrorCodeExceptionHelper
								.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);

			} else
				throw new DataBaseOperationFailedException(
						"Unable to update data for applications into db with data :  "
								+ applications, e);
		} finally {
			DataBaseHelper.dbCleanUp(connection, pstmt);
		}
	
		//return jsonObject.toString();
	}// end of method addApplication
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * This method is used to add Application to db
	 * 
	 * @param addApplication
	 *            : addApplication Object
	 * @throws DataBaseOperationFailedException
	 *             : Error in adding application to db
	 */
	public String addApplication(Applications applications)
			throws DataBaseOperationFailedException {
		LOGGER.debug(".addApplication method of ApplicationDAO");
		JSONObject jsonObject=new JSONObject();
		DataBaseConnectionFactory connectionFactory = new DataBaseConnectionFactory();
		Connection connection = null;
		PreparedStatement pstmt = null;
		int apps_id=0;
		
		try {
			connection = connectionFactory.getConnection(MYSQL_DB);
		pstmt = (PreparedStatement) connection
					.prepareStatement(INSERT_INTO_APPLICATION,Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setString(1, applications.getApplicantionName());
			pstmt.setString(2, applications.getDescription());
			pstmt.setInt(3, applications.getTenant_id());
			pstmt.executeUpdate();
			ResultSet resultSet=pstmt.getGeneratedKeys();
			if(resultSet.next()){
				apps_id=resultSet.getInt(1);
			}
			jsonObject.put("id", apps_id);
			LOGGER.debug("Application inserted sucessfull with Id="+apps_id);		
			} catch (ClassNotFoundException | IOException e) {
			LOGGER.error( "Unable to insert data for applications into db with data : " + applications, e);
			throw new DataBaseOperationFailedException(
					"Unable to insert data for applications into db with data :  "
							+ applications, e);
		} catch (SQLException e) {
			jsonObject.put("id", "error");

			e.printStackTrace();
			if (e.getErrorCode() == 1064) {
				String message = "Unable to insert data for applications into db with because: "
						+ PAASErrorCodeExceptionHelper
								.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if (e.getErrorCode() == 1146) {

				String message = "Unable to insert data for applications into db with because: "
						+ PAASErrorCodeExceptionHelper
								.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);

			} else
				throw new DataBaseOperationFailedException(
						"Unable to insert data for applications into db with data :  "
								+ applications, e);
		} finally {
			DataBaseHelper.dbCleanUp(connection, pstmt);
		}
		return apps_id+"";
		//return jsonObject.toString();
	}// end of method addApplication
	
	
	
	
	
	
	/**
	 * This method is used to get All applicant summary from db
	 * 
	 * @return List<ApplicantSummary> : List of ApplicantSummary Object
	 * @throws DataBaseOperationFailedException
	 *             : Unable to get all applicant summary
	 */
	public List<Service> getAllServiceByAppsIdAndTenantID(int apps_id,int tennat_Id)
			throws DataBaseOperationFailedException {
		LOGGER.debug(".getAllServiceByAppsIdAndTenantID method of ApplicationDAO");
		DataBaseConnectionFactory connectionFactory = new DataBaseConnectionFactory();
		imRegistryDAO = new ImageRegistryDAO();
		containerTypesDAO = new ContainerTypesDAO();
		subnetDao = new SubnetDAO();
		policiesDAO = new PoliciesDAO();
		List<Service> serviceList = new LinkedList<Service>();
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			connection = connectionFactory.getConnection(MYSQL_DB);
			stmt = connection.prepareStatement(GET_ALL_SERVICES_BY_TENANTID_BY_SERVICE_ID);
			stmt.setInt(1,apps_id);
			stmt.setInt(2,tennat_Id);

			result = stmt.executeQuery();
			if (result != null) {
				while (result.next()) {
					Service service = new Service();
					service.setId(result.getInt(1));
					service.setServiceName(result.getString(2));
					service.setImageRepository(result.getString(3));
					service.setTag(result.getString(4));
					service.setRun(result.getString(5));
					service.setHostName(result.getString(6));
					service.setHostPort(result.getInt(7));
					service.setContainerPort(result.getInt(8));
					service.setProtocal(result.getString(9));
					//port index is missing 10 index
					service.setEnvPath(result.getString(11));
					service.setEnvInterval(result.getInt(12));
					//timeout seconds missing 13 index
					service.setEnvThreshold(result.getInt(14));
					//grace_period_seconds missing 15 index
					service.setEnvIgnore(result.getInt(16));
					//instance count missing 17 index
					//host path missing 18 index
					//container path missing 19 index
					service.setVolume(result.getInt(20));
					service.setSubnetName(subnetDao.getSubnetNameById(result.getInt(21)));
					//date time index 22
					service.setTenantId(result.getInt(23));
					service.setImageRegistry(imRegistryDAO.getImageRegistryNameById(result.getInt(24), tennat_Id));
					service.setType(policiesDAO.getContainerTypeNameById(result.getInt(25), service.getTenantId())); 
					service.setAppsId(result.getInt(26));
					service.setNetwork_bridge(NETWORK_TYPE);
					service.setEnv(getApplicationVariableByServiceId(service.getId()));
					serviceList.add(service);
				}
			} else {
				LOGGER.debug("No data avilable in sevice table");
			}
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Unable to fetch serviceinto db ");
			throw new DataBaseOperationFailedException(
					"Unable to fetch Service ", e);
		} catch (SQLException e) {
			if (e.getErrorCode() == 1064) {
				String message = "Unable to fetch Service into db because: "
						+ PAASErrorCodeExceptionHelper
								.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if (e.getErrorCode() == 1146) {
				String message = "Unable to fetch Service into db because: "
						+ PAASErrorCodeExceptionHelper
								.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException(
						"Unable to fetch Service ", e);
		} finally {
			DataBaseHelper.dbCleanup(connection, stmt, result);
		}
		return serviceList;
	}// end of method getAllServiceByAppsIdAndTenantID

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
		imRegistryDAO = new ImageRegistryDAO();
		subnetDao = new SubnetDAO();
		containerTypesDAO = new ContainerTypesDAO();
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
			pstmt.setInt(20, subnetDao.getSubnetIdBySubnetName(service.getSubnetName(),service.getTenantId()));
			pstmt.setInt(21, service.getTenantId());
			pstmt.setInt(22, imRegistryDAO.getImageRegistryIdByName(service.getImageRegistry(), service.getTenantId()));
			pstmt.setInt(23, containerTypesDAO.getContainerTypeIdByContainerName(service.getType()));
			pstmt.setInt(24, service.getAppsId());
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
		subnetDao = new SubnetDAO();
		policiesDAO = new PoliciesDAO();
		imRegistryDAO = new ImageRegistryDAO();
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
					service.setId(result.getInt(1));
					service.setServiceName(result.getString(2));
					service.setImageRepository(result.getString(3));
					service.setTag(result.getString(4));
					service.setRun(result.getString(5));
					service.setHostName(result.getString(6));
					service.setHostPort(result.getInt(7));
					service.setContainerPort(result.getInt(8));
					service.setProtocal(result.getString(9));
					//port index is missing 10 index
					service.setEnvPath(result.getString(11));
					service.setEnvInterval(result.getInt(12));
					//timeout seconds missing 13 index
					service.setEnvThreshold(result.getInt(14));
					//grace_period_seconds missing 15 index
					service.setEnvIgnore(result.getInt(16));
					//instance count missing 17 index
					//host path missing 18 index
					//container path missing 19 index
					service.setVolume(result.getInt(20));
					service.setSubnetName(subnetDao.getSubnetNameById(result.getInt(21)));
					//date time index 22
					service.setTenantId(result.getInt(23));
					service.setImageRegistry(imRegistryDAO.getImageRegistryNameById(result.getInt(24), user_id));
					service.setType(policiesDAO.getContainerTypeNameById(result.getInt(25), service.getTenantId())); 
					service.setAppsId(result.getInt(26));
					service.setNetwork_bridge(NETWORK_TYPE);
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
	 * This method is used to delete service from db by service Id
	 * 
	 * @param serviceId
	 *            : service id in String
	 * @throws DataBaseOperationFailedException
	 *             : Unable to delete service from db using service Id
	 */
	public void deleteServiceByServiceId(Service service)
			throws DataBaseOperationFailedException {
		LOGGER.debug(".deleteServiceByServiceId method of ApplicationDAO");
		DataBaseConnectionFactory connectionFactory = new DataBaseConnectionFactory();
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = connectionFactory.getConnection(MYSQL_DB);
			// "delete from application where service_name=? and tenant_id=? and apps_id=?";
			pstmt = (PreparedStatement) connection.prepareStatement(DELETE_SERVICE_BY_SERVICE_ID);
			pstmt.setInt(1, service.getId());
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

	


	public void deleteApplicationById(int  apps_id,int tenant_Id)
			throws DataBaseOperationFailedException {
		LOGGER.debug(".deleteApplicationById method of ApplicationDAO");
		DataBaseConnectionFactory connectionFactory = new DataBaseConnectionFactory();
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = connectionFactory.getConnection(MYSQL_DB);
			pstmt = (PreparedStatement) connection.prepareStatement(DELETE_APPLICATION_BY_ID_AND_BY_TENANT_ID);
			pstmt.setInt(1, apps_id);
			pstmt.setInt(2,tenant_Id );
			//pstmt.setInt(3, service.getAppsId());
			pstmt.executeUpdate();
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Unable to delete   data for application from db : "
					+ apps_id);
			throw new DataBaseOperationFailedException(
					"Unable to delete  data for applications from db " + apps_id,
					e);
		} catch (SQLException e) {
			if (e.getErrorCode() == 1064) {
				String message = "Unable to delete   data for applications from db because: "
						+ PAASErrorCodeExceptionHelper
								.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if (e.getErrorCode() == 1146) {
				String message = "Unable to delete   data for applications from db because: "
						+ PAASErrorCodeExceptionHelper
								.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException(
						"Unable to delete  data for applications from db "
								+ apps_id, e);
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
	public  int getApplicationsIdByName(String applicationName, int tenantId) throws DataBaseOperationFailedException,
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
	
	
	/**
	 * This method used for check servicename is exist for given userId or not 
	 * @param connection
	 * @param addService
	 * @return Boolean
	 * @throws DataBaseOperationFailedException
	 * @throws SQLException
	 */
	/*private boolean checkApplicationExistByNameAndTenantId(String applicationsName, int tenant_id) throws DataBaseOperationFailedException,
		SQLException {
		LOGGER.debug(".checkApplicationExistByNameAndTenantId Exist (.) method of ApplicationDAO with Applicationname "+applicationsName+" tenant id :"+tenant_id);
		boolean isServiceExist = false;
		PreparedStatement statement = null;
		ResultSet reSet = null;
		connectionFactory = new DataBaseConnectionFactory();
		Connection connection = null; 
		try {
			connection=connectionFactory.getConnection("mysql");
			statement = connection .prepareStatement(CHECK_APPLICATION_EXIST_WITH_GIVEN_APPL_NAME);
			LOGGER.debug("statement "+statement);
			statement.setString(1, applicationsName);
			statement.setInt(2, tenant_id);
			reSet = statement.executeQuery();
			if(reSet != null){
				while (reSet.next()) {
					return isServiceExist=true;
				}
			}
		}catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Error in getting the vpc detail from db");
			throw new DataBaseOperationFailedException("Error in fetching the vpc from db",e);
		}  
		catch (SQLException e) {
			LOGGER.error("Unable to fetch   application :"
					+ applicationsName + "   from db with tenant_id="
					+ tenant_id);
			throw new DataBaseOperationFailedException(
					"Unable to fetch   application" + applicationsName
							+ " from db", e);
		} finally {
			if(reSet != null)
			reSet.close();
			statement.close();
		}
		return isServiceExist;
	}*/
	
	/**
	* This method used for check servicename is exist for given userId or not
	* @param connection
	* @param addService
	* @return Boolean
	* @throws DataBaseOperationFailedException
	* @throws SQLException
	*/
	public int checkApplicationExistByNameAndTenantId(String applicationsName,
			int tenant_id) throws DataBaseOperationFailedException,
			SQLException {
		LOGGER.debug(".checkApplicationExistByNameAndTenantId Exist (.) method of ApplicationDAO with Applicationname "
				+ applicationsName + " tenant id :" + tenant_id);
		int isServiceExist = 0;
		PreparedStatement statement = null;
		ResultSet reSet = null;
		DataBaseConnectionFactory connectionFactory = new DataBaseConnectionFactory();
		Connection connection = null;
		try {
			connection = connectionFactory.getConnection("mysql");
			statement = (PreparedStatement) connection
					.prepareStatement(CHECK_APPLICATION_EXIST_WITH_GIVEN_APPL_NAME);
			LOGGER.debug("statement ////////" + statement);
			statement.setString(1, applicationsName);
			statement.setInt(2, tenant_id);
			reSet = statement.executeQuery();
			LOGGER.debug("reSet >>>>>>>>>>>>>." + reSet);
			if (reSet != null) {
				while (reSet.next()) {

					isServiceExist = reSet.getInt("apps_id");

				}
			}
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Error in getting the aplication detail from db");
			throw new DataBaseOperationFailedException(
					"Error in fetching the aplication from db", e);
		} catch (SQLException e) {
			LOGGER.error("Unable to fetch application :" + applicationsName
					+ " from db with tenant_id=" + tenant_id);
			throw new DataBaseOperationFailedException(
					"Unable to fetch application" + applicationsName
							+ " from db", e);
		} finally {
			if (reSet != null)
				reSet.close();
			statement.close();
		}
		return isServiceExist;
	}
	
	
	/**
	 * to get Applications Details By given tenant ID 
	 * @param tenantId
	 * @return
	 */
	public List<Applications> getApplicationsDetailsBytTenantId(int tenantId) {

		LOGGER.debug("(.)  inside getApplicationsDetailsBytTenantId method of Application Dao with tenantID="+tenantId);
		List<Applications> applicationsList = new LinkedList<Applications>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = (Connection) DBConnection.getConnection();
			preparedStatement = (PreparedStatement) connection.prepareStatement(GET_ALL_APPLICATIONS_BY_TENANT_ID);
			preparedStatement.setInt(1, tenantId);
			resultSet = preparedStatement.executeQuery();

			Applications applications = null;
			while (resultSet.next()) {
				applications = new Applications();
				applications.setApps_id(resultSet.getInt(1));
				applications.setApplicantionName(resultSet.getString(2));
				applications.setDescription(resultSet.getString(3));
				applications.setTenant_id(tenantId);
				applicationsList.add(applications);
			}
			LOGGER.info("application list "+applicationsList);

		} catch (SQLException e) {
			LOGGER.error(e.getMessage());

		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				LOGGER.error(e.getMessage());
			}
		}

		return applicationsList;
	}

	

	/**
	 * To update Service by service id only.
	 * @param service
	 * @throws DataBaseOperationFailedException
	 */
	public void updateServiceByServiceId(Service service)
			throws DataBaseOperationFailedException {
		LOGGER.debug(".getAllServiceByAppsIdAndTenantID method of ApplicationDAO");
		DataBaseConnectionFactory connectionFactory = new DataBaseConnectionFactory();
		imRegistryDAO = new ImageRegistryDAO();
		containerTypesDAO = new ContainerTypesDAO();
		subnetDao = new SubnetDAO();
		policiesDAO = new PoliciesDAO();
 		Connection connection = null;
 		PreparedStatement stmt = null;
 		PreparedStatement pstmt2 = null;
		
		
		ResultSet result = null;
		try {
			connection = connectionFactory.getConnection(MYSQL_DB);
			stmt = connection.prepareStatement(UPDATE_SERVICE_BY_SERVICE_ID);
			stmt.setString(1,service.getServiceName()	);
			stmt.setString(2,service.getImageRepository());
			stmt.setString(3, service.getTag());
			stmt.setString(4, service.getRun());
			stmt.setString(5, service.getHostName());
			stmt.setInt(6, service.getHostPort());
			stmt.setInt(7, service.getContainerPort());
			stmt.setString(8, service.getProtocal());
			stmt.setInt(9, 111);
			stmt.setString(10, service.getEnvPath());
			stmt.setInt(11, service.getEnvInterval());
			stmt.setInt(12, 111);
			stmt.setInt(13, service.getEnvThreshold());
			stmt.setInt(14, 111);
			stmt.setInt(15, service.getEnvIgnore());
			stmt.setInt(16, 111);	
			stmt.setString(17, "dummy");
			stmt.setString(18, "dummy");
			stmt.setInt(19, service.getVolume());
			stmt.setInt(20, subnetDao.getSubnetIdBySubnetName(service.getSubnetName(), service.getTenantId()));
			stmt.setInt(21, service.getTenantId());
			stmt.setInt(22, imRegistryDAO.getImageRegistryIdByName(service.getImageRegistry(), service.getTenantId()));
			stmt.setInt(23, containerTypesDAO.getContainerTypeIdByContainerName(service.getType()));
			stmt.setInt(24, service.getAppsId());
			stmt.setInt(25, service.getId());
			
			stmt.execute();
			
			pstmt2 = (PreparedStatement) connection
						.prepareStatement(UPDATE_APPLICATION_VARIABLE_BY_SERVICE_ID);
	            List<EnvironmentVariable> listOfEnvrnmtVar = service.getEnv(); 
	            LOGGER.debug("Number of environment variabl "+listOfEnvrnmtVar.size());
	            EnvironmentVariable env= null;
	            for(int i=0; i < listOfEnvrnmtVar.size(); i++){
	            	 env=listOfEnvrnmtVar.get(i);
	            	LOGGER.debug("Environmentvariable object  "+env);
	            	pstmt2.setString(1, env.getEnvkey());
	    			pstmt2.setString(2, env.getEnvvalue());
	    			pstmt2.setInt(3, service.getId());
	    			pstmt2.executeUpdate();
	            }
			
			  
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Unable to Update service into db ");
			throw new DataBaseOperationFailedException(
					"Unable to fetch Service ", e);
		} catch (SQLException e) {
			if (e.getErrorCode() == 1064) {
				String message = "Unable to Update Service into db because: "
						+ PAASErrorCodeExceptionHelper
								.exceptionFormat(PAASConstant.ERROR_IN_SQL_SYNTAX);
				throw new DataBaseOperationFailedException(message, e);
			} else if (e.getErrorCode() == 1146) {
				String message = "Unable to Update Service into db because: "
						+ PAASErrorCodeExceptionHelper
								.exceptionFormat(PAASConstant.TABLE_NOT_EXIST);
				throw new DataBaseOperationFailedException(message, e);
			} else
				throw new DataBaseOperationFailedException(
						"Unable to Update Service ", e);
		} finally {
			DataBaseHelper.dbCleanup(connection, stmt, result);
		}
		 
	}// end of method getAllServiceByAppsIdAndTenantID
	
	/**
	 * To get application_variable by  service Id
	 * @param serviceId
	 * @return
	 * @throws DataBaseOperationFailedException
	 * @throws SQLException
	 */
	public List<EnvironmentVariable> getApplicationVariableByServiceId(int serviceId) throws DataBaseOperationFailedException,
			SQLException {
		 
		List<EnvironmentVariable> listOfEnvs = new ArrayList<EnvironmentVariable>();
		PreparedStatement pstmt = null;
		ResultSet reSet = null;
		DataBaseConnectionFactory connectionFactory = new DataBaseConnectionFactory();
		Connection connection = null;
		try {
			connection = connectionFactory.getConnection("mysql");
			pstmt = (PreparedStatement) connection
					.prepareStatement(GET_APPLICATION_VARIABLE_BY_SERVICE_ID);
			pstmt.setInt(1, serviceId);
			reSet = pstmt.executeQuery();
			LOGGER.debug("reSet >>>>>>>>>>>>>." + reSet);
			if (reSet != null) {
				while (reSet.next()) {
					EnvironmentVariable envVar = new EnvironmentVariable();
					envVar.setId(reSet.getInt(1));
					envVar.setEnvkey(reSet.getString(2));
					envVar.setEnvvalue(reSet.getString(3));
					envVar.setAppId(reSet.getInt(4));
					listOfEnvs.add(envVar);
				}
			} else {
				LOGGER.debug("No data available for environment varible for service name : "
						+ serviceId);
			}
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error("Error in getting the aplication detail from db");
			throw new DataBaseOperationFailedException(
					"Error in fetching the aplication from db", e);
		} catch (SQLException e) {
			LOGGER.error("Unable to fetch application :" + serviceId
					+ " from db with tenant_id=" + serviceId);
			throw new DataBaseOperationFailedException(
					"Unable to fetch application" + serviceId
							+ " from db", e);
		} finally {
			if (reSet != null)
				reSet.close();
			pstmt.close();
		}
		return listOfEnvs;
	}
	
	
}
