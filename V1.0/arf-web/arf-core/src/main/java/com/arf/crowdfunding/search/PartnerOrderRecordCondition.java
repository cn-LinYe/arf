package com.arf.crowdfunding.search;

import com.arf.base.BaseSearchForm;

public class PartnerOrderRecordCondition extends BaseSearchForm{
	
	private String community_number;//小区编号
	private String project_id;//项目ID
	
	public String getCommunity_number() {
		return community_number;
	}
	public void setCommunity_number(String community_number) {
		this.community_number = community_number;
	}
	public String getProject_id() {
		return project_id;
	}
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}
}
