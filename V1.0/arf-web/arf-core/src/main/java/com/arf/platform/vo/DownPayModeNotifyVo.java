package com.arf.platform.vo;

public class DownPayModeNotifyVo extends DownRequestDataVo {

	private String version;//接口版本号,目前是1
	private String license;//车牌号码，UTF8编码
	private Integer payMode;//0：无安尔发结算方式；1：电子钱包结算
	private Integer balance;//电子钱包余额(分)
	private String orderNo;//订单编号，UTF8编码
	private String berthNo;//泊位编号，UTF8编码
	private Integer isOrder;//0：否；1：是
	
	public String getVersion() {
		return version;
	}
	public String getLicense() {
		return license;
	}
	public Integer getPayMode() {
		return payMode;
	}
	public Integer getBalance() {
		return balance;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public String getBerthNo() {
		return berthNo;
	}
	public Integer getIsOrder() {
		return isOrder;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public void setPayMode(Integer payMode) {
		this.payMode = payMode;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public void setBerthNo(String berthNo) {
		this.berthNo = berthNo;
	}
	public void setIsOrder(Integer isOrder) {
		this.isOrder = isOrder;
	}
	
}
