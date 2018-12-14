package com.arf.goldcard.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "r_clear_gold_card_balance_log")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "ClearGoldCardBalanceLog_sequence")
public class ClearGoldCardBalanceLog extends BaseEntity<Long>{
	private static final long serialVersionUID = 6017666928916177953L;
	
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 卡号
	 */
	private String cardNo;
	/**
	 * 本次清零金额
	 */
	private BigDecimal clearAmount;
	
	private Date endDate;//失效时间
	
	public ClearGoldCardBalanceLog(String userName, String cardNo, BigDecimal clearAmount, Date endDate) {
		super();
		this.userName = userName;
		this.cardNo = cardNo;
		this.clearAmount = clearAmount;
		this.endDate = endDate;
	}
	public ClearGoldCardBalanceLog() {
		super();
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getClearAmount() {
		return clearAmount;
	}
	public void setClearAmount(BigDecimal clearAmount) {
		this.clearAmount = clearAmount;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
