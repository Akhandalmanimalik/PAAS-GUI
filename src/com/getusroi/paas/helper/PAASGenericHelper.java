package com.getusroi.paas.helper;

import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * This class is used to provide generic utility method
 * @author bizruntime
 *
 */
public class PAASGenericHelper {
	 static final Logger logger = LoggerFactory.getLogger(PAASGenericHelper.class);
	 
	 /**
	  * This method is used to read properties file
	  * @param fileName : file name 
	  * @return Properties 
	  * @throws UnableToLoadPropertyFileException : Unable to read property file
	  */
	public synchronized static Properties getPropertyFile(String fileName) throws UnableToLoadPropertyFileException{
		logger.debug(".getPropertyFile mehod of PAASGenericHelper");
		Properties prop=new Properties();
		try {
			prop.load(PAASGenericHelper.class.getClassLoader().getResourceAsStream(fileName));
			return prop;
		} catch (IOException e) {
			logger.error("Unable to load the property file"+fileName);
			throw new UnableToLoadPropertyFileException("Unable to load the property file"+fileName);
		}			
	}//end of method getPropertyFile
	
	/**
	 * This method is used to get custom unique id
	 * @param prefix : prefix String
	 * @return String : custom unique ID
	 */
	public static  String getCustomUUID(String prefix){
		 logger.debug(".getCustomUUID method of PAASGenericHelper");
		 UUID uuid = UUID.randomUUID();
		 return prefix+uuid.toString().substring(0, 8);
	 }//end of method getCustomUUID
	
	//addedBY sanath
	public static  String getCustomUUID(){
		 logger.debug(".getCustomUUID method of PAASGenericHelper");
		 UUID uuid = UUID.randomUUID();
		 return uuid.toString().substring(0, 4);
	 }//end of method getCustomUUID
	
	/**
	 * This method is used to increment the value passed
	 * @param value : original value passed as String
	 * @return  String  : increment value in String
	 */
	public static String getIncrementValue(String value) {
		logger.debug(".getIncrementValue method of PAASGenericHelper");
		String ad [] = value.split("/");
		logger.debug("array lengh "+ad.length);		
		String s=ad[0];
		logger.debug("s value : "+s);
		 String[] s2 = s.split("\\.");
		 int actualvalue=Integer.parseInt(s2[s2.length-1]);
		 actualvalue= actualvalue+1;
		 s2[s2.length-1]=actualvalue+"";
		 String data="";
		 for (int i = 0; i < s2.length-1; i++) {
			 data=data+s2[i]+".";
		}
		 data=data+value+"/"+ad[1];
		 logger.debug("value "+data );
		return data;
	}//end of method getIncrementValue
	
	 /**
	  * This method is used to read properties file
	  * @param fileName : file name 
	  * @return Properties 
	  * @throws UnableToLoadPropertyFileException : Unable to read property file
	  */
	public synchronized static Properties getDockerHubPropertyFile(String fileName) throws UnableToLoadPropertyFileException{
		logger.debug(".getPropertyFile mehod of PAASGenericHelper");
		Properties prop=new Properties();
		try {
			prop.load(PAASGenericHelper.class.getClassLoader().getResourceAsStream(fileName));
			return prop;
		} catch (IOException e) {
			logger.error("Unable to load the property file"+fileName);
			throw new UnableToLoadPropertyFileException("Unable to load the property file"+fileName);
		}			
	}//end of method getDockerHubPropertyFile
	

	
	 /**
	  * This method is used to read properties file
	  * @param fileName : file name 
	  * @return Properties 
	  * @throws UnableToLoadPropertyFileException : Unable to read property file
	  */
	public synchronized static Properties getScriptPropertyFile(String fileName) throws UnableToLoadPropertyFileException{
		logger.debug(".getPropertyFile mehod of PAASGenericHelper");
		Properties prop=new Properties();
		try {
			prop.load(PAASGenericHelper.class.getClassLoader().getResourceAsStream(fileName));
			return prop;
		} catch (IOException e) {
			logger.error("Unable to load the property file"+fileName);
			throw new UnableToLoadPropertyFileException("Unable to load the property file"+fileName);
		}			
	}//end of method getPropertyFile
	
}
