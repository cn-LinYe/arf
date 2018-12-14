package com.arf.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "wx_access_token")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "wx_access_token_sequence")
public class WxAccessToken extends BaseEntity<Long>{
	private static final long serialVersionUID = 1L;

	private String accessToken;
	private Integer expireSeconds;
	private String classifier;
	private Long createTime;
	
	public WxAccessToken(){
		
	}
	
	public WxAccessToken(String accessToken, Integer expireSeconds, String classifier, Long createTime) {
		super();
		this.accessToken = accessToken;
		this.expireSeconds = expireSeconds;
		this.classifier = classifier;
		this.createTime = createTime;
	}

	@NotEmpty
	@Column(name="accessToken",nullable = false)
	public String getAccessToken() {
		return accessToken;
	}
	
	@Column(name = "expireSeconds",nullable = false)
	public Integer getExpireSeconds() {
		return expireSeconds;
	}
	
	@Column(name = "classifier")
	public String getClassifier() {
		return classifier;
	}
	@Column(name = "createTime")
	public Long getCreateTime() {
		return createTime;
	}
	
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public void setExpireSeconds(Integer expireSeconds) {
		this.expireSeconds = expireSeconds;
	}
	public void setClassifier(String classifier) {
		this.classifier = classifier;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	
}
