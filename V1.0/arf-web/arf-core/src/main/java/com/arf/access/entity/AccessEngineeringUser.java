package com.arf.access.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "access_engineering_user")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "access_engineering_user_sequence")
public class AccessEngineeringUser extends BaseEntity<Long>{

	/**
	 * 门禁工程机用户表
	 */
	private static final long serialVersionUID = 8458810348918175462L;
	
	private String fullName ;//	varchar(40)		用户姓名
	private String userName;//	varchar(40)		用户名称(登录名称)
	private String passwrod	;//varchar(40)		用户密码
	private Date   loginDate	;//datetime		登录时间
	private String communityNumber	;//varchar(40)		小区编号(管理小区编号)
	private String phoneImei	;//varchar(40)		手机imei地址
	private Date validityDate	;//datetime		登录截止日期
	private String phone	;//varchar(40)		联系方式
	private Status status;//状态:0正常1禁用
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		normal,disable
	}
	
	@Column(nullable=false)
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
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
	
	
}
