package com.arf.gas.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "refuel_gas_recharge_conf")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "refuel_gas_recharge_conf_sequence")
public class RefuelGasRechargeConf extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4766912140024942623L;
	
	private Integer rechargeAmount;//充值金额（单位：分）
	private Integer giftAmount;//充值金额（单位：分）
	private Integer businessNum;//	商户编号
	private Integer gasNum	;//加油站编号
	private Integer rechargeTotalAmount;//充值累计金额（单位：分）
	private Date effectiveStart;//有效起始日期
	private Date effectiveEnd;//有效结束日期
	private Status status;//状态信息(0、未生效 Not_Active 1、生效 Active 2、已冻结 Frozen)
	private String policyCopy;//政策文案
	private String policyName;//正常名称
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		Not_Active,//未生效
		Active,//生效
		Frozen;// 已冻结
	}

	public Integer getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(Integer rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	public Integer getGiftAmount() {
		return giftAmount;
	}

	public void setGiftAmount(Integer giftAmount) {
		this.giftAmount = giftAmount;
	}

	public Integer getBusinessNum() {
		return businessNum;
	}

	public void setBusinessNum(Integer businessNum) {
		this.businessNum = businessNum;
	}

	public Integer getGasNum() {
		return gasNum;
	}

	public void setGasNum(Integer gasNum) {
		this.gasNum = gasNum;
	}

	public Integer getRechargeTotalAmount() {
		return rechargeTotalAmount;
	}

	public void setRechargeTotalAmount(Integer rechargeTotalAmount) {
		this.rechargeTotalAmount = rechargeTotalAmount;
	}

	public Date getEffectiveStart() {
		return effectiveStart;
	}

	public void setEffectiveStart(Date effectiveStart) {
		this.effectiveStart = effectiveStart;
	}

	public Date getEffectiveEnd() {
		return effectiveEnd;
	}

	public void setEffectiveEnd(Date effectiveEnd) {
		this.effectiveEnd = effectiveEnd;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Column(length=200)
	public String getPolicyCopy() {
		return policyCopy;
	}

	public void setPolicyCopy(String policyCopy) {
		this.policyCopy = policyCopy;
	}

	@Column(length=100)
	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

}
