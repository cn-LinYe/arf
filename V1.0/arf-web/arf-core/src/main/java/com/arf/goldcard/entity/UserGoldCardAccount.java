package com.arf.goldcard.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "p_user_gold_card_account")
@SequenceGenerator(name = "sequenceGenerator",sequenceName="p_user_gold_card_account_sequence")
public class UserGoldCardAccount extends BaseEntity<Long> {
	private static final long serialVersionUID = 1L;
	
	private BigDecimal balance;//余额
	private String userName;//用户名
	private Date endDate;//失效时间
	private String cardNo;//第一次购买金卡时的卡编号
	
	@Column(precision=10,scale=2)
	public BigDecimal getBalance() {
		return balance;
	}
	@Column(length = 20)
	public String getUserName() {
		return userName;
	}
	public Date getEndDate() {
		return endDate;
	}
	@Column(length = 40)
	public String getCardNo() {
		return cardNo;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
}
