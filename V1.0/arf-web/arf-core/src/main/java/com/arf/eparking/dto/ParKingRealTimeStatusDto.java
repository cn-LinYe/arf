package com.arf.eparking.dto;

import com.arf.eparking.entity.ParkingInfo;
import com.arf.platform.entity.PParKingrealTimeStatus;

public class ParKingRealTimeStatusDto extends PParKingrealTimeStatus {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 该实时状态对应的停车场基础信息
	 */
	private ParkingInfo parkingInfo;
	
	/**
	 * 被搜索次数
	 */
	private int searchCount;
	
	private int distance;
	
	
	public ParKingRealTimeStatusDto(){
		super();
	}
	
	public ParKingRealTimeStatusDto(ParkingInfo parkingInfo){
		super();
		this.parkingInfo = parkingInfo;
	}

	public ParkingInfo getParkingInfo() {
		return parkingInfo;
	}


	public int getDistance() {
		return distance;
	}


	public void setParkingInfo(ParkingInfo parkingInfo) {
		this.parkingInfo = parkingInfo;
	}


	public void setDistance(int distance) {
		this.distance = distance;
	}


	public int getSearchCount() {
		return searchCount;
	}


	public void setSearchCount(int searchCount) {
		this.searchCount = searchCount;
	}
	
}
