package com.arf.statistics.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "access_unlock_log_statistic",
	indexes={
			@Index(name="index_community_number",columnList="communityNumber")}
)
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "access_unlock_log_statistic_sequence")
public class StatisticsAccessUnlockLog extends BaseEntity<Long>{

	private static final long serialVersionUID = 1L;

	private String communityNumber;//小区
	private Date accessUnlockDate;//开门日期
	private Integer accessUnlockSuccessCount;//开门成功次数
	private Integer accessUnlockFailedCount;//开门失败次数
	
	//开门失败次数 ： 按手机统计
	private String accessUnlockFailedDetailAndroid;//安卓开门失败详情，json字符串{"xiao mi":1,"hua wei:12"}
	private String accessUnlockFailedDetailIOS;//iOS开门失败，json字符串{"iOS10":100,"iOS11:50"}
	private Integer accessUnlockFailedDetailOther;//其他原因开门失败，json字符串
	
	//开门失败次数 ：按原因统计
	private Integer accessUnlockFailedDetailConnectError;//连接错误（连接错误 + 未连接到门禁设备）
	private Integer accessUnlockFailedDetailNotFoundAccess;//未检测到门禁设备
	private Integer accessUnlockFailedDetailPwdError;//门禁密码错误
	
	@Column(length=20)
	public String getCommunityNumber() {
		return communityNumber;
	}
	public Date getAccessUnlockDate() {
		return accessUnlockDate;
	}
	public Integer getAccessUnlockSuccessCount() {
		return accessUnlockSuccessCount;
	}
	public Integer getAccessUnlockFailedCount() {
		return accessUnlockFailedCount;
	}
	@Column(length=1024)
	public String getAccessUnlockFailedDetailAndroid() {
		return accessUnlockFailedDetailAndroid;
	}
	@Column(length=1024)
	public String getAccessUnlockFailedDetailIOS() {
		return accessUnlockFailedDetailIOS;
	}
	public Integer getAccessUnlockFailedDetailOther() {
		return accessUnlockFailedDetailOther;
	}
	public Integer getAccessUnlockFailedDetailConnectError() {
		return accessUnlockFailedDetailConnectError;
	}
	public Integer getAccessUnlockFailedDetailNotFoundAccess() {
		return accessUnlockFailedDetailNotFoundAccess;
	}
	public Integer getAccessUnlockFailedDetailPwdError() {
		return accessUnlockFailedDetailPwdError;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public void setAccessUnlockDate(Date accessUnlockDate) {
		this.accessUnlockDate = accessUnlockDate;
	}
	public void setAccessUnlockSuccessCount(Integer accessUnlockSuccessCount) {
		this.accessUnlockSuccessCount = accessUnlockSuccessCount;
	}
	public void setAccessUnlockFailedCount(Integer accessUnlockFailedCount) {
		this.accessUnlockFailedCount = accessUnlockFailedCount;
	}
	public void setAccessUnlockFailedDetailAndroid(
			String accessUnlockFailedDetailAndroid) {
		this.accessUnlockFailedDetailAndroid = accessUnlockFailedDetailAndroid;
	}
	public void setAccessUnlockFailedDetailIOS(String accessUnlockFailedDetailIOS) {
		this.accessUnlockFailedDetailIOS = accessUnlockFailedDetailIOS;
	}
	public void setAccessUnlockFailedDetailOther(
			Integer accessUnlockFailedDetailOther) {
		this.accessUnlockFailedDetailOther = accessUnlockFailedDetailOther;
	}
	public void setAccessUnlockFailedDetailConnectError(
			Integer accessUnlockFailedDetailConnectError) {
		this.accessUnlockFailedDetailConnectError = accessUnlockFailedDetailConnectError;
	}
	public void setAccessUnlockFailedDetailNotFoundAccess(
			Integer accessUnlockFailedDetailNotFoundAccess) {
		this.accessUnlockFailedDetailNotFoundAccess = accessUnlockFailedDetailNotFoundAccess;
	}
	public void setAccessUnlockFailedDetailPwdError(
			Integer accessUnlockFailedDetailPwdError) {
		this.accessUnlockFailedDetailPwdError = accessUnlockFailedDetailPwdError;
	}
	
	
}
