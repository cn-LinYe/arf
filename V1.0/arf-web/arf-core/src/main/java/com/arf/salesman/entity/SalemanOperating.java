package com.arf.salesman.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name="r_saleman_operating")
@SequenceGenerator(name="sequenceGenerator",sequenceName="r_saleman_operating_sequence")
public class SalemanOperating extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5964970176003542238L;

	private String userName	;//	varchar(40)	业务员账号
	private String fullName	;//业务员姓名
	private Date operatingDate	;//	操作开始时间
	private String communityNumber	;//	小区编号
	private Byte status	;//	闸门状态 ( 0开启)
	
	@Column(length=40)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length=40)
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Date getOperatingDate() {
		return operatingDate;
	}
	public void setOperatingDate(Date operatingDate) {
		this.operatingDate = operatingDate;
	}
	@Column(length=40)
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	
}
