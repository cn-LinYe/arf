package com.arf.office.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "office_company_attendance_group")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "office_company_attendance_group_sequence")
public class OfficeCompanyAttendanceGroup extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5927583017439438970L;
	
	private String groupName;//考勤组名称
	private GroupType groupType;//考勤组类型（0 fixed 固定班制 1free自由工时 2 scheduling排班制）
	private String departmentList;//参与部门（部门编号，以“，”分割）
	private String otherUserList;//其他参与人员（用户名，以“，”分割）
	private String excludeUserList;//无需考勤人员（以“，”分割）
	private SignType signType;//（0 two 一天两次1four一天四次）
	private String amWorkStartTime;//上午上班打卡时间（小时08:00（））
	private String amWorkEndTime;//上午下班打卡时间
	private String pmWorkStartTime;//下午上班打卡时间
	private String pmWorkEndTime;//下午下班打卡时间
	private String weekList;//工作日（取系统星期数字，以“，”分割）
	private String refreshTime;//每天考勤开始时间（09：00”）
	private Date effectiveStartDate;//有效开始时间
	private Date effectiveEndDate;//有效结束时间
	private Status status;//	状态（0normal正常 1 invalid失效）
	private String companyNumber;//企业编号
	private String accessList;//考勤门禁设备（门禁设备id，以“，”分割）
	private String wifiList;//考勤wifi（考勤方式id，以“，”分割）
	private String locationList;//考勤定位（考勤方式id，以“，”分割）
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum GroupType{
		fixed,free,scheduling;
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum SignType{
		two,four;
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		normal,invalid;
	}
	@Column(length = 40)
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public GroupType getGroupType() {
		return groupType;
	}

	public void setGroupType(GroupType groupType) {
		this.groupType = groupType;
	}
	@Column(length = 1000)
	public String getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(String departmentList) {
		this.departmentList = departmentList;
	}
	@Column(length = 1000)
	public String getOtherUserList() {
		return otherUserList;
	}

	public void setOtherUserList(String otherUserList) {
		this.otherUserList = otherUserList;
	}
	@Column(length = 1000)
	public String getExcludeUserList() {
		return excludeUserList;
	}

	public void setExcludeUserList(String excludeUserList) {
		this.excludeUserList = excludeUserList;
	}

	public SignType getSignType() {
		return signType;
	}

	public void setSignType(SignType signType) {
		this.signType = signType;
	}
	@Column(length = 10)
	public String getAmWorkStartTime() {
		return amWorkStartTime;
	}

	public void setAmWorkStartTime(String amWorkStartTime) {
		this.amWorkStartTime = amWorkStartTime;
	}
	@Column(length = 10)
	public String getAmWorkEndTime() {
		return amWorkEndTime;
	}

	public void setAmWorkEndTime(String amWorkEndTime) {
		this.amWorkEndTime = amWorkEndTime;
	}
	@Column(length = 10)
	public String getPmWorkStartTime() {
		return pmWorkStartTime;
	}

	public void setPmWorkStartTime(String pmWorkStartTime) {
		this.pmWorkStartTime = pmWorkStartTime;
	}
	@Column(length = 10)
	public String getPmWorkEndTime() {
		return pmWorkEndTime;
	}

	public void setPmWorkEndTime(String pmWorkEndTime) {
		this.pmWorkEndTime = pmWorkEndTime;
	}
	@Column(length = 20)
	public String getWeekList() {
		return weekList;
	}

	public void setWeekList(String weekList) {
		this.weekList = weekList;
	}
	@Column(length = 10)
	public String getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(String refreshTime) {
		this.refreshTime = refreshTime;
	}

	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}

	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}

	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	@Column(length = 20)
	public String getCompanyNumber() {
		return companyNumber;
	}

	public void setCompanyNumber(String companyNumber) {
		this.companyNumber = companyNumber;
	}
	@Column(length = 100)
	public String getAccessList() {
		return accessList;
	}

	public void setAccessList(String accessList) {
		this.accessList = accessList;
	}
	@Column(length = 100)
	public String getWifiList() {
		return wifiList;
	}

	public void setWifiList(String wifiList) {
		this.wifiList = wifiList;
	}
	@Column(length = 100)
	public String getLocationList() {
		return locationList;
	}

	public void setLocationList(String locationList) {
		this.locationList = locationList;
	}

}
