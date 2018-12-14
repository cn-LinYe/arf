package com.arf.eparking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "s_orderfee_rule_item")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "s_orderfee_rule_item_sequence")
public class OrderfeeRuleItem extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	public enum Items{
		/**  每次预定收取的费用（分） */
		OrderFeeOnce,
		/** 最大预定范围（分钟） */
		OrderMaxWaitTime,
		/** 续约最大通知时间 */
		OrderNotifyMax,
		/** 续约最小通知时间  */
		OrderNotifyMin,
		/** 爽约扣费范围（分钟） */
		BreakContractTime,
		/** 爽约扣费（分） */
		BreakContractFee,
		/** 余额最小预约金额（分） */
		OrderAccountLower,
		;
	}
	
	private String parkingId;//停车场ID/小区ID
	private String parkingName;//停车场名
	private Integer ruleId;//限制ID
	private String iNo;//配置项名称
	private String iName;//名称
	private String iValue;//值
	private Integer iOrder;//排序值
	private String iDesc;//描述
	private Integer iUse;//是否启用：1，启用；0，不启用
	//物业编号 
	private Integer propertyNumber;
	//子公司编号 
	private Integer branchId;
	
	public enum Use{
		unusable,usable;
	}
	
	@Column(name="parking_id",length=50)
	public String getParkingId() {
		return parkingId;
	}
	@Column(name="parking_name",length=100)
	public String getParkingName() {
		return parkingName;
	}
	@Column(name="rule_id",length=11)
	public Integer getRuleId() {
		return ruleId;
	}
	@Column(name="i_no",length=20)
	public String getiNo() {
		return iNo;
	}
	@Column(name="i_name",length=20)
	public String getiName() {
		return iName;
	}
	@Column(name="i_value",length=20)
	public String getiValue() {
		return iValue;
	}
	@Column(name="i_order",length=11)
	public Integer getiOrder() {
		return iOrder;
	}
	@Column(name="i_desc",length=20)
	public String getiDesc() {
		return iDesc;
	}
	@Column(name="i_use",length=11)
	public Integer getiUse() {
		return iUse;
	}
	public void setParkingId(String parkingId) {
		this.parkingId = parkingId;
	}
	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}
	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}
	public void setiNo(String iNo) {
		this.iNo = iNo;
	}
	public void setiName(String iName) {
		this.iName = iName;
	}
	public void setiValue(String iValue) {
		this.iValue = iValue;
	}
	public void setiOrder(Integer iOrder) {
		this.iOrder = iOrder;
	}
	public void setiDesc(String iDesc) {
		this.iDesc = iDesc;
	}
	public void setiUse(Integer iUse) {
		this.iUse = iUse;
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
