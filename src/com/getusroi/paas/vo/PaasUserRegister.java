package com.getusroi.paas.vo;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * This is a pojo class for PAAS User register
 * @author bizruntime
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaasUserRegister {
	private String company_name;
	private String company_address;
	private String email;
	private String password;
	private int id;
	private String tenant_name;
	
	@Override
	public String toString() {
		return "PaasUserRegister [company_name=" + company_name
				+ ", company_address=" + company_address + ", email=" + email
				+ ", password=" + password + ", id=" + id + ", tenant_name="
				+ tenant_name + "]";
	}
	public PaasUserRegister(String company_name, String company_address,
			String email, String password, int id, String tenant_name) {
		super();
		this.company_name = company_name;
		this.company_address = company_address;
		this.email = email;
		this.password = password;
		this.id = id;
		this.tenant_name = tenant_name;
		 
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getCompany_address() {
		return company_address;
	}
	public void setCompany_address(String company_address) {
		this.company_address = company_address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public PaasUserRegister() {
		
	} 
	
	public String getTenant_name() {
		return tenant_name;
	}
	public void setTenant_name(String tenant_name) {
		this.tenant_name = tenant_name;
	}
	
	public static void main(String[] args) throws JsonParseException,
			JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		PaasUserRegister paasUserRegister = null;

		String myvar = "{\"company_name\":\"biz\",\"company_address\":\"bangalore\",\"email\":\"venkatesh.m@bizruntime.com\",\"password\":\"Bizruntime@123\"}";

		paasUserRegister = mapper.readValue(myvar, PaasUserRegister.class);

		System.out.println("error value " + paasUserRegister);

	}
}
