package com.arf.axdshopkeeper.dto;

import java.util.List;

public class ShopkeeperCommunityDto {
	
	/**
	 * 排序枚举:SCALE 按规模排序;NEWEST 按最新排序
	 */
	public enum OrderBy{
		SCALE,
		NEWEST;
	}
	
	private String introduceUrl;//小区详情介绍URL,用户分享
	private String communityAddress;//小区所在地址
	private String cityName;//所在城市
	private List<String> openPropertyService;//开通的物业服务
	private String imageBanner;//小区banner图,最多4张,多张采用英文逗号分隔
	private String offlineDate;//下线时间
	private String communityDesc;//小区介绍
	private String imageIntroduce;//小区介绍图片,最多10张,多张采用英文逗号分隔
	private String proprietorCount;//业主户数
	private String invitingTenderName;//招商经理名称
	
	private String onlineDate;//上线时间
	private String browseCount;//浏览次数
	private String enterAmount;//入驻金额
	private String invitingTenderMobile;//招商经理电话
	private String branchId;//子公司编号
	private String branchName;//子公司名称
	private String communityName;//小区名称
	private String communityNumber;//小区编号
	private String bizChannelNumber;//渠道编号
	private String bizChannelName;//渠道名称
	
	private String propertyName;//物业公司名称
	private String propertyNumber;//物业公司编号
	
	public String getIntroduceUrl() {
		return introduceUrl;
	}
	public String getCommunityAddress() {
		return communityAddress;
	}
	public String getCityName() {
		return cityName;
	}
	public List<String> getOpenPropertyService() {
		return openPropertyService;
	}
	public String getImageBanner() {
		return imageBanner;
	}
	public String getOfflineDate() {
		return offlineDate;
	}
	public String getCommunityDesc() {
		return communityDesc;
	}
	public String getImageIntroduce() {
		return imageIntroduce;
	}
	public String getProprietorCount() {
		return proprietorCount;
	}
	public String getInvitingTenderName() {
		return invitingTenderName;
	}
	public String getOnlineDate() {
		return onlineDate;
	}
	public String getBrowseCount() {
		return browseCount;
	}
	public String getEnterAmount() {
		return enterAmount;
	}
	public String getInvitingTenderMobile() {
		return invitingTenderMobile;
	}
	public String getBranchId() {
		return branchId;
	}
	public String getBranchName() {
		return branchName;
	}
	public String getCommunityName() {
		return communityName;
	}
	public String getCommunityNumber() {
		return communityNumber;
	}
	public String getBizChannelNumber() {
		return bizChannelNumber;
	}
	public String getBizChannelName() {
		return bizChannelName;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public String getPropertyNumber() {
		return propertyNumber;
	}
	public void setIntroduceUrl(String introduceUrl) {
		this.introduceUrl = introduceUrl;
	}
	public void setCommunityAddress(String communityAddress) {
		this.communityAddress = communityAddress;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public void setOpenPropertyService(List<String> openPropertyService) {
		this.openPropertyService = openPropertyService;
	}
	public void setImageBanner(String imageBanner) {
		this.imageBanner = imageBanner;
	}
	public void setOfflineDate(String offlineDate) {
		this.offlineDate = offlineDate;
	}
	public void setCommunityDesc(String communityDesc) {
		this.communityDesc = communityDesc;
	}
	public void setImageIntroduce(String imageIntroduce) {
		this.imageIntroduce = imageIntroduce;
	}
	public void setProprietorCount(String proprietorCount) {
		this.proprietorCount = proprietorCount;
	}
	public void setInvitingTenderName(String invitingTenderName) {
		this.invitingTenderName = invitingTenderName;
	}
	public void setOnlineDate(String onlineDate) {
		this.onlineDate = onlineDate;
	}
	public void setBrowseCount(String browseCount) {
		this.browseCount = browseCount;
	}
	public void setEnterAmount(String enterAmount) {
		this.enterAmount = enterAmount;
	}
	public void setInvitingTenderMobile(String invitingTenderMobile) {
		this.invitingTenderMobile = invitingTenderMobile;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public void setBizChannelNumber(String bizChannelNumber) {
		this.bizChannelNumber = bizChannelNumber;
	}
	public void setBizChannelName(String bizChannelName) {
		this.bizChannelName = bizChannelName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public void setPropertyNumber(String propertyNumber) {
		this.propertyNumber = propertyNumber;
	}

}
