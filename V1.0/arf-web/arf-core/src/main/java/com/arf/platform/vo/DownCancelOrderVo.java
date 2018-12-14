package com.arf.platform.vo;

public class DownCancelOrderVo extends DownRequestDataVo {

	private String version;//接口版本号
	private String oderSn;//订单编号
	private String license;//车牌
	private String type;//取消原因 0客户取消，1超时取消，2其它原因
	private Integer parkingType;//停车场类型 0小区 1 紧急场所
	
	public Integer getParkingType() {
		return parkingType;
	}
	public void setParkingType(Integer parkingType) {
		this.parkingType = parkingType;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getOderSn() {
		return oderSn;
	}
	public void setOderSn(String oderSn) {
		this.oderSn = oderSn;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
