package com.arf.activity.parkrate.dto;

public class ParkRateActivityDto {

	private int ret;
	private String msg;
	public ParkRateActivityDto() {
		super();
	}
	public ParkRateActivityDto(int ret, String msg) {
		super();
		this.ret = ret;
		this.msg = msg;
	}
	public int getRet() {
		return ret;
	}
	public String getMsg() {
		return msg;
	}
	public void setRet(int ret) {
		this.ret = ret;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
