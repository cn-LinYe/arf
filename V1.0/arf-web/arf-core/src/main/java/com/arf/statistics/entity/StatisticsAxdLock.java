package com.arf.statistics.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="statistics_axd_lock")
@SequenceGenerator(name="sequenceGenerator",sequenceName="statistics_axd_lock_sequence")
public class StatisticsAxdLock extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4643038640403778764L;
	
	private String communityNumber;//小区编号
	private Integer clickLockCount;//点击锁闸次数
	private Integer clickUnlockCount;//点击开闸次数
	private Date clickDate;//点击日期
	private Status status;//开闸/关闸状态 0 Success成功 1 Failure 失败
	private String userName;//用户名
	
	public enum Status{
		Success,//成功
		Failure;// 失败
	}
	@Column(length=20)
	public String getCommunityNumber() {
		return communityNumber;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}

	public Integer getClickLockCount() {
		return clickLockCount;
	}

	public void setClickLockCount(Integer clickLockCount) {
		this.clickLockCount = clickLockCount;
	}

	public Integer getClickUnlockCount() {
		return clickUnlockCount;
	}

	public void setClickUnlockCount(Integer clickUnlockCount) {
		this.clickUnlockCount = clickUnlockCount;
	}
	@JsonFormat(pattern="yyyy-MM-dd")
	public Date getClickDate() {
		return clickDate;
	}

	public void setClickDate(Date clickDate) {
		this.clickDate = clickDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
