package com.arf.access.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "elevator_authority")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "elevator_authority_sequence")
public class ElevatorAuthority extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8458810348918175462L;
	private String userName	;//varchar(32)	not null	用户账号	
	private String nickName	;//varchar(32)	not null	用户姓名
	private String communityNumber	;//varchar(20)		小区编号	
	private String boundNumber	;//varchar(20)		绑定编号
	private String roomNumber	;//varchar(20)		房屋编号
	private Status	status	;//not null	状态:绑定状态 0 New 新建 1 Bind 已绑定 2 UnBound 已解绑3 Deleted 已删除 
	private BindType bindType	;//tinyint	Not null 	绑定方式 0 Property 物业添加 1 UserApply 用户申请
	private String building	;//varchar(20)		栋--系统生成的阿拉伯数字标号,与buildingName字段对应
	private String unit	;//varchar(20)			单元	--系统生成的阿拉伯数字标号,与unitName字段对应
	private String floor	;//varchar(20)		楼层	
	private String bindUser	;//varchar(20)		绑定发起人	
	private Date bindDate;//	datetime		绑定时间	
	private String unboundUser	;//varchar(20)		解除绑定发起人	
	private Date unboundDate;//	datetime		解绑时间	
	private Boolean needFirstAlert	;//是否弹窗 0 false  true
	
	
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{//正常，禁用，删除
		New ,Bind ,UnBound , Deleted//绑定状态 0 New 新建 1 Bind 已绑定 2 UnBound 已解绑3 Deleted 已删除
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum BindType{
		Property,UserApply
	}
	@Column(length=40,nullable=false)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(length=20,nullable=false)
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	@Column(nullable=false)
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
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
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	
	public Date getUnboundDate() {
		return unboundDate;
	}
	public void setUnboundDate(Date unboundDate) {
		this.unboundDate = unboundDate;
	}
	@Column(length=40,nullable=false)
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	@Column(length=32)
	public String getBoundNumber() {
		return boundNumber;
	}
	public void setBoundNumber(String boundNumber) {
		this.boundNumber = boundNumber;
	}
	@Column(length=32)
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	public BindType getBindType() {
		return bindType;
	}
	public void setBindType(BindType bindType) {
		this.bindType = bindType;
	}
	public String getBindUser() {
		return bindUser;
	}
	public void setBindUser(String bindUser) {
		this.bindUser = bindUser;
	}
	public Date getBindDate() {
		return bindDate;
	}
	public void setBindDate(Date bindDate) {
		this.bindDate = bindDate;
	}
	@Column(length=40)
	public String getUnboundUser() {
		return unboundUser;
	}
	public void setUnboundUser(String unboundUser) {
		this.unboundUser = unboundUser;
	}
	public Boolean getNeedFirstAlert() {
		return needFirstAlert;
	}
	public void setNeedFirstAlert(Boolean needFirstAlert) {
		this.needFirstAlert = needFirstAlert;
	}
	
}
