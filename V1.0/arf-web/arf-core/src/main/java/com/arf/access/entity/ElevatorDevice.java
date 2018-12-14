package com.arf.access.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "elevator_device")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "elevator_device_sequence")
public class ElevatorDevice extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8458810348918175462L;
	
	
	
	private String elevatorName	;//varchar(32)	not null	梯控名称	
	private String communityNumber	;//varchar(20)		小区编号	
	private ElevatorType elevatorType	;//tinyint	Not null 	梯控类型 0 Outside 外呼式 1 Inside 内置式
	private String building	;//varchar(20)		栋--系统生成的阿拉伯数字标号,与buildingName字段对应
	private String unit	;//varchar(20)			单元	--系统生成的阿拉伯数字标号,与unitName字段对应
	private String bluetoothMac	;//varchar(20)		蓝牙mac地址	
	private Integer source	;//0金博 1h005
	
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum ElevatorType{//梯控类型 0 Outside 外呼式 1 Inside 内置式
		Outside,Inside
	}
	
	
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	@Column(length=40,nullable=false)
	public String getElevatorName() {
		return elevatorName;
	}
	public void setElevatorName(String elevatorName) {
		this.elevatorName = elevatorName;
	}
	@Column(nullable=false)
	public ElevatorType getElevatorType() {
		return elevatorType;
	}
	public void setElevatorType(ElevatorType elevatorType) {
		this.elevatorType = elevatorType;
	}
	@Column(length=32,nullable=false)
	public String getBluetoothMac() {
		return bluetoothMac;
	}
	public void setBluetoothMac(String bluetoothMac) {
		this.bluetoothMac = bluetoothMac;
	}
	
	
	@Column(length=20,nullable=false)
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	
	@Column(length=10)
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	@Column(length=10)
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
}
