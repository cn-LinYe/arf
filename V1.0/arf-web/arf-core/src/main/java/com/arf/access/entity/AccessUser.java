package com.arf.access.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "access_user")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "access_user_sequence")
public class AccessUser extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8458810348918175462L;
	
	//x秒倒计时后关闭弹窗再给开门
	public static final String Unpaid_Property_Fee_Alert_Second = "Unpaid_Property_Fee_Alert_Second";
	//用户使用门禁设备时判断用户物业费用欠缴情况并提示用户(0-每天一次,1-每次都提示,2-不提示)
	public static final String Unpaid_Property_Fee_Alert_Type = "Unpaid_Property_Fee_Alert_Type";
	
	private String userName	;//varchar(32)	not null	用户账号	
	private String realName	;//varchar(32)	not null	用户姓名
	private String communityName	;//varchar(32)		小区名称	
	private String communityNumber	;//varchar(20)		小区编号	
	private Status	status	;//not null	状态:0正常1禁用2删除
	private UserType userType	;//tinyint	Not null 	用户类型1 户主2 租客3 物业 4 其他（只有进入大门权限）
	private String buildingName;//varchar(20)		栋名称--与之对应的building字段 eg. 3A,3-A,春风阁
	private String unitName;//varchar(20)		单元	名称--与之对应的unit字段 eg. A单元,2B单元,一单元
	private String building	;//varchar(20)		栋--系统生成的阿拉伯数字标号,与buildingName字段对应
	private String unit	;//varchar(20)			单元	--系统生成的阿拉伯数字标号,与unitName字段对应
	private String floor	;//varchar(20)		楼层	
	private String room	;//varchar(20)		房号	
	private String accessList	;//varchar(100)		门禁列表，以，分割（关联access_management）ID	
	private Date authorizeStartDate;//	datetime		授权开始时间	
	private Date authorizeEndDate;//	datetime		授权结束时间	
	private AuthorizePermissions authorizePermissions	;//tinyint		授权权限0允许1禁止	
	
	private String roomBoundNumber;//房间绑定编号
	
	public String getRoomBoundNumber() {
		return roomBoundNumber;
	}
	public void setRoomBoundNumber(String roomBoundNumber) {
		this.roomBoundNumber = roomBoundNumber;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{//正常，禁用，删除
		normal,disable,delete;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum AuthorizePermissions{
		allow,prohibit;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum UserType{
		no_used,householder, tenant, property, others;
	}
	@Column(length=32,nullable=false)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length=32)
	public String getCommunityName() {
		return communityName;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
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
	@Column(nullable=false)
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
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
	@Column(length=20)
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	@Column(length=100)
	public String getAccessList() {
		return accessList;
	}
	@Column(length=30)
	public String getBuildingName() {
		return buildingName;
	}
	@Column(length=30)
	public String getUnitName() {
		return unitName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public void setAccessList(String accessList) {
		this.accessList = accessList;
	}
	public Date getAuthorizeStartDate() {
		return authorizeStartDate;
	}
	public void setAuthorizeStartDate(Date authorizeStartDate) {
		this.authorizeStartDate = authorizeStartDate;
	}
	public Date getAuthorizeEndDate() {
		return authorizeEndDate;
	}
	public void setAuthorizeEndDate(Date authorizeEndDate) {
		this.authorizeEndDate = authorizeEndDate;
	}
	public AuthorizePermissions getAuthorizePermissions() {
		return authorizePermissions;
	}
	public void setAuthorizePermissions(AuthorizePermissions authorizePermissions) {
		this.authorizePermissions = authorizePermissions;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
}
