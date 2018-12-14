package com.arf.insurance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "p_insurance_type")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "p_insurance_type_sequence")
public class InsuranceType extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;

	private Integer insuranceCompanyId;// int comment '保险公司ID(外键)',
	private String insuranceType; // varchar(100) comment '保险险种',
	private String insuranceTypeDesc; // varchar(500) comment '险种描述',
	private String communityNumber; // varchar(32) comment '小区编号',
	private Integer branchId; // int comment '子公司名称',
	private Integer popertyNumber; // int comment '物业信息',
	private Integer businessNum; // int comment '商户编号',
	private Byte status; // tinyint comment '状态：0启用，1禁用',

	public enum Status{
		Enable,Disable
	}
	
	
	public Integer getInsuranceCompanyId() {
		return insuranceCompanyId;
	}

	public void setInsuranceCompanyId(Integer insuranceCompanyId) {
		this.insuranceCompanyId = insuranceCompanyId;
	}

	@Column(length = 100)
	public String getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(String insuranceType) {
		this.insuranceType = insuranceType;
	}
	@Column(length = 500)
	public String getInsuranceTypeDesc() {
		return insuranceTypeDesc;
	}

	public void setInsuranceTypeDesc(String insuranceTypeDesc) {
		this.insuranceTypeDesc = insuranceTypeDesc;
	}

	@Column(length = 32)
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

	public Integer getPopertyNumber() {
		return popertyNumber;
	}

	public void setPopertyNumber(Integer popertyNumber) {
		this.popertyNumber = popertyNumber;
	}

	public Integer getBusinessNum() {
		return businessNum;
	}

	public void setBusinessNum(Integer businessNum) {
		this.businessNum = businessNum;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

}
