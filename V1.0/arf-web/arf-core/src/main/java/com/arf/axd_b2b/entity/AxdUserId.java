package com.arf.axd_b2b.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;


@Entity
@Table(name = "b2b_axd_user_id")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "b2b_axd_user_id_sequence")
public class AxdUserId extends BaseEntity<Long> {
	private static final long serialVersionUID = 1L;
	private String axdUserName;//安心点平台的用户民
	private String axdUserId;//axdUserID
	private String businessNum;//商户编号
	
	public String getAxdUserName() {
		return axdUserName;
	}
	public String getAxdUserId() {
		return axdUserId;
	}
	public String getBusinessNum() {
		return businessNum;
	}
	public void setAxdUserName(String axdUserName) {
		this.axdUserName = axdUserName;
	}
	public void setAxdUserId(String axdUserId) {
		this.axdUserId = axdUserId;
	}
	public void setBusinessNum(String businessNum) {
		this.businessNum = businessNum;
	}
}
