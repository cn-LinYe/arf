package com.arf.core.entity;


import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 停车费纪录表（订单）
 * @author Administrator
 */

@Entity
@Table(name = "parkRateRecord")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "parkRateRecord_sequence")
public class ParkRateRecordModel extends BaseEntity<Long>{
	
    private static final long serialVersionUID = -6861684132208555896L;
    
    /*** 订单号*/
    private String out_trade_no;
    /*** 支付用途 */
    private String subject;
    /*** 订单状态 */
    private String tradeStatus;
    
    /** 业主电话,必须用户名*/
	private String userName;
	/** 业主姓名*/
	private String name;
	/**车牌号 * */ 
	private String licensePlateNumber;
	/*** 物业id*/
	private int propretyId;
	/** 计费类型 1:按月计费 2:按季度计费 3:按年计费*/
	private int chargeType;
	/** 停车场类型1:地上 2:地下*/
	private int parkingType;
	/** 支付金额 */
	private BigDecimal amount;
	 /** 开始日期*/
	private long startTime;
	/** 结束日期*/
	private long endTime;
	/** 纪录类型 停车缴费默认为1*/
	private int recordType=1;
	/** 小区id  */
	private String communityNumber;
	/** 订单成功是否同步前端小区车闸有效期*/
//	private boolean isSync;
	/**
	 * 向ECC道闸提交是否成功
	 * -1订单生成默认值
	 * 0提交失败
	 * 1提交成功
	 */
	private Integer isSubmitSuccess=-1;
	//子公司编号 
	private Integer branchId;
	
	/**
	 * 支付类型
	 */
	private FeePayType feePayType;
	

	
	private int point;//赠送积分
	private String voucherName;//礼品券名称
	
	private Integer chargeMonths;//缴费月数
	private Date paidDate;//支付完成时间
	
	/**
	 * 月租缴费预缴一定月份赠送几个月这样的需求
	 */
	private BigDecimal receivablesMoney; //应收缴费金额
	private Integer donativeMonths;//包含赠送月数
	private String couponNum;//优惠编码（关联缴费优惠活动）
	
	@Column(precision = 10, scale = 2)
	public BigDecimal getReceivablesMoney() {
		return receivablesMoney;
	}
	public Integer getDonativeMonths() {
		return donativeMonths;
	}
	public String getCouponNum() {
		return couponNum;
	}
	public void setReceivablesMoney(BigDecimal receivablesMoney) {
		this.receivablesMoney = receivablesMoney;
	}
	public void setDonativeMonths(Integer donativeMonths) {
		this.donativeMonths = donativeMonths;
	}
	public void setCouponNum(String couponNum) {
		this.couponNum = couponNum;
	}
	/**
	 * 费用支付方式
	 * 支付方式 0 微信支付，1支付宝支付, 2钱包支付,  3银行卡, 4现金,  5套餐支付 6停车卡支付
	 * @author Caolibao
	 * 2016年5月23日 下午4:25:55
	 *
	 */
	public enum FeePayType{
		Paid_WeiXin, //微信支付
		Paid_Alipay, //支付宝支付
		Paid_Account, //钱包支付
		Paid_UnionPay, //银行卡
		Paid_Cash, //现金
		Paid_Package, //套餐支付
		Paid_GoldCard //停车卡支付
		;
		
		public static FeePayType get(int ordinal){
			FeePayType types[] = FeePayType.values();
			if(ordinal > types.length - 1){
				return null;
			}else{
				return types[ordinal];
			}
		}
	}
	
	@Column(name = "isSubmitSuccess")
	public Integer getIsSubmitSuccess() {
		return isSubmitSuccess;
	}
	public void setIsSubmitSuccess(Integer isSubmitSuccess) {
		this.isSubmitSuccess = isSubmitSuccess;
	}
	
	@Column(name = "userName", length = 64)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name = "name", length = 32)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "licensePlateNumber", length = 32)
	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}
	public void setLicensePlateNumber(String licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
	}
	
	@Column(name = "propretyId")
	public int getPropretyId() {
		return propretyId;
	}
	public void setPropretyId(int propretyId) {
		this.propretyId = propretyId;
	}
	
	@Column(name = "chargeType")
	public int getChargeType() {
		return chargeType;
	}
	public void setChargeType(int chargeType) {
		this.chargeType = chargeType;
	}
	
	@Column(name = "parkingType")
	public int getParkingType() {
		return parkingType;
	}
	public void setParkingType(int parkingType) {
		this.parkingType = parkingType;
	}
	
	@Column(name = "amount")
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	
	@Column(name = "startTime")
	public long getStartTime() {
		return startTime;
	}
	
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	@Column(name = "endTime")
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	@Column(name = "recordType")
	public int getRecordType() {
		return recordType;
	}
	public void setRecordType(int recordType) {
		this.recordType = recordType;
	}
	
	@Column(name = "communityNumber",length=32)
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	
	
	
	
	
	
	
	
	public FeePayType getFeePayType() {
		return feePayType;
	}
	public void setFeePayType(FeePayType feePayType) {
		this.feePayType = feePayType;
	}
	@Column(name = "out_trade_no",length=32)
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	@Column(name = "subject",length=32)
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
//	@Column(name = "isSync")
//	public boolean isSync() {
//		return isSync;
//	}
//	public void setSync(boolean isSync) {
//		this.isSync = isSync;
//	}
	
	@Column(name = "tradeStatus",length=32)
	public String getTradeStatus() {
		return tradeStatus;
	}
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	
	public ParkRateRecordModel(String userName, String name, String licensePlateNumber, int propretyId, int chargeType,
			int parkingType, BigDecimal bigDecimal, long startTime, long endTime, int recordType) {
		super();
		this.userName = userName;
		this.name = name;
		this.licensePlateNumber = licensePlateNumber;
		this.propretyId = propretyId;
		this.chargeType = chargeType;
		this.parkingType = parkingType;
		this.amount = bigDecimal;
		this.startTime = startTime;
		this.endTime = endTime;
		this.recordType = recordType;
	}
	public ParkRateRecordModel() {
		super();
	}
	public Integer getBranchId() {
		return branchId;
	}
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	public int getPoint() {
		return point;
	}
	@Column(length=40)
	public String getVoucherName() {
		return voucherName;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public void setVoucherName(String voucherName) {
		this.voucherName = voucherName;
	}
	public Integer getChargeMonths() {
		return chargeMonths;
	}
	public void setChargeMonths(Integer chargeMonths) {
		this.chargeMonths = chargeMonths;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getPaidDate() {
		return paidDate;
	}
	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}
}
