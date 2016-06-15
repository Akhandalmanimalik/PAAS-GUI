package com.getusroi.paas.vo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FirewallInbound {
	
	 private String  in_name;
	 private String in_type;
	 private String in_protocol;
	 private String in_portrange;
	 private String in_source;
	 private String in_ip;
	public FirewallInbound(String in_name, String in_type, String in_protocol,String in_portrange, String in_source, String in_ip) {
		this.in_name = in_name;
		this.in_type = in_type;
		this.in_protocol = in_protocol;
		this.in_portrange = in_portrange;
		this.in_source = in_source;
		this.in_ip = in_ip;
	}
	public String getIn_name() {
		return in_name;
	}
	public void setIn_name(String in_name) {
		this.in_name = in_name;
	}
	public String getIn_type() {
		return in_type;
	}
	public void setIn_type(String in_type) {
		this.in_type = in_type;
	}
	public String getIn_protocol() {
		return in_protocol;
	}
	public void setIn_protocol(String in_protocol) {
		this.in_protocol = in_protocol;
	}
	public String getIn_portrange() {
		return in_portrange;
	}
	public void setIn_portrange(String in_portrange) {
		this.in_portrange = in_portrange;
	}
	public String getIn_source() {
		return in_source;
	}
	public void setIn_source(String in_source) {
		this.in_source = in_source;
	}
	public String getIn_ip() {
		return in_ip;
	}
	public void setIn_ip(String in_ip) {
		this.in_ip = in_ip;
	}
	@Override
	public String toString() {
		return "FirewallInbound [in_name=" + in_name + ", in_type=" + in_type
				+ ", in_protocol=" + in_protocol + ", in_portrange="
				+ in_portrange + ", in_source=" + in_source + ", in_ip="
				+ in_ip + "]";
	}
	  
	 

}
