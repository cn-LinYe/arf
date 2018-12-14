package com.arf.core.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 安心点用户赠送记录缓存,缓存有效期【当天】有效
 * @author Caolibao
 * 2016年8月26日 下午3:57:30
 *
 */
public class AxdGiftMemberCache implements Serializable{

	private static final long serialVersionUID = 814963722244270273L;
	public static final String Key_Cached_AxdGiftMemberCache_Prefix = "AxdGiftMemberCache.Map";
	
	public static final String Key_GiftCountOfToday = "giftCountOfToday";
	public static final String Key_GiftAmountOfToday = "giftAmountOfToday";
	public static final String Key_GiftPointOfToday = "giftPointOfToday";
	public static final String Key_LastAxdTime = "lastAxdTime";
	public static final String Key_LastSignTime = "lastSignTime";//签到时间
	
	private String userName;//用户名
	private Integer giftCountOfToday;//当天的已赠送有效次数
	private BigDecimal giftAmountOfToday;//当天的有效赠送现金总金额
	private Integer giftPointOfToday;//当天的有效赠送积分总数
	private String lastAxdTime;//当天最近一次安心点时间(格式:yyyy-MM-dd HH:mm:ss)
	
	private String lastSignTime;//当天签到时间(格式:yyyy-MM-dd HH:mm:ss)
	
	public String getLastSignTime() {
		return lastSignTime;
	}
	public void setLastSignTime(String lastSignTime) {
		this.lastSignTime = lastSignTime;
	}
	public String getUserName() {
		return userName;
	}
	public Integer getGiftCountOfToday() {
		return giftCountOfToday;
	}
	public BigDecimal getGiftAmountOfToday() {
		return giftAmountOfToday;
	}
	public Integer getGiftPointOfToday() {
		return giftPointOfToday;
	}
	public String getLastAxdTime() {
		return lastAxdTime;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setGiftCountOfToday(Integer giftCountOfToday) {
		this.giftCountOfToday = giftCountOfToday;
	}
	public void setGiftAmountOfToday(BigDecimal giftAmountOfToday) {
		this.giftAmountOfToday = giftAmountOfToday;
	}
	public void setGiftPointOfToday(Integer giftPointOfToday) {
		this.giftPointOfToday = giftPointOfToday;
	}
	public void setLastAxdTime(String lastAxdTime) {
		this.lastAxdTime = lastAxdTime;
	}
	
}
