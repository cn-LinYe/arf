package com.arf.platform.search;

import java.util.Date;

import com.arf.base.BaseSearchForm;

public class StoprecordCondition extends BaseSearchForm {

	private String 	userName;//用户名
	private String parkingId;//停车场编号|
	private String license;//车牌
	private Date time;//订单生成时间
	private Integer status;//订单状态
	
	
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getUserName() {
		return userName;
	}
	public String getParkingId() {
		return parkingId;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setParkingId(String parkingId) {
		this.parkingId = parkingId;
	}
	
}
