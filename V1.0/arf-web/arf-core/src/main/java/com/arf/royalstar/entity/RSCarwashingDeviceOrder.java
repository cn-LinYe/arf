package com.arf.royalstar.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "rs_carwashing_device_order")
@SequenceGenerator(name = "sequenceGenerator",sequenceName = "carwashing_device_order_sequence")
public class RSCarwashingDeviceOrder extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	private String deviceCode;//varchar(20) 设备编号
	private String orderNo;//varchar(32) 订单编号,唯一约束
	private Status status;//tinyint 状态,0:未支付,1:已支付,2:已退币(款),3已启动
	private PayType payType;//tinyint 0:投币支付,1:微信支付,2:支付宝支付,3:在线余额支付
	private String userName;//varchar(30) 用户名,对于app提交订单的用户存在该字段
	private BigDecimal fee;//decimal(10,2) 金额
	
	public enum Status{
		Not_Paid,
		Paid,
		Refunded,
		Started,
		;
	}
	/**
	 * 0:投币支付,1:微信支付,2:支付宝支付,3:在线余额支付
	 * @author Caolibao
	 *
	 */
	public enum PayType{
		Cash,
		Wechat,
		Alipay,
		Ewallet;
	}
	
	@Column(length=20)
	public String getDeviceCode(){
		return deviceCode;
	}
	@Column(length=32)
	public String getOrderNo(){
		return orderNo;
	}
	public Status getStatus(){
		return status;
	}
	public PayType getPayType(){
		return payType;
	}
	@Column(length=30)
	public String getUserName(){
		return userName;
	}
	@Column(precision = 10, scale = 2)
	public BigDecimal getFee() {
		return fee;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public void setPayType(PayType payType) {
		this.payType = payType;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
}
