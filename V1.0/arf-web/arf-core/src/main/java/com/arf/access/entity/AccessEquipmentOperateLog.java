package com.arf.access.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "access_equipment_operate_log")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "access_equipment_operate_log_sequence")
public class AccessEquipmentOperateLog extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7044985691529604504L;
	
	private String communityNumber;//小区编号
	private OperateType operateType;//操作类型（0 INIT 初始化门禁、1 LOGIN 登录、2 SYNCHRONIZE_SECRETKEY同步密钥、
								//3 MODIFY_PWD修改密码、4 SYNCHRONIZE_PWD同步密码、5 SYNCHRONIZE_APP_UNLOCK_LOG同步APP开门记录 6 LOGOUT注销
								//7 SYNCHRONIZE_PWD_UNLOCK_LOG同步密码开门记录、  8 SYNCHRONIZE_CARD_UNLOCK_LOG同步门禁卡开门记录）
	private Date operateTime;//操作时间
	private String bluetoothMac;//蓝牙Mac地址
	private String building;//楼栋
	private String unit;//单元
	private String userName;//用户名
	private String fullName;//操作用户姓名
	private String accessName;//门禁名称
	private Status status;//状态（0success 成功 1failure失败）
	private String failureReason;//失败原因
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum OperateType{
		INIT,LOGIN,SYNCHRONIZE_SECRETKEY,MODIFY_PWD,SYNCHRONIZE_PWD,SYNCHRONIZE_APP_UNLOCK_LOG,LOGOUT,
		SYNCHRONIZE_PWD_UNLOCK_LOG,SYNCHRONIZE_CARD_UNLOCK_LOG,SYNCHRONIZE_CARD_INFO;
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		success,failure;
	}

	@Column(length=20)
	public String getCommunityNumber() {
		return communityNumber;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}

	public OperateType getOperateType() {
		return operateType;
	}

	public void setOperateType(OperateType operateType) {
		this.operateType = operateType;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	@Column(length=20)
	public String getBluetoothMac() {
		return bluetoothMac;
	}

	public void setBluetoothMac(String bluetoothMac) {
		this.bluetoothMac = bluetoothMac;
	}

	@Column(length=20)
	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	@Column(length=20)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(length=20)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(length=20)
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Column(length=20)
	public String getAccessName() {
		return accessName;
	}

	public void setAccessName(String accessName) {
		this.accessName = accessName;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Column(length=30)
	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

}
