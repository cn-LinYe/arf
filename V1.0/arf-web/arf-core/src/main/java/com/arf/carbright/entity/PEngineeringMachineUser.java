package com.arf.carbright.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "p_engineering_machine_user")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "p_engineering_machine_user_sequence")
public class PEngineeringMachineUser extends BaseEntity<Long>{

	/**
	 * 工程机登录用户表
	 */
	private static final long serialVersionUID = -3519681109375920195L;
	private String fullName;//用户姓名;
	private String userName;//用户名称(登录名称);
	private String passwrod;//用户密码;
	private Date loginDate;//登录时间;
	private String communityNumber;//小区编号(管理小区编号);
	private String phoneImei;//手机imei地址;
	private Date validityDate;//登录截止日期;
	private String phone; //联系方式;
	private Integer status;//状态(0启用1禁用);
	private String branchPropertyIds;//绑定子公司下小区	
	
	public enum Status{
		enablem, not_Enable;
	}
	
	@Column(length = 40)
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	@Column(length = 40)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length = 40)
	public String getPasswrod() {
		return passwrod;
	}
	public void setPasswrod(String passwrod) {
		this.passwrod = passwrod;
	}
	public Date getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	@Column(length = 1000)
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	@Column(length = 40)
	public String getPhoneImei() {
		return phoneImei;
	}
	public void setPhoneImei(String phoneImei) {
		this.phoneImei = phoneImei;
	}
	public Date getValidityDate() {
		return validityDate;
	}
	public void setValidityDate(Date validityDate) {
		this.validityDate = validityDate;
	}
	@Column(length = 40)
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(length = 1000)
	public String getBranchPropertyIds() {
		return branchPropertyIds;
	}
	public void setBranchPropertyIds(String branchPropertyIds) {
		this.branchPropertyIds = branchPropertyIds;
	}
	
}
