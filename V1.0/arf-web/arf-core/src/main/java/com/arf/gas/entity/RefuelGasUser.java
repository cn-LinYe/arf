package com.arf.gas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "refuel_gas_user")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "refuel_gas_user_sequence")
public class RefuelGasUser  extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1841104786669740767L;
	
	private String userName;//加油员账号
	private String phone;//加油员手机号码
	private String password;//加油员账号密码
	private Integer gasNum;//加油站编号
	private Integer businessNum;//商户编号
	private String loginAccount;//登陆账号（登陆账号不同即可）登陆用
	private Status status;//人员状态（0、正常 Normal 1、禁用 Disabled）
	private String realName;//加油员名称
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		Normal,//正常
		Disabled;// 禁用
	}

	@Column(length=20)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(length=20)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(length=20)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getGasNum() {
		return gasNum;
	}

	public void setGasNum(Integer gasNum) {
		this.gasNum = gasNum;
	}

	public Integer getBusinessNum() {
		return businessNum;
	}

	public void setBusinessNum(Integer businessNum) {
		this.businessNum = businessNum;
	}

	@Column(length=20)
	public String getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

}
