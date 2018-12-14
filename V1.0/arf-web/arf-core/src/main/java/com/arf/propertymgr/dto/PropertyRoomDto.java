package com.arf.propertymgr.dto;

import com.arf.propertymgr.entity.PropertyRoomInfo;

public class PropertyRoomDto extends PropertyRoomInfo{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7462283092704463069L;
	private String userName;//用户名
	private String boundNumber;//绑定编号
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getBoundNumber() {
		return boundNumber;
	}
	public void setBoundNumber(String boundNumber) {
		this.boundNumber = boundNumber;
	}

	
	
}
