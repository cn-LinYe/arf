package com.arf.traffic.dto;

import java.io.Serializable;

public class ParkStatusDto implements Serializable{

	/**
	 * 停车场状态汇报DTO
	 */
	private static final long serialVersionUID = -5484670064204023238L;
	private String parkingId; //YES String 停车场ID
	private int totalPortNum; //YES int 车位总数
	private int inNum;	//YES int 当天进场总数
	private int outNum;	//YES	int	当天出场总数
	private int freePortNum; //YES	int	当前剩余车位数
	
	public String getParkingId() {
		return parkingId;
	}
	public void setParkingId(String parkingId) {
		this.parkingId = parkingId;
	}
	public int getTotalPortNum() {
		return totalPortNum;
	}
	public void setTotalPortNum(int totalPortNum) {
		this.totalPortNum = totalPortNum;
	}
	public int getInNum() {
		return inNum;
	}
	public void setInNum(int inNum) {
		this.inNum = inNum;
	}
	public int getOutNum() {
		return outNum;
	}
	public void setOutNum(int outNum) {
		this.outNum = outNum;
	}
	public int getFreePortNum() {
		return freePortNum;
	}
	public void setFreePortNum(int freePortNum) {
		this.freePortNum = freePortNum;
	}

}
