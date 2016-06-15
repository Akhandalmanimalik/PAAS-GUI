package com.getusroi.paas.vo;

public class VPCRegion {
	private int vpcRegionId;
	private String region;
	public VPCRegion(String region) {
		this.region = region;
	}
	public VPCRegion(int vpcRegionId, String region) {
		this.vpcRegionId = vpcRegionId;
		this.region = region;
	}
	public int getVpcRegionId() {
		return vpcRegionId;
	}
	public void setVpcRegionId(int vpcRegionId) {
		this.vpcRegionId = vpcRegionId;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	@Override
	public String toString() {
		return "VPCRegionVO [vpcRegionId=" + vpcRegionId + ", region=" + region
				+ "]";
	}

}
