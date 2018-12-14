package com.arf.axdshopkeeper.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "bi_shopkeeper_community")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "bi_shopkeeper_community_sequence")
public class ShopkeeperCommunity extends BaseEntity<Long> {
	
	private static final long serialVersionUID = 1L;
	
	private String communityNumber;//小区编号
	private Integer proprietorCount;//业主户数
	private String invitingTenderName;//招商经理姓名
	private Date onlineDate;//上线时间
	private Status status;//状态枚举
	private Integer browseCount;//浏览量
	private BigDecimal enterAmount;//入驻金额,单位:元
	private String invitingTenderMobile;//招商经理电话
	private Date offlineDate;//下线时间
	private String imageBanner;//小区banner图,最多4张,多张采用英文逗号分隔
	private String communityDesc;//小区介绍
	private String imageIntroduce;//小区介绍图片,最多10张,多张采用英文逗号分隔
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		OFFLINE_ING, //下线中
		PUBLISH_ING, //发布中
		NEGOTIATE_ING,//洽谈中
		SIGNED//已签约
	}

	@Column(length=20,nullable=false)
	public String getCommunityNumber() {
		return communityNumber;
	}

	public Integer getProprietorCount() {
		return proprietorCount;
	}

	@Column(length=20)
	public String getInvitingTenderName() {
		return invitingTenderName;
	}
	
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss")
	public Date getOnlineDate() {
		return onlineDate;
	}

	@Column(nullable=false)
	public Status getStatus() {
		return status;
	}

	@Column(nullable=false)
	public Integer getBrowseCount() {
		return browseCount;
	}

	@Column(precision = 10, scale = 2,nullable = false)
	public BigDecimal getEnterAmount() {
		return enterAmount;
	}

	@Column(length=20,nullable=false)
	public String getInvitingTenderMobile() {
		return invitingTenderMobile;
	}

	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss")
	public Date getOfflineDate() {
		return offlineDate;
	}

	@Column(length=800)
	public String getImageBanner() {
		return imageBanner;
	}

	@Type(type = "text")
	@Column(length=65535)
	public String getCommunityDesc() {
		return communityDesc;
	}

	@Column(length=2000)
	public String getImageIntroduce() {
		return imageIntroduce;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}

	public void setProprietorCount(Integer proprietorCount) {
		this.proprietorCount = proprietorCount;
	}

	public void setInvitingTenderName(String invitingTenderName) {
		this.invitingTenderName = invitingTenderName;
	}

	public void setOnlineDate(Date onlineDate) {
		this.onlineDate = onlineDate;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setBrowseCount(Integer browseCount) {
		this.browseCount = browseCount;
	}

	public void setEnterAmount(BigDecimal enterAmount) {
		this.enterAmount = enterAmount;
	}

	public void setInvitingTenderMobile(String invitingTenderMobile) {
		this.invitingTenderMobile = invitingTenderMobile;
	}

	public void setOfflineDate(Date offlineDate) {
		this.offlineDate = offlineDate;
	}

	public void setImageBanner(String imageBanner) {
		this.imageBanner = imageBanner;
	}

	public void setCommunityDesc(String communityDesc) {
		this.communityDesc = communityDesc;
	}

	public void setImageIntroduce(String imageIntroduce) {
		this.imageIntroduce = imageIntroduce;
	}

}
