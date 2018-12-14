package com.arf.smart.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name="user_custom_function_record")
@SequenceGenerator(name="sequenceGenerator",sequenceName="user_custom_function_record_sequence")
public class UserCustomFunctionRecord extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1822910929742272682L;
	
	private String userName;//用户信息
	private String bigIcon;//大图标列表
	private String smallIcon;//小图标列表
	private String iconOrderList;//图标顺序列表
	private String horizontalIcon;//横图标列表
	private String cityCode;//城市编号
	private String communityNumber;//小区编号
	
	@Column(length=40)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length=100)
	public String getBigIcon() {
		return bigIcon;
	}
	public void setBigIcon(String bigIcon) {
		this.bigIcon = bigIcon;
	}
	@Column(length=100)
	public String getSmallIcon() {
		return smallIcon;
	}
	public void setSmallIcon(String smallIcon) {
		this.smallIcon = smallIcon;
	}
	@Column(length=500)
	public String getIconOrderList() {
		return iconOrderList;
	}
	public void setIconOrderList(String iconOrderList) {
		this.iconOrderList = iconOrderList;
	}
	@Column(length=100)
	public String getHorizontalIcon() {
		return horizontalIcon;
	}
	public void setHorizontalIcon(String horizontalIcon) {
		this.horizontalIcon = horizontalIcon;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	
}
