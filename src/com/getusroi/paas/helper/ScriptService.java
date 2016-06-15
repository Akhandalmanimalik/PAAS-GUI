package com.getusroi.paas.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getusroi.paas.vo.MessosTaskInfo;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
public class ScriptService  {
	 static final Logger logger = LoggerFactory.getLogger(PAASGenericHelper.class);

	private static String USERNAME ="root"; // username for remote host
	 private static String PASSWORD ="bizruntime@123"; // password of the remote host
	 private static String host = "54.86.181.80"; // remote host address
	 private static int port=22;
	 
	 private static final String NETWORK_SCRIPT_PATH_FOR_CREATION="/home/ubuntu/network.sh";
	 private static final String NETWORK_SCRIPT_PATH_FOR_DELETION="/home/ubuntu/delete_subnet.sh";
	 
	 private static final String MESSOS_SCRIPT_PATH_FOR_GET_TASK_ID="/home/ubuntu/get_mesos_containerid.sh";
	 private static final String MESSOS_SCRIPT_PATHE_FOR_UPDATE_NETWORK="/home/ubuntu/attach_dockernet_container.sh";

	public  void callScript(String conainerName,String ip) throws InterruptedException, IOException {
		  logger.debug(".callScript method of ScriptService");
		  ProcessBuilder pb = new ProcessBuilder("/root/network.sh", conainerName, ip);
		   Process p = pb.start();
		   BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		   String line = null;
		   while ((line = reader.readLine()) != null)
		   {
		      logger.debug(line);
		   } 
}//end of method callScript
	 
	/*# added by sanath*/
	public  void createSubnetNetwork(String cidr,String enivernment) throws InterruptedException, IOException {
		  logger.debug(".callScript method of ScriptService");
		  ProcessBuilder pb = new ProcessBuilder(NETWORK_SCRIPT_PATH_FOR_CREATION, cidr, enivernment);
		   Process p = pb.start();
		   BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		   String line = null;
		   while ((line = reader.readLine()) != null)
		   {
		      logger.debug(line);
		   } 
}//end of createDockerNetwork
	
	
	/*# added by sanath*/
	public  void deleteSubnetNetwork(String subnetName) throws InterruptedException, IOException {
		  logger.debug(".callScript method of ScriptService");
		  ProcessBuilder pb = new ProcessBuilder(NETWORK_SCRIPT_PATH_FOR_DELETION,subnetName);
		   Process p = pb.start();
		   BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		   String line = null;
		   while ((line = reader.readLine()) != null)
		   {
		      logger.debug(line);
		   } 
}//end o
	    
	
	/*# added by sanath*/
	public  void updateSubnetNetworkInMessos(MessosTaskInfo messosTaskInfo,String subnetName) throws InterruptedException, IOException {
		  logger.debug(".updateSubnetNetworkInMessos method of ScriptService");
		  ProcessBuilder pb = new ProcessBuilder(MESSOS_SCRIPT_PATHE_FOR_UPDATE_NETWORK,messosTaskInfo.getHostIp(),subnetName,messosTaskInfo.getContainerId());
		   Process p = pb.start();
		   BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		   String line = null;
		   while ((line = reader.readLine()) != null)
		   {
		      logger.debug(line);
		   } 
}//end o
	    
	
	 public  List<String> executeFile(String conainerName,String ip){
		 logger.debug(".executeFile method of ScriptService");
	     List<String> result = new ArrayList<String>();
	     try
	     {   /**
	         * Create a new Jsch object
	         * This object will execute shell commands or scripts on server
	         */
	         JSch jsch = new JSch();

	         /*
	         * Open a new session, with your username, host and port
	         * Set the password and call connect.
	         * session.connect() opens a new connection to remote SSH server.
	         * Once the connection is established, you can initiate a new channel.
	         * this channel is needed to connect to remotely execution program
	         */
	         Session session = jsch.getSession(USERNAME, host, port);
	         session.setConfig("StrictHostKeyChecking", "no");
	         session.setPassword(PASSWORD);
	         session.connect();

	         //create the excution channel over the session
	         ChannelExec channelExec = (ChannelExec)session.openChannel("exec");

	         // Gets an InputStream for this channel. All data arriving in as messages from the remote side can be read from this stream.
	         InputStream in = channelExec.getInputStream();

	         // Set the command that you want to execute
	         // In our case its the remote shell script
	         String  command="/root/network.sh"+conainerName+ip;
	         channelExec.setCommand("sh "+command);

	         // Execute the command
	         channelExec.connect();

	         // Read the output from the input stream we set above
	         BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	         String line;
	         
	         //Read each line from the buffered reader and add it to result list
	         // You can also simple print the result here 
	         while ((line = reader.readLine()) != null)
	         {
	             result.add(line);
	         }

	         //retrieve the exit status of the remote command corresponding to this channel
	         int exitStatus = channelExec.getExitStatus();

	         //Safely disconnect channel and disconnect session. If not done then it may cause resource leak
	         channelExec.disconnect();
	         session.disconnect();

	         if(exitStatus < 0){
	            logger.debug("Done, but exit status not set!");
	         }
	         else if(exitStatus > 0){
	        	 logger.debug("Done, but with error!");
	         }
	         else{
	        	 logger.debug("Done!");
	         }

	     }
	     catch(Exception e)
	     {
	         System.err.println("Error: " + e);
	     }
	     return result;
	 }//end of method executeFile

	 
	 //Added by sanath
	 public static List<MessosTaskInfo> runSCriptGetMessosTaskId(String appID) throws IOException{
		  logger.debug(".runSCriptGetMessosTaskId method of ScriptService with appiD "+ appID);
		  ProcessBuilder pb = new ProcessBuilder(MESSOS_SCRIPT_PATH_FOR_GET_TASK_ID);
		   Process p = pb.start();
		   BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		   String line = null;
		   String splitStruing[]=null;

		   List<MessosTaskInfo> listTaskInfo=new ArrayList<MessosTaskInfo>();
		   while((line = reader.readLine()) != null)
		   {
		    
			   logger.debug("messos task id "+line);
		     if(line.contains(appID)){
			     MessosTaskInfo messosTaskInfo=new MessosTaskInfo();

		      splitStruing=line.split("#");
		      if(splitStruing!=null&&splitStruing.length==3){
		      messosTaskInfo.setHostIp(splitStruing[0]);
		      messosTaskInfo.setContainerId(splitStruing[1]);
		      messosTaskInfo.setMesso_Task_Id(splitStruing[2]);
		      listTaskInfo.add(messosTaskInfo);
		      }
		     }
		   } 
		  logger.debug("list  of value messos tasks running "+listTaskInfo);
		  return listTaskInfo;
	 }
	 
	 public static void main(String[] args) throws IOException {
	ScriptService.runSCriptGetMessosTaskId("tenant1-dev-2324");
	}
}
