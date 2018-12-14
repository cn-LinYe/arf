package com.arf.access.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.access.entity.AccessUser.Status;
import com.arf.access.entity.AccessUser.UserType;
import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "access_n_households")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "access_n_households_sequence")
public class AccessNHouseholds extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 607381314788643570L;
	
	private String userName	;//varchar(32)	not null	用户账号	
	private String realName	;//varchar(32)	not null	用户姓名
	private String communityName	;//varchar(32)		小区名称	
	private String communityNumber	;//varchar(20)		小区编号	
	private Status	status	;//not null	状态:0正常1禁用2删除
	private UserType userType	;//tinyint	Not null 	用户类型1 户主2 租客3 物业 4 其他
	private String midCommunityNumber;//设备小区编号（新门禁）
	private Integer communityBuildingId;//关联楼栋单元
	private Integer roomNum;//房间编号
	private String room;//房间信息
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getCommunityName() {
		return communityName;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	public String getMidCommunityNumber() {
		return midCommunityNumber;
	}
	public void setMidCommunityNumber(String midCommunityNumber) {
		this.midCommunityNumber = midCommunityNumber;
	}
	
	public Integer getCommunityBuildingId() {
		return communityBuildingId;
	}
	public void setCommunityBuildingId(Integer communityBuildingId) {
		this.communityBuildingId = communityBuildingId;
	}
	
	public Integer getRoomNum() {
		return roomNum;
	}
	public void setRoomNum(Integer roomNum) {
		this.roomNum = roomNum;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	
}
