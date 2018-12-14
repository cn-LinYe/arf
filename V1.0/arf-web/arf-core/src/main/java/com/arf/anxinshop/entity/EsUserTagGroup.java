package com.arf.anxinshop.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "es_user_tag_group")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "es_user_tag_group_sequence")
public class EsUserTagGroup extends BaseEntity<Long> {

	private static final long serialVersionUID = -3517491659206108154L;
	
	private String name;//分组名
	private Integer threshold;//阈值
	private Integer baseScore;//基数
	private Date lastSyncDate;//上一次同步时间
	private Integer tagHitCount;//标签命中用户数
	
	@Column(length=20)
	public String getName() {
		return name;
	}
	public Integer getThreshold() {
		return threshold;
	}
	public Integer getBaseScore() {
		return baseScore;
	}
	public Date getLastSyncDate() {
		return lastSyncDate;
	}
	public Integer getTagHitCount() {
		return tagHitCount;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}
	public void setBaseScore(Integer baseScore) {
		this.baseScore = baseScore;
	}
	public void setLastSyncDate(Date lastSyncDate) {
		this.lastSyncDate = lastSyncDate;
	}
	public void setTagHitCount(Integer tagHitCount) {
		this.tagHitCount = tagHitCount;
	}
}
