package com.arf.axd.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(
	name = "r_exchange_code_record",
	indexes={
			@Index(name="index_communityNumber",columnList="communityNumber"),
			@Index(name="index_propertyNumber",columnList="propertyNumber"),
			@Index(name="index_branchId",columnList="branchId")
	}
)
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "r_exchange_code_record_sequence")
public class ExchangeCodeRecord extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8764516515716906024L;
	
	private Long verificationId;//验证码记录ID（外键）
	
	private String redeemCode;//兑换码
	
	private Date redeemDate;//兑换时间
	
	private String userName;//兑换账号
	
	private Integer status;//状态(0未兑换1已兑换)
	
	private BigDecimal amount;//金额
	
	private Integer branchId;
	
	private Integer propertyNumber;
	
	private String communityNumber;
	
	public enum Status{
		NoExchange,Exchanged
	}

	public Long getVerificationId() {
		return verificationId;
	}

	public void setVerificationId(Long verificationId) {
		this.verificationId = verificationId;
	}

	@Column(length=40)
	public String getRedeemCode() {
		return redeemCode;
	}

	public void setRedeemCode(String redeemCode) {
		this.redeemCode = redeemCode;
	}

	public Date getRedeemDate() {
		return redeemDate;
	}

	public void setRedeemDate(Date redeemDate) {
		this.redeemDate = redeemDate;
	}

	@Column(length=40)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(precision=10,scale=2)
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public Integer getPropertyNumber() {
		return propertyNumber;
	}

	public void setPropertyNumber(Integer propertyNumber) {
		this.propertyNumber = propertyNumber;
	}
	@Column(length=40)
	public String getCommunityNumber() {
		return communityNumber;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}

	
	
}
