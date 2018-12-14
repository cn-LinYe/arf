package com.arf.gas.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "refuel_gas_oil_conf")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "refuel_gas_oil_conf_sequence")
public class RefuelGasOilConf  extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 775611816538974892L;
	
	private Byte oilType;//油类型（0、#0 1、#92 2、 #95）此类型缓存redis 不做数据库维护
	private String policyName;//政策名称
	private Integer oilAmount;//执行价格
	private Integer guideAmount;//指导价格
	private Integer businessNum;//商户编号
	private Integer gasNum;//油站编号
	private Date effectiveStart;//有效起始日期
	private Date effectiveEnd;//有效期结束日期
	private Status status;//状态信息(0、未生效 Not_Active 1、生效 Active 2、已冻结 Frozen 3、已过期Expired)
	private RetailWholesale retailWholesale;//零售批发（0、Retail 零售 1、Wholesale 批发）
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		Not_Active,//未生效
		Active,//生效
		Frozen,// 已冻结
		Expired;//已过期
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum RetailWholesale{
		Retail,// 零售
		Wholesale;//批发
	}

	public Byte getOilType() {
		return oilType;
	}

	public void setOilType(Byte oilType) {
		this.oilType = oilType;
	}

	@Column(length=200)
	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public Integer getOilAmount() {
		return oilAmount;
	}

	public void setOilAmount(Integer oilAmount) {
		this.oilAmount = oilAmount;
	}

	public Integer getGuideAmount() {
		return guideAmount;
	}

	public void setGuideAmount(Integer guideAmount) {
		this.guideAmount = guideAmount;
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

	public RetailWholesale getRetailWholesale() {
		return retailWholesale;
	}

	public void setRetailWholesale(RetailWholesale retailWholesale) {
		this.retailWholesale = retailWholesale;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
