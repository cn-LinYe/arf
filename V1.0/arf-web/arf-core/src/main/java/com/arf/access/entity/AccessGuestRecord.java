package com.arf.access.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "access_guest_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "access_guest_record_sequence")
public class AccessGuestRecord extends BaseEntity<Long> {

	private static final long serialVersionUID = 7638255907947910754L;
	public static final String APPLY_TIMEOUT_HOUR = "APPLY_TIMEOUT_HOUR";
	public static final String WECHAT_PUBLIC_ACCESS_GUEST_EFFECTIVE = "WECHAT_PUBLIC_ACCESS_GUEST_EFFECTIVE";
	public static final int WECHAT_PUBLIC_ACCESS_GUEST_EFFECTIVE_DEFAULT = 60;
	
	private String nickname;//(微信)昵称
	private String avatarUrl;//(微信)头像
	private String guestIdentifyId;//访客识别id(微信open_id)
	
	/**
	 * 新的门禁拜访申请通过房间编号来做关联关系,原有的只能拜访房产证业主的逻辑已不适用
	 */
	@Deprecated
	private Long accessUserId;//门禁用户表关联id
	private String communityNumber;//小区编号
	private String userName;//户主用户名
	/**
		状态枚举: 
		NEW 0 新建
		ACCESSIBLE 1已允许
		DECLINED 2已拒绝
		TIMEOUT 3 已过期
		SHIELDING 4 已拉黑
		DELETED 5 已删除
	 */
	private Status status = Status.NEW;
	private String reason;//申请理由
	private Date applyDate;//申请时间
	private Date allowDate;//允许时间
	/**
	 * 访客类型枚举(暂定只有微信端)
		WECHAT 0 微信小程序
		VIDEO_INTERCOM 1视频对讲 (如果是视频对讲没有微信相关信息)
		WECHAT_PUBLIC 微信公众号
	 */
	private GuestType guestType;
	private String accessBarcode;//开门条码
	private String accessTempPasswd;//临时密码
	
	private ReadStatus readStatus = ReadStatus.NONE;//反馈已读状态枚举
	
	private String roomBoundNumber;//房间绑定编号
	private String roomNumber;//房间编号
	private String accessNum;//拜访门禁编号
	private String buildingName;//楼栋名
	private String unitName;//单元名
	private String communityName;//小区名
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		NEW,
		ACCESSIBLE,
		DECLINED,
		TIMEOUT,
		SHIELDING,
		DELETED;
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum ReadStatus{
		NONE,
		READ,
		;
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum GuestType{
		WECHAT,
		VIDEO_INTERCOM,
		WECHAT_PUBLIC;
	}

	@Column(length=30)
	public String getNickname() {
		return nickname;
	}

	@Column(length=200)
	public String getAvatarUrl() {
		return avatarUrl;
	}

	@Column(length=50)
	public String getGuestIdentifyId() {
		return guestIdentifyId;
	}

	/**
	 * 新的门禁拜访申请通过房间编号来做关联关系,原有的只能拜访房产证业主的逻辑已不适用
	 */
	@Deprecated
	public Long getAccessUserId() {
		return accessUserId;
	}
	
	@Column(length=20)
	public String getCommunityNumber() {
		return communityNumber;
	}

	@Column(length=30)
	public String getUserName() {
		return userName;
	}

	public Status getStatus() {
		return status;
	}

	@Column(length=50)
	public String getReason() {
		return reason;
	}

	@Column(nullable=false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	public Date getApplyDate() {
		return applyDate;
	}

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	public Date getAllowDate() {
		return allowDate;
	}

	public GuestType getGuestType() {
		return guestType;
	}

	@Column(length=30)
	public String getAccessBarcode() {
		return accessBarcode;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public void setGuestIdentifyId(String guestIdentifyId) {
		this.guestIdentifyId = guestIdentifyId;
	}

	/**
	 * 新的门禁拜访申请通过房间编号来做关联关系,原有的只能拜访房产证业主的逻辑已不适用
	 * @param accessUserId
	 */
	@Deprecated
	public void setAccessUserId(Long accessUserId) {
		this.accessUserId = accessUserId;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public void setAllowDate(Date allowDate) {
		this.allowDate = allowDate;
	}

	public void setGuestType(GuestType guestType) {
		this.guestType = guestType;
	}

	public void setAccessBarcode(String accessBarcode) {
		this.accessBarcode = accessBarcode;
	}

	public ReadStatus getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(ReadStatus readStatus) {
		this.readStatus = readStatus;
	}

	public String getAccessTempPasswd() {
		return accessTempPasswd;
	}

	public void setAccessTempPasswd(String accessTempPasswd) {
		this.accessTempPasswd = accessTempPasswd;
	}

	public String getRoomBoundNumber() {
		return roomBoundNumber;
	}

	public void setRoomBoundNumber(String roomBoundNumber) {
		this.roomBoundNumber = roomBoundNumber;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getAccessNum() {
		return accessNum;
	}

	public void setAccessNum(String accessNum) {
		this.accessNum = accessNum;
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
	@Column(length=40)
	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
}
