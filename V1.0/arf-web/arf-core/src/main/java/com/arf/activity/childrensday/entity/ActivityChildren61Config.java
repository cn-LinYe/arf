package com.arf.activity.childrensday.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "activity_children61_config")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "activity_children61_config_sequence")
public class ActivityChildren61Config extends BaseEntity<Long> {
	
	private static final long serialVersionUID = 1L;
	
	private Date activityStartDate;//活动开始日期
	private Date activityEndDate;//活动结束日期
	private Integer wechatTimes;//微信投票一次对应的票数倍数
	private Integer axdTimes;//安心点app投票一次对应的票数倍数
	private Date prizeGrantEnd;//奖品发放截止日期
	
	@Column(nullable = false)
	public Date getPrizeGrantEnd() {
		return prizeGrantEnd;
	}
	@Column(nullable = false)
	public Date getActivityStartDate() {
		return activityStartDate;
	}
	@Column(nullable = false)
	public Date getActivityEndDate() {
		return activityEndDate;
	}
	public Integer getWechatTimes() {
		return wechatTimes;
	}
	public Integer getAxdTimes() {
		return axdTimes;
	}
	public void setPrizeGrantEnd(Date prizeGrantEnd) {
		this.prizeGrantEnd = prizeGrantEnd;
	}
	public void setActivityStartDate(Date activityStartDate) {
		this.activityStartDate = activityStartDate;
	}
	public void setActivityEndDate(Date activityEndDate) {
		this.activityEndDate = activityEndDate;
	}
	public void setWechatTimes(Integer wechatTimes) {
		this.wechatTimes = wechatTimes;
	}
	public void setAxdTimes(Integer axdTimes) {
		this.axdTimes = axdTimes;
	}
	
}
