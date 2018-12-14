package com.arf.axdshopkeeper.communityactivity.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="act_activity_sign_up")
@SequenceGenerator(name="sequenceGenerator",sequenceName="act_activity_sign_up_sequence")
public class ActActivitySignUp extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	private Long activityId;//活动id
	private String mobile;//用户手机号
	private String name;//用户姓名
	private Status status;//状态:0报名，1取消
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		SIGN_UP,CANCELED
	}

	public Long getActivityId() {
		return activityId;
	}
	@Column(length = 20)
	public String getMobile() {
		return mobile;
	}
	@Column(length = 20)
	public String getName() {
		return name;
	}

	public Status getStatus() {
		return status;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
}
