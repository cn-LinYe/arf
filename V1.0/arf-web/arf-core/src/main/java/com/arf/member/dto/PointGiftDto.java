package com.arf.member.dto;

public class PointGiftDto {

	/**
	 * 赠送状态 true 成功（giftPoint >= 0） false 失败（参数错误 或者 系统错误）
	 */
	private boolean giftResult;
	/**
	 * 赠送积分（giftResult为false,此字段为空）
	 */
	private Integer giftPoint;
	private String reason;//giftPoint == 0 或者 giftPoint = null的原因
	
	public PointGiftDto() {}
	
	public PointGiftDto(boolean giftResult, Integer giftPoint,String reason) {
		this.giftResult = giftResult;
		this.giftPoint = giftPoint;
		this.reason = reason;
	}

	public boolean isGiftResult() {
		return giftResult;
	}

	public Integer getGiftPoint() {
		return giftPoint;
	}

	public String getReason() {
		return reason;
	}

	public void setGiftResult(boolean giftResult) {
		this.giftResult = giftResult;
	}

	public void setGiftPoint(Integer giftPoint) {
		this.giftPoint = giftPoint;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
