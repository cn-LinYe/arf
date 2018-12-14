package com.arf.propertymgr.dto;

import com.arf.propertymgr.entity.PropertyRoomInfo;

public class PropertyRoomInfoDto extends PropertyRoomInfo {

	private static final long serialVersionUID = 1L;
	
	private String houseHolderUserName;//业主
	private Integer status;//审核状态

	public String getHouseHolderUserName() {
		return houseHolderUserName;
	}

	public void setHouseHolderUserName(String houseHolderUserName) {
		this.houseHolderUserName = houseHolderUserName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
