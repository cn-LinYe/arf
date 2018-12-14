package com.arf.core.dto;

import java.io.Serializable;

public class CommunityCitynoDto implements Serializable{

	private static final long serialVersionUID = -6444127871387841637L;
	
	private String communityName;//小区名
	private String communityNumber;//小区编号
	private String province;//省
	private String city;//市
	private String district;//区
	private String address;//地址
	private String propertyOfficePhone;//物业管理处电话
	private Integer isAxd;//是否安心点小区
	
	public String getCommunityName() {
		return communityName;
	}
	public String getCommunityNumber() {
		return communityNumber;
	}
	public String getProvince() {
		return province;
	}
	public String getCity() {
		return city;
	}
	public String getDistrict() {
		return district;
	}
	public String getAddress() {
		return address;
	}
	public String getPropertyOfficePhone() {
		return propertyOfficePhone;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setPropertyOfficePhone(String propertyOfficePhone) {
		this.propertyOfficePhone = propertyOfficePhone;
	}
	public Integer getIsAxd() {
		return isAxd;
	}
	public void setIsAxd(Integer isAxd) {
		this.isAxd = isAxd;
	}
}
