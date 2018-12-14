package com.arf.search.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name="search_app_module_tags")
@SequenceGenerator(name="sequenceGenerator",sequenceName="search_app_module_tags_sequence")
public class AppModuleTags extends BaseEntity<Long>{
	private static final long serialVersionUID = 1L;
	
	private int type; //普通	见搜索接口说明extrData.appModule.type
	
	@Column(nullable=false)
	private String name; //普通	模块名称
	private String tags; //普通	标签set,多个英文逗号分隔
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
}
