package com.arf.ecc.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "settlement_record")
@SequenceGenerator(name="sequenceGenerator",sequenceName="settlement_record_sequence")
public class SettlementRecord extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	private String number;//物业公司/子公司编号
	private Integer type;//1:子公司,2:物业公司
	private Integer status;//0:完成,1:冻结,2:失败
	private BigDecimal settlementAmount;//本次结算金额
	private Integer settlementType;//结算类型:1 开通ECC会员，其它待定 
	private String userName;//产生结算金额的用户名,如果是开通ECC产生的结算金额,则user_name为开通ECC的用户
	private String communityNumber;//产生结算金额的小区编号,如果是开通ECC产生的结算金额,则community_number为开通ECC的小区编号
	
	public static enum Type{
		no_use,
		branch,
		property,
	}
	
	public static enum Status{
		finish,
		freeze,
		fail,
	}
	
	public static enum SettlementType{
		no_use,
		open_ecc,
	}
	
	public String getNumber() {
		return number;
	}
	public Integer getType() {
		return type;
	}
	public Integer getStatus() {
		return status;
	}
	@Column(precision = 10, scale = 2)
	public BigDecimal getSettlementAmount() {
		return settlementAmount;
	}
	public Integer getSettlementType() {
		return settlementType;
	}
	@Column(length=20)
	public String getUserName() {
		return userName;
	}
	@Column(length=20)
	public String getCommunityNumber() {
		return communityNumber;
	}
	
	
	public void setNumber(String number) {
		this.number = number;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setSettlementAmount(BigDecimal settlementAmount) {
		this.settlementAmount = settlementAmount;
	}
	public void setSettlementType(Integer settlementType) {
		this.settlementType = settlementType;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	
}
