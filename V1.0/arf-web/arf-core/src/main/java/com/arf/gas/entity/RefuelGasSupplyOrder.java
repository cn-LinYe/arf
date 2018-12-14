package com.arf.gas.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 家加油商家补油订单表
 */
@Entity
@Table(name = "refuel_gas_supply_order")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "refuel_gas_supply_order_sequence")
public class RefuelGasSupplyOrder  extends BaseEntity<Long>{

	private static final long serialVersionUID = 5000790444322881127L;

	private String supplyOrderNo	;//varchar(20)	否		普通	补油订单编号
	private Integer supplyAmount	;//int	否		普通	补油金额（单位：分）
	private Integer supplyActualAmount	;//int	否		普通	订单实际付款金额（单位:分）
	private Integer supplyPayPercent	;//int	否		普通	订单支付百分比（此比例缓存redis配合前端APP使用）
	private Integer supplyFreight;//	int	否		普通	补油运费
	private Integer gasNum	;//int	否		普通	油站编号
	private Integer businessNum	;//int	否		普通	商户编号
	private PayTpye payTpye	;//tinyint	否		普通	支付类型（0、微信支付 Wechat_Payment 1、支付宝支付 Alipay_Payment）
	private OrderStatus orderStatus	;//tinyint	否		普通	订单状态（0、待支付 Not_Paid 1、已付定金 Paid_Deposit 2、待付尾款 Not_Paid_Tail 3、付款完成 Paid_Completed）
	private Date supplyDate	;//datetime	否		普通	补油时间

	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum PayTpye{
		 Wechat_Payment,Alipay_Payment
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum OrderStatus{
		Not_Paid,Paid_Deposit,Not_Paid_Tail,Paid_Completed
	}
	@Column(length=30)
	public String getSupplyOrderNo() {
		return supplyOrderNo;
	}
	public void setSupplyOrderNo(String supplyOrderNo) {
		this.supplyOrderNo = supplyOrderNo;
	}
	public Integer getSupplyAmount() {
		return supplyAmount;
	}
	public void setSupplyAmount(Integer supplyAmount) {
		this.supplyAmount = supplyAmount;
	}
	public Integer getSupplyActualAmount() {
		return supplyActualAmount;
	}
	public void setSupplyActualAmount(Integer supplyActualAmount) {
		this.supplyActualAmount = supplyActualAmount;
	}
	public Integer getSupplyPayPercent() {
		return supplyPayPercent;
	}
	public void setSupplyPayPercent(Integer supplyPayPercent) {
		this.supplyPayPercent = supplyPayPercent;
	}
	public Integer getSupplyFreight() {
		return supplyFreight;
	}
	public void setSupplyFreight(Integer supplyFreight) {
		this.supplyFreight = supplyFreight;
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
	public PayTpye getPayTpye() {
		return payTpye;
	}
	public void setPayTpye(PayTpye payTpye) {
		this.payTpye = payTpye;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Date getSupplyDate() {
		return supplyDate;
	}
	public void setSupplyDate(Date supplyDate) {
		this.supplyDate = supplyDate;
	}
	
	
}
