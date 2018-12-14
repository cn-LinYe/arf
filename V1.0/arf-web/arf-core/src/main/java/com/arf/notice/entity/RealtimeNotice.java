package com.arf.notice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "realtime_notice")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "realtime_notice_sequence")
public class RealtimeNotice extends BaseEntity<Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String adClickedUrl;//点击链接地址
	private String noticeContent	;//varchar(2000)	公告内容	Not null
	private Date noticeTime	;//datetime	公告时间	Not null
	private Byte noticeType	;//tinyint	公告跳转类型（0 跳URL  1跳原生 2 H5源码）	
	private Byte typeEnum	;//tinyint	公告类型枚举	
	private String cityCode	;//varchar(1000)	城市编码	
	private String cityName	;//varchar(4000)	城市名称	
	private Integer survivalTime	;//int	存活时间（秒）	Not null
	private String sourceCode	;//varchar(20000)	h5源码	
	private String dataArray	;//varchar(1000)	参数列表	
	private Byte noticeLevel	;//tinyint	公告级别(0 系统 1普通)	Not null
	private Byte noticeStatus	;//tinyint	公告状态(0 正常 1 公告中 2 已失效)	Not null
	
	@Column(length=100)
	public String getAdClickedUrl() {
		return adClickedUrl;
	}
	public void setAdClickedUrl(String adClickedUrl) {
		this.adClickedUrl = adClickedUrl;
	}
	@Column(length=2000,nullable=false)
	public String getNoticeContent() {
		return noticeContent;
	}
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}
	@Column(nullable=false)
	public Date getNoticeTime() {
		return noticeTime;
	}
	public void setNoticeTime(Date noticeTime) {
		this.noticeTime = noticeTime;
	}
	public Byte getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(Byte noticeType) {
		this.noticeType = noticeType;
	}
	public Byte getTypeEnum() {
		return typeEnum;
	}
	public void setTypeEnum(Byte typeEnum) {
		this.typeEnum = typeEnum;
	}
	@Column(length=1000)
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	@Column(length=4000)
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	@Column(nullable=false)
	public Integer getSurvivalTime() {
		return survivalTime;
	}
	public void setSurvivalTime(Integer survivalTime) {
		this.survivalTime = survivalTime;
	}
	@Lob 
	@Column(columnDefinition="TEXT")
	public String getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
	@Column(length=1000)
	public String getDataArray() {
		return dataArray;
	}
	public void setDataArray(String dataArray) {
		this.dataArray = dataArray;
	}
	@Column(nullable=false)
	public Byte getNoticeLevel() {
		return noticeLevel;
	}
	public void setNoticeLevel(Byte noticeLevel) {
		this.noticeLevel = noticeLevel;
	}
	@Column(nullable=false)
	public Byte getNoticeStatus() {
		return noticeStatus;
	}
	public void setNoticeStatus(Byte noticeStatus) {
		this.noticeStatus = noticeStatus;
	}

}
