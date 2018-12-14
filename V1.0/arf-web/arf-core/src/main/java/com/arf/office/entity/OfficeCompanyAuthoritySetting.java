package com.arf.office.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "office_company_authority_setting")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "office_company_authority_setting_sequence")
public class OfficeCompanyAuthoritySetting extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8593176084051616544L;
	
	private String buletoothMac;//门禁蓝牙Mac
	private String authorityName;//权限组名称
	private AttendType attendType;//参与类型（0department 部门 1 human 人员）
	private String departmentList;//参与部门/人员（部门编号/用户名，以“，”分割）
	private String excludeUserList;//不参与人员（以“，”分割）
	private String weekList;//有效工作日（取系统星期数字，以“，”分割）
	private Date effectiveStartDate;//有效开始时间
	private Date effectiveEndDate;//有效结束时间
	private Status status;//状态（0normal正常 1 invalid失效）
	private String companyNumber;//企业编号
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum AttendType{
		department,human;
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		normal,invalid;
	}
	@Column(length = 32)
	public String getBuletoothMac() {
		return buletoothMac;
	}

	public void setBuletoothMac(String buletoothMac) {
		this.buletoothMac = buletoothMac;
	}
	@Column(length = 40)
	public String getAuthorityName() {
		return authorityName;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

	public AttendType getAttendType() {
		return attendType;
	}

	public void setAttendType(AttendType attendType) {
		this.attendType = attendType;
	}
	@Column(length = 1000)
	public String getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(String departmentList) {
		this.departmentList = departmentList;
	}
	@Column(length = 1000)
	public String getExcludeUserList() {
		return excludeUserList;
	}

	public void setExcludeUserList(String excludeUserList) {
		this.excludeUserList = excludeUserList;
	}
	@Column(length = 1000)
	public String getWeekList() {
		return weekList;
	}

	public void setWeekList(String weekList) {
		this.weekList = weekList;
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

}
