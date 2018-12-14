package com.arf.core.dto;

import java.io.Serializable;

public class MemberCommunityDto implements Serializable {

	private static final long serialVersionUID = 6583029271612274773L;

	private String communityNumber;//编号
	private String communityName;//名称
	private Integer propertyNumber;//物业编号
	private Integer branchId;//子公司编号
	
	public String getCommunityNumber() {
		return communityNumber;
	}
	public String getCommunityName() {
		return communityName;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	public Integer getPropertyNumber() {
		return propertyNumber;
	}
	public Integer getBranchId() {
		return branchId;
	}
	public void setPropertyNumber(Integer propertyNumber) {
		this.propertyNumber = propertyNumber;
	}
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

}
