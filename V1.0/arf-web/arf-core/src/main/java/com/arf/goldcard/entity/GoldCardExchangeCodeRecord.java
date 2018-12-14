package com.arf.goldcard.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "p_gold_card_exchange_code_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "p_gold_card_exchange_code_record_sequence")
public class GoldCardExchangeCodeRecord extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	private String exchangeCode;//兑换码,8为数字+大写字母组合
	private String businessName;//发放人(商户)名称
	private String businessMobile;//发放人(商户)手机号
	private Date grantDate;//发放时间
	private String userName;//兑换人用户名
	private String cardNo;//卡编号,与p_user_gold_card. card_no多对一映射
	private Date exchangeDate;//兑换时间
	
	@Column(length=20)
	public String getExchangeCode() {
		return exchangeCode;
	}
	@Column(length=20)
	public String getBusinessName() {
		return businessName;
	}
	@Column(length=20)
	public String getBusinessMobile() {
		return businessMobile;
	}
	public Date getGrantDate() {
		return grantDate;
	}
	@Column(length=20)
	public String getUserName() {
		return userName;
	}
	@Column(length=40)
	public String getCardNo() {
		return cardNo;
	}
	public Date getExchangeDate() {
		return exchangeDate;
	}
	
	
	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public void setBusinessMobile(String businessMobile) {
		this.businessMobile = businessMobile;
	}
	public void setGrantDate(Date grantDate) {
		this.grantDate = grantDate;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public void setExchangeDate(Date exchangeDate) {
		this.exchangeDate = exchangeDate;
	}

}
