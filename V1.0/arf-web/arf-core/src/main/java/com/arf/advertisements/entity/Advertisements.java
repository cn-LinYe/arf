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
@Table(name = "advertisements")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "advertisements_sequence")
public class Advertisements extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -748623275774153148L;
	
	private String advNum;//广告编号
	private String adImgUrl;//广告图片地址
	private Integer type;//广告类型0没有跳转1跳转webview页面2以上都是原生界面
	private AdStatus adStatus;//广告状态
	private String url;//广告跳转地址（type=1才有值）
	private String paramName;//广告跳转需要的额外参数的名称
	private String paramValue;//广告跳转需要的额外参数的值
	private String cities;//广告可见城市
	private String excludeCities;//广告不可见城市
	private String adName;//广告标题
	private String adDesc;//广告内容
	private String latAndLong;//广告商圈经纬度
	private String businessRange;//广告商圈范围
	private String businessDistrictName;//广告商圈名称
	private String adClickedUrl;//广告链接地址
	private Date adStartDate;//广告上架时间
	private Date adEndDate;//广告下架时间
	private AdType adType;//广告类型1.广告2.推广3.家加油
	private Style style;//图片状态0或null.大图1.小图
	private String voiceUrl;//广告点开声音
	private FileType fileType;//文件类型 0 img图片 1gif 2小视频video
	private String linkStyleUrl;//跳转样式
	private Integer adOrder;//广告顺序
	//private AuditStatus auditStatus;//审核状态0NEW待审核1PASS审核通过2REFUSE审核驳回
	private String communities;//广告可见小区
	private String excludeCommunities;//广告不可见小区
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum AdStatus{
		Normal,//正常
		UnShelves;//下架
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum AdType{
		NotUse,
		Advertisement,//1.广告
		Promotion,//2.推广
		RefuelGas;//3.家加油
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Style{
		BigPictrue,//0.大图
		SmallPictrue;//1.小图
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum FileType{
		img,//0.图片
		gif,//1.gif 
		video;//2.小视频
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum AuditStatus{
		NEW,//0待审核
		PASS,//1.审核通过
		REFUSE;//2.审核驳回
	}
	
	@Column(unique = true,nullable = false,length=20)
	public String getAdvNum() {
		return advNum;
	}
	public String getAdImgUrl() {
		return adImgUrl;
	}

	public AdStatus getAdStatus() {
		return adStatus;
	}
	public String getUrl() {
		return url;
	}
	public String getParamName() {
		return paramName;
	}
	public String getParamValue() {
		return paramValue;
	}
	@Column(length=2500)
	public String getCities() {
		return cities;
	}
	@Column(length=2500)
	public String getExcludeCities() {
		return excludeCities;
	}
	public String getAdName() {
		return adName;
	}
	public String getAdDesc() {
		return adDesc;
	}
	public String getLatAndLong() {
		return latAndLong;
	}
	public String getBusinessRange() {
		return businessRange;
	}
	public String getBusinessDistrictName() {
		return businessDistrictName;
	}
	public String getAdClickedUrl() {
		return adClickedUrl;
	}
	public Integer getType() {
		return type;
	}
	public Date getAdStartDate() {
		return adStartDate;
	}
	public Date getAdEndDate() {
		return adEndDate;
	}
	public AdType getAdType() {
		return adType;
	}
	public Style getStyle() {
		return style;
	}
	public void setStyle(Style style) {
		this.style = style;
	}
	public void setAdType(AdType adType) {
		this.adType = adType;
	}
	public void setAdvNum(String advNum) {
		this.advNum = advNum;
	}
	public void setAdImgUrl(String adImgUrl) {
		this.adImgUrl = adImgUrl;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public void setAdStatus(AdStatus adStatus) {
		this.adStatus = adStatus;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public void setCities(String cities) {
		this.cities = cities;
	}
	public void setExcludeCities(String excludeCities) {
		this.excludeCities = excludeCities;
	}
	public void setAdName(String adName) {
		this.adName = adName;
	}
	public void setAdDesc(String adDesc) {
		this.adDesc = adDesc;
	}
	public void setLatAndLong(String latAndLong) {
		this.latAndLong = latAndLong;
	}
	public void setBusinessRange(String businessRange) {
		this.businessRange = businessRange;
	}
	public void setBusinessDistrictName(String businessDistrictName) {
		this.businessDistrictName = businessDistrictName;
	}
	public void setAdClickedUrl(String adClickedUrl) {
		this.adClickedUrl = adClickedUrl;
	}
	public void setAdStartDate(Date adStartDate) {
		this.adStartDate = adStartDate;
	}
	public void setAdEndDate(Date adEndDate) {
		this.adEndDate = adEndDate;
	}
	@Column(length=100)
	public String getVoiceUrl() {
		return voiceUrl;
	}
	public void setVoiceUrl(String voiceUrl) {
		this.voiceUrl = voiceUrl;
	}
	public FileType getFileType() {
		return fileType;
	}
	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}
	@Column(length=100)
	public String getLinkStyleUrl() {
		return linkStyleUrl;
	}
	public void setLinkStyleUrl(String linkStyleUrl) {
		this.linkStyleUrl = linkStyleUrl;
	}
	public Integer getAdOrder() {
		return adOrder;
	}
	public void setAdOrder(Integer adOrder) {
		this.adOrder = adOrder;
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

}
