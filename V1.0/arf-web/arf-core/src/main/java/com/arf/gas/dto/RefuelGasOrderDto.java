package com.arf.gas.dto;

import com.arf.gas.entity.RefuelGasOrder;

public class RefuelGasOrderDto extends RefuelGasOrder{

	private static final long serialVersionUID = 1L;
	private String gasName ;
	private Integer cardAmount ;
	private String businessName;
	private String businessAddress ;
	private String businessPic ;
	private String lat;
	private String lng ;
	private String businessPhone ;
	
	
	public String getBusinessPhone() {
		return businessPhone;
	}
	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}
	public String getGasName() {
		return gasName;
	}
	public void setGasName(String gasName) {
		this.gasName = gasName;
	}
	public Integer getCardAmount() {
		return cardAmount;
	}
	public void setCardAmount(Integer cardAmount) {
		this.cardAmount = cardAmount;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getBusinessAddress() {
		return businessAddress;
	}
	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}
	public String getBusinessPic() {
		return businessPic;
	}
	public void setBusinessPic(String businessPic) {
		this.businessPic = businessPic;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	

}
