package com.arf.anxinshop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "es_user_tags")
@SequenceGenerator(name = "sequenceGenerator",sequenceName = "es_user_tags_sequence")
public class EsUserTags extends BaseEntity<Long> {

	private static final long serialVersionUID = -5807463311280383792L;
	
	private String userName;//用户名
	private String groupIds;//分组id(多个英文逗号分隔)
	@Column(length = 20)
	public String getUserName() {
		return userName;
	}
	@Column(length = 200)
	public String getGroupIds() {
		return groupIds;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}
}
