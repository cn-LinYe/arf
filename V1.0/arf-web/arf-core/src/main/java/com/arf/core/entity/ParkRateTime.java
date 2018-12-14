package com.arf.core.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 月租车缴费更新车闸时间
 * @author Administrator
 */

@Entity
@Table(name = "parkrate_time")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "parkrate_time_sequence")
public class ParkRateTime extends BaseEntity<Long>{
	
	private static final long serialVersionUID = 239278293559574057L;
	/** 小区id*/
	private String communityNumber;
	/** 开始时间 */
	private long startTime;
	/** 结束时间 */
	private long endTime;
	/** 车牌*/
	private String LicensePlateNumber;
	/**停车费类型*/
	private int parkRateType;
	
	/**区分临时车月租车参数*/
	/**
	 * 临时车：100A
	 * 月租车：1005
	 */
	private int command;
	/**订单支付金额*/
	private BigDecimal amount;
	/**支付类型：临时车  0  月租车  1*/
	private Integer type;
	/**订单id*/
	private Long orderid;
	
	//物业编号 
	private Integer propertyNumber;
	//子公司编号 
	private Integer branchId;
	
	@Column(name = "communityNumber",length=32,nullable=false)
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	@Column(name = "startTime",length=32,nullable=false)
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	@Column(name = "endTime",length=32,nullable=false)
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	@Column(name = "LicensePlateNumber",length=32,nullable=false)
	public String getLicensePlateNumber() {
		return LicensePlateNumber;
	}
	public void setLicensePlateNumber(String licensePlateNumber){
		LicensePlateNumber = licensePlateNumber;
	}
	@Column(name = "parkRateType",nullable=false)
	public int getParkRateType() {
		return parkRateType;
	}
	public void setParkRateType(int parkRateType) {
		this.parkRateType = parkRateType;
	}
	
	@Column(name = "command",nullable=false)
	public int getCommand() {
		return command;
	}
	public void setCommand(int command) {
		this.command = command;
	}
    /**  
     * 获取订单支付金额  
     * @return amount 订单支付金额  
     */
	@Column(name = "amount",nullable=false)
    public BigDecimal getAmount() {
        return amount;
    }
    /**  
     * 设置订单支付金额  
     * @param amount 订单支付金额  
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    /**  
     * 获取支付类型：临时车0月租车1  
     * @return type 支付类型：临时车0月租车1  
     */
    @Column(name = "type",nullable=false)
    public Integer getType() {
        return type;
    }
    /**  
     * 设置支付类型：临时车0月租车1  
     * @param type 支付类型：临时车0月租车1  
     */
    public void setType(Integer type) {
        this.type = type;
    }
    /**  
     * 获取订单id  
     * @return orderid 订单id  
     */
    @Column(name = "order_id",nullable=false)
    public Long getOrderid() {
        return orderid;
    }
    /**  
     * 设置订单id  
     * @param orderid 订单id  
     */
    public void setOrderid(Long orderid) {
        this.orderid = orderid;
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
}
