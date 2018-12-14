package com.arf.royalstar.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.arf.royalstar.entity.RSCarwashingDeviceOrder.PayType;

@Entity
@Table(name = "rs_carwashing_device_log")
@SequenceGenerator(name = "sequenceGenerator",sequenceName="carwashing_device_log_sequence")
public class RSCarwashingDeviceLog extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2559991947123438394L;

	private String deviceCode; //varchar(20) 设备编号,唯一约束
	private String orderNo;//varchar(32) 订单编号,唯一约束
	private BigDecimal fee;//decimal(10,2) 金额
	private PayType payType;//tinyint 0:投币支付,1:微信支付,2:支付宝支付,3:在线余额支付
	private Type type;//tinyint 类型,0:支付回调,1:出币结果通知
	private String status;//varchar(20) 状态，SUCCESS 成功，FAIL失败
	private String params;//varchar(500) 参数
	private String returnmsg;//varchar(500)返回结果
	
	public enum Type{
		Payed_call_ack,
		Coin_out_result,
	}
	
	/**
	 * 0:投币支付,1:微信支付,2:支付宝支付,3:在线余额支付
	 * @author Caolibao
	 *
	 */
/*	public enum PayType{
		Cash,
		Wechat,
		Alipay,
		Ewallet;
	}*/
	
	public enum Status{
		SUCCESS,
		FAIL,
	}
	
	public RSCarwashingDeviceLog(String orderNo,BigDecimal fee,PayType payType,Type type,String status,String params,String returnmsg){
		this.orderNo = orderNo;
		this.fee = fee;
		this.payType = payType;
		this.type = type;
		this.status = status;
		this.params = params;
		this.returnmsg = returnmsg;
	}

	@Column(length=20)
	public String getDeviceCode() {
		return deviceCode;
	}
	@Column(length=32)
	public String getOrderNo() {
		return orderNo;
	}
	@Column(precision = 10, scale = 2)
	public BigDecimal getFee() {
		return fee;
	}
	public PayType getPayType() {
		return payType;
	}
	public Type getType() {
		return type;
	}
	@Column(length=20)
	public String getStatus() {
		return status;
	}
	@Column(name = "params", length =500)
	public String getParams() {
		return params;
	}
	@Column(name = "returnmsg", length =500)
	public String getReturnmsg() {
		return returnmsg;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public void setPayType(PayType payType) {
		this.payType = payType;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public void setReturnmsg(String returnmsg) {
		this.returnmsg = returnmsg;
	}
	
}
