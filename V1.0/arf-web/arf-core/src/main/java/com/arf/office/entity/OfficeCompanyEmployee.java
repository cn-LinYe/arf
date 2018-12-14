package com.arf.office.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "office_company_employee")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "office_company_employee_sequence")
public class OfficeCompanyEmployee extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4965667838644753379L;
	
	private String userName;//用户名
	private String realName;//员工姓名
	private String companyNumber;//公司编号
	private String departmentNumber;//部门编号
	private String position;//职位
	private Status status;//状态（0 unBind 未创建1 normal在职 2leave 离职 ）
	private UserType userType;//用户身份（0 superManager 主管理员1manager管理员2employee员工）
	private String roomNumber;//办公室房屋编号
	private BindType bindType;//绑定类型（0 property物业添加 1supply主动申请 2 invitedByCode被邀请-二维码 3invitedByPhone被邀请-手机号）
	private Date leaveTime;//离职时间
	private String bindUser;//绑定发起人
	private String unbundUser;//触发离职的人
	private Boolean needFirstAlert;//首次查询到该记录是否需要alert提示
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum UserType{
		superManager,manager,employee;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		unBind,normal,leave;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum BindType{
		property,supply,invitedByCode,invitedByPhone;
	}
	
	@Column(length = 32)
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
	@Column(length = 20)
	public String getCompanyNumber() {
		return companyNumber;
	}
	public void setCompanyNumber(String companyNumber) {
		this.companyNumber = companyNumber;
	}
	@Column(length = 2000)
	public String getDepartmentNumber() {
		return departmentNumber;
	}
	public void setDepartmentNumber(String departmentNumber) {
		this.departmentNumber = departmentNumber;
	}
	@Column(length = 40)
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
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
	@Column(length = 40)
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	public BindType getBindType() {
		return bindType;
	}
	public void setBindType(BindType bindType) {
		this.bindType = bindType;
	}
	public Date getLeaveTime() {
		return leaveTime;
	}
	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}
	@Column(length = 20)
	public String getBindUser() {
		return bindUser;
	}
	public void setBindUser(String bindUser) {
		this.bindUser = bindUser;
	}
	@Column(length = 20)
	public String getUnbundUser() {
		return unbundUser;
	}
	public void setUnbundUser(String unbundUser) {
		this.unbundUser = unbundUser;
	}
	public Boolean getNeedFirstAlert() {
		return needFirstAlert;
	}
	public void setNeedFirstAlert(Boolean needFirstAlert) {
		this.needFirstAlert = needFirstAlert;
	}

}
