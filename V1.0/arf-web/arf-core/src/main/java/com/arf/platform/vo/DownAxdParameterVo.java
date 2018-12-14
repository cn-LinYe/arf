package com.arf.platform.vo;

public class DownAxdParameterVo extends DownRequestDataVo {

	private String version;//接口版本号
	private String communityNo;//communityNo
//	private String userId;//用户id(用户名)
	private Integer isAxd;//是否开启安心点 0、不开启 1、开启
	private Integer times;//设置允许出场次数
	private String authorizedTime;//0、每天 1、每周 2、每月  多长时间内允许出场次数，与次数(times)配合使用
	private String license;//车牌号
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getCommunityNo() {
		return communityNo;
	}
	public void setCommunityNo(String communityNo) {
		this.communityNo = communityNo;
	}
//	public String getUserId() {
//		return userId;
//	}
//	public void setUserId(String userId) {
//		this.userId = userId;
//	}
	public Integer getIsAxd() {
		return isAxd;
	}
	public void setIsAxd(Integer isAxd) {
		this.isAxd = isAxd;
	}
	public Integer getTimes() {
		return times;
	}
	public void setTimes(Integer times) {
		this.times = times;
	}
	public String getAuthorizedTime() {
		return authorizedTime;
	}
	public void setAuthorizedTime(String authorizedTime) {
		this.authorizedTime = authorizedTime;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	
}
