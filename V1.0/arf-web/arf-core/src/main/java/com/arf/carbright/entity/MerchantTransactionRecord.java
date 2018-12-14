package com.arf.carbright.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "merchant_transaction_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "merchant_transaction_record_sequence")
public class MerchantTransactionRecord extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5491792202404312768L;
	private RecordType recordType;/*记录类型枚举 0、 全部记录AllRecord 1、物业缴费PropertyPayment 
									2、月租缴费 MonthlyPayment 3、临时停车及减免TemporaryParking 
									4、车保超市 InsuranceSupermarket 5、洗衣帮帮 Laundry 
									6、扫码支付 SweepPayment 7、点滴洗 dripCar*/
	private Integer totalAmount;//消费总金额（单位:分）
	private Integer paymentAmount;//支付金额（单位：分）
	private Integer platformVouchers;//平台代金券（单位：分）
	private Integer businessVouchers;//商家代金券（单位：分）
	private Integer businessIncome;//商家进账（单位：分）
	private Integer dailyDeductions;//日常抵扣金额（单位：分）
	private Integer balance;//账户余额（单位：分）
	private RecordStatus recordStatus;//记录状态（0 已支付pay 1 退款 refunds ）
	private SourceType sourceType;//来源（ 0 用户 user 1 merchant 商户）
	private TransactionStatus transactionStatus;//交易状态（0 credited 进账 1 扣除deduction）
	private String orderNo;//订单编号
	private String communityNumber;//小区编号
	private String businessNum;//商户编号
	private Integer platformPoint;//平台积分抵扣（单位：分）
	private Integer businessPoint;//商户积分抵扣（单位：分）
	private String consumeUser;//实际消费者
	private PayType payType;//用户支付方式:0钱包支付，1微信支付，2支付宝支付，3 银行卡, 4现金, 5停车卡, 6钱包-停车卡
	private String remark;//简要备注:eg.临时停车缴费/购买点滴洗
	
	public enum RecordType{
		AllRecord,// 0、 全部记录（查询记录时才会使用）
		PropertyPayment,//1、物业缴费
		MonthlyPayment,//2、月租缴费 
		TemporaryParking,//3、临时停车及减免
		InsuranceSupermarket,//4、车保超市
		Laundry,//5、洗衣帮帮 
		SweepPayment,//6、扫码支付 
		dripCar,//7、点滴洗 
		Violation;//8、违章
	}
	
	public enum RecordStatus{
		pay,// 0 已支付pay 1 退款  refunds
		refunds;//1 退款  refunds
	}
	
	public enum SourceType{
		user,//  0 用户 user 
		merchant;//1 merchant 商户
	}
	
	public enum TransactionStatus{
		credited,// 0 credited 进账 
		deduction;//1 扣除deduction
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

	public RecordType getRecordType() {
		return recordType;
	}

	public Integer getTotalAmount() {
		return totalAmount;
	}

	public Integer getPaymentAmount() {
		return paymentAmount;
	}

	public Integer getPlatformVouchers() {
		return platformVouchers;
	}

	public Integer getBusinessVouchers() {
		return businessVouchers;
	}

	public Integer getBusinessIncome() {
		return businessIncome;
	}

	public Integer getDailyDeductions() {
		return dailyDeductions;
	}

	public Integer getBalance() {
		return balance;
	}

	public RecordStatus getRecordStatus() {
		return recordStatus;
	}

	public SourceType getSourceType() {
		return sourceType;
	}

	public TransactionStatus getTransactionStatus() {
		return transactionStatus;
	}

	@Column(length=40)
	public String getOrderNo() {
		return orderNo;
	}

	@Column(length=40)
	public String getCommunityNumber() {
		return communityNumber;
	}

	@Column(length=40)
	public String getBusinessNum() {
		return businessNum;
	}

	public Integer getPlatformPoint() {
		return platformPoint;
	}
	
	public Integer getBusinessPoint() {
		return businessPoint;
	}

	public String getConsumeUser() {
		return consumeUser;
	}

	public PayType getPayType() {
		return payType;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setRecordType(RecordType recordType) {
		this.recordType = recordType;
	}

	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setPaymentAmount(Integer paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public void setPlatformVouchers(Integer platformVouchers) {
		this.platformVouchers = platformVouchers;
	}

	public void setBusinessVouchers(Integer businessVouchers) {
		this.businessVouchers = businessVouchers;
	}

	public void setBusinessIncome(Integer businessIncome) {
		this.businessIncome = businessIncome;
	}

	public void setDailyDeductions(Integer dailyDeductions) {
		this.dailyDeductions = dailyDeductions;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public void setRecordStatus(RecordStatus recordStatus) {
		this.recordStatus = recordStatus;
	}

	public void setSourceType(SourceType sourceType) {
		this.sourceType = sourceType;
	}

	public void setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	
	public void setBusinessNum(String businessNum) {
		this.businessNum = businessNum;
	}
	
	public void setPlatformPoint(Integer platformPoint) {
		this.platformPoint = platformPoint;
	}
	
	public void setBusinessPoint(Integer businessPoint) {
		this.businessPoint = businessPoint;
	}
	
	public void setConsumeUser(String consumeUser) {
		this.consumeUser = consumeUser;
	}
	
	public void setPayType(PayType payType) {
		this.payType = payType;
	}

	public String getRemark() {
		return remark;
	}

}
