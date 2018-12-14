package com.arf.axdshopkeeper.shopkeeperrecommend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

//店长推荐评论表
@Entity
@Table(name="cof_comment")
@SequenceGenerator(name="sequenceGenerator",sequenceName="cof_comment_sequence")
public class CofComment extends BaseEntity<Long> {
	private static final long serialVersionUID = 86038046985491450L;
	private String userName;//用户名
	private Long recommendId;//推荐id
	private Long parentId;//父评论id
	private String content;//评论内容
	private Status status;//状态
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		NORMAL,//正常
		DELETE//删除
	}
	@Column(length=20,nullable=false)
	public String getUserName() {
		return userName;
	}
	@Column(nullable=false)
	public Long getRecommendId() {
		return recommendId;
	}
	public Long getParentId() {
		return parentId;
	}
	@Column(length=255)
	public String getContent() {
		return content;
	}
	@Column(nullable=false)
	public Status getStatus() {
		return status;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setRecommendId(Long recommendId) {
		this.recommendId = recommendId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
}
