package com.arf.platform.vo;

public class LeaveReportVo extends RequestDataVo {

	//业务数据
	private String recordId;//在停车场中，能唯一标记条停车记录
	private String license;//车牌号码，UTF8编码
	private String leaveTime;//时间戳(秒)
	private Integer fee ;//停车费，单位：分
	private Integer payType;//支付方式:0钱包支付，1微信支付，2支付宝支付，3 银行卡 4、现金
	private String arriveTime;//时间戳(秒)
	private String isBook;//0没有，1有
	private String isEscape;//0没有逃欠费，1逃费，2欠费，为0时没有escape位0
	private String stopType;//0月卡车,1临时车,3免费车,4电子账户车
	private Integer escape;//逃欠费金额，单位：分
	private String arriveCarImgUrl ;//来车图片url，utf8编码
	private String leaveCarImgUrl ;//走车图片url，utf8编码
	
	private Integer fieldType;//场中场类型 0场外 1场内
	
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getLeaveTime() {
		return leaveTime;
	}
	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}
	public Integer getFee() {
		return fee;
	}
	public void setFee(Integer fee) {
		this.fee = fee;
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public String getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}
	public String getIsBook() {
		return isBook;
	}
	public void setIsBook(String isBook) {
		this.isBook = isBook;
	}
	public String getIsEscape() {
		return isEscape;
	}
	public void setIsEscape(String isEscape) {
		this.isEscape = isEscape;
	}
	public String getStopType() {
		return stopType;
	}
	public void setStopType(String stopType) {
		this.stopType = stopType;
	}
	public Integer getEscape() {
		return escape;
	}
	public void setEscape(Integer escape) {
		this.escape = escape;
	}
	public String getArriveCarImgUrl() {
		return arriveCarImgUrl;
	}
	public void setArriveCarImgUrl(String arriveCarImgUrl) {
		this.arriveCarImgUrl = arriveCarImgUrl;
	}
	public String getLeaveCarImgUrl() {
		return leaveCarImgUrl;
	}
	public void setLeaveCarImgUrl(String leaveCarImgUrl) {
		this.leaveCarImgUrl = leaveCarImgUrl;
	}
	public Integer getFieldType() {
		return fieldType;
	}
	public void setFieldType(Integer fieldType) {
		this.fieldType = fieldType;
	}
	@Override
	public String toString() {
		return "LeaveReportVo [recordId=" + recordId + ", license=" + license
				+ ", leaveTime=" + leaveTime + ", fee=" + fee + ", payType="
				+ payType + ", arriveTime=" + arriveTime + ", isBook=" + isBook
				+ ", isEscape=" + isEscape + ", stopType=" + stopType
				+ ", escape=" + escape + ", arriveCarImgUrl=" + arriveCarImgUrl
				+ ", leaveCarImgUrl=" + leaveCarImgUrl + ", fieldType="
				+ fieldType + ", getVer()=" + getVer() + ", getCommunityNo()="
				+ getCommunityNo() + ", getParkingType()=" + getParkingType()
				+ "]";
	}
	
}
