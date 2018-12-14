package com.arf.core.dto;

import java.io.Serializable;

public class AxdGiftResultDto implements Serializable{

	private static final long serialVersionUID = -3375114628307283470L;
	
	private double giftCash;//点击安心点赠送的现金
	private int giftPoint;//点击安心点赠送的积分
	private String reason;//没有赠送的原因
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public double getGiftCash() {
		return giftCash;
	}
	public int getGiftPoint() {
		return giftPoint;
	}
	public void setGiftCash(double giftCash) {
		this.giftCash = giftCash;
	}
	public void setGiftPoint(int giftPoint) {
		this.giftPoint = giftPoint;
	}
	public AxdGiftResultDto() {
		super();
	}
	public AxdGiftResultDto(double giftCash) {
		super();
		this.giftCash = giftCash;
	}
	public AxdGiftResultDto(int giftPoint) {
		super();
		this.giftPoint = giftPoint;
	}
	public AxdGiftResultDto(double giftCash, int giftPoint) {
		super();
		this.giftCash = giftCash;
		this.giftPoint = giftPoint;
	}
	public AxdGiftResultDto(int giftPoint, String reason) {
		super();
		this.giftPoint = giftPoint;
		this.reason = reason;
	}

}
