package com.arf.goldcard.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "r_gold_card_transfer_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "r_gold_card_transfer_record_sequence")
public class GoldCardTransferRecord extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	private BigDecimal balance;//余额
	private BigDecimal amount;//扣费金额
	private String remark;//说明:eg.月租车包月扣费、临时停车扣费
	private Integer type;//类型:枚举 ECC_Month_Pkg月租车缴费 Temp_Parking_Fee临时停车费 Property_Fee物业费, Exchange_Code_Get 使用兑换码兑换金卡,Buy_GoldCard_Get 购买金卡套餐
	private String userName;//用户名
	private Integer status;//状态,0:正常,1:冻结
	private String orderNo;//订单号,如果为月租车缴费则为月租车订单编号,如果为临时停车缴费则为临时停车费订单编号
	private Integer payType ;//0充值 1扣除
	
	public enum Type{
		ECC_Month_Pkg, //月租缴费
		Temp_Parking_Fee,//临时停车费
		Property_Fee,//物业费缴费
		Exchange_Code_Get,//兑换码兑换停车卡获得余额
		Buy_GoldCard_Get,//购买新的停车卡获得余额
		Buy_ParkingCard_Get,//购买安心点停车卡获得余额,
		Parking_toPay,//停车减免代付
		Scan_Code,//扫码支付
		;
	}
	public enum PayType{
		Deduct,Recharge 
	}
	
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getBalance() {
		return balance;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getAmount() {
		return amount;
	}
	@Column(length=100)
	public String getRemark() {
		return remark;
	}
	@Column(length=11)
	public Integer getType() {
		return type;
	}
	@Column(length=20)
	public String getUserName() {
		return userName;
	}
	@Column(length=11)
	public Integer getStatus() {
		return status;
	}
	@Column(length=40)
	public String getOrderNo() {
		return orderNo;
	}
	
	
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
}
