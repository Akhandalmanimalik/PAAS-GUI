package com.opendaylight.helper;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jettison.json.JSONObject;

public class OpenDaylightHelper {

    public static JSONObject getNodes(String user, String password,
            String baseURL) {

        StringBuffer result = new StringBuffer();
        try {

            if (!baseURL.contains("http")) {
                baseURL = "http://" + baseURL;
            }
            baseURL = baseURL + "/controller/nb/v2/switchmanager/default/nodes";

            // Create URL = base URL + container
            URL url = new URL(baseURL);

            // Create authentication string and encode it to Base64
            String authStr = user + ":" + password;
            String encodedAuthStr = Base64.encodeBase64String(authStr
                    .getBytes());

            // Create Http connection
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();

            // Set connection properties
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Basic "
                    + encodedAuthStr);
            connection.setRequestProperty("Accept", "application/json");

            // Get the response from connection's inputStream
            InputStream content = (InputStream) connection.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    content));
            String line = "";
            while ((line = in.readLine()) != null) {
                result.append(line);
            }

            JSONObject nodes = new JSONObject(result.toString());
            System.out.println("List  of  node  is"+nodes);
            return nodes;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public static String getUserDetails(String user, String password,
            String baseURL) {

        StringBuffer result = new StringBuffer();
        try {

            /*if (!baseURL.contains("http")) {
                baseURL = "http://" + baseURL;
            }*/
            baseURL = "https://"+baseURL + "/v1/repositories/guysyml/private/tags";
            System.out.println("base url  is"+baseURL);

            // Create URL = base URL + container
            URL url = new URL(baseURL);

            // Create authentication string and encode it to Base64
            String authStr = user + ":" + password;
            String encodedAuthStr = Base64.encodeBase64String(authStr
                    .getBytes());

            // Create Http connection
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();

            // Set connection properties
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Basic "
                    + encodedAuthStr);
            connection.setRequestProperty("Accept", "application/json");
            
            System.out.println("Response  Code"+connection.getResponseCode());
            System.out.println("getResponseMessage"+connection.getResponseMessage());
            
          InputStream in   =connection.getInputStream();
          
       
          BufferedReader br = new BufferedReader(
                  new InputStreamReader(in));

	String output;
	System.out.println("Output from Server .... \n");
	while ((output = br.readLine()) != null) {
		System.out.println(output);
	}
            
       System.out.println( "data  from  Dockerhub"+in);
       
       
            /*System.out.println("Response  Code"+connection.getContentType());
            System.out.println("Response  Code"+connection.getContentType());
            System.out.println("Response  Code"+connection.getContentType());
            System.out.println("Response  Code"+connection.getContentType());*/


           /* // Get the response from connection's inputStream
            InputStream content = (InputStream) connection.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    content));
            String line = "";
            while ((line = in.readLine()) != null) {
                result.append(line);
            }*/

            /*JSONObject nodes = new JSONObject(result.toString());
            System.out.println("List  of  node  is"+nodes);*/
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public static void main(String[] args) {
    	OpenDaylightHelper  helper=	new  OpenDaylightHelper();
		
    	//helper.getNodes("admin", "admin", "http://192.168.1.109:8080");
    	
    	
    	helper.getUserDetails("vijaykumar0690", "Bizruntime@123", "index.docker.io");
	}
}