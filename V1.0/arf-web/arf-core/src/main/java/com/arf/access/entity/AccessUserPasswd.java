package com.arf.access.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 用户门禁密码记录表
 * @author Caolibao
 *
 */
@Entity
@Table(name = "access_user_passwd")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "access_user_passwd_sequence")
public class AccessUserPasswd extends BaseEntity<Long>{
	private static final long serialVersionUID = 1L;
	private String userName;
	private String passwd;
	private String bluetoothMac;
	private String roomNumber;
	private String buildingName;
	private String unitName;
	private String communityNumber;
	private String communityName;
	private Integer status;//状态: 0-未同步,1-已同步
	private Integer smsNotified;//是否已经短信通知用户,0-未通知,1-已通知
	private UseStatus useStatus;//使用状态 0 正常 1已删除 
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum UseStatus{
		Normal,Deleted;
	}
	
	@Column(length = 20)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length = 20)
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	@Column(length = 20)
	public String getBluetoothMac() {
		return bluetoothMac;
	}
	public void setBluetoothMac(String bluetoothMac) {
		this.bluetoothMac = bluetoothMac;
	}
	@Column(length = 40)
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	@Column(length = 20)
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	@Column(length = 20)
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(length = 20)
	public String getCommunityNumber() {
		return communityNumber;
	}
	@Column(length = 30)
	public String getCommunityName() {
		return communityName;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	public Integer getSmsNotified() {
		return smsNotified;
	}
	public void setSmsNotified(Integer smsNotified) {
		this.smsNotified = smsNotified;
	}
	public UseStatus getUseStatus() {
		return useStatus;
	}
	public void setUseStatus(UseStatus useStatus) {
		this.useStatus = useStatus;
	}
}
