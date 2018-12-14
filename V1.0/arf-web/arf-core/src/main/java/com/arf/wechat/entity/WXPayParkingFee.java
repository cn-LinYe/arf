package com.arf.wechat.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="p_wx_pay_parking_fee")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "p_wx_pay_parking_fee_sequence")
public class WXPayParkingFee extends BaseEntity<Long> {

	private static final long serialVersionUID = 6125007104277405397L;
	
	private String license;//车牌号
	private String openId;//微信openid
	private Date arriveTime;//来车时间
	private Date leaveTime;//走车时间
	private Date paidDate;//支付时间
	private String parkingNumber;//停车场编号/小区编号
	private String parkingName;//停车场/小区名称
	private Integer duration;//停车时长,单位:秒
	private BigDecimal receivablesMoney;//应付车费 单位:元
	private BigDecimal receivedMoney;//预收车费 单位:元
	private BigDecimal fee;//实收车费 单位:元
	private PayStatus payStatus;//支付状态枚举 NOT_PAY 0未支付,PAID 1 已支付
	private Status status;//状态枚举:NORMAL 0 正常 ,DELETED 1 已删除
	private String outTradeNo;//订单号
	private Integer stopType;//停车类型
	private String userName;//业主电话/用户名
	private FeeSource feeSource;//0 CommunityOnlineFetch 小区在线抓取 1 ManualInput 手动输入
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum FeeSource{
		CommunityOnlineFetch,//小区在线抓取
		ManualInput,//手动输入
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum PayStatus{
		NOT_PAY,//未支付
		PAID,//已支付
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		NORMAL,//正常
		DELETED,//已删除
	}
	
	@Column(length = 10,nullable=false)
	public String getLicense() {
		return license;
	}
	@Column(length = 40,nullable=false)
	public String getOpenId() {
		return openId;
	}
	
	public Date getArriveTime() {
		return arriveTime;
	}
	public Date getLeaveTime() {
		return leaveTime;
	}
	public Date getPaidDate() {
		return paidDate;
	}
	@Column(length = 50,nullable=false)
	public String getParkingNumber() {
		return parkingNumber;
	}
	@Column(length = 50)
	public String getParkingName() {
		return parkingName;
	}
	@Column(length = 11)
	public Integer getDuration() {
		return duration;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getReceivablesMoney() {
		return receivablesMoney;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getReceivedMoney() {
		return receivedMoney;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getFee() {
		return fee;
	}
	public PayStatus getPayStatus() {
		return payStatus;
	}
	public Status getStatus() {
		return status;
	}
	@Column(length = 32,nullable=false)
	public String getOutTradeNo() {
		return outTradeNo;
	}
	@Column(length = 11)
	public Integer getStopType() {
		return stopType;
	}
	@Column(length = 64)
	public String getUserName() {
		return userName;
	}
	public FeeSource getFeeSource() {
		return feeSource;
	}
	
	public void setLicense(String license) {
		this.license = license;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}
	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}
	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}
	public void setParkingNumber(String parkingNumber) {
		this.parkingNumber = parkingNumber;
	}
	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public void setReceivablesMoney(BigDecimal receivablesMoney) {
		this.receivablesMoney = receivablesMoney;
	}
	public void setReceivedMoney(BigDecimal receivedMoney) {
		this.receivedMoney = receivedMoney;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	public void setPayStatus(PayStatus payStatus) {
		this.payStatus = payStatus;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public void setStopType(Integer stopType) {
		this.stopType = stopType;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setFeeSource(FeeSource feeSource) {
		this.feeSource = feeSource;
	}
	
}
