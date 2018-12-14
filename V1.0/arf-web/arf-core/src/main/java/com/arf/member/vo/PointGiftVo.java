package com.arf.member.vo;

import java.math.BigDecimal;

import com.arf.core.dto.AxdGiftConfig;
import com.arf.member.entity.PointTransferRecord.PointGiftBusinessType;
import com.arf.review.entity.ServiceReview;

public class PointGiftVo {

	private PointGiftBusinessType businessType;//业务类型
	private String userName;//用户名
	private String communityNumber;//小区编号
	
	private AxdGiftConfig axdGiftConfig;//安心点积分赠送相关配置
	private String license;//车牌号
	
	private ServiceReview.Type reviewType;//评价类型
	
	private Byte subType;//消费类型
	private BigDecimal amout;//消费金额
	
	public PointGiftVo() {}

	public PointGiftVo(PointGiftBusinessType businessType, String userName,
			AxdGiftConfig axdGiftConfig,String communityNumber) {
		this.businessType = businessType;
		this.userName = userName;
		this.axdGiftConfig = axdGiftConfig;
		this.communityNumber = communityNumber;
	}
	
	public PointGiftVo(PointGiftBusinessType businessType, String userName,
			AxdGiftConfig axdGiftConfig,String communityNumber, 
			String license) {
		this.businessType = businessType;
		this.userName = userName;
		this.axdGiftConfig = axdGiftConfig;
		this.communityNumber = communityNumber;
		this.license = license;
	}
	
	public PointGiftVo(PointGiftBusinessType businessType, String userName,
			String communityNumber, ServiceReview.Type reviewType) {
		this.businessType = businessType;
		this.userName = userName;
		this.communityNumber = communityNumber;
		this.reviewType = reviewType;
	}

	public PointGiftVo(PointGiftBusinessType businessType, String userName,
			String communityNumber, BigDecimal amout, Byte subType) {
		this.businessType = businessType;
		this.userName = userName;
		this.communityNumber = communityNumber;
		this.amout = amout;
		this.subType = subType;
	}

	public PointGiftBusinessType getBusinessType() {
		return businessType;
	}

	public String getUserName() {
		return userName;
	}

	public String getCommunityNumber() {
		return communityNumber;
	}

	public AxdGiftConfig getAxdGiftConfig() {
		return axdGiftConfig;
	}

	public String getLicense() {
		return license;
	}

	public ServiceReview.Type getReviewType() {
		return reviewType;
	}

	public Byte getSubType() {
		return subType;
	}

	public BigDecimal getAmout() {
		return amout;
	}

	public void setBusinessType(PointGiftBusinessType businessType) {
		this.businessType = businessType;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}

	public void setAxdGiftConfig(AxdGiftConfig axdGiftConfig) {
		this.axdGiftConfig = axdGiftConfig;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public void setReviewType(ServiceReview.Type reviewType) {
		this.reviewType = reviewType;
	}

	public void setSubType(Byte subType) {
		this.subType = subType;
	}

	public void setAmout(BigDecimal amout) {
		this.amout = amout;
	}
	
}
