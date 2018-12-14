package com.arf.carbright.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.arf.core.entity.BaseEntity;

/**
 * 套餐类型表
 */
@Entity
@Table(name = "p_package_type", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "packageTypeNum" }, name = "unique_package_type_num") })
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "p_package_type_sequence")
public class PackageType extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;

	public static final String Prefix_Cache_Package_Type_Setting = "RPackageRecord.PackageTypeSetting:";
	public static final String Prefix_Cache_Package_Type_Booking_Count_Of_Day = "RPackageRecord.PackageTypeBookingCountOfDay:";

	/**
	 * {@link com.arf.carbright.entity.CarWashingRecord.OrderType}
	 */
	//public static final String Prefix_Carbrighter_Appearance = "OTA";// 点滴洗-洗外观
	//public static final String Prefix_Carbrighter_Appearance_Interior = "OTB";// 点滴洗-洗外观+内饰

	/**
	 * 套餐类型编码 编码规则: OTA打头的类型编号为洗外观的套餐; OTB打头的类型编号为洗外观+内饰的套餐;
	 */
	private String packageTypeNum;
	// 套餐名称
	private String packageName;
	// 套餐价格，单位元
	private BigDecimal packagePrice;
	// 赠送金额，单位元
	private BigDecimal giftAmount;
	// 有效开始时间（页面）
	private Date startTime;
	// 有效开始时间（时间戳）(字段已经用来判断是否重复提交)
	private Long startTimeNum;
	// 有效结束时间（页面）
	private Date endTime;
	// 有效结束时间（时间戳）
	private Long endTimeNum;
	// 套餐描述
	private String packageDesc;
	// 是否启用0启用；1禁用2已删除3售罄新增
	private Integer isEnabled;
	// 是否允许积分兑换： 0不允许 1 允许
	private Integer isExchange;
	// 发放开始时间
	private Date sendStartTime;
	// 发放结束时间
	private Date sendEndTime;
	// 可使用次数
	private Integer times;
	// 使用范围Id
	private String useRangeNum;

	// 限制(购买)次数,为0表示不限制购买
	private int limitTimes;
	// 套餐限制周期
	private int limitCycle;
	// 限制周期（使用）次数
	private int limitUseTimes;

	// 小区编号
	private String communityNumber;
	// 物业编号
	private Integer propertyNumber;
	// 子公司编号
	private Integer branchId;

	// 车牌限制个数
	private Integer licenseCount;
	// 可以使用时间段12:00-15:00
	private String limitTimesUse;
	//限制不能使用时间13:00-15:00
	private String limitUseTime;
	//限制使用描述
	private String limitUseTimeDescription;
	//服务时长(分钟)
	private String longService;
    //发行量（0为不限制）
	private Integer packageCirculation;
//	有效时间（用户购买套餐后有效期）单位，分钟
	private Integer effectiveTime;	
	//置顶时间
	private Date putTopDate;
	

	public enum IsEnabled {
		enablem, not_Enable,delete,sellOut;
	}

	public enum IsExchange {
		not_exchange, exchange;
	}

	public Integer getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(Integer effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public Integer getPackageCirculation() {
		return packageCirculation;
	}

	public void setPackageCirculation(Integer packageCirculation) {
		this.packageCirculation = packageCirculation;
	}

	@Column(name = "times", length = 11)
	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
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

	@Column(name = "isEnabled")
	public Integer getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Integer isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Column(name = "isExchange")
	public Integer getIsExchange() {
		return isExchange;
	}

	public Date getPutTopDate() {
		return putTopDate;
	}

	public void setPutTopDate(Date putTopDate) {
		this.putTopDate = putTopDate;
	}

	public void setIsExchange(Integer isExchange) {
		this.isExchange = isExchange;
	}

	@Column(name = "sendStartTime")
	public Date getSendStartTime() {
		return sendStartTime;
	}

	public void setSendStartTime(Date sendStartTime) {
		this.sendStartTime = sendStartTime;
	}

	@Column(name = "sendEndTime")
	public Date getSendEndTime() {
		return sendEndTime;
	}

	public void setSendEndTime(Date sendEndTime) {
		this.sendEndTime = sendEndTime;
	}

	@Column(name = "useRangeNum")
	public String getUseRangeNum() {
		return useRangeNum;
	}

	public void setUseRangeNum(String useRangeNum) {
		this.useRangeNum = useRangeNum;
	}

	@Column(name = "limitTimes")
	public int getLimitTimes() {
		return limitTimes;
	}

	public void setLimitTimes(int limitTimes) {
		this.limitTimes = limitTimes;
	}

	@Column(name = "limitCycle")
	public int getLimitCycle() {
		return limitCycle;
	}

	public void setLimitCycle(int limitCycle) {
		this.limitCycle = limitCycle;
	}

	@Column(name = "limitUseTimes")
	public int getLimitUseTimes() {
		return limitUseTimes;
	}

	public void setLimitUseTimes(int limitUseTimes) {
		this.limitUseTimes = limitUseTimes;
	}

	public String getCommunityNumber() {
		return communityNumber;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
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

	public Integer getLicenseCount() {
		return licenseCount;
	}

	public void setLicenseCount(Integer licenseCount) {
		this.licenseCount = licenseCount;
	}
	
	@Column(name = "limitTimesUse", length = 200)
	public String getLimitTimesUse() {
		return limitTimesUse;
	}

	public void setLimitTimesUse(String limitTimesUse) {
		this.limitTimesUse = limitTimesUse;
	}

	public String getLimitUseTimeDescription() {
		return limitUseTimeDescription;
	}

	public void setLimitUseTimeDescription(String limitUseTimeDescription) {
		this.limitUseTimeDescription = limitUseTimeDescription;
	}

	public String getLongService() {
		return longService;
	}

	public void setLongService(String longService) {
		this.longService = longService;
	}

	public String getLimitUseTime() {
		return limitUseTime;
	}

	public void setLimitUseTime(String limitUseTime) {
		this.limitUseTime = limitUseTime;
	}

	
	
}
