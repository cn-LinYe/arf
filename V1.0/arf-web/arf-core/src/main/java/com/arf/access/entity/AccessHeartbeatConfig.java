package com.arf.access.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;

@Entity
@Table(name = "access_heartbeat_config")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "access_heartbeat_config_sequence")
public class AccessHeartbeatConfig extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2218343799285996786L;
	
	private String accessNum;//门禁编号,8位数字编号
	private Type type;//配置类型（ 0 advertising 广告、 1 system 设备音量 2 voice 设备语音 3 upgrade是否升级）
	private Status status;//状态 0 normal 正常、1 失效invalid
	private String fileUrl;//文件路径
	private VoiceStatus voiceStatus;//语音状态，0 Yes代表有更新 1No代表没有更新
	private AdType adType;//广告类型：1 picture图片 2 video视频 3 html5 H5页面
	private String adPutTimeStart;//广告投放开始时间
	private String adPutTimeEnd;//广告投放结束时间
	private Integer systemVolume;//音量0-100
	private Date startEffectiveTime;//有效开始时间
	private Date endEffectiveTime;//有效结束时间
	private String accessMac;//门禁蓝牙Mac地址,存储时所有H008存为“H008”和所有H008b存为“H008b”
	private UpgradeStatus upgradeStatus;//是否要升级，0升级 1不升级
	private String upgradeVersion;//版本信息/批次
	private UpgradeForcedUpgrade upgradeForcedUpgrade;//是否强制升级 0 是 1否
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Type{
		advertising,system,voice,upgrade;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		normal,invalid;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum VoiceStatus{
		Yes,No;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum AdType{
		picture,video,html5;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum UpgradeStatus{
		Upgrade,NotUpgrade;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum UpgradeForcedUpgrade{
		Yes,No;
	}
	@Column(length=8)
	public String getAccessNum() {
		return accessNum;
	}
	public void setAccessNum(String accessNum) {
		this.accessNum = accessNum;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	@Column(length=100)
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public VoiceStatus getVoiceStatus() {
		return voiceStatus;
	}
	public void setVoiceStatus(VoiceStatus voiceStatus) {
		this.voiceStatus = voiceStatus;
	}
	public AdType getAdType() {
		return adType;
	}
	public void setAdType(AdType adType) {
		this.adType = adType;
	}
	@Column(length=10)
	public String getAdPutTimeStart() {
		return adPutTimeStart;
	}
	public void setAdPutTimeStart(String adPutTimeStart) {
		this.adPutTimeStart = adPutTimeStart;
	}
	@Column(length=10)
	public String getAdPutTimeEnd() {
		return adPutTimeEnd;
	}
	public void setAdPutTimeEnd(String adPutTimeEnd) {
		this.adPutTimeEnd = adPutTimeEnd;
	}
	public Integer getSystemVolume() {
		return systemVolume;
	}
	public void setSystemVolume(Integer systemVolume) {
		this.systemVolume = systemVolume;
	}
	public Date getStartEffectiveTime() {
		return startEffectiveTime;
	}
	public void setStartEffectiveTime(Date startEffectiveTime) {
		this.startEffectiveTime = startEffectiveTime;
	}
	public Date getEndEffectiveTime() {
		return endEffectiveTime;
	}
	public void setEndEffectiveTime(Date endEffectiveTime) {
		this.endEffectiveTime = endEffectiveTime;
	}
	@Column(length=32)
	public String getAccessMac() {
		return accessMac;
	}
	public void setAccessMac(String accessMac) {
		this.accessMac = accessMac;
	}
	public UpgradeStatus getUpgradeStatus() {
		return upgradeStatus;
	}
	public void setUpgradeStatus(UpgradeStatus upgradeStatus) {
		this.upgradeStatus = upgradeStatus;
	}
	@Column(length=20)
	public String getUpgradeVersion() {
		return upgradeVersion;
	}
	public void setUpgradeVersion(String upgradeVersion) {
		this.upgradeVersion = upgradeVersion;
	}
	public UpgradeForcedUpgrade getUpgradeForcedUpgrade() {
		return upgradeForcedUpgrade;
	}
	public void setUpgradeForcedUpgrade(UpgradeForcedUpgrade upgradeForcedUpgrade) {
		this.upgradeForcedUpgrade = upgradeForcedUpgrade;
	}

}
