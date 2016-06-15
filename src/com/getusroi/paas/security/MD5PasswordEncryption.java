package com.getusroi.paas.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.getusroi.paas.dao.DataBaseOperationFailedException;
import com.getusroi.paas.dao.PaasUserRegisterAndLoginDAO;
import com.getusroi.paas.vo.PaasUserRegister;
 
public class MD5PasswordEncryption {
    public String getMD5EncryptedPassword(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
 
    public static void main(String[] args) throws NoSuchAlgorithmException {
    	PaasUserRegisterAndLoginDAO dao = new PaasUserRegisterAndLoginDAO();
    	PaasUserRegister paasUserRegister = new PaasUserRegister();
    	paasUserRegister.setPassword(new MD5PasswordEncryption().getMD5EncryptedPassword("Manoj123"));
    	String dbPassword = null;
    	try {
			dbPassword = dao.matchPassward(paasUserRegister);
			System.out.println("dbPassword  "+dbPassword);
		} catch (DataBaseOperationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
}