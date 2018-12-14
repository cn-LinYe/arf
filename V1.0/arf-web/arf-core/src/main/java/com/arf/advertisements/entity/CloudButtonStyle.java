package com.arf.advertisements.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="button_cloud_button_style")
@SequenceGenerator(name="sequenceGenerator",sequenceName="button_cloud_button_style_sequence")
public class CloudButtonStyle extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2276454305848606163L;
	
	private String buttonName;//活动名称
	private ButtonType buttonType;//按钮类型0 homeCloud首页云按钮
	private ActiveType activeType;//按钮类型0 nomal常规 1festival节气 2hotspot热点 3operate运营 4welfare福利 5feastday 节日
	private String buttonImgUrl;//按钮图片路径
	private String buttonVoiceUrl;//按钮语音路径
	private String voiceStartTime;//按钮语音每天开始时间（存小时）
	private String voiceEndTime;//按钮语音每天截止时间（存小时）
	private ButtonStatus buttonStatus;//按钮状态态0 normal 正常1unShelves 下架
	private Date buttonStartTime;//有效开始时间
	private Date buttonEndTime;//有效截止时间
	private String excludeCities;//可见城市（城市编号，以“,”分割）
	private String startTime;//每天开始时间（存小时）
	private String endTime;//每天截止时间（存小时）
	private String remark;//备注
	private String communities;//广告可见小区
	private String excludeCommunities;//广告不可见小区
	private String voiceList;//不同时段语音数组（json数组格式存储例：[{"voiceStartTime":"08:00:00","voiceEndTime":"12:00:00","voiceUrl":"http://www.baidu.com"}]）
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum ButtonType{
		homeCloud;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum ActiveType{
		nouse,festival,hotspot,operate,welfare,feastday,nomal;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum ButtonStatus{
		normal,unShelves;
	}
	@Column(length=32)
	public String getButtonName() {
		return buttonName;
	}
	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}
	public ButtonType getButtonType() {
		return buttonType;
	}
	public void setButtonType(ButtonType buttonType) {
		this.buttonType = buttonType;
	}
	public ActiveType getActiveType() {
		return activeType;
	}
	public void setActiveType(ActiveType activeType) {
		this.activeType = activeType;
	}
	@Column(length=100)
	public String getButtonImgUrl() {
		return buttonImgUrl;
	}
	public void setButtonImgUrl(String buttonImgUrl) {
		this.buttonImgUrl = buttonImgUrl;
	}
	@Column(length=100)
	public String getButtonVoiceUrl() {
		return buttonVoiceUrl;
	}
	public void setButtonVoiceUrl(String buttonVoiceUrl) {
		this.buttonVoiceUrl = buttonVoiceUrl;
	}
	@Column(length=10)
	public String getVoiceStartTime() {
		return voiceStartTime;
	}
	public void setVoiceStartTime(String voiceStartTime) {
		this.voiceStartTime = voiceStartTime;
	}
	@Column(length=10)
	public String getVoiceEndTime() {
		return voiceEndTime;
	}
	public void setVoiceEndTime(String voiceEndTime) {
		this.voiceEndTime = voiceEndTime;
	}
	public ButtonStatus getButtonStatus() {
		return buttonStatus;
	}
	public void setButtonStatus(ButtonStatus buttonStatus) {
		this.buttonStatus = buttonStatus;
	}
	public Date getButtonStartTime() {
		return buttonStartTime;
	}
	public void setButtonStartTime(Date buttonStartTime) {
		this.buttonStartTime = buttonStartTime;
	}
	public Date getButtonEndTime() {
		return buttonEndTime;
	}
	public void setButtonEndTime(Date buttonEndTime) {
		this.buttonEndTime = buttonEndTime;
	}
	@Column(length=2500)
	public String getExcludeCities() {
		return excludeCities;
	}
	public void setExcludeCities(String excludeCities) {
		this.excludeCities = excludeCities;
	}
	@Column(length=10)
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	@Column(length=10)
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	@Column(length=150)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Lob
	public String getCommunities() {
		return communities;
	}
	public void setCommunities(String communities) {
		this.communities = communities;
	}
	@Lob
	public String getExcludeCommunities() {
		return excludeCommunities;
	}
	public void setExcludeCommunities(String excludeCommunities) {
		this.excludeCommunities = excludeCommunities;
	}
	@Column(length=500)
	public String getVoiceList() {
		return voiceList;
	}
	public void setVoiceList(String voiceList) {
		this.voiceList = voiceList;
	}

}
