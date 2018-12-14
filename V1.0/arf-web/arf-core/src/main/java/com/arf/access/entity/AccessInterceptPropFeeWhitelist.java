package com.arf.access.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "access_intercept_prop_fee_whitelist",uniqueConstraints={
		@UniqueConstraint(name="communityNumber_userName",columnNames={"communityNumber","userName"})})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "access_intercept_prop_fee_whitelist_sequence")
public class AccessInterceptPropFeeWhitelist extends BaseEntity<Long> {

	private static final long serialVersionUID = -2603886746787511175L;

	private String communityNumber;//
	private String userName;//
	
	@Column(length=32,nullable=false)
	public String getCommunityNumber() {
		return communityNumber;
	}
	@Column(length=32,nullable=false)
	public String getUserName() {
		return userName;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
