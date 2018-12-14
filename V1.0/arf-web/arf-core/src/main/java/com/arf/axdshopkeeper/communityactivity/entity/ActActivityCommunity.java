package com.arf.axdshopkeeper.communityactivity.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name="act_activity_community")
@SequenceGenerator(name="sequenceGenerator",sequenceName="act_activity_community_sequence")
public class ActActivityCommunity extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	private Long activityId;//活动id
	private String communityNumber;//小区编号
	
	public Long getActivityId() {
		return activityId;
	}
	@Column(length = 32)
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	
}
