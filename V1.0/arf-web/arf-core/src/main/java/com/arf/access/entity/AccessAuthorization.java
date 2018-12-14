package com.arf.access.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "access_authorization")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "access_authorization_sequence")
public class AccessAuthorization extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userName	;//varchar(32)	not null	用户账号(被授权用户)
	private String nickName ;//	varchar(32)		用户昵称
	private String authorizeUserName	;//varchar(32)	not null	用户账号(授权用户)
	private String communityName	;//varchar(32)		小区名称	
	private String communityNumber	;//varchar(20)		小区编号	
	private Status	status	;//not null	状态:0正常1禁用2 删除3过期
	private UserType userType	;//tinyint	Not null 	1 家属2 租客3 访客 4 其他
	private IsTemporary isTemporary;//是否是临时 0 非临时1 临时
	
	private String buildingName;//varchar(20)		栋名称--与之对应的building字段 eg. 3A,3-A,春风阁
	private String unitName;//varchar(20)		单元	名称--与之对应的unit字段 eg. A单元,2B单元,一单元
	
	private String building	;//varchar(20)		栋	
	private String unit	;//varchar(20)		单元	
	
	private String floor	;//varchar(20)		楼层	
	private String room	;//varchar(20)		房号	
	private String accessList	;//varchar(100)		门禁列表，以，分割（关联access_management）ID	
	private Date authorizeStartDate;//	datetime		授权开始时间	
	private Date authorizeEndDate;//	datetime		授权结束时间	
	
	private String roomBoundNumber;//房间绑定编号
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum IsTemporary{
		non_temporary,temporary
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{//正常，禁用，删除，过期
		normal,disable,delete,expired;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum AuthorizePermissions{
		allow,prohibit;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum UserType{
		notUse,family_members, tenant, visitor, others;
	}
	
	@Column(length = 40)
	public String getRoomBoundNumber() {
		return roomBoundNumber;
	}
	public void setRoomBoundNumber(String roomBoundNumber) {
		this.roomBoundNumber = roomBoundNumber;
	}
	@Column(length=32)
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public IsTemporary getIsTemporary() {
		return isTemporary;
	}
	public void setIsTemporary(IsTemporary isTemporary) {
		this.isTemporary = isTemporary;
	}
	@Column(length=32,nullable=false)
	public String getAuthorizeUserName() {
		return authorizeUserName;
	}
	public void setAuthorizeUserName(String authorizeUserName) {
		this.authorizeUserName = authorizeUserName;
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
	@Column(length=20)
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	
	@Column(length=20)
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
}
