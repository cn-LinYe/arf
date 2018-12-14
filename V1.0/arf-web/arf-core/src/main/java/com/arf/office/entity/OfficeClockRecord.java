package com.arf.office.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.arf.office.entity.OfficeCompanyAttendanceGroup.SignType;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "office_clock_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "office_clock_record_sequence")
public class OfficeClockRecord extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8472020015539646669L;
	
	private String userName;//用户名
	private String companyNumber;//公司编号
	private String amBeginWorkTime;//上午上班时间
	private String amOffWorkTime;//上午下班时间
	private String pmBeginWorkTime;//下午上班时间
	private String pmOffWorkTime;//下午下班时间
	private Date clockDate;//考勤日期
	private Long attendenceGroupId;//所属考勤组id
	private Integer workDuration;//工作时长（秒）
	private Status status;//状态（0.normal正常 1.late迟到、2.advance早退、3.lack缺卡、4.absenteeism旷工、5.overtime加班）
	private Status amBeginStatus;//上午上班打卡状态（0.normal正常 1.late迟到、2.advance早退、3.lack缺卡、4.absenteeism旷工、5.overtime加班）
	private Status amOffStatus;//上午下班打卡状态（0.normal正常 1.late迟到、2.advance早退、3.lack缺卡、4.absenteeism旷工、5.overtime加班）
	private Status pmBeginStatus;//下午上班打卡状态（0.normal正常 1.late迟到、2.advance早退、3.lack缺卡、4.absenteeism旷工、5.overtime加班）
	private Status pmOffStatus;//下午下班打卡状态（0.normal正常 1.late迟到、2.advance早退、3.lack缺卡、4.absenteeism旷工、5.overtime加班）
	private SignType signType;//签到类型（0 two 一天两次1four一天四次）
	
	private String equipmentIdentifier;//设备标识符
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		normal,late,advance,lack,absenteeism,overtime;
	}
	
	@Column(length = 40)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length = 20)
	public String getCompanyNumber() {
		return companyNumber;
	}
	public void setCompanyNumber(String companyNumber) {
		this.companyNumber = companyNumber;
	}
	@Column(length = 10)
	public String getAmBeginWorkTime() {
		return amBeginWorkTime;
	}
	public void setAmBeginWorkTime(String amBeginWorkTime) {
		this.amBeginWorkTime = amBeginWorkTime;
	}
	@Column(length = 10)
	public String getAmOffWorkTime() {
		return amOffWorkTime;
	}
	public void setAmOffWorkTime(String amOffWorkTime) {
		this.amOffWorkTime = amOffWorkTime;
	}
	@Column(length = 10)
	public String getPmBeginWorkTime() {
		return pmBeginWorkTime;
	}
	public void setPmBeginWorkTime(String pmBeginWorkTime) {
		this.pmBeginWorkTime = pmBeginWorkTime;
	}
	@Column(length = 10)
	public String getPmOffWorkTime() {
		return pmOffWorkTime;
	}
	public void setPmOffWorkTime(String pmOffWorkTime) {
		this.pmOffWorkTime = pmOffWorkTime;
	}
	public Date getClockDate() {
		return clockDate;
	}
	public void setClockDate(Date clockDate) {
		this.clockDate = clockDate;
	}
	public Long getAttendenceGroupId() {
		return attendenceGroupId;
	}
	public void setAttendenceGroupId(Long attendenceGroupId) {
		this.attendenceGroupId = attendenceGroupId;
	}
	public Integer getWorkDuration() {
		return workDuration;
	}
	public void setWorkDuration(Integer workDuration) {
		this.workDuration = workDuration;
	}
	public Status getAmBeginStatus() {
		return amBeginStatus;
	}
	public void setAmBeginStatus(Status amBeginStatus) {
		this.amBeginStatus = amBeginStatus;
	}
	public Status getAmOffStatus() {
		return amOffStatus;
	}
	public void setAmOffStatus(Status amOffStatus) {
		this.amOffStatus = amOffStatus;
	}
	public Status getPmBeginStatus() {
		return pmBeginStatus;
	}
	public void setPmBeginStatus(Status pmBeginStatus) {
		this.pmBeginStatus = pmBeginStatus;
	}
	public Status getPmOffStatus() {
		return pmOffStatus;
	}
	public void setPmOffStatus(Status pmOffStatus) {
		this.pmOffStatus = pmOffStatus;
	}
	public SignType getSignType() {
		return signType;
	}
	public void setSignType(SignType signType) {
		this.signType = signType;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	@Column(length = 50)
	public String getEquipmentIdentifier() {
		return equipmentIdentifier;
	}
	public void setEquipmentIdentifier(String equipmentIdentifier) {
		this.equipmentIdentifier = equipmentIdentifier;
	}

}
