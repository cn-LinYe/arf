package com.arf.platform.vo;

public class QueryCarInfoVo extends RequestDataVo {

	private String license;//车牌
	private Integer queryType;//要查询的信息 0所有信息；1可结算帐户信息　2逃欠费信息、3是否违章信息
	
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public Integer getQueryType() {
		return queryType;
	}
	public void setQueryType(Integer queryType) {
		this.queryType = queryType;
	}
	@Override
	public String toString() {
		return "QueryCarInfoVo [license=" + license + ", queryType="
				+ queryType + ", getVer()=" + getVer() + ", getCommunityNo()="
				+ getCommunityNo() + ", getParkingType()=" + getParkingType()
				+ "]";
	}
	
}
