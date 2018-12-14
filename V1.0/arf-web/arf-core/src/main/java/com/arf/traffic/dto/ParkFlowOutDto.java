package com.arf.traffic.dto;

import java.io.Serializable;

public class ParkFlowOutDto implements Serializable{
	
	/**
	 * 停车场车走状态汇报DTO
	 */
	private static final long serialVersionUID = -3155233835640978214L;
	private String parkingId; //String 停车场ID
	private String plateNo; //String 车牌号
	private String leaveTime; //String出场时间
	private String leavePlace; //String	出口
	private String leaveImg; //String出口抓拍图片url
	private String cashUser; //String 操作员
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
	public String getLeaveTime() {
		return leaveTime;
	}
	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}
	public String getLeavePlace() {
		return leavePlace;
	}
	public void setLeavePlace(String leavePlace) {
		this.leavePlace = leavePlace;
	}
	public String getLeaveImg() {
		return leaveImg;
	}
	public void setLeaveImg(String leaveImg) {
		this.leaveImg = leaveImg;
	}
	public String getCashUser() {
		return cashUser;
	}
	public void setCashUser(String cashUser) {
		this.cashUser = cashUser;
	}
}
