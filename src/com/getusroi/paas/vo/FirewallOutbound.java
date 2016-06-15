package com.getusroi.paas.vo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FirewallOutbound {
	private String out_name;
	private String out_type;
	private String out_protocol;
	private String out_portrange;
	private String out_source;
	private String out_ip;
	public FirewallOutbound(String out_name, String out_type,String out_protocol, String out_portrange, String out_source,String out_ip) {
		this.out_name = out_name;
		this.out_type = out_type;
		this.out_protocol = out_protocol;
		this.out_portrange = out_portrange;
		this.out_source = out_source;
		this.out_ip = out_ip;
	}
	public String getOut_name() {
		return out_name;
	}
	public void setOut_name(String out_name) {
		this.out_name = out_name;
	}
	public String getOut_type() {
		return out_type;
	}
	public void setOut_type(String out_type) {
		this.out_type = out_type;
	}
	public String getOut_protocol() {
		return out_protocol;
	}
	public void setOut_protocol(String out_protocol) {
		this.out_protocol = out_protocol;
	}
	public String getOut_portrange() {
		return out_portrange;
	}
	public void setOut_portrange(String out_portrange) {
		this.out_portrange = out_portrange;
	}
	public String getOut_source() {
		return out_source;
	}
	public void setOut_source(String out_source) {
		this.out_source = out_source;
	}
	public String getOut_ip() {
		return out_ip;
	}
	public void setOut_ip(String out_ip) {
		this.out_ip = out_ip;
	}
	@Override
	public String toString() {
		return "FirewallOutbound [out_name=" + out_name + ", out_type="
				+ out_type + ", out_protocol=" + out_protocol
				+ ", out_portrange=" + out_portrange + ", out_source="
				+ out_source + ", out_ip=" + out_ip + "]";
	}
	
	
	
}
