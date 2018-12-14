package com.arf.platform.dto;

public class PakingRateDto {

	//下行返回数据
	private String parkname;//车场名称
	private String monthpay;//0代表未任何包，1包月，2包日，3包时
	private String allparking;//总共车位
	private String preHour;//2元/15分钟
	private String address;//地址
	private String imageurl;//图片路径
	private String feedetail;//收费明细
	private String intro;//其他介绍
	private String planUrl;//停车场平面图
	private String isYd;//是否合作停车场0否1是
	private Integer feeday;//白天收费价格
	private Integer feenight;//晚上收费价格
	private Integer ydFee;//会员价格
	private String parkType;//停车场类型 0露天，1路边，2地下
	private String daytimeperiod;//8:00-20:00白天时间段
	private String nighttimeperiod;//20:00-8:00晚上时间段
	
	public String getParkname() {
		return parkname;
	}
	public String getMonthpay() {
		return monthpay;
	}
	public String getAllparking() {
		return allparking;
	}
	public String getPreHour() {
		return preHour;
	}
	public String getAddress() {
		return address;
	}
	public String getImageurl() {
		return imageurl;
	}
	public String getFeedetail() {
		return feedetail;
	}
	public String getIntro() {
		return intro;
	}
	public String getPlanUrl() {
		return planUrl;
	}
	public String getIsYd() {
		return isYd;
	}
	public Integer getFeeday() {
		return feeday;
	}
	public Integer getFeenight() {
		return feenight;
	}
	public Integer getYdFee() {
		return ydFee;
	}
	public String getParkType() {
		return parkType;
	}
	public String getDaytimeperiod() {
		return daytimeperiod;
	}
	public String getNighttimeperiod() {
		return nighttimeperiod;
	}
	public void setParkname(String parkname) {
		this.parkname = parkname;
	}
	public void setMonthpay(String monthpay) {
		this.monthpay = monthpay;
	}
	public void setAllparking(String allparking) {
		this.allparking = allparking;
	}
	public void setPreHour(String preHour) {
		this.preHour = preHour;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	public void setFeedetail(String feedetail) {
		this.feedetail = feedetail;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public void setPlanUrl(String planUrl) {
		this.planUrl = planUrl;
	}
	public void setIsYd(String isYd) {
		this.isYd = isYd;
	}
	public void setFeeday(Integer feeday) {
		this.feeday = feeday;
	}
	public void setFeenight(Integer feenight) {
		this.feenight = feenight;
	}
	public void setYdFee(Integer ydFee) {
		this.ydFee = ydFee;
	}
	public void setParkType(String parkType) {
		this.parkType = parkType;
	}
	public void setDaytimeperiod(String daytimeperiod) {
		this.daytimeperiod = daytimeperiod;
	}
	public void setNighttimeperiod(String nighttimeperiod) {
		this.nighttimeperiod = nighttimeperiod;
	}
	
}
