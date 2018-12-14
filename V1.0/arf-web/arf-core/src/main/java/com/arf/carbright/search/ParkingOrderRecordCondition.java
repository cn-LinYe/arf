package com.arf.carbright.search;

import com.arf.base.BaseSearchForm;

public class ParkingOrderRecordCondition extends BaseSearchForm {
	
	private String userName;//用户名（手机号）
	private Integer status;	// tinyint;		订单状态：为空：查询所有记录，0：待支付，1：进行中，2：已完成，3：已取消
	private String license;	// Varchar(20)		预定停车的车牌号码
	
	public String getUserName() {
		return userName;
	}
	public Integer getStatus() {
		return status;
	}
	public String getLicense() {
		return license;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setLicense(String license) {
		this.license = license;
	}

}
