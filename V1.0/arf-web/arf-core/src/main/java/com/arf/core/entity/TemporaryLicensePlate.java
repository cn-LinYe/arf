package com.arf.core.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 临时停车表
 * @author dg
 */

@Entity
@Table(name = "temporary_license_plate")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "temporary_license_plate_sequence")
public class TemporaryLicensePlate extends BaseEntity<Long> {

	private static final long serialVersionUID = -2868858388454074166L;
	/** 停车开始时间*/
	private Long startTime;
	/** app支付结束时间 */
	private Long endTime;
	/**开闸失败原因**/
	private String failReason;
	/** 车牌号 */
	private String licensePlateNumber;
	/** 小区id */
	private String communityNumber;
	/** 单价**/
	private BigDecimal unityPrice;
	/**支付金额**/
	private BigDecimal payAmount;
	/**计算出金额**/
	private BigDecimal calculationAmount;
	/**支付总钱数**/
	private BigDecimal payTotal;
	/**停车时长**/
	private BigDecimal parkTime;

	
	/**
	 * 进出闸状态
	 * 1:代表入闸
	 * 2：代表缴费
	 * 3：代表出闸
	 */
	private int state;
	/**控制参数**/
	/**
	 * 0:默认值
	 * 1:命令字
	 */
	private int commandParameter;
	
	@Column(name = "startTime", nullable = true)
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	@Column(name = "parkTime", nullable = true)
	public BigDecimal getParkTime() {
		return parkTime;
	}
	public void setParkTime(BigDecimal parkTime) {
		this.parkTime = parkTime;
	}
	@Column(name = "endTime", nullable = true)
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	@Column(name = "licensePlateNumber", nullable = true)
	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}
	public void setLicensePlateNumber(String licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
	}
	@Column(name = "communityNumber", nullable = true)
	public String getCommunityNumber() {
		return communityNumber;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}

	@Column(name = "unityPrice", nullable = true)
	public BigDecimal getUnityPrice() {
		return unityPrice;
	}

	public void setUnityPrice(BigDecimal unityPrice) {
		this.unityPrice = unityPrice;
	}

	@Column(name = "payAmount", nullable = true)
	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	@Column(name = "calculationAmount", nullable = true)
	public BigDecimal getCalculationAmount() {
		return calculationAmount;
	}

	public void setCalculationAmount(BigDecimal calculationAmount) {
		this.calculationAmount = calculationAmount;
	}
	@Column(name = "state", nullable = true)
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	@Column(name = "failReason")
	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	@Column(name = "commandParameter")
	public int getCommandParameter() {
		return commandParameter;
	}
	public void setCommandParameter(int commandParameter) {
		this.commandParameter = commandParameter;
	}
	
	@Column(name = "payTotal")
	public BigDecimal getPayTotal() {
		return payTotal;
	}
	public void setPayTotal(BigDecimal payTotal) {
		this.payTotal = payTotal;
	}
	
	
}










