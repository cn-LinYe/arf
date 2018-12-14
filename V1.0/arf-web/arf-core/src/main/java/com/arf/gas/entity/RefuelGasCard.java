package com.arf.gas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 家加油充值卡表
 */
@Entity
@Table(name = "refuel_gas_card")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "refuel_gas_card_sequence")
public class RefuelGasCard extends BaseEntity<Long>{

	private static final long serialVersionUID = 1L;
	private String cardNumber	;//varchar(20)	否		普通	油卡编号
	private Integer cardAmount	;//int	否		普通	油卡账号金额
	private Integer consumptionAmount	;//int	否		普通	油卡消费金额（仅统计消费金额）
	private CardStatus cardStatus	;//tinyint	否		普通	油卡状态 （0、待激活 Not_Activated 1、已经激活（正常使用）Activated 2、已挂失 Loss 3、禁用 Disabled）
	private Integer businessNum	;//int	否		普通	商户编号
	private Integer gasNum	;//int	否		普通	加油站编号
	private String userName	;//varchar(20)	否		普通	持卡人账号
	private Integer rechargeTotalAmount;//充值累计金额（单位：分）
	private Integer giftTotalAmount;//赠送累计金额（单位：分）
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum CardStatus{
		Not_Activated, Activated ,Loss,Disabled
	}
	@Column(length=20)
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public Integer getCardAmount() {
		return cardAmount;
	}
	public void setCardAmount(Integer cardAmount) {
		this.cardAmount = cardAmount;
	}
	public Integer getConsumptionAmount() {
		return consumptionAmount;
	}
	public void setConsumptionAmount(Integer consumptionAmount) {
		this.consumptionAmount = consumptionAmount;
	}
	public CardStatus getCardStatus() {
		return cardStatus;
	}
	public void setCardStatus(CardStatus cardStatus) {
		this.cardStatus = cardStatus;
	}
	public Integer getBusinessNum() {
		return businessNum;
	}
	public void setBusinessNum(Integer businessNum) {
		this.businessNum = businessNum;
	}
	public Integer getGasNum() {
		return gasNum;
	}
	public void setGasNum(Integer gasNum) {
		this.gasNum = gasNum;
	}
	@Column(length=20)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getRechargeTotalAmount() {
		return rechargeTotalAmount;
	}
	public void setRechargeTotalAmount(Integer rechargeTotalAmount) {
		this.rechargeTotalAmount = rechargeTotalAmount;
	}
	public Integer getGiftTotalAmount() {
		return giftTotalAmount;
	}
	public void setGiftTotalAmount(Integer giftTotalAmount) {
		this.giftTotalAmount = giftTotalAmount;
	}
	
}
