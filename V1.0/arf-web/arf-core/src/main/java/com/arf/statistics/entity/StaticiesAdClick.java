package com.arf.statistics.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="button_staticies_ad_click")
@SequenceGenerator(name="sequenceGenerator",sequenceName="button_staticies_ad_click_sequence")
public class StaticiesAdClick extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8852220930426181654L;
	
	private String userName;//用户名
	private String realName;//昵称
	private ClickType clickType;/*点击类型（0 home首页 1clickCloud云按钮-直接开门禁 2clickAccess云按钮-门禁图标 
								3clickCar云按钮-车闸图标 4clickLock云按钮-门锁图标 5cloudPage云按钮页面 6clickDetail云按钮-查看详情 
								7popup弹框页面 8 clickPlatformbanner首页平台banner 9clickBanner banner广告 
								10clickCityBanner首页城市banner）*/
	private Integer clickTimes;//点击次数
	private Integer waitTime;//等待时长（秒）
	private Integer shareTimes;//分享次数
	private Integer videoLoadTime;//视频加载时长（秒）
	private Integer videoSuccessTimes;//视频播放成功次数
	private Integer videoFailureTimes;//视频播放失败次数
	private Integer videoPlayTime;//小视频播放时长
	private Integer videoSuspendTimes;//小视频暂停次数
	private Integer imgLoadTime;//图片加载时长
	private Integer imgSuccessTimes;//图片加载成功次数
	private Integer imgFailureTimes;//图片加载失败次数
	private Integer gifLoadTime;//gif加载时长
	private Integer gifSuccessTimes;//gif加载成功次数
	private Integer gifFailureTimes;//gif加载失败次数
	private Integer linkFailureTimes;//链接跳转失败次数
	private Date clickDate;//点击时间
	private Integer linkSuccessTimes;//链接跳转成功次数
	private Integer linkOperateTimes;//链接页面内操作次数
	private String license;//	车牌号
	private String accessMac;//门禁编号/门禁Mac/门锁Mac
	private String adNum;//广告编号
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum ClickType{
		clickCloud,clickAccess,
		clickCar,clickLock,cloudPage,clickDetail,
		clickPopup,clickPlatformbanner,clickBanner,clickCityBanner
	}
	@Column(length=20)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length=32)
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public ClickType getClickType() {
		return clickType;
	}

	public void setClickType(ClickType clickType) {
		this.clickType = clickType;
	}

	public Integer getClickTimes() {
		return clickTimes;
	}

	public void setClickTimes(Integer clickTimes) {
		this.clickTimes = clickTimes;
	}

	public Integer getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(Integer waitTime) {
		this.waitTime = waitTime;
	}

	public Integer getShareTimes() {
		return shareTimes;
	}

	public void setShareTimes(Integer shareTimes) {
		this.shareTimes = shareTimes;
	}

	public Integer getVideoLoadTime() {
		return videoLoadTime;
	}

	public void setVideoLoadTime(Integer videoLoadTime) {
		this.videoLoadTime = videoLoadTime;
	}

	public Integer getVideoSuccessTimes() {
		return videoSuccessTimes;
	}

	public void setVideoSuccessTimes(Integer videoSuccessTimes) {
		this.videoSuccessTimes = videoSuccessTimes;
	}

	public Integer getVideoFailureTimes() {
		return videoFailureTimes;
	}

	public void setVideoFailureTimes(Integer videoFailureTimes) {
		this.videoFailureTimes = videoFailureTimes;
	}

	public Integer getVideoPlayTime() {
		return videoPlayTime;
	}

	public void setVideoPlayTime(Integer videoPlayTime) {
		this.videoPlayTime = videoPlayTime;
	}

	public Integer getVideoSuspendTimes() {
		return videoSuspendTimes;
	}

	public void setVideoSuspendTimes(Integer videoSuspendTimes) {
		this.videoSuspendTimes = videoSuspendTimes;
	}

	public Integer getImgLoadTime() {
		return imgLoadTime;
	}

	public void setImgLoadTime(Integer imgLoadTime) {
		this.imgLoadTime = imgLoadTime;
	}

	public Integer getImgSuccessTimes() {
		return imgSuccessTimes;
	}

	public void setImgSuccessTimes(Integer imgSuccessTimes) {
		this.imgSuccessTimes = imgSuccessTimes;
	}

	public Integer getImgFailureTimes() {
		return imgFailureTimes;
	}

	public void setImgFailureTimes(Integer imgFailureTimes) {
		this.imgFailureTimes = imgFailureTimes;
	}

	public Integer getGifLoadTime() {
		return gifLoadTime;
	}

	public void setGifLoadTime(Integer gifLoadTime) {
		this.gifLoadTime = gifLoadTime;
	}

	public Integer getGifSuccessTimes() {
		return gifSuccessTimes;
	}

	public void setGifSuccessTimes(Integer gifSuccessTimes) {
		this.gifSuccessTimes = gifSuccessTimes;
	}

	public Integer getGifFailureTimes() {
		return gifFailureTimes;
	}

	public void setGifFailureTimes(Integer gifFailureTimes) {
		this.gifFailureTimes = gifFailureTimes;
	}

	public Integer getLinkFailureTimes() {
		return linkFailureTimes;
	}

	public void setLinkFailureTimes(Integer linkFailureTimes) {
		this.linkFailureTimes = linkFailureTimes;
	}

	public Date getClickDate() {
		return clickDate;
	}

	public void setClickDate(Date clickDate) {
		this.clickDate = clickDate;
	}

	public Integer getLinkSuccessTimes() {
		return linkSuccessTimes;
	}

	public void setLinkSuccessTimes(Integer linkSuccessTimes) {
		this.linkSuccessTimes = linkSuccessTimes;
	}

	public Integer getLinkOperateTimes() {
		return linkOperateTimes;
	}

	public void setLinkOperateTimes(Integer linkOperateTimes) {
		this.linkOperateTimes = linkOperateTimes;
	}
	@Column(length=10)
	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}
	@Column(length=20)
	public String getAccessMac() {
		return accessMac;
	}

	public void setAccessMac(String accessMac) {
		this.accessMac = accessMac;
	}
	@Column(length=10)
	public String getAdNum() {
		return adNum;
	}

	public void setAdNum(String adNum) {
		this.adNum = adNum;
	}

}
