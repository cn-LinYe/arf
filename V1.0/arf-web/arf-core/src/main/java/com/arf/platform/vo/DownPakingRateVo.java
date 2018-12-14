package com.arf.platform.vo;

public class DownPakingRateVo extends DownRequestDataVo {

	private String version;//接口版本号
	private String vehicle;//车型
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
	public String getVehicle() {
		return vehicle;
	}
	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}
	
}
