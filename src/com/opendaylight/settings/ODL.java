package com.opendaylight.settings;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
 

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
public class ODL {

	public static final String USERNAME = "admin";
	public static final String PASSWORD = "admin";//opendaylite controller
	public static final String FreeNasUSERNAME = "root";
	public static final String FreeNasPASSWORD = "bizruntime@123";
	public static final String URL = "http://52.201.120.26:8080";//flow manager url
	public static final String USERNAME1 = "admin";
	public static final String PASSWORD1 = "adminpass";//opendaylite coordinator
	public static final String URL1 = "http://52.201.120.26:8083"; //open daylight controller
	public static final String freeNasURL = "http://192.168.1.209/api/v1.0/storage/volume/tank/zvols/?format=json";
	public static final String URL3 = "http://192.168.1.109:8181"; //fuse
	public static final String URL4 = "http://54.86.181.80:2375"; //docker
	//public static final String URL3 = "http://192.168.1.200:8181";

public static void main(String[] args) throws Exception {/*
	TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }
        public void checkClientTrusted(X509Certificate[] certs, String authType) {
        }
        public void checkServerTrusted(X509Certificate[] certs, String authType) {
        }
    }
};

// Install the all-trusting trust manager
SSLContext sc = SSLContext.getInstance("SSL");
sc.init(null, trustAllCerts, new java.security.SecureRandom());
HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

// Create all-trusting host name verifier
HostnameVerifier allHostsValid = new HostnameVerifier() {
    public boolean verify(String hostname, SSLSession session) {
        return true;
    }
};

// Install the all-trusting host verifier
HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

URL url = new URL("https://mydomain.com:5000/test/v1/repositories/mydomain.com:5000/test/tags");
URLConnection con = url.openConnection();
Reader reader = new InputStreamReader(con.getInputStream());
while (true) {
    int ch = reader.read();
    if (ch==-1) {
        break;
    }
    System.out.print((char)ch);
}
*/}
}

