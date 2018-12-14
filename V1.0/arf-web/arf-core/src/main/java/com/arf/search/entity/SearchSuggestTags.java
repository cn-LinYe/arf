package com.arf.search.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name="search_suggest_tags")
@SequenceGenerator(name="sequenceGenerator",sequenceName="search_suggest_tags_sequence")
public class SearchSuggestTags extends BaseEntity<Long>{
	private static final long serialVersionUID = 1L;
	
	private String suggest; //关键字建议
	private String tags; //普通	标签
	private String communityNumber;//小区编号,nullable
	
	public String getSuggest() {
		return suggest;
	}
	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	
}
