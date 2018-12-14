package com.arf.finance.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="finance_info")
@SequenceGenerator(name="sequenceGenerator",sequenceName="finance_info_sequence")
public class FinanceInfo extends BaseEntity<Long> {
	
	private static final long serialVersionUID = 1L;
	private Scope scope;//作用域 0 全部小区 1 部分小区
	private String communityNumbers;//小区
	private String photo;//项目图片
	private String name;//项目名称
	private String tags;//项目标签
	private String fee;//项目金额
	private String summary;//项目简介
	private String content;//内容
	private Status status;//状态 0上线 1下线 2删除
	
	private Integer viewTimes;//累计浏览次数
	private Date onlineTime;//上线时间
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Scope{
		ALL,PART
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		ONLINE,OFFLINE,DELETE
	}

	public Scope getScope() {
		return scope;
	}

	@Column(length=6600)
	public String getCommunityNumbers() {
		return communityNumbers;
	}
	@Column(length=200)
	public String getPhoto() {
		return photo;
	}
	@Column(length=20)
	public String getName() {
		return name;
	}
	@Column(length=20)
	public String getTags() {
		return tags;
	}
	@Column(length=20)
	public String getFee() {
		return fee;
	}
	@Column(length=50)
	public String getSummary() {
		return summary;
	}
	@Type(type = "text")
	@Column(length=65535,nullable=false)
	public String getContent() {
		return content;
	}

	public Status getStatus() {
		return status;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}

	public void setCommunityNumbers(String communityNumbers) {
		this.communityNumbers = communityNumbers;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Integer getViewTimes() {
		return viewTimes;
	}

	public void setViewTimes(Integer viewTimes) {
		this.viewTimes = viewTimes;
	}

	public Date getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(Date onlineTime) {
		this.onlineTime = onlineTime;
	}
	
	
	
}
