package com.arf.axd_b2b.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;


@Entity
@Table(name = "b2b_goods_order")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "b2b_goods_order_sequence")
public class GoodsOrder extends BaseEntity<Long> {
	private static final long serialVersionUID = 1L;
	
	private String orderNo;//商户方订单编号
	private String axdOrderNo;//安心点订单编号
	private String notifyUrl;//支付结果通知地址url
	private String axdUserId;//安心点用户id
	private int payPrice;//订单金额(单位:分)
	private int paidFee;//实付金额(单位:分)
	private String commodityData;//订单商品JSON数据
	private Status status;//状态枚举:
	private String businessNum;//商户编号
	private Date paidDate;//支付时间
	
	private PayType payType;//支付方式
	
	
	public enum PayType {
		AXD_ACCOUNT,
		ALIPAY,
		WX_PAY,
		;
	}

	public enum Status{
		NEW,//未支付;
		PAID,//已支付;
		REFUNDING,//退款中;
		REFUNDED,//退款成功
		REFUNDFAILED,//退款失败
		CANCELED,//已取消
		;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public String getAxdOrderNo() {
		return axdOrderNo;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public String getAxdUserId() {
		return axdUserId;
	}

	public int getPayPrice() {
		return payPrice;
	}

	public int getPaidFee() {
		return paidFee;
	}

	public String getCommodityData() {
		return commodityData;
	}

	public Status getStatus() {
		return status;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public void setAxdOrderNo(String axdOrderNo) {
		this.axdOrderNo = axdOrderNo;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public void setAxdUserId(String axdUserId) {
		this.axdUserId = axdUserId;
	}

	public void setPayPrice(int payPrice) {
		this.payPrice = payPrice;
	}

	public void setPaidFee(int paidFee) {
		this.paidFee = paidFee;
	}

	public void setCommodityData(String commodityData) {
		this.commodityData = commodityData;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getBusinessNum() {
		return businessNum;
	}

	public void setBusinessNum(String businessNum) {
		this.businessNum = businessNum;
	}

	public Date getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}

	public PayType getPayType() {
		return payType;
	}

	public void setPayType(PayType payType) {
		this.payType = payType;
	}
}
