package com.arf.platform.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "r_ecc_connection_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "r_ecc_connection_record_sequence")
public class EccConnectionRecord extends BaseEntity<Long>{

	private static final long serialVersionUID = 1L;
	
	private String communityNo;
	private String communityName;
	private String propertyNumber;
	private String branchId;
	private Short notified;
	private String notifiedMobile;
	private String notifiedDingding;
	private String notifiedWechat;
	private Date onlineTime;
	private Date offlineTime;
	private Long duration;
	private Long lastOfflineDuration;
	private String ip;
	private Long sessionId;
	
	@Column(length=20)
	public String getCommunityNo() {
		return communityNo;
	}
	@Column(length=100)
	public String getCommunityName() {
		return communityName;
	}
	@Column(length=20)
	public String getPropertyNumber() {
		return propertyNumber;
	}
	@Column(length=20)
	public String getBranchId() {
		return branchId;
	}
	@Column(length=1)
	public Short getNotified() {
		return notified;
	}
	@Column(length=20)
	public String getNotifiedMobile() {
		return notifiedMobile;
	}
	@Column(length=50)
	public String getNotifiedDingding() {
		return notifiedDingding;
	}
	@Column(length=50)
	public String getNotifiedWechat() {
		return notifiedWechat;
	}
	public Date getOnlineTime() {
		return onlineTime;
	}
	public Date getOfflineTime() {
		return offlineTime;
	}
	public Long getDuration() {
		return duration;
	}
	public Long getLastOfflineDuration() {
		return lastOfflineDuration;
	}
	@Column(length=50)
	public String getIp() {
		return ip;
	}
	public Long getSessionId() {
		return sessionId;
	}
	public void setCommunityNo(String communityNo) {
		this.communityNo = communityNo;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	public void setPropertyNumber(String propertyNumber) {
		this.propertyNumber = propertyNumber;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public void setNotified(Short notified) {
		this.notified = notified;
	}
	public void setNotifiedMobile(String notifiedMobile) {
		this.notifiedMobile = notifiedMobile;
	}
	public void setNotifiedDingding(String notifiedDingding) {
		this.notifiedDingding = notifiedDingding;
	}
	public void setNotifiedWechat(String notifiedWechat) {
		this.notifiedWechat = notifiedWechat;
	}
	public void setOnlineTime(Date onlineTime) {
		this.onlineTime = onlineTime;
	}
	public void setOfflineTime(Date offlineTime) {
		this.offlineTime = offlineTime;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public void setLastOfflineDuration(Long lastOfflineDuration) {
		this.lastOfflineDuration = lastOfflineDuration;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}

}
