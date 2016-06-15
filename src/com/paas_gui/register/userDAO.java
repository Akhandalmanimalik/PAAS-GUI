package com.paas_gui.register;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.log4j.Logger;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.paas_gui.dbconnection.DBConnection;
import com.paas_gui.vpc.Acl;
import com.paas_gui.vpc.AddService;
import com.paas_gui.vpc.ApplicantSummary;
import com.paas_gui.vpc.ApplicantUser;
import com.paas_gui.vpc.CloudProviders;
import com.paas_gui.vpc.ContainerTypes;
import com.paas_gui.vpc.EnvironmentTypes;
import com.paas_gui.vpc.EnvironmentVariable;
import com.paas_gui.vpc.Environments;
import com.paas_gui.vpc.FirewallInbounds;
import com.paas_gui.vpc.FirewallOutbounds;
import com.paas_gui.vpc.HostScalingPolicy;
import com.paas_gui.vpc.ImageRegistry;
import com.paas_gui.vpc.MarathonRest;
import com.paas_gui.vpc.ResourceSelection;
import com.paas_gui.vpc.Route;
import com.paas_gui.vpc.Scale;
import com.paas_gui.vpc.ScalingAndRecovery;
import com.paas_gui.vpc.ServiceAffinities;
import com.paas_gui.vpc.Storage;
import com.paas_gui.vpc.Subnet;
import com.paas_gui.vpc.Vpc_pozo;

public class userDAO {

	private static final String GET_ALL_APPLICATIONS = "select * from applications";
	private static final String REMOVE_APPLICATION_FROM_DB = "delete from applications where applications_name = ?";
	/*
	 * public static void main(String[] args) { new
	 * userDAO().getvpcId("vpv-01"); }
	 */

	/* SIGN UP */

	final Logger LOGGER = Logger.getLogger(userDAO.class);
	private static final String INSERTSQL = "INSERT INTO register VALUES(?,?,?,?)";
	private static final String  INSERT_APPLICATION_DETAILS= "INSERT INTO applications(applications_name,description,tenant_id) VALUES(?,?,?)";
	public static Logger log = Logger.getLogger(userDAO.class);

	public boolean viewdata(Employee user) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = (Connection) DBConnection.getConnection();
			pstmt = (PreparedStatement) con.prepareStatement(INSERTSQL);
			pstmt.setString(1, user.getCompany_name());
			pstmt.setString(2, user.getCompany_address());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getPassword());
			pstmt.executeUpdate();
			LOGGER.info("Data Inserted");
		} catch (SQLException sqlexp) {
			log.error(sqlexp.getMessage());
		}
		return true;
	}

	/* VPC PAGE */
	public List<ApplicantUser> selectApplicantName() {

		List<ApplicantUser> customers = new LinkedList<ApplicantUser>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = (Connection) DBConnection.getConnection();
			preparedStatement = (PreparedStatement) connection.prepareStatement(GET_ALL_APPLICATIONS);
			resultSet = preparedStatement.executeQuery();

			ApplicantUser customer = null;
			while (resultSet.next()) {
				customer = new ApplicantUser();
				customer.setApplicantionName(resultSet.getString(2));
				customer.setDescription(resultSet.getString(3));
				customers.add(customer);
			}

		} catch (SQLException e) {
			LOGGER.error(e.getMessage());

		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				LOGGER.error(e.getMessage());
			}
		}

		return customers;
	}

	public List<ApplicantSummary> getApplicationNameSummary() {

		List<ApplicantSummary> customers = new LinkedList<ApplicantSummary>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = (Connection) DBConnection.getConnection();
			String sql = "select applicantName from appsummary";
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			ApplicantSummary customer = null;
			while (resultSet.next()) {

				customer = new ApplicantSummary();
				customer.setApplicantionName(resultSet.getString(1));

				customers.add(customer);
			}

		} catch (SQLException e) {
			LOGGER.error(e.getMessage());

		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				LOGGER.error(e.getMessage());
			}
		}
		return customers;
	}

	public List<Vpc_pozo> selectVpc() {

		List<Vpc_pozo> customers = new LinkedList<Vpc_pozo>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = (Connection) DBConnection.getConnection();
			String sql = "select * from vpc";
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			Vpc_pozo customer = null;
			while (resultSet.next()) {

				customer = new Vpc_pozo();
				customer.setVpc_name(resultSet.getString(1));
				customer.setVpc_region(resultSet.getString(2));
				customer.setCidr(resultSet.getString(3));
				customer.setAcl(resultSet.getString(4));
				customer.setVpcId(resultSet.getString(5));

				customers.add(customer);
			}

		} catch (SQLException e) {
			LOGGER.error(e.getMessage());

		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				LOGGER.error(e.getMessage());
			}
		}

		return customers;
	}

	public List<AddService> selectServices() {
		List<AddService> customers = new LinkedList<AddService>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet addServiceResultSet = null;
		try {
			connection = (Connection) DBConnection.getConnection();
			String sql = "select * from addService";
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			addServiceResultSet = preparedStatement.executeQuery();
			AddService customer = null;
			while (addServiceResultSet.next()) {
				customer = new AddService();
				customer.setServiceName(addServiceResultSet.getString("serviceName"));
				customer.setType(addServiceResultSet.getString("type"));
				customer.setApplicantionName(addServiceResultSet.getString("applicantionName"));
				customer.setImageRegistry(addServiceResultSet.getString("imageRegistry"));
				customer.setImageRegistry(addServiceResultSet.getString("imageRepository"));
				customer.setTag(addServiceResultSet.getString("tag"));
				customer.setRun(addServiceResultSet.getString("run"));
				customer.setHostname(addServiceResultSet.getString("hostname"));
				customer.setTypename(addServiceResultSet.getString("typename"));
				customer.setEnvirnament(addServiceResultSet.getString("envirnament"));
				customer.setEnvpath(addServiceResultSet.getString("envpath"));
				customer.setEnvinterval(addServiceResultSet.getString("envinterval"));
				customer.setEnvtimeout(addServiceResultSet.getString("envtimeout"));
				customer.setEnvthresold(addServiceResultSet.getString("envthresold"));
				customer.setEnvignore(addServiceResultSet.getString("envignore"));
				// READING ENVIRONMENT_VARIABLE
				String READ_ENVIRONMENT_VARIABLE = "select * from environment_variable where serviceName =?";
				PreparedStatement envPreparedStatement = (PreparedStatement) connection
						.prepareStatement(READ_ENVIRONMENT_VARIABLE);
				envPreparedStatement.setString(1, customer.getServiceName());
				ResultSet envResultSet = envPreparedStatement.executeQuery();
				List<EnvironmentVariable> listOfEnvs = new ArrayList<>();
				EnvironmentVariable envVar = null;
				while (envResultSet.next()) {
					envVar = new EnvironmentVariable();
					envVar.setEnvkey(envResultSet.getString(1));
					envVar.setEnvvalue(envResultSet.getString(2));
					listOfEnvs.add(envVar);
				}
				customer.setEnv(listOfEnvs);
				// READING ROUTE
				String READ_ROUTE = "select * from route where serviceName =?";
				PreparedStatement routeReparedStatement = (PreparedStatement) connection.prepareStatement(READ_ROUTE);
				routeReparedStatement.setString(1, customer.getServiceName());
				ResultSet routeResultSet = routeReparedStatement.executeQuery();
				List<Route> listOfRoute = new ArrayList<>();
				Route routeVar = null;
				while (routeResultSet.next()) {
					routeVar = new Route();
					routeVar.setType(routeResultSet.getString("typename"));
					routeVar.setPort(routeResultSet.getString("portname"));
					routeVar.setRoutetype(routeResultSet.getString("routetype"));
					routeVar.setTarget(routeResultSet.getString("target"));
					listOfRoute.add(routeVar);
				}
				customer.setRoute(listOfRoute);
				// READING Netwrok_policy
				String policy = "select * from network_policy where serviceName =?";
				PreparedStatement scaleReparedStatement = (PreparedStatement) connection.prepareStatement(policy);
				scaleReparedStatement.setString(1, customer.getServiceName());
				ResultSet scaleResultSet = scaleReparedStatement.executeQuery();
				List<Scale> listOfscale = new ArrayList<>();
				Scale scale = null;
				while (scaleResultSet.next()) {
					scale = new Scale();
					scale.setPortname(scaleResultSet.getString(1));
					scale.setPorttype(scaleResultSet.getString(2));
					scale.setHostport(scaleResultSet.getString(3));
					scale.setContainerport(scaleResultSet.getString(4));
					listOfscale.add(scale);
				}
				customer.setScales(listOfscale);
				customers.add(customer);
			} // customers.add(customer);
			System.out.println("size of custmere " + customers.size());
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		} finally {
			try {
				addServiceResultSet.close();
			} catch (SQLException e) {
				LOGGER.error(e.getMessage());
			}
		}
		return customers;
	}

	public List<Environments> selectEnvirnamentList() {

		List<Environments> customers = new ArrayList<Environments>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = (Connection) DBConnection.getConnection();
			String sql = "select * from envirnament";
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			Environments customer = null;
			while (resultSet.next()) {

				customer = new Environments();
				customer.setContainername(resultSet.getString("containername"));
				customer.setService(resultSet.getString("service"));
				customer.setTag(resultSet.getString("tag"));
				customer.setHost("192.168.1.219");
				String ipadde = MarathonRest.getgatewayRoute();
				customer.setIpadress(ipadde);

				customer.setState(resultSet.getString("state"));

				customers.add(customer);
			}

		} catch (SQLException e) {
			LOGGER.error(e.getMessage());

		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				LOGGER.error(e.getMessage());
			}
		}

		return customers;
	}

	public boolean viewVpc(Vpc_pozo user) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = (Connection) DBConnection.getConnection();
			pstmt = (PreparedStatement) con.prepareStatement("INSERT INTO vpc VALUES(?,?,?,?,?)");
			pstmt.setString(1, user.getVpc_name());
			pstmt.setString(2, user.getVpc_region());
			pstmt.setString(3, user.getCidr());
			pstmt.setString(4, user.getAcl());
			pstmt.setString(5, user.getVpcId());
			pstmt.executeUpdate();
			LOGGER.info("Data Inserted");
		} catch (SQLException sqlexp) {
			log.error(sqlexp.getMessage());
		}
		return true;
	}

	public boolean storeEnvironments(Environments user) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = (Connection) DBConnection.getConnection();
			pstmt = (PreparedStatement) con.prepareStatement("INSERT INTO envirnament VALUES(?,?,?,?,?,?)");
			pstmt.setString(1, user.getContainername());
			pstmt.setString(2, user.getService());
			pstmt.setString(3, user.getTag());
			pstmt.setString(4, user.getHost());
			pstmt.setString(5, user.getIpadress());
			pstmt.setString(6, user.getState());
			pstmt.executeUpdate();
			LOGGER.info("Data Inserted");
		} catch (SQLException sqlexp) {
			log.error(sqlexp.getMessage());
		}
		return true;
	}

	public boolean storeStorage(Storage storage) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = (Connection) DBConnection.getConnection();
			pstmt = (PreparedStatement) con.prepareStatement("INSERT INTO storage VALUES(?,?,?)");
			pstmt.setString(1, storage.getServiceName());
			pstmt.setString(2, storage.getTag());
			pstmt.setString(3, storage.getVolumeSize());
			pstmt.executeUpdate();
			LOGGER.info("Data Inserted");
		} catch (SQLException sqlexp) {
			log.error(sqlexp.getMessage());
		}
		return true;
	}

	public boolean storeServices(AddService user) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = (Connection) DBConnection.getConnection();
			pstmt = (PreparedStatement) con
					.prepareStatement("INSERT INTO addService VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setString(1, user.getServiceName());
			pstmt.setString(2, user.getType());
			pstmt.setString(3, user.getApplicantionName());
			pstmt.setString(4, user.getImageRegistry());
			pstmt.setString(5, user.getImageRepository());
			pstmt.setString(6, user.getTag());
			pstmt.setString(7, user.getRun());
			pstmt.setString(8, user.getHostname());
			pstmt.setString(9, user.getTypename());
			pstmt.setString(10, user.getEnvirnament());
			pstmt.setString(11, user.getEnvpath());
			pstmt.setString(12, user.getEnvinterval());
			pstmt.setString(13, user.getEnvtimeout());
			pstmt.setString(14, user.getEnvthresold());
			pstmt.setString(15, user.getEnvignore());
			pstmt.setString(16, user.getVolume());
			pstmt.executeUpdate();
			// pstmt.addBatch();
			// private String
			// GET_PRIMARYKEY_SERVICENAME="select serviceName from addService
			// where serviceName";

			String INSERT_ENVIRONMENT_VARIABLE = "insert into environment_variable values(?,?,?)";
			pstmt = (PreparedStatement) con.prepareStatement(INSERT_ENVIRONMENT_VARIABLE);
			List<EnvironmentVariable> envVars = user.getEnv();

			System.out.println("envVars.size() " + envVars.size());

			for (EnvironmentVariable envVar : envVars) {
				System.out.println("lkjkjhkjghfg");
				pstmt.setString(1, envVar.getEnvkey());
				pstmt.setString(2, envVar.getEnvvalue());
				pstmt.setString(3, user.getServiceName());
				pstmt.executeUpdate();
				// pstmt.addBatch();
			}

			String INSERT_NETWORK_POLICY = "insert into network_policy values(?,?,?,?,?)";
			pstmt = (PreparedStatement) con.prepareStatement(INSERT_NETWORK_POLICY);
			List<Scale> scales = user.getScales();

			System.out.println("envVars.size() " + scales.size());

			for (Scale scale : scales) {
				System.out.println("lkjkjhkjghfg");
				pstmt.setString(1, scale.getPortname());
				pstmt.setString(2, scale.getPorttype());
				pstmt.setString(3, scale.getHostport());
				pstmt.setString(4, scale.getContainerport());
				pstmt.setString(5, user.getServiceName());
				pstmt.executeUpdate();
				// pstmt.addBatch();
			}

			String INSERT_ROUTE = "insert into route values(?,?,?,?,?)";
			pstmt = (PreparedStatement) con.prepareStatement(INSERT_ROUTE);
			List<Route> routes = user.getRoute();

			System.out.println("envVars.size() " + routes.size());

			for (Route route : routes) {
				System.out.println("lkjkjhkjghfg");
				pstmt.setString(1, route.getType());
				pstmt.setString(2, route.getPort());
				pstmt.setString(3, route.getRoutetype());
				pstmt.setString(4, route.getTarget());
				pstmt.setString(5, user.getServiceName());
				pstmt.executeUpdate();
				// pstmt.addBatch();
			}

			/*
			 * int[] noOfRecords=pstmt.executeBatch();
			 * System.out.println("KKKKKKKKKKKKKKKK"+noOfRecords); for(int
			 * i:noOfRecords){ System.out.println(i); }
			 */
			System.out.println("inserted successflyll");
			LOGGER.info("Data Inserted");
		} catch (SQLException sqlexp) {
			log.error(sqlexp.getMessage());
		}
		return true;
	}

	public boolean storeApplicant(ApplicantUser user) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = (Connection) DBConnection.getConnection();
			pstmt = (PreparedStatement) con.prepareStatement(INSERT_APPLICATION_DETAILS);
			pstmt.setString(1, user.getApplicantionName());
			pstmt.setString(2, user.getDescription());
			pstmt.setInt(3, user.getTenant_id());
			pstmt.executeUpdate();
			LOGGER.info("Data Inserted");
		} catch (SQLException sqlexp) {
			log.error(sqlexp.getMessage());
		}
		return true;
	}

	public List<ApplicantSummary> selectSummary() {

		List<ApplicantSummary> customers = new LinkedList<ApplicantSummary>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = (Connection) DBConnection.getConnection();
			String sql = "select * from appsummary";
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			ApplicantSummary customer = null;
			while (resultSet.next()) {

				customer = new ApplicantSummary();
				customer.setApplicantionName(resultSet.getString(1));
				customer.setDescription(resultSet.getString(2));
				customer.setImageRegistry(resultSet.getString(3));
				customer.setImageRepository(resultSet.getString(4));
				customer.setTag(resultSet.getString(5));

				customers.add(customer);
			}

		} catch (SQLException e) {
			LOGGER.error(e.getMessage());

		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				LOGGER.error(e.getMessage());
			}
		}

		return customers;
	}

	public boolean storeSummary(ApplicantSummary user) {
		log.debug("inside the storeSummary dao ");

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = (Connection) DBConnection.getConnection();
			pstmt = (PreparedStatement) con.prepareStatement("INSERT INTO appsummary VALUES(?,?,?,?,?)");
			pstmt.setString(1, user.getApplicantionName());
			pstmt.setString(2, user.getDescription());
			pstmt.setString(3, user.getImageRegistry());
			pstmt.setString(4, user.getImageRepository());
			pstmt.setString(5, user.getTag());
			pstmt.executeUpdate();
			LOGGER.info("Data Inserted");
		} catch (SQLException sqlexp) {
			log.error(sqlexp.getMessage());
		}
		return true;
	}

	public void deleteData(String data) {
		LOGGER.info("Inside (.) deleteData of userDAO");
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = (Connection) DBConnection.getConnection();
			preparedStatement = (PreparedStatement) connection.prepareStatement(REMOVE_APPLICATION_FROM_DB);
			preparedStatement.setString(1, data);
			preparedStatement.executeUpdate();

		} catch (SQLException se) {
			LOGGER.error(se.getMessage());
		}

	}

	/* ======================end of vpc DAO========================== */

	/* ======subnet starts======= */

	public List<Subnet> selectSubnet() {

		List<Subnet> customers = new ArrayList<Subnet>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = (Connection) DBConnection.getConnection();
			String sql = "select * from subnet";
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			Subnet customer = null;
			while (resultSet.next()) {

				customer = new Subnet();
				customer.setVpc_name(resultSet.getString(1));
				customer.setSubnet_name(resultSet.getString(2));
				customer.setCidr(resultSet.getString(3));
				customer.setAcl(resultSet.getString(4));
				customer.setSubnetId(resultSet.getString(5));
				customer.setVpcId(resultSet.getString(6));

				customers.add(customer);
			}

		} catch (SQLException e) {
			LOGGER.error(e.getMessage());

		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				LOGGER.error(e.getMessage());
			}
		}

		return customers;
	}

	public List<Acl> selectAcl() {

		List<Acl> customers = new ArrayList<Acl>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = (Connection) DBConnection.getConnection();
			String sql = "select * from acl";
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			Acl customer = null;
			while (resultSet.next()) {

				customer = new Acl();

				customer.setAction(resultSet.getString("action"));
				customer.setSrcIp(resultSet.getString("sourceip"));
				customer.setDestIP(resultSet.getString("destip"));
				customer.setAclName(resultSet.getString("aclname"));

				customers.add(customer);
			}

		} catch (SQLException e) {
			LOGGER.error(e.getMessage());

		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				LOGGER.error(e.getMessage());
			}
		}

		return customers;
	}

	public List<Acl> getAclList() {

		List<Acl> customers = new ArrayList<Acl>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = (Connection) DBConnection.getConnection();
			String sql = "select aclname from acl";
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			Acl customer = null;
			while (resultSet.next()) {

				customer = new Acl();

				customer.setAclName(resultSet.getString("aclname"));

				customers.add(customer);
			}

		} catch (SQLException e) {
			LOGGER.error(e.getMessage());

		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				LOGGER.error(e.getMessage());
			}
		}

		return customers;
	}

	public boolean viewAcl(Acl acl) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = (Connection) DBConnection.getConnection();
			pstmt = (PreparedStatement) con.prepareStatement("INSERT INTO acl VALUES(?,?,?,?)");
			pstmt.setString(1, acl.getAction());
			pstmt.setString(2, acl.getSrcIp());
			pstmt.setString(3, acl.getDestIP());
			pstmt.setString(4, acl.getAclName());

			pstmt.executeUpdate();
			LOGGER.info("Data Inserted  to  acl");
		} catch (SQLException sqlexp) {
			log.error(sqlexp.getMessage());
		}
		return true;
	}

	public boolean viewSubnet(Subnet subnet) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = (Connection) DBConnection.getConnection();
			pstmt = (PreparedStatement) con.prepareStatement("INSERT INTO subnet VALUES(?,?,?,?,?,?)");
			pstmt.setString(1, subnet.getVpc_name());
			pstmt.setString(2, subnet.getSubnet_name());
			pstmt.setString(3, subnet.getCidr());
			pstmt.setString(4, subnet.getAcl());
			pstmt.setString(5, subnet.getSubnetId());
			pstmt.setString(6, subnet.getVpcId());
			pstmt.executeUpdate();
			LOGGER.info("Data Inserted");
		} catch (SQLException sqlexp) {
			log.error(sqlexp.getMessage());
		}
		return true;
	}

	public String getvpcId(String vpcName) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String vpcId = "";
		ResultSet rs = null;
		try {
			con = (Connection) DBConnection.getConnection();
			pstmt = (PreparedStatement) con.prepareStatement("select vpcId from vpc where vpc_name='" + vpcName + "'");

			rs = pstmt.executeQuery();
			while (rs.next()) {
				vpcId = rs.getString("vpcId");
			}

			LOGGER.info("------------vpcId  from  data------------------" + vpcId);
			LOGGER.info("Data Inserted");
		} catch (SQLException sqlexp) {
			log.error(sqlexp.getMessage());
		}
		System.out.println(vpcId);
		return vpcId;
	}

	public String getAclId(String vpcName) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String vpcId = "";
		ResultSet rs = null;
		try {
			con = (Connection) DBConnection.getConnection();
			pstmt = (PreparedStatement) con.prepareStatement("select vpcId from vpc where vpc_name='" + vpcName + "'");

			rs = pstmt.executeQuery();
			while (rs.next()) {
				vpcId = rs.getString("vpcId");
			}

			LOGGER.info("------------vpcId  from  data------------------" + vpcId);
			LOGGER.info("Data Inserted");
		} catch (SQLException sqlexp) {
			log.error(sqlexp.getMessage());
		}
		System.out.println(vpcId);
		return vpcId;
	}

	public void deleteSubnet(String data) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			connection = (Connection) DBConnection.getConnection();
			String sql = "delete from subnet where subnet_name = ?";
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, data);
			preparedStatement.executeUpdate();

		} catch (SQLException se) {
			LOGGER.error(se.getMessage());
		}

	}

	/* =====================END OF SUBNET======================== */
	/* ==============scaling and recovery starts================ */

	public List<ScalingAndRecovery> selectSAR() {

		List<ScalingAndRecovery> customers = new ArrayList<ScalingAndRecovery>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = (Connection) DBConnection.getConnection();
			String sql = "select * from scaling_and_recovery";
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			ScalingAndRecovery customer = null;
			while (resultSet.next()) {

				customer = new ScalingAndRecovery();
				customer.setApplication(resultSet.getString(1));
				customer.setServices(resultSet.getString(2));
				customer.setEnvironment_types(resultSet.getString(3));
				customer.setDesired_count(resultSet.getString(4));
				customer.setAuto_recovery(resultSet.getString(5));

				customers.add(customer);
			}

		} catch (SQLException e) {
			LOGGER.error(e.getMessage());

		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				LOGGER.error(e.getMessage());
			}
		}

		return customers;
	}

	public boolean viewSAR(ScalingAndRecovery sar) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = (Connection) DBConnection.getConnection();
			pstmt = (PreparedStatement) con.prepareStatement("INSERT INTO scaling_and_recovery VALUES(?,?,?,?,?)");
			pstmt.setString(1, sar.getApplication());
			pstmt.setString(2, sar.getServices());
			pstmt.setString(3, sar.getEnvironment_types());
			pstmt.setString(4, sar.getDesired_count());
			pstmt.setString(5, sar.getAuto_recovery());
			pstmt.executeUpdate();
			LOGGER.info("Data Inserted");
		} catch (SQLException sqlexp) {
			log.error(sqlexp.getMessage());
		}
		return true;
	}

	public void deleteSAR(String data) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			connection = (Connection) DBConnection.getConnection();
			String sql = "delete from scaling_and_recovery where application = ?";
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, data);
			preparedStatement.executeUpdate();

		} catch (SQLException se) {
			LOGGER.error(se.getMessage());
		}

	}

	/* =======end======= */

	/* ================HOST SCALING POLICY STARTS================== */

	public List<HostScalingPolicy> selectHSP() {

		List<HostScalingPolicy> customers = new ArrayList<HostScalingPolicy>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = (Connection) DBConnection.getConnection();
			String sql = "select * from host_scaling_policy";
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			HostScalingPolicy customer = null;
			while (resultSet.next()) {

				customer = new HostScalingPolicy();
				customer.setName(resultSet.getString(1));
				customer.setHost_groups(resultSet.getString(2));

				customers.add(customer);
			}

		} catch (SQLException e) {
			LOGGER.error(e.getMessage());

		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				LOGGER.error(e.getMessage());
			}
		}

		return customers;
	}

	public boolean viewHSP(HostScalingPolicy hsp) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = (Connection) DBConnection.getConnection();
			pstmt = (PreparedStatement) con.prepareStatement("INSERT INTO host_scaling_policy VALUES(?,?)");
			pstmt.setString(1, hsp.getName());
			pstmt.setString(2, hsp.getHost_groups());

			pstmt.executeUpdate();

			LOGGER.info("Data Inserted");
		} catch (SQLException sqlexp) {
			log.error(sqlexp.getMessage());
		}
		return true;
	}

	public void deleteHSP(String data) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			connection = (Connection) DBConnection.getConnection();
			String sql = "delete from host_scaling_policy where name = ?";
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, data);
			preparedStatement.executeUpdate();

		} catch (SQLException se) {
			LOGGER.error(se.getMessage());
		}

	}

	/* ============END================= */

	/* ================SERVICE AFFINITIES STARTS================== */

	public List<ServiceAffinities> selectServiceAffinities() {

		List<ServiceAffinities> customers = new ArrayList<ServiceAffinities>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = (Connection) DBConnection.getConnection();
			String sql = "select * from service_affinities";
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			ServiceAffinities customer = null;
			while (resultSet.next()) {

				customer = new ServiceAffinities();
				customer.setApplication(resultSet.getString(1));
				customer.setServices(resultSet.getString(2));
				customer.setEnvironment_types(resultSet.getString(3));
				customer.setAffinity(resultSet.getString(4));

				customers.add(customer);
			}

		} catch (SQLException e) {
			LOGGER.error(e.getMessage());

		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				LOGGER.error(e.getMessage());
			}
		}

		return customers;
	}

	public boolean viewServiceAffinities(ServiceAffinities sa) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = (Connection) DBConnection.getConnection();
			pstmt = (PreparedStatement) con.prepareStatement("INSERT INTO service_affinities VALUES(?,?,?,?)");
			pstmt.setString(1, sa.getApplication());
			pstmt.setString(2, sa.getServices());
			pstmt.setString(3, sa.getEnvironment_types());
			pstmt.setString(4, sa.getAffinity());

			pstmt.executeUpdate();

			LOGGER.info("Data Inserted");
		} catch (SQLException sqlexp) {
			log.error(sqlexp.getMessage());
		}
		return true;
	}

	public void deleteServiceAffinities(String data) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			connection = (Connection) DBConnection.getConnection();
			String sql = "delete from service_affinities where application = ?";
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, data);
			preparedStatement.executeUpdate();

		} catch (SQLException se) {
			LOGGER.error(se.getMessage());
		}

	}

	/* ============SERVICE AFFINITY ENDS================= */

	/* ================RESOURCE SELECTION STARTS================== */

	public List<ResourceSelection> selectResourceSelection() {

		List<ResourceSelection> customers = new ArrayList<ResourceSelection>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = (Connection) DBConnection.getConnection();
			String sql = "select * from resource_selection";
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			ResourceSelection customer = null;
			while (resultSet.next()) {

				customer = new ResourceSelection();

				customer.setRank(resultSet.getString("rank"));
				customer.setName(resultSet.getString("name"));
				customer.setContainer_types(resultSet.getString("container_types"));
				customer.setEnvironment_types(resultSet.getString("environment_types"));
				customer.setHost_groups(resultSet.getString("host_groups"));
				customer.setPlacement(resultSet.getString("placement"));
				customer.setMinimum(resultSet.getString("minimum"));

				customers.add(customer);
			}

		} catch (SQLException e) {
			LOGGER.error(e.getMessage());

		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				LOGGER.error(e.getMessage());
			}
		}

		return customers;
	}

	public boolean viewResourceSelection(ResourceSelection rs) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = (Connection) DBConnection.getConnection();
			pstmt = (PreparedStatement) con.prepareStatement("INSERT INTO resource_selection VALUES(?,?,?,?,?,?,?)");
			pstmt.setString(1, rs.getRank());
			pstmt.setString(2, rs.getName());
			pstmt.setString(3, rs.getContainer_types());
			pstmt.setString(4, rs.getEnvironment_types());
			pstmt.setString(5, rs.getHost_groups());
			pstmt.setString(6, rs.getPlacement());
			pstmt.setString(7, rs.getMinimum());

			pstmt.executeUpdate();

			LOGGER.info("Data Inserted");
		} catch (SQLException sqlexp) {
			log.error(sqlexp.getMessage());
		}
		return true;
	}

	public void deleteResourceSelection(String data) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			connection = (Connection) DBConnection.getConnection();
			String sql = "delete from resource_selection where rank = ?";
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, data);
			preparedStatement.executeUpdate();

		} catch (SQLException se) {
			LOGGER.error(se.getMessage());
		}

	}

	/* ============ENDS================= */

	/* ================CONTAINER TYPES STARTS================== */

	public List<ContainerTypes> selectContainerTypes() {

		List<ContainerTypes> customers = new ArrayList<ContainerTypes>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = (Connection) DBConnection.getConnection();
			String sql = "select * from container_types";
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			ContainerTypes customer = null;
			while (resultSet.next()) {

				customer = new ContainerTypes();

				customer.setName(resultSet.getString(1));
				customer.setCpu_shares(resultSet.getInt(2));
				customer.setMemory(resultSet.getInt(3));
				customer.setDescription(resultSet.getString(4));

				customers.add(customer);
			}

		} catch (SQLException e) {
			LOGGER.error(e.getMessage());

		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				LOGGER.error(e.getMessage());
			}
		}

		return customers;
	}

	public boolean viewContainerTypes(ContainerTypes ct) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = (Connection) DBConnection.getConnection();
			pstmt = (PreparedStatement) con.prepareStatement("INSERT INTO container_types VALUES(?,?,?,?)");
			pstmt.setString(1, ct.getName());
			pstmt.setInt(2, ct.getCpu_shares());
			pstmt.setInt(3, ct.getMemory());
			pstmt.setString(4, ct.getDescription());

			pstmt.executeUpdate();

			LOGGER.info("Data Inserted");
		} catch (SQLException sqlexp) {
			log.error(sqlexp.getMessage());
		}
		return true;
	}

	public void deleteContainerTypes(String data) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			connection = (Connection) DBConnection.getConnection();
			String sql = "delete from container_types where name = ?";
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, data);
			preparedStatement.executeUpdate();

		} catch (SQLException se) {
			LOGGER.error(se.getMessage());
		}

	}

	/* ============ENDS================= */

	/* ================CLOUD PROVIDERS STARTS================== */

	public List<CloudProviders> selectCloudProviders() {

		List<CloudProviders> customers = new ArrayList<CloudProviders>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = (Connection) DBConnection.getConnection();
			String sql = "select * from cloud_providers";
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			CloudProviders customer = null;
			while (resultSet.next()) {

				customer = new CloudProviders();

				customer.setName(resultSet.getString(1));
				customer.setType(resultSet.getString(2));
				customer.setPrivate_cloud(resultSet.getString(3));
				customer.setDescription(resultSet.getString(4));
				customer.setDefault_region(resultSet.getString(5));
				customer.setAccount_id(resultSet.getInt(6));
				customer.setExternal_id(resultSet.getString(7));
				customer.setRole_arn(resultSet.getString(8));

				customers.add(customer);
			}

		} catch (SQLException e) {
			LOGGER.error(e.getMessage());

		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				LOGGER.error(e.getMessage());
			}
		}

		return customers;
	}

	public boolean viewCloudProviders(CloudProviders cp) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = (Connection) DBConnection.getConnection();
			pstmt = (PreparedStatement) con.prepareStatement("INSERT INTO cloud_providers VALUES(?,?,?,?,?,?,?,?)");
			pstmt.setString(1, cp.getName());
			pstmt.setString(2, cp.getType());
			pstmt.setString(3, cp.getPrivate_cloud());
			pstmt.setString(4, cp.getDescription());
			pstmt.setString(5, cp.getDefault_region());
			pstmt.setInt(6, cp.getAccount_id());
			pstmt.setString(7, cp.getExternal_id());
			pstmt.setString(8, cp.getRole_arn());

			pstmt.executeUpdate();

			LOGGER.info("Data Inserted");
		} catch (SQLException sqlexp) {
			log.error(sqlexp.getMessage());
		}
		return true;
	}

	public void deleteCloudProviders(String data) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			connection = (Connection) DBConnection.getConnection();
			String sql = "delete from cloud_providers where name = ?";
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, data);
			preparedStatement.executeUpdate();

		} catch (SQLException se) {
			LOGGER.error(se.getMessage());
		}

	}

	/* ============ENDS================= */

	/* ================IMAGE REGISTRY STARTS================== */

	public List<ImageRegistry> selectImageRegistry() {

		List<ImageRegistry> customers = new ArrayList<ImageRegistry>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = (Connection) DBConnection.getConnection();
			String sql = "select * from image_registry";
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			ImageRegistry customer = null;
			while (resultSet.next()) {

				customer = new ImageRegistry();

				customer.setName(resultSet.getString(1));
				customer.setLocation(resultSet.getString(2));
				customer.setVersion(resultSet.getString(3));
				customer.setPrivate_cloud(resultSet.getString(4));
				customer.setUser_name(resultSet.getString(5));
				customer.setPassword(resultSet.getString(6));

				customers.add(customer);
			}

		} catch (SQLException e) {
			LOGGER.error(e.getMessage());

		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				LOGGER.error(e.getMessage());
			}
		}

		return customers;
	}

	public ImageRegistry selectImageRegistry(String reponame) {

		ImageRegistry customer = null;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = (Connection) DBConnection.getConnection();
			String sql = "select * from image_registry where name=?";
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, reponame);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {

				customer = new ImageRegistry();

				customer.setName(resultSet.getString(1));
				customer.setLocation(resultSet.getString(2));
				customer.setVersion(resultSet.getString(3));
				customer.setPrivate_cloud(resultSet.getString(4));
				customer.setUser_name(resultSet.getString(5));
				customer.setPassword(resultSet.getString(6));

			}

		} catch (SQLException e) {
			LOGGER.error(e.getMessage());

		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				LOGGER.error(e.getMessage());
			}
		}

		return customer;
	}

	public ApplicantSummary selectImageRegistryfromsummary(String reponame) {

		ApplicantSummary customer = null;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = (Connection) DBConnection.getConnection();
			String sql = "select imageRepository from appsummary where applicantName='" + reponame + "'";
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {

				customer = new ApplicantSummary();

				customer.setImageRepository(resultSet.getString("imageRepository"));

			}

		} catch (SQLException e) {
			LOGGER.error(e.getMessage());

		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				LOGGER.error(e.getMessage());
			}
		}

		return customer;
	}

	public boolean viewImageRegistry(ImageRegistry imgreg) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = (Connection) DBConnection.getConnection();
			pstmt = (PreparedStatement) con.prepareStatement("INSERT INTO image_registry VALUES(?,?,?,?,?,?)");
			pstmt.setString(1, imgreg.getName());
			pstmt.setString(2, imgreg.getLocation());
			pstmt.setString(3, imgreg.getVersion());
			pstmt.setString(4, imgreg.getPrivate_cloud());
			pstmt.setString(5, imgreg.getUser_name());
			pstmt.setString(6, imgreg.getPassword());

			pstmt.executeUpdate();

			LOGGER.info("Data Inserted");
		} catch (SQLException sqlexp) {
			log.error(sqlexp.getMessage());
		}
		return true;
	}

	public void deleteImageRegistry(String data) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			connection = (Connection) DBConnection.getConnection();
			String sql = "delete from image_registry where name = ?";
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, data);
			preparedStatement.executeUpdate();

		} catch (SQLException se) {
			LOGGER.error(se.getMessage());
		}

	}

	/* ============ENDS================= */

	/* ================ENVIROMENT TYPES STARTS================== */

	public List<EnvironmentTypes> selectEnvironmentTypes() {

		List<EnvironmentTypes> customers = new ArrayList<EnvironmentTypes>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = (Connection) DBConnection.getConnection();
			String sql = "select * from environment_types";
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			EnvironmentTypes customer = null;
			while (resultSet.next()) {

				customer = new EnvironmentTypes();

				customer.setName(resultSet.getString(1));
				customer.setDescription(resultSet.getString(2));
				customer.setAccept_tag(resultSet.getString(3));
				customer.setPromote_tag(resultSet.getString(4));
				customer.setAction(resultSet.getString(5));
				customer.setRestart_interval(resultSet.getInt(6));
				customer.setQuiet_period(resultSet.getInt(7));

				customers.add(customer);
			}

		} catch (SQLException e) {
			LOGGER.error(e.getMessage());

		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				LOGGER.error(e.getMessage());
			}
		}

		return customers;
	}

	public boolean viewEnvironmentTypes(EnvironmentTypes envtypes) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = (Connection) DBConnection.getConnection();
			pstmt = (PreparedStatement) con.prepareStatement("INSERT INTO environment_types VALUES(?,?,?,?,?,?,?)");
			pstmt.setString(1, envtypes.getName());
			pstmt.setString(2, envtypes.getDescription());
			pstmt.setString(3, envtypes.getAccept_tag());
			pstmt.setString(4, envtypes.getPromote_tag());
			pstmt.setString(5, envtypes.getAction());
			pstmt.setInt(6, envtypes.getRestart_interval());
			pstmt.setInt(7, envtypes.getQuiet_period());

			pstmt.executeUpdate();

			LOGGER.info("Data Inserted");
		} catch (SQLException sqlexp) {
			log.error(sqlexp.getMessage());
		}
		return true;
	}

	public void deleteEnvironmentTypes(String data) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			connection = (Connection) DBConnection.getConnection();
			String sql = "delete from environment_types where name = ?";
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, data);
			preparedStatement.executeUpdate();

		} catch (SQLException se) {
			LOGGER.error(se.getMessage());
		}

	}

	/* ============ENDS================= */

	/* ================FIREWALL OUTBOUNDS STARTS================== */

	public List<FirewallOutbounds> selectFirewallOutbounds() {

		List<FirewallOutbounds> customers = new ArrayList<FirewallOutbounds>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = (Connection) DBConnection.getConnection();
			String sql = "select * from firewall_outbounds";
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			FirewallOutbounds customer = null;
			while (resultSet.next()) {

				customer = new FirewallOutbounds();

				customer.setOut_type(resultSet.getString(1));
				customer.setOut_protocol(resultSet.getString(2));
				customer.setOut_portrange(resultSet.getString(3));
				customer.setOut_source(resultSet.getString(4));
				customer.setOut_ip(resultSet.getString(5));
				customer.setOut_name(resultSet.getString(6));

				customers.add(customer);
			}

		} catch (SQLException e) {
			LOGGER.error(e.getMessage());

		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				LOGGER.error(e.getMessage());
			}
		}

		return customers;
	}

	public boolean viewFirewallOutbounds(FirewallOutbounds fwlout) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = (Connection) DBConnection.getConnection();
			pstmt = (PreparedStatement) con.prepareStatement("INSERT INTO firewall_outbounds VALUES(?,?,?,?,?,?)");
			pstmt.setString(1, fwlout.getOut_type());
			pstmt.setString(2, fwlout.getOut_protocol());
			pstmt.setString(3, fwlout.getOut_portrange());
			pstmt.setString(4, fwlout.getOut_source());
			pstmt.setString(5, fwlout.getOut_ip());
			pstmt.setString(6, fwlout.getOut_name());

			pstmt.executeUpdate();

			LOGGER.info("Data Inserted");
		} catch (SQLException sqlexp) {
			log.error(sqlexp.getMessage());
		}
		return true;
	}

	public void deleteFirewallOutbounds(String data) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			connection = (Connection) DBConnection.getConnection();
			String sql = "delete from firewall_outbounds where out_type = ?";
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, data);
			preparedStatement.executeUpdate();

		} catch (SQLException se) {
			LOGGER.error(se.getMessage());
		}

	}

	/* ============ENDS================= */

	/* ================FIREWALL INBOUNDS STARTS================== */

	public List<FirewallInbounds> selectFirewallInbounds() {

		List<FirewallInbounds> customers = new ArrayList<FirewallInbounds>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = (Connection) DBConnection.getConnection();
			String sql = "select * from firewall_inbounds";
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			FirewallInbounds customer = null;
			while (resultSet.next()) {

				customer = new FirewallInbounds();

				customer.setIn_type(resultSet.getString(1));
				customer.setIn_protocol(resultSet.getString(2));
				customer.setIn_portrange(resultSet.getString(3));
				customer.setIn_source(resultSet.getString(4));
				customer.setIn_ip(resultSet.getString(5));
				customer.setIn_ip(resultSet.getString(6));

				customers.add(customer);
			}

		} catch (SQLException e) {
			LOGGER.error(e.getMessage());

		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				LOGGER.error(e.getMessage());
			}
		}

		return customers;
	}

	public boolean viewFirewallInbounds(FirewallInbounds fwlin) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = (Connection) DBConnection.getConnection();
			pstmt = (PreparedStatement) con.prepareStatement("INSERT INTO firewall_inbounds VALUES(?,?,?,?,?,?)");
			pstmt.setString(1, fwlin.getIn_type());
			pstmt.setString(2, fwlin.getIn_protocol());
			pstmt.setString(3, fwlin.getIn_portrange());
			pstmt.setString(4, fwlin.getIn_source());
			pstmt.setString(5, fwlin.getIn_ip());
			pstmt.setString(6, fwlin.getIn_name());

			pstmt.executeUpdate();

			LOGGER.info("Data Inserted");
		} catch (SQLException sqlexp) {
			log.error(sqlexp.getMessage());
		}
		return true;
	}

	public void deleteFirewallInbounds(String data) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			connection = (Connection) DBConnection.getConnection();
			String sql = "delete from firewall_inbounds where in_type = ?";
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, data);
			preparedStatement.executeUpdate();

		} catch (SQLException se) {
			LOGGER.error(se.getMessage());
		}

	}

	/* ============ENDS================= */
	/*
	 * public static void main(String[] args) { userDAO use= new userDAO();
	 * System.out.println(use.selectServices());
	 * 
	 * 
	 * }
	 */
	public void deleteServiceByName(String serviceName) {
		LOGGER.info("Inside deleteServiceByName method of userDAO");
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			LOGGER.debug("Before Getting Connectiong");
			System.out.println("Before Getting Connectiong");
			connection = (Connection) DBConnection.getConnection();
			LOGGER.debug("after connection");
			System.out.println("after connection");
			String sql = "delete from addService where serviceName = ?";
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, serviceName);
			LOGGER.debug("Before Hitting to DB");
			System.out.println("Before Hitting to DB");
			preparedStatement.executeUpdate();
			LOGGER.debug(serviceName + " is Permantently Deleted from the DB...");
			System.out.println(serviceName + " is Permantently Deleted from the DB...");
		} catch (SQLException sqle) {
			LOGGER.error("Error when deleting service from DB in DAO", sqle);
		}
	}

	public List<Storage> selectStoragelist() {

		List<Storage> customers = new ArrayList<Storage>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = (Connection) DBConnection.getConnection();
			String sql = "select * from storage";
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			Storage customer = null;
			while (resultSet.next()) {

				customer = new Storage();

				customer.setServiceName(resultSet.getString(1));
				customer.setTag(resultSet.getString(2));
				customer.setVolumeSize(resultSet.getString(3));

				customers.add(customer);
			}

		} catch (SQLException e) {
			LOGGER.error(e.getMessage());

		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				LOGGER.error(e.getMessage());
			}
		}

		return customers;

	}
}
