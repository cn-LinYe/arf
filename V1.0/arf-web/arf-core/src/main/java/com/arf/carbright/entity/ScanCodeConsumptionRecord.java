package com.arf.carbright.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "r_scan_code_consumption_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "r_scan_code_consumption_record_sequence")
public class ScanCodeConsumptionRecord extends BaseEntity<Long>{

	/**
	 * 扫码消费记录表
	 */
	private static final long serialVersionUID = -5415124002163354892L;
	
	private String orderNum	;//varchar(40)	订单编号
	private Date consumptionTime	;//datetime	消费时间
	private String userName	;//int	用户名
	private Integer businessNum	;//int	商户编号
	private String businessName	;//Varchar(100)	商户名称
	private BigDecimal consumptionAmount	;//decimal	消费金额
	private Integer IsDiscount	;//int	是否抵扣（0抵扣1无抵扣）
	private Integer VouchersType	;//int	代金券类型（0：平台1：商家）
	private String vouchersNum	;//tinyint	优惠券编号
	private String vouchersName	;//String	优惠券名称
	private BigDecimal facePrice	;//decimal	代金券面值
	private BigDecimal payPrice	;//decimal	支付金额
	private BigDecimal bussinessIncome	;//decimal	商家收入
	private BigDecimal platformIncome	;//decimal	平台收入
	private BigDecimal discountAmount;//商家折扣金额
	private PayStatus payStatus;//支付状态0未支付1已支付
	private PayType payType;//支付方式0钱包支付，1微信支付，2支付宝支付，3 银行卡, 4现金, 5停车卡, 6钱包-停车卡
	private Long voucherRecordId;//优惠券记录id
	
	public enum PayStatus{
		noDeductible,deductible
	}
	public enum PayType{
		wallet,WeChat,Alipay,Bank ,cash,Parking ,walletPark;
		public static PayType get(int index){
			PayType status[]=PayType.values();
			if(index > status.length - 1){
				return null;
			}else{
				return status[index];
			}
		}
		
	}
	
	public Long getVoucherRecordId() {
		return voucherRecordId;
	}
	public void setVoucherRecordId(Long voucherRecordId) {
		this.voucherRecordId = voucherRecordId;
	}
	public PayStatus getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(PayStatus payStatus) {
		this.payStatus = payStatus;
	}
	public PayType getPayType() {
		return payType;
	}
	public void setPayType(PayType payType) {
		this.payType = payType;
	}
	public enum IsDiscount{
		deductible,noDeductible
	}
	public enum VouchersType{
		platform,business
	}
	@Column(length=40)
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public Date getConsumptionTime() {
		return consumptionTime;
	}
	public void setConsumptionTime(Date consumptionTime) {
		this.consumptionTime = consumptionTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getBusinessNum() {
		return businessNum;
	}
	public void setBusinessNum(Integer businessNum) {
		this.businessNum = businessNum;
	}
	@Column(length=40)
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getConsumptionAmount() {
		return consumptionAmount;
	}
	public void setConsumptionAmount(BigDecimal consumptionAmount) {
		this.consumptionAmount = consumptionAmount;
	}
	public Integer getIsDiscount() {
		return IsDiscount;
	}
	public void setIsDiscount(Integer isDiscount) {
		IsDiscount = isDiscount;
	}
	public Integer getVouchersType() {
		return VouchersType;
	}
	public void setVouchersType(Integer vouchersType) {
		VouchersType = vouchersType;
	}
	public String getVouchersNum() {
		return vouchersNum;
	}
	public void setVouchersNum(String vouchersNum) {
		this.vouchersNum = vouchersNum;
	}
	@Column(length=40)
	public String getVouchersName() {
		return vouchersName;
	}
	public void setVouchersName(String vouchersName) {
		this.vouchersName = vouchersName;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getFacePrice() {
		return facePrice;
	}
	public void setFacePrice(BigDecimal facePrice) {
		this.facePrice = facePrice;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getPayPrice() {
		return payPrice;
	}
	public void setPayPrice(BigDecimal payPrice) {
		this.payPrice = payPrice;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getBussinessIncome() {
		return bussinessIncome;
	}
	public void setBussinessIncome(BigDecimal bussinessIncome) {
		this.bussinessIncome = bussinessIncome;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getPlatformIncome() {
		return platformIncome;
	}
	public void setPlatformIncome(BigDecimal platformIncome) {
		this.platformIncome = platformIncome;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}
	
	

}

