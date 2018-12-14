package com.arf.traffic.dto;

import java.io.Serializable;

public class ParkFlowInDto implements Serializable{
	/**
	 * 停车场车来汇报DTO
	 */
	private static final long serialVersionUID = -4475862846925186327L;
	private String parkingId; //String 停车场ID
	private String plateNo; //String 车牌号
	private String entryTime; //String入场时间
	private String entryPlace; //String入口
	private String entryImg; //String入口抓拍图片url
	private String cashUser; //String操作员
	
	public String getParkingId() {
		return parkingId;
	}
	public void setParkingId(String parkingId) {
		this.parkingId = parkingId;
	}
	public String getPlateNo() {
		return plateNo;
	}
	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}
	public String getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(String entryTime) {
		this.entryTime = entryTime;
	}
	public String getEntryPlace() {
		return entryPlace;
	}
	public void setEntryPlace(String entryPlace) {
		this.entryPlace = entryPlace;
	}
	public String getEntryImg() {
		return entryImg;
	}
	public void setEntryImg(String entryImg) {
		this.entryImg = entryImg;
	}
	public String getCashUser() {
		return cashUser;
	}
	public void setCashUser(String cashUser) {
		this.cashUser = cashUser;
	}
}
