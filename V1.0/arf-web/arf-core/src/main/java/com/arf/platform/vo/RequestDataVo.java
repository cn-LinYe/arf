package com.arf.platform.vo;

import com.arf.core.entity.CommunityModel;
import com.arf.eparking.entity.ParkingInfo;

public class RequestDataVo {

	//参数
	private String ver;//协议版本号
	private String communityNo;//小区编号(停车场编码)
	private Integer parkingType;//停车场类型 0小区 1 紧急场所
	
	private CommunityModel community;//小区信息
	private ParkingInfo parkingInfo;//停车场信息
	
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	public String getCommunityNo() {
		return communityNo;
	}
	public void setCommunityNo(String communityNo) {
		this.communityNo = communityNo;
	}
	public CommunityModel getCommunity() {
		return community;
	}
	public void setCommunity(CommunityModel community) {
		this.community = community;
	}
	public Integer getParkingType() {
		return parkingType;
	}
	public void setParkingType(Integer parkingType) {
		this.parkingType = parkingType;
	}
	public ParkingInfo getParkingInfo() {
		return parkingInfo;
	}
	public void setParkingInfo(ParkingInfo parkingInfo) {
		this.parkingInfo = parkingInfo;
	}
	
}
