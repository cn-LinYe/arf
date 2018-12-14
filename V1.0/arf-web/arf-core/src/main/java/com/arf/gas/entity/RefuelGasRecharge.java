package com.arf.gas.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 家加油充值表
 */
		
@Entity
@Table(name = "refuel_gas_recharge")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "refuel_gas_recharge_sequence")
public class RefuelGasRecharge extends BaseEntity<Long>{

	private static final long serialVersionUID = -9151220579511473587L;

	private Integer gasNum	;//int	否		普通	油站编号
	private Integer businessNum	;//int	否		普通	商户编号
	private String rechargeOrderNo	;//varchar(20)	否		普通	充值订单编号
	private Integer rechargeAmount;//	int	否		普通	充值金额
	private Integer giftAmount;//	int	否		普通	赠送金额
	private PayType payType;//	tinyint	否		普通	支付类型（0、微信支付 Wechat_Payment 1、支付宝支付 Alipay_Payment
												//2、 银行支付 Bank_Payment 3、 电子钱包支付 Account_Payment）
	private String userName;//会员信息
	private OrderStatus orderStatus;//订单状态（0、待支付 Not_Pay 1、已支付 Pay 2、支付失败 Pay_Failure）
	private BusinessPayment businessPayment;//商家代充（0、Not 不是代充 1、Yes 是代充）
	private Date payDate;//支付时间
	private String cardNumber;//油卡编号
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum PayType{
		Wechat_Payment,Alipay_Payment , Bank_Payment,Account_Payment;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum OrderStatus{
		Not_Pay,Pay,Pay_Failure;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum BusinessPayment{
		Not,//0、不是代充
		Yes;//1、是代充
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

	@Column(length=32)
	public String getRechargeOrderNo() {
		return rechargeOrderNo;
	}

	public void setRechargeOrderNo(String rechargeOrderNo) {
		this.rechargeOrderNo = rechargeOrderNo;
	}

	public Integer getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(Integer rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	public Integer getGiftAmount() {
		return giftAmount;
	}

	public void setGiftAmount(Integer giftAmount) {
		this.giftAmount = giftAmount;
	}

	public PayType getPayType() {
		return payType;
	}

	public void setPayType(PayType payType) {
		this.payType = payType;
	}

	@Column(length=20)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public BusinessPayment getBusinessPayment() {
		return businessPayment;
	}

	public void setBusinessPayment(BusinessPayment businessPayment) {
		this.businessPayment = businessPayment;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

}
