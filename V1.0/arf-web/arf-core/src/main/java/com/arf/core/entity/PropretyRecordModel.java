package com.arf.core.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 物业订单表
 * @author Administrator
 */

@Entity
@Table(name = "propretyrecord")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "proprety_record_sequence")
public class PropretyRecordModel extends BaseEntity<Long>{
	
    private static final long serialVersionUID = -7646619615244868440L;
    /*** 订单号*/
    private String out_trade_no;
    /*** 支付用途 */
    private String subject;
    /** 用户名*/
	private String userName;
	/**支付金额 */
	private double amount; 
	/*** 姓名*/
	private String name;
	/** 支付时间 */
    @Column(name="currentTime",nullable = false)
    private Date currentTimes;
	 /**开始日期 */
	private Date startTime;
	/** 结束日期 */
	private Date endTime;
	/** 计费类型 1 按面积计费2:按照 月份计费3:按照年计费 */
	private int chargeType;
	/** 纪录类型 1默认为物业缴费类型 */
	private int recordType=1;
	/** 房间id */
	private String houseId="0";
	/** 订单状态 */
	private String tradeStatus;
	
	/** 小区id */
	private String communityNumber;
	//子公司编号 
	private Integer branchId;
	
//	/**物业后台外键 */
	private Integer proprety_id;
	
	@Column(name = "userName", length = 32)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name = "amount")
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	@Column(name = "name", length = 32)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	/**  
     * 获取支付时间  
     * @return currentTimes 支付时间  
     */
    public Date getCurrentTimes() {
        return currentTimes;
    }
    /**  
     * 设置支付时间  
     * @param currentTimes 支付时间  
     */
    public void setCurrentTimes(Date currentTimes) {
        this.currentTimes = currentTimes;
    }
    @Column(name = "startTime")
	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	@Column(name = "endTime")
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	@Column(name = "chargeType")
	public int getChargeType() {
		return chargeType;
	}
	public void setChargeType(int chargeType) {
		this.chargeType = chargeType;
	}
	@Column(name = "recordType")
	public int getRecordType() {
		return recordType;
	}
	public void setRecordType(int recordType) {
		this.recordType = recordType;
	}
	@Column(name = "out_trade_no",length=32)
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no){
		this.out_trade_no = out_trade_no;
	}
	@Column(name = "subject",length=32)
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	@Column(name = "houseId",length=32)
	public String getHouseId() {
		return houseId;
	}
	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}
	
	@Column(name = "tradeStatus",length=32)
	public String getTradeStatus() {
		return tradeStatus;
	}
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	@Column(name = "communityNumber",length=32)
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public Integer getBranchId() {
		return branchId;
	}
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	public Integer getProprety_id() {
		return proprety_id;
	}
	public void setProprety_id(Integer proprety_id) {
		this.proprety_id = proprety_id;
	}
}
