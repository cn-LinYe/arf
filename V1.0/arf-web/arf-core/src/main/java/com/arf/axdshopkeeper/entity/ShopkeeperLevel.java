package com.arf.axdshopkeeper.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "bi_shopkeeper_level")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "bi_shopkeeper_level_sequence")
public class ShopkeeperLevel extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	private String levelName;//星级名称
	private Integer levelIndex;//星级
	private Integer lowerLimit;//星级下线
	private Integer upperLimit;//星级上线
	private BigDecimal pushMoneyPer;//提成百分比
	
	@Column(length=10,nullable=false)
	public String getLevelName() {
		return levelName;
	}
	@Column(length=11,nullable=false)
	public Integer getLevelIndex() {
		return levelIndex;
	}
	@Column(length=11,nullable=false)
	public Integer getLowerLimit() {
		return lowerLimit;
	}
	@Column(length=11,nullable=false)
	public Integer getUpperLimit() {
		return upperLimit;
	}
	@Column(precision = 4, scale = 4,nullable = false)
	public BigDecimal getPushMoneyPer() {
		return pushMoneyPer;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public void setLevelIndex(Integer levelIndex) {
		this.levelIndex = levelIndex;
	}
	public void setLowerLimit(Integer lowerLimit) {
		this.lowerLimit = lowerLimit;
	}
	public void setUpperLimit(Integer upperLimit) {
		this.upperLimit = upperLimit;
	}
	public void setPushMoneyPer(BigDecimal pushMoneyPer) {
		this.pushMoneyPer = pushMoneyPer;
	}

}
