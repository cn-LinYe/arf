package com.arf.eparking.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "r_parking_fee_application_records")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "r_parking_fee_application_records_sequence")
public class ParkingFeeApplicationRecords extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6607233201542700658L;

	private String userName;//车主账号
	private String name;//车主姓名
	private String orderNo;//订单编号
	private Date arriveTime;//车来时间
	private Date leaveTime;//停车截止时间
	private Integer duration;//停车时长
	private String businessNum;//商户编号
	private String communityNumber;//小区编号
	private BigDecimal fee;//停车费用（实收）,单位元
	private BigDecimal discountPrice;//商家抵扣金额
	private BigDecimal toPayPrice;//代付金额
	private ToPay toPay;//是否代付 0 不代付  1 代付用户 2 代付商家
	private String toPayUser;//代付用户（商家）
	private Date applyDate;//用户申请时间
	private Date operatingDate;//商家操作时间
	private ApplyStatus applyStatus;//申请情况（通过\拒绝）Pending_Review 0待审核Through 1通过 Refuse  2 拒绝
	private FeePayStatus feePayStatus;//支付状态0;未支付1;已支付 2;支付失败
	private PayType payType;//支付方式:0钱包支付，1微信支付，2支付宝支付，3 银行卡, 4现金, 5停车卡, 6钱包-停车卡
	private String license;//车牌号码
	private Date payDate;//支付时间
	private String openId;//微信openid
	private IsUseServer isUseServer;//是否使用本次减免0未使用（未支付）1已使用
	
	public enum ToPay{
		NotToPay,//0 不代付 
		To_Pay_User,//  1 代付用户 
		To_Business_Pay;//2 代付商家
	}
	public enum ApplyStatus{
		Pending_Review,// 0待审核
		Through,//  1 通过 
		Refuse,//2 拒绝
		Failure;//3 无效记录
	}
	public enum FeePayStatus{
		NotPay,//0;未支付
		PaySuccess,//  1;已支付 
		PayFail;//2;支付失败
	}
	public enum PayType{
		Paid_Account,//0钱包支付， 
		Paid_WeiXin,// 1微信支付，
		Paid_Alipay,//2支付宝支付，
		Paid_UnionPay, //3 银行卡,
		Paid_Cash, // 4现金,
		Paid_GoldCard, //5停车卡,
		Paid_AccountToGoldCard;// 6钱包-停车卡
	}
	public enum IsUseServer{
		NotUse,// 0未使用
		HasUse;//  1 已使用 
	}

	
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	@Column(length = 40)
	public String getUserName() {
		return userName;
	}
	@Column(length = 40)
	public String getName() {
		return name;
	}
	@Column(length = 40)
	public String getOrderNo() {
		return orderNo;
	}

	public Date getArriveTime() {
		return arriveTime;
	}

	public Date getLeaveTime() {
		return leaveTime;
	}

	@Column(length = 40)
	public String getBusinessNum() {
		return businessNum;
	}
	@Column(length = 20)
	public String getCommunityNumber() {
		return communityNumber;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getFee() {
		return fee;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getToPayPrice() {
		return toPayPrice;
	}

	public ToPay getToPay() {
		return toPay;
	}
	@Column(length=40)
	public String getToPayUser() {
		return toPayUser;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public Date getOperatingDate() {
		return operatingDate;
	}

	public ApplyStatus getApplyStatus() {
		return applyStatus;
	}

	public FeePayStatus getFeePayStatus() {
		return feePayStatus;
	}

	public PayType getPayType() {
		return payType;
	}
	@Column(length=40)
	public String getLicense() {
		return license;
	}

	public Date getPayDate() {
		return payDate;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}


	public void setBusinessNum(String businessNum) {
		this.businessNum = businessNum;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}

	public void setToPayPrice(BigDecimal toPayPrice) {
		this.toPayPrice = toPayPrice;
	}

	public void setToPay(ToPay toPay) {
		this.toPay = toPay;
	}

	public void setToPayUser(String toPayUser) {
		this.toPayUser = toPayUser;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public void setOperatingDate(Date operatingDate) {
		this.operatingDate = operatingDate;
	}

	public void setApplyStatus(ApplyStatus applyStatus) {
		this.applyStatus = applyStatus;
	}

	public void setFeePayStatus(FeePayStatus feePayStatus) {
		this.feePayStatus = feePayStatus;
	}

	public void setPayType(PayType payType) {
		this.payType = payType;
	}

	public void setLicense(String license) {
		this.license = license;
	}
	public IsUseServer getIsUseServer() {
		return isUseServer;
	}
	public void setIsUseServer(IsUseServer isUseServer) {
		this.isUseServer = isUseServer;
	}
}
