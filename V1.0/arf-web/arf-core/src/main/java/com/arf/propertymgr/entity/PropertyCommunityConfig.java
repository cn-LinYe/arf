package com.arf.propertymgr.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "pty_property_community_config")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "pty_property_community_config_sequence")
public class PropertyCommunityConfig extends BaseEntity<Long>  {
	private static final long serialVersionUID = 1L;
	
	private Integer bindPropertyAudit;//绑定时需要物业审核开关,0:需要,1:不需要
	private Integer bindHouseholderAudit;//绑定时需要业主审核开关,0:需要,1:不需要
	private String communityNumber;//小区编号
	public Integer getBindPropertyAudit() {
		return bindPropertyAudit;
	}
	public void setBindPropertyAudit(Integer bindPropertyAudit) {
		this.bindPropertyAudit = bindPropertyAudit;
	}
	public Integer getBindHouseholderAudit() {
		return bindHouseholderAudit;
	}
	public void setBindHouseholderAudit(Integer bindHouseholderAudit) {
		this.bindHouseholderAudit = bindHouseholderAudit;
	}
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
}
