package com.arf.access.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "access_open_setting")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "access_open_setting_sequence")
public class AccessOpenSetting extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8458810348918175462L;
	
	
	
	private Long accessId	;//varchar(32)	not null	门禁id
	private TimeType timeType	;//tinyint	Not null 	时间类型 0 Whole 全天 1 Period 时间段 
	private String userName	;//varchar(20)		用户名	
	private String startTime	;//varchar(20)		开始时间（09:00）
	private String endTime	;//varchar(20)			结束时间（12:00）
	
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum TimeType{//时间类型 0 Whole 全天 1 Period 时间段 
		Whole,Period
	}

	
	

	
	public TimeType getTimeType() {
		return timeType;
	}

	@Column(nullable=false)
	public Long getAccessId() {
		return accessId;
	}


	public void setAccessId(Long accessId) {
		this.accessId = accessId;
	}

	@Column(nullable=false)
	public void setTimeType(TimeType timeType) {
		this.timeType = timeType;
	}

	@Column(length=20,nullable=false)
	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(length=10)
	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@Column(length=10)
	public String getEndTime() {
		return endTime;
	}


	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
