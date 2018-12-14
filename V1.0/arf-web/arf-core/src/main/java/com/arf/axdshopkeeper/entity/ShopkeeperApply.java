package com.arf.axdshopkeeper.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "bi_shopkeeper_apply")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "bi_shopkeeper_apply_sequence")
public class ShopkeeperApply extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	private String applyName;//申请人姓名
	private String applyMobile;//申请人手机号
	private String applyAddress;//申请人地址
	private ApplyMode applyMode;//申请方式枚举: 
	private String sharedUserName;//分享人用户名/线下提交经办人
	private String communityNumber;//申请小区
	private Date applyDate;//申请时间
	private Status status;//记录状态
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		NEW,//刚申请
		EGOTIATE_ING,//洽谈中
		SIGNED,//已签约
		SIGN_FAILED,//签约失败
		;
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum ApplyMode{
		SHARED_WX, //他人微信分享
		SHARED_QQ, // 他人QQ分享 
		SHARED_WEIBO, //他们微博分享
		ONLINE_BROWSE,//线上浏览申请
		OFFLIINE_SUBMIT; //线下提交个人信息
	}
	
	@Column(length=20)
	public String getApplyName() {
		return applyName;
	}

	@Column(length=16)
	public String getApplyMobile() {
		return applyMobile;
	}

	@Column(length=100)
	public String getApplyAddress() {
		return applyAddress;
	}

	@Column(nullable=false)
	public ApplyMode getApplyMode() {
		return applyMode;
	}

	@Column(length=20)
	public String getSharedUserName() {
		return sharedUserName;
	}

	@Column(length=20,nullable=false)
	public String getCommunityNumber() {
		return communityNumber;
	}

	@Column(nullable=false)
	public Date getApplyDate() {
		return applyDate;
	}

	public Status getStatus() {
		status = status == null?Status.NEW:status;
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}

	public void setApplyMobile(String applyMobile) {
		this.applyMobile = applyMobile;
	}

	public void setApplyAddress(String applyAddress) {
		this.applyAddress = applyAddress;
	}

	public void setApplyMode(ApplyMode applyMode) {
		this.applyMode = applyMode;
	}

	public void setSharedUserName(String sharedUserName) {
		this.sharedUserName = sharedUserName;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

}
