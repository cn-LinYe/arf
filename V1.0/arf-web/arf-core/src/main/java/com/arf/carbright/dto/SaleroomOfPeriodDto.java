package com.arf.carbright.dto;

import java.io.Serializable;
import java.util.Date;

public class SaleroomOfPeriodDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long businessId;//商户Id
	private Date startBuyingTime;//开始时间
	private Date endBuyingTime;//结束时间
	private int packagePrice;//销售总额 单位:元
	private int giftAmount;//打折总额 单位:元
	private int count;//销售单数 
	public Long getBusinessId() {
		return businessId;
	}
	public int getPackagePrice() {
		return packagePrice;
	}
	public int getGiftAmount() {
		return giftAmount;
	}
	public int getCount() {
		return count;
	}
	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}
	public void setPackagePrice(int packagePrice) {
		this.packagePrice = packagePrice;
	}
	public void setGiftAmount(int giftAmount) {
		this.giftAmount = giftAmount;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Date getStartBuyingTime() {
		return startBuyingTime;
	}
	public Date getEndBuyingTime() {
		return endBuyingTime;
	}
	public void setStartBuyingTime(Date startBuyingTime) {
		this.startBuyingTime = startBuyingTime;
	}
	public void setEndBuyingTime(Date endBuyingTime) {
		this.endBuyingTime = endBuyingTime;
	}
	
}
