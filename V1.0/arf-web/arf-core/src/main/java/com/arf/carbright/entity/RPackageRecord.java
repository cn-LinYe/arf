package com.arf.carbright.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

/**
 * 套餐类型表
 */
@Entity
@Table(name = "r_package_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "rPackageRecord_sequence")
public class RPackageRecord extends BaseEntity<Long> {
	private static final long serialVersionUID = 1L;

	public static final long default_Timestamp=4070880000L;//购买套餐时不限制套餐时间统一采用默认时间戳
	public static final String default_Times="2099-01-01 00:00:00";//购买套餐时不限制套餐时间统一采用默认时间
	/**
	 * 内部订单编号,唯一
	 */
	private String orderNo;

	/**
	 * 支付订单编号,区别于{@link com.arf.carbright.entity.RPackageRecord.orderNo}
	 * 该字段用于购物车一次购买多条套餐记录时对多条订单记录 多条套餐一次付款购买时使用同一outTradeNo
	 */
	private String outTradeNo;

	// 套餐类型id
	private Integer packageTypeId;
	// 用户账户id
	private String userName;

	/**
	 * 编码规则: OTA打头的类型编号为洗外观的套餐; OTB打头的类型编号为洗外观+内饰的套餐;
	 */
	private String packageTypeNum;
	// 套餐名称
	private String packageName;
	// 套餐价格，单位元
	private BigDecimal packagePrice;
	// 赠送金额，单位元
	private BigDecimal giftAmount;
	// 购买日期
	private Date buyingDate;
	// 有效起始时间（页面）
	private Date startTime;
	// 有效起始时间（时间戳）
	private Long startTimeNum;
	// 有效结束时间（页面）
	private Date endTime;
	// 有效结束时间（时间戳）
	private Long endTimeNum;
	// 套餐描述
	private String packageDesc;
	// 已使用次数
	private int usedTimes;
	// 可使用次数
	private int times;
	// 状态0可用；1不可用
	private Integer status;
	// 支付状态0;未支付1;已支付 2;支付失败
	private Integer feePayStatus;
	// 使用范围编码
	private String useRangeNum;
	// 停车场id/小区id
	private String parkingId;
	// 商户id
	private Integer businessId;

	// 车牌号
	private String license;
	/**
	 * 支付方式:{@link com.arf.carbright.entity.RPackageRecord.FeePayType}
	 */
	private Integer feePayType;
	// 物业编号
	private Integer propertyNumber;
	// 子公司编号
	private Integer branchId;

	// 限制使用时间12:00-15:00
	private String limitTimesUse;
	// 限制不能使用时间13:00-15:00
	private String limitUseTime;
	// 服务时长(分钟)
	private Integer longService;
	//发行量（0为不限制）
	private Integer packageCirculation;
	//代金券金额
	private BigDecimal vouchersMoney ;
	//代金券编号
	private String vouchersNums ;
	//代金券组名
	private String vouchersNames ;
	//积分
	private int point ;
	//代金券抵扣金额
	private BigDecimal deductions ;	
	//是否返还 积分或者代金券（0不用返还1有可能要返还 2已返还）
	private Integer returnPoint;
	//返还时间
	private Date returnDate;
	
	private Date pushTime;//推送时间

	public enum ReturnPointVoucher{
		Not_Return,Possible_Return,IS_Return;
	}
	
	public enum FeePayType {
		Others, 
		Weixin_Pay, // 微信支付
		Alipay, // 支付宝支付
		Member_Account, // 会员在线账户
		Property_Donation,//物业转赠
		Others_Donation,//他人转赠
		;

		public static RPackageRecord.FeePayType get(int ordinal) {
			RPackageRecord.FeePayType statuss[] = RPackageRecord.FeePayType.values();
			if (ordinal > statuss.length - 1) {
				return null;
			} else {
				return statuss[ordinal];
			}
		}
	}

	public enum Status {
		available, not_available;

		public static RPackageRecord.Status get(int ordinal) {
			RPackageRecord.Status statuss[] = RPackageRecord.Status.values();
			if (ordinal > statuss.length - 1) {
				return null;
			} else {
				return statuss[ordinal];
			}
		}
	}

	public enum FeePayStatus {
		not_pay, pay_success, pay_fail,;

		public static RPackageRecord.FeePayStatus get(int ordinal) {
			RPackageRecord.FeePayStatus statuss[] = RPackageRecord.FeePayStatus.values();
			if (ordinal > statuss.length - 1) {
				return null;
			} else {
				return statuss[ordinal];
			}
		}
	}
	
	

	public Date getPushTime() {
		return pushTime;
	}

	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}

	@Column(name = "orderNo", length = 32, unique = true)
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name = "feePayStatus")
	public Integer getFeePayStatus() {
		return feePayStatus;
	}

	public void setFeePayStatus(Integer feePayStatus) {
		this.feePayStatus = feePayStatus;
	}

	@Column(name = "packageTypeId")
	public Integer getPackageTypeId() {
		return packageTypeId;
	}

	public void setPackageTypeId(Integer packageTypeId) {
		this.packageTypeId = packageTypeId;
	}

	@Column(name = "userName", length = 20)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "packageTypeNum", length = 20)
	public String getPackageTypeNum() {
		return packageTypeNum;
	}

	public void setPackageTypeNum(String packageTypeNum) {
		this.packageTypeNum = packageTypeNum;
	}

	@Column(name = "packageName", length = 32)
	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	@Column(name = "packagePrice", precision = 10, scale = 2)
	public BigDecimal getPackagePrice() {
		return packagePrice;
	}

	public void setPackagePrice(BigDecimal packagePrice) {
		this.packagePrice = packagePrice;
	}

	@Column(name = "giftAmount", precision = 10, scale = 2)
	public BigDecimal getGiftAmount() {
		return giftAmount;
	}

	public void setGiftAmount(BigDecimal giftAmount) {
		this.giftAmount = giftAmount;
	}

	@Column(name = "buyingDate")
	public Date getBuyingDate() {
		return buyingDate;
	}

	public void setBuyingDate(Date buyingDate) {
		this.buyingDate = buyingDate;
	}

	@Column(name = "startTime")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "startTimeNum")
	public Long getStartTimeNum() {
		return startTimeNum;
	}

	public void setStartTimeNum(Long startTimeNum) {
		this.startTimeNum = startTimeNum;
	}

	@Column(name = "endTime")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "endTimeNum")
	public Long getEndTimeNum() {
		return endTimeNum;
	}

	public void setEndTimeNum(Long endTimeNum) {
		this.endTimeNum = endTimeNum;
	}

	@Column(name = "packageDesc", length = 200)
	public String getPackageDesc() {
		return packageDesc;
	}

	public void setPackageDesc(String packageDesc) {
		this.packageDesc = packageDesc;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "useRangeNum", length = 20)
	public String getUseRangeNum() {
		return useRangeNum;
	}

	public void setUseRangeNum(String useRangeNum) {
		this.useRangeNum = useRangeNum;
	}

	@Column(name = "parkingId", length = 20)
	public String getParkingId() {
		return parkingId;
	}

	public void setParkingId(String parkingId) {
		this.parkingId = parkingId;
	}

	@Column(name = "businessId")
	public Integer getBusinessId() {
		return businessId;
	}

	@Column(length = 32, unique = false)
	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	public int getUsedTimes() {
		return usedTimes;
	}

	public int getTimes() {
		return times;
	}

	public void setUsedTimes(int usedTimes) {
		this.usedTimes = usedTimes;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	@Column(length = 200)
	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public Integer getFeePayType() {
		return feePayType;
	}

	public void setFeePayType(Integer feePayType) {
		this.feePayType = feePayType;
	}

	public Integer getPropertyNumber() {
		return propertyNumber;
	}

	public void setPropertyNumber(Integer propertyNumber) {
		this.propertyNumber = propertyNumber;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	@Column(length = 40)
	public String getLimitTimesUse() {
		return limitTimesUse;
	}

	public void setLimitTimesUse(String limitTimesUse) {
		this.limitTimesUse = limitTimesUse;
	}

	@Column(length = 40)
	public String getLimitUseTime() {
		return limitUseTime;
	}

	public void setLimitUseTime(String limitUseTime) {
		this.limitUseTime = limitUseTime;
	}

	public Integer getLongService() {
		return longService;
	}

	public void setLongService(Integer longService) {
		this.longService = longService;
	}

	public Integer getPackageCirculation() {
		return packageCirculation;
	}

	public void setPackageCirculation(Integer packageCirculation) {
		this.packageCirculation = packageCirculation;
	}

	@Column(precision=10,scale=2)
	public BigDecimal getVouchersMoney() {
		return vouchersMoney;
	}

	public void setVouchersMoney(BigDecimal vouchersMoney) {
		this.vouchersMoney = vouchersMoney;
	}
	@Column(length=100)
	public String getVouchersNums() {
		return vouchersNums;
	}

	public void setVouchersNums(String vouchersNums) {
		this.vouchersNums = vouchersNums;
	}
	@Column(length=100)
	public String getVouchersNames() {
		return vouchersNames;
	}

	public void setVouchersNames(String vouchersNames) {
		this.vouchersNames = vouchersNames;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getDeductions() {
		return deductions;
	}

	public void setDeductions(BigDecimal deductions) {
		this.deductions = deductions;
	}

	public Integer getReturnPoint() {
		return returnPoint;
	}

	public void setReturnPoint(Integer returnPoint) {
		this.returnPoint = returnPoint;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	

}
