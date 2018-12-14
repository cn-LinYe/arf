package com.arf.statistics.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name="statistics_axd_click")
@SequenceGenerator(name="sequenceGenerator",sequenceName="axd_click_statistics_sequence")
public class StatisticsAxdClick extends BaseEntity<Long>{

	private static final long serialVersionUID = -1659116585044170423L;

	private String userName	;//varchar(50)	是		普通	用户名
	private String communityNumber	;//varchar(50)	否		普通	小区编号
	private Integer propertyNumber	;//int	否		普通	物业编号
	private Integer clickTimes	;//int	是		普通	点击次数
	private String month	;//varchar(50)	是		普通	月份
	private String day	;//varchar(50)	是		普通	月份
	@Column(length=50)
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	@Column(length=50)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length=50)
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public Integer getPropertyNumber() {
		return propertyNumber;
	}
	public void setPropertyNumber(Integer propertyNumber) {
		this.propertyNumber = propertyNumber;
	}
	public Integer getClickTimes() {
		return clickTimes;
	}
	public void setClickTimes(Integer clickTimes) {
		this.clickTimes = clickTimes;
	}
	@Column(length=50)
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	
	
}
