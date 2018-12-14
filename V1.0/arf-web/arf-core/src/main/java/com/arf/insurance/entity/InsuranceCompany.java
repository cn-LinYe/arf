package com.arf.insurance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "p_insurance_company")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "p_insurance_company_sequence")
public class InsuranceCompany extends BaseEntity<Long> {
	private static final long serialVersionUID = 1L;

	private String insuranceCompany; // varchar(100) comment '保险公司名称',
	private String companyIntroduction; // varchar(1000) comment '公司介绍',
	private String companyReferred;// varchar(10) comment '公司简称（一个汉字）',
	private String companyColor;//varcahr(10) comment '颜色',
	private String communityNumber; // String comment '小区编号',
	private Integer branchId; // int comment '子公司名称',
	private Integer popertyNumber; // int comment '物业信息',
	private Integer businessNum; // int comment '商户编号',
	private Byte status; // tinyint comment '状态：0启用，1禁用',
	
	public enum Status{
		Enable,Disable
	}

	@Column(length = 100)
	public String getInsuranceCompany() {
		return insuranceCompany;
	}

	public void setInsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}
	@Column(length = 1000)
	public String getCompanyIntroduction() {
		return companyIntroduction;
	}

	public void setCompanyIntroduction(String companyIntroduction) {
		this.companyIntroduction = companyIntroduction;
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
	
	@Column(length = 10)
	public String getCompanyReferred() {
		return companyReferred;
	}

	public void setCompanyReferred(String companyReferred) {
		this.companyReferred = companyReferred;
	}
	@Column(length = 10)
	public String getCompanyColor() {
		return companyColor;
	}

	public void setCompanyColor(String companyColor) {
		this.companyColor = companyColor;
	}
	
}
