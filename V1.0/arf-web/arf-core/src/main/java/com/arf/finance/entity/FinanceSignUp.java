package com.arf.finance.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="finance_sign_up")
@SequenceGenerator(name="sequenceGenerator",sequenceName="finance_sign_up_sequence")
public class FinanceSignUp extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	private String communityNumber;//小区编号
	private Long financeInfoId;//项目id
	private String phone;//电话号码
	private String name;//用户姓名
	private Identity identity;//身份 0 安心点用户 1 游客
	private Date signUpTime;//报名时间
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Identity{
		MEMBER,VISITOR
	}

	@Column(length=20)
	public String getCommunityNumber() {
		return communityNumber;
	}
	public Long getFinanceInfoId() {
		return financeInfoId;
	}
	@Column(length=20)
	public String getPhone() {
		return phone;
	}
	@Column(length=50)
	public String getName() {
		return name;
	}
	public Identity getIdentity() {
		return identity;
	}
	public Date getSignUpTime() {
		return signUpTime;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public void setFinanceInfoId(Long financeInfoId) {
		this.financeInfoId = financeInfoId;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setIdentity(Identity identity) {
		this.identity = identity;
	}
	public void setSignUpTime(Date signUpTime) {
		this.signUpTime = signUpTime;
	}
	
}
