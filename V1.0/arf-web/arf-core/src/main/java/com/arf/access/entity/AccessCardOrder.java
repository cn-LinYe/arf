package com.arf.access.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "access_card_order")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "access_card_order_sequence")
public class AccessCardOrder extends BaseEntity<Long> {

	private static final long serialVersionUID = 4018909913364858377L;
	
	private String userName;//购买人用户名
	private Integer count;//购买数量,当前默认1
	private String outTradeNo;//订单编号
	private BigDecimal totalFee;//订单金额
	private String carNum;//卡编号(如果有)
	private PayType payType;//支付方式
	private Date paidDate;//购买时间
	private HadBound hadBound;//是否已经绑定
	private PayStatus payStatus;//缴费状态: 0:NOT_PAID  未缴费，1:PAID_SUCCESS 已缴费
	private String roomBoundNumber;//房间绑定编号
	private String roomNumber; //房间编号
	private BoundType boundType ;//0安心点1物业

	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum BoundType{
		AXD,
		PROPERTY
		;
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum PayType{
		UNKNOWN,
		WEIXIN,
		ALIPAY,
		WALLET_ACCOUNT,
		GOLDCARD
		;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum HadBound{
		NO,
		YES;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum PayStatus{
		NOT_PAID,
		PAID_SUCCESS,
		;
	}
	public BoundType getBoundType() {
		return boundType;
	}
	public void setBoundType(BoundType boundType) {
		this.boundType = boundType;
	}
	@Column(length = 40)
	public String getRoomBoundNumber() {
		return roomBoundNumber;
	}
	public void setRoomBoundNumber(String roomBoundNumber) {
		this.roomBoundNumber = roomBoundNumber;
	}
	@Column(length = 20)
	public String getUserName() {
		return userName;
	}
	public Integer getCount() {
		return count;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	@Column(precision = 10, scale = 2,nullable = false)
	public BigDecimal getTotalFee() {
		return totalFee;
	}
	public String getCarNum() {
		return carNum;
	}
	public PayType getPayType() {
		return payType;
	}
	public Date getPaidDate() {
		return paidDate;
	}
	public HadBound getHadBound() {
		return hadBound;
	}
	public PayStatus getPayStatus() {
		return payStatus;
	}
	
	public void setPayStatus(PayStatus payStatus) {
		this.payStatus = payStatus;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	public void setPayType(PayType payType) {
		this.payType = payType;
	}
	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}
	public void setHadBound(HadBound hadBound) {
		this.hadBound = hadBound;
	}
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
}
