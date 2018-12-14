package com.arf.ecc.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.Resolution;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "settlement_account")
@SequenceGenerator(name = "sequenceGenerator",sequenceName="settlement_account_sequence")
public class SettlementAccount extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	private String number;//子公司 /物业公司编号
	private Integer type;//1:子公司2:物业公司
	private Integer status;//0:启用,1禁用
	private BigDecimal balanceAmount;//账户余额
	private BigDecimal settlingAmount;//待结转金额
	private BigDecimal settledAmount;//已结转金额
	private Date lastSettledDate;//上次结转时间
	
	public static enum Type{
		no_use,
		branch,
		property,
	}
	
	public static enum Status{
		on,
		off,
	}
	
	@Column(length=20)
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
	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}
	@Column(precision = 10, scale = 2)
	public BigDecimal getSettlingAmount() {
		return settlingAmount;
	}
	@Column(precision = 10, scale = 2)
	public BigDecimal getSettledAmount() {
		return settledAmount;
	}
	@DateBridge(resolution = Resolution.SECOND)
	public Date getLastSettledDate() {
		return lastSettledDate;
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
	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public void setSettlingAmount(BigDecimal settlingAmount) {
		this.settlingAmount = settlingAmount;
	}
	public void setSettledAmount(BigDecimal settledAmount) {
		this.settledAmount = settledAmount;
	}
	public void setLastSettledDate(Date lastSettledDate) {
		this.lastSettledDate = lastSettledDate;
	}
}
