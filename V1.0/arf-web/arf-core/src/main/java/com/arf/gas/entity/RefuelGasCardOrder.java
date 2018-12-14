package com.arf.gas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 用户开卡订单表
 */
@Entity
@Table(name = "refuel_gas_card_order")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "refuel_gas_card_order_sequence")
public class RefuelGasCardOrder extends BaseEntity<Long>{

	private static final long serialVersionUID = 496603003753149567L;
	private String orderNo	;//varchar(50)	否		普通	订单编号
	private String userName	;//varchar(1000)	否		普通	用户名（多个用户用,分割）
	private Integer cardAmount		;//否		普通	开卡金额
	private PayType payTpye		;//否		普通	支付类型（0、微信支付 Wechat_Payment 1、支付宝支付 Alipay_Payment）
	private OrderStatus orderStatus		;//否		普通	订单状态（0、待支付 Not_Pay 1、已支付 Pay 2、支付失败 Pay_Failure）
	private Integer gunNum		;//否		普通	油站编号
	private Integer businessNum	;//否		普通	商户编号
	private Integer rewardAmount;//奖励金额(商户)
	private Integer userRewardAmount;//奖励金额(用户)
	private Long configId;//奖励政策id
	
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum OrderStatus{
		 Not_Pay,Pay,Pay_Failure
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum PayType{
		Wechat_Payment,Alipay_Payment
	}
	
	
	
	public Long getConfigId() {
		return configId;
	}
	public void setConfigId(Long configId) {
		this.configId = configId;
	}
	public Integer getUserRewardAmount() {
		return userRewardAmount;
	}
	public void setUserRewardAmount(Integer userRewardAmount) {
		this.userRewardAmount = userRewardAmount;
	}
	public Integer getRewardAmount() {
		return rewardAmount;
	}
	public void setRewardAmount(Integer rewardAmount) {
		this.rewardAmount = rewardAmount;
	}
	@Column(length=50)
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	@Column(length=1000)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getCardAmount() {
		return cardAmount;
	}
	public void setCardAmount(Integer cardAmount) {
		this.cardAmount = cardAmount;
	}
	public PayType getPayTpye() {
		return payTpye;
	}
	public void setPayTpye(PayType payTpye) {
		this.payTpye = payTpye;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Integer getGunNum() {
		return gunNum;
	}
	public void setGunNum(Integer gunNum) {
		this.gunNum = gunNum;
	}
	public Integer getBusinessNum() {
		return businessNum;
	}
	public void setBusinessNum(Integer businessNum) {
		this.businessNum = businessNum;
	}
	
}
