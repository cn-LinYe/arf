package com.arf.platform.vo;

public class ArriveReportVo extends RequestDataVo {
	
	//业务数据
	private String recordId;//在停车场下，能唯一标记一次来车信息
	private String license;//车牌号码，UTF8编码
	private String licenseColor;//车牌颜色
	private String carColor;//车身颜色
	private String carType;//车型
	private String arriveCarImgUrl;//来车图片url，utf8编码
	private String arriveTime;//时间戳(秒)
	private Integer isBook;//是否有预定停车？ 0没有，1有
	private String orderSn;//订单长度为0时没有本域
	private Integer stopType;//停车类型 0月卡车,1临时车,2免费车,3电子账户车
	private String berthNo;//车位编号，UTF8编码
	
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
	public String getLicenseColor() {
		return licenseColor;
	}
	public void setLicenseColor(String licenseColor) {
		this.licenseColor = licenseColor;
	}
	public String getCarColor() {
		return carColor;
	}
	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getArriveCarImgUrl() {
		return arriveCarImgUrl;
	}
	public void setArriveCarImgUrl(String arriveCarImgUrl) {
		this.arriveCarImgUrl = arriveCarImgUrl;
	}
	public String getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}
	public Integer getIsBook() {
		return isBook;
	}
	public void setIsBook(Integer isBook) {
		this.isBook = isBook;
	}
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public Integer getStopType() {
		return stopType;
	}
	public void setStopType(Integer stopType) {
		this.stopType = stopType;
	}
	public String getBerthNo() {
		return berthNo;
	}
	public void setBerthNo(String berthNo) {
		this.berthNo = berthNo;
	}
	public Integer getFieldType() {
		return fieldType;
	}
	public void setFieldType(Integer fieldType) {
		this.fieldType = fieldType;
	}
	@Override
	public String toString() {
		return "ArriveReportVo [recordId=" + recordId + ", license=" + license
				+ ", licenseColor=" + licenseColor + ", carColor=" + carColor
				+ ", carType=" + carType + ", arriveCarImgUrl="
				+ arriveCarImgUrl + ", arriveTime=" + arriveTime + ", isBook="
				+ isBook + ", orderSn=" + orderSn + ", stopType=" + stopType
				+ ", berthNo=" + berthNo + ", fieldType=" + fieldType
				+ ", getVer()=" + getVer() + ", getCommunityNo()="
				+ getCommunityNo() + ", getParkingType()=" + getParkingType()
				+ "]";
	}
	
}
