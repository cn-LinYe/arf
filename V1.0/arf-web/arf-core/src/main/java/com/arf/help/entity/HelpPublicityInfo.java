package com.arf.help.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="help_publicity_info")
@SequenceGenerator(name="sequenceGenerator",sequenceName="help_publicity_info_sequence")
public class HelpPublicityInfo extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	private String title;//项目简介
	private String content;//内容
	private Status status;//状态 0上线 1下线 2删除
	
	private Date onlineTime;//上线时间
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		ONLINE,OFFLINE,DELETE
	}

	@Column(length=20)
	public String getTitle() {
		return title;
	}

	@Type(type = "text")
	@Column(length=65535,nullable=false)
	public String getContent() {
		return content;
	}

	public Status getStatus() {
		return status;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(Date onlineTime) {
		this.onlineTime = onlineTime;
	}
	
}
