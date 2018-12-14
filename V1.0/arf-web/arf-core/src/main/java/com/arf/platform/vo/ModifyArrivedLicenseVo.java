package com.arf.platform.vo;

public class ModifyArrivedLicenseVo extends RequestDataVo{

	private String license;//原始车牌号码
	private String modifiedLicense;//修改后的车牌号码
	private Long timestamp;//道闸系统当前Unix时间戳
	private Long arrivedTimestamp;//车辆原始来车时间Unix时间戳
	private Long modifiedArrivedTimestamp;//修改后的来车时间Unix时间戳,保留字段,如需要对来车时间也进行修改,可使用该字段
	private Integer fieldType;//0场外 1场内
	
	public String getLicense() {
		return license;
	}
	public String getModifiedLicense() {
		return modifiedLicense;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public Long getArrivedTimestamp() {
		return arrivedTimestamp;
	}
	public Long getModifiedArrivedTimestamp() {
		return modifiedArrivedTimestamp;
	}
	public Integer getFieldType() {
		return fieldType;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public void setModifiedLicense(String modifiedLicense) {
		this.modifiedLicense = modifiedLicense;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public void setArrivedTimestamp(Long arrivedTimestamp) {
		this.arrivedTimestamp = arrivedTimestamp;
	}
	public void setModifiedArrivedTimestamp(Long modifiedArrivedTimestamp) {
		this.modifiedArrivedTimestamp = modifiedArrivedTimestamp;
	}
	public void setFieldType(Integer fieldType) {
		this.fieldType = fieldType;
	}
	@Override
	public String toString() {
		return "ModifyArrivedLicenseVo [license=" + license
				+ ", modifiedLicense=" + modifiedLicense + ", timestamp="
				+ timestamp + ", arrivedTimestamp=" + arrivedTimestamp
				+ ", modifiedArrivedTimestamp=" + modifiedArrivedTimestamp
				+ ", fieldType=" + fieldType + ", getVer()=" + getVer()
				+ ", getCommunityNo()=" + getCommunityNo()
				+ ", getParkingType()=" + getParkingType() + "]";
	}
	
}
