package com.arf.carbright.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(
		name = "p_business_to_community",
		uniqueConstraints={
				@UniqueConstraint(columnNames={"businessNum","communityNum"},name="unique_businessNum_communityNum"),
				@UniqueConstraint(columnNames={"businessId","communityId"},name="unique_businessId_communityId")
		}
)
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "business_to_community_sequence")
public class BusinessToCommunity extends BaseEntity<Long> {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer businessId;
	
	private String businessNum;
	
	private Integer communityId;
	
	private String communityNum;
	//物业编号 
	private Integer propertyNumber;
	//子公司编号 
	private Integer branchId;
	
	public Integer getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}
	@Column(length = 20)
	public String getBusinessNum() {
		return businessNum;
	}

	public void setBusinessNum(String businessNum) {
		this.businessNum = businessNum;
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}
	@Column(length = 20)
	public String getCommunityNum() {
		return communityNum;
	}

	public void setCommunityNum(String communityNum) {
		this.communityNum = communityNum;
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
