package com.arf.activity.childrensday.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "activity_children61_voted_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "activity_children61_voted_record_sequence")
public class ActivityChildren61VotedRecord extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	private String userIdentify;//用户标识,安心点APP为用户名,微信端为openId
	private SourceType sourceType;//投票来源枚举,AXDAPP 安心点app;WECHAT 微信端
	private Integer votedCount;//投票数
	private Integer worksNum;//作品编号
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum SourceType{
		AXDAPP,
		WECHAT;
	}

	@Column(length = 40)
	public String getUserIdentify() {
		return userIdentify;
	}

	public SourceType getSourceType() {
		return sourceType;
	}

	public Integer getVotedCount() {
		return votedCount;
	}

	public Integer getWorksNum() {
		return worksNum;
	}

	public void setUserIdentify(String userIdentify) {
		this.userIdentify = userIdentify;
	}

	public void setSourceType(SourceType sourceType) {
		this.sourceType = sourceType;
	}

	public void setVotedCount(Integer votedCount) {
		this.votedCount = votedCount;
	}

	public void setWorksNum(Integer worksNum) {
		this.worksNum = worksNum;
	}

}
