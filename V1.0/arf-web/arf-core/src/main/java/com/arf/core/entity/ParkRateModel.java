package com.arf.core.entity;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 停车费表,只包含月租车
 * @author Administrator
 */
@Entity
@Table(name = "parkRate",
indexes={
			@Index(name="lic_pla_num_index",columnList="licensePlateNumber"),
			@Index(name="index_community_number",columnList="communityNumber")
		})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "parkRate_sequence")
public class ParkRateModel extends BaseEntity<Long>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6652690599282769139L;
	public static final String Key_Cache_Inout_Free_List_Prefix = "inout_free_list";
	
	public static final String PARKRATE_DAY_BEFORE_ENDTIME = "PARKRATE_DAY_BEFORE_ENDTIME";//月租车过期n天内，发送短信和推送
	public static final String PARKRATE_DAY_AFTER_ENDTIME = "PARKRATE_DAY_AFTER_ENDTIME";//月租车还有n天过期，发送短信和推送
	
	//状态  默认0 未审核     1审核通过     
	private Integer status = 0;
	/** 业主电话,必须用户名*/
	private String userName="";
	/** 证件号 */
	private String cardNumber;
	/** 业主姓名*/
	private String name;
	/** 车牌号*/ 
	private String licensePlateNumber;
	/** 物业id*/
	private Integer propretyId=0;
	/** 停车场类型0：默认不区分停车场 1:地上停车场2:地下停车场*/
	private Integer parkingType;
	/** 地上停车场价格*/
	private BigDecimal overGroundPrice;
	/** 地下停车场价格*/
	private BigDecimal underGroundPrice;
	/** 计费类型 1:按月计费 2:按季度计费 3:按年计费*/
	private Integer chargeType;
	/** 按月计费*/
	private BigDecimal pricePerMonth;
	/** 按季度计费*/
	private BigDecimal pricePerQuarter;
	/*** 按年计费*/
	private BigDecimal pricePerYear;
	/** 开始时间 */
	private Date createTime;
	/** 到期时间*/
	private Date endTime;
	/** 缴费金额*/
	private BigDecimal amount;
	/** 小区id*/
	private String communityNumber;
	/** 是否推送 */
	private Integer isPush=0;
	/** 推送时间*/
	private Date pushTime;
	/**
	 * 1:入
	 * 0：出
	 */
	private Integer inStatus;
	/**
	 * 1:自由
	 * 3：锁定
	 */
	private Integer lock_status;
	
	
	
	/**
	 * 用户当前状态
	 * 1：自由
	 * 3:锁定
	 */
	private Integer userLocks;
	/**是否自动锁定*/
	private Integer isAutoLock;
	/**
	 * 地址
	 */
	private String address;
	
	private Integer inTime;
	
	private Integer outTime;
	//子公司编号 
	private Integer branchId;
	
	@Column(name = "address",length=200)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	/**  
     * 获取是否自动锁定  
     * @return isAutoLock 是否自动锁定  
     */
	@Column(name = "is_auto_lock")
    public Integer getIsAutoLock() {
        return isAutoLock;
    }
    /**  
     * 设置是否自动锁定  
     * @param isAutoLock 是否自动锁定  
     */
    public void setIsAutoLock(Integer isAutoLock) {
        this.isAutoLock = isAutoLock;
    }

	@Column(name = "userLocks")
	public Integer getUserLocks() {
		return userLocks;
	}
	public void setUserLocks(Integer userLocks) {
		this.userLocks = userLocks;
	}
	@Column(name = "inStatus")
	public Integer getInStatus() {
		return inStatus;
	}
	public void setInStatus(Integer inStatus) {
		this.inStatus = inStatus;
	}
	@Column(name = "lock_status")
	public Integer getLock_status() {
		return lock_status;
	}
	public void setLock_status(Integer lock_status) {
		this.lock_status = lock_status;
	}
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "userName", length = 64)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name = "licensePlateNumber", length = 64)
	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}
	public void setLicensePlateNumber(String licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
	}
	@Column(name = "propretyId")
	public Integer getPropretyId() {
		return propretyId;
	}
	public void setPropretyId(Integer propretyId) {
		this.propretyId = propretyId;
	}
	@Column(name = "parkingType")
	public Integer getParkingType() {
		return parkingType;
	}
	public void setParkingType(Integer parkingType) {
		this.parkingType = parkingType;
	}
	
	@Column(name = "overGroundPrice")
	public BigDecimal getOverGroundPrice() {
		return overGroundPrice;
	}
	public void setOverGroundPrice(BigDecimal overGroundPrice) {
		this.overGroundPrice = overGroundPrice;
	}
	
	@Column(name = "underGroundPrice")
	public BigDecimal getUnderGroundPrice() {
		return underGroundPrice;
	}
	public void setUnderGroundPrice(BigDecimal underGroundPrice) {
		this.underGroundPrice = underGroundPrice;
	}
	
	@Column(name = "chargeType")
	public Integer getChargeType() {
		return chargeType;
	}
	public void setChargeType(Integer chargeType) {
		this.chargeType = chargeType;
	}
	
	@Column(name = "pricePerMonth")
	public BigDecimal getPricePerMonth() {
		return pricePerMonth;
	}
	public void setPricePerMonth(BigDecimal pricePerMonth) {
		this.pricePerMonth = pricePerMonth;
	}
	@Column(name = "pricePerQuarter")
	public BigDecimal getPricePerQuarter() {
		return pricePerQuarter;
	}
	public void setPricePerQuarter(BigDecimal pricePerQuarter) {
		this.pricePerQuarter = pricePerQuarter;
	}
	@Column(name = "pricePerYear")
	public BigDecimal getPricePerYear() {
		return pricePerYear;
	}
	public void setPricePerYear(BigDecimal pricePerYear) {
		this.pricePerYear = pricePerYear;
	}
	@Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "endTime")
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	@Column(name = "amount")
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	@Column(name = "communityNumber",length=32)
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	@Column(name = "cardNumber",length=32)
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	@Column(name = "name",length=32)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "isPush")
	public Integer getIsPush() {
		return isPush;
	}

	public void setIsPush(Integer isPush) {
		this.isPush = isPush;
	}
	@Column(name = "pushTime")
	public Date getPushTime() {
		return pushTime;
	}

	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}
	
	@Column(name = "in_time")
	public Integer getInTime() {
		return inTime;
	}
	public void setInTime(Integer inTime) {
		this.inTime = inTime;
	}
	@Column(name = "out_time")
	public Integer getOutTime() {
		return outTime;
	}
	public void setOutTime(Integer outTime) {
		this.outTime = outTime;
	}
	@Override
	public String toString() {
		return "ParkRateModel [status=" + status + ", userName=" + userName
				+ ", cardNumber=" + cardNumber + ", name=" + name
				+ ", licensePlateNumber=" + licensePlateNumber
				+ ", propretyId=" + propretyId + ", parkingType=" + parkingType
				+ ", overGroundPrice=" + overGroundPrice
				+ ", underGroundPrice=" + underGroundPrice + ", chargeType="
				+ chargeType + ", pricePerMonth=" + pricePerMonth
				+ ", pricePerQuarter=" + pricePerQuarter + ", pricePerYear="
				+ pricePerYear + ", createTime=" + createTime + ", endTime="
				+ endTime + ", amount=" + amount + ", communityNumber="
				+ communityNumber + ", isPush=" + isPush + ", pushTime="
				+ pushTime + ", inStatus=" + inStatus + ", lock_status="
				+ lock_status + ", userLocks=" + userLocks + ", isAutoLock="
				+ isAutoLock + ", address=" + address + ", inTime=" + inTime
				+ ", outTime=" + outTime + "]";
	}
	public Integer getBranchId() {
		return branchId;
	}
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	
}
