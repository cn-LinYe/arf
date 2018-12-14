package com.arf.axd.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "r_verification_code_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "r_verification_code_record_sequence")
public class VerificationCodeRecord extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1661813165495246166L;
	
	private Date operationDate;
	private String operationPeople;
	private Integer count;//数量
	private BigDecimal amount;//金额
	private BigDecimal total_amount;//总金额
	private Integer propertyNumber;
	private Integer branchId;//子公司id
	private String communityNumber;
	
	@Column(length=40)
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public Date getOperationDate() {
		return operationDate;
	}
	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}
	@Column(length=40)
	public String getOperationPeople() {
		return operationPeople;
	}
	public void setOperationPeople(String operationPeople) {
		this.operationPeople = operationPeople;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(BigDecimal total_amount) {
		this.total_amount = total_amount;
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
