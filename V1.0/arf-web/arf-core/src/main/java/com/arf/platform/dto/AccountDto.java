package com.arf.platform.dto;

import java.io.Serializable;

public class AccountDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String type;//帐户类型
	private int remain;//帐户余额、单位为分
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getRemain() {
		return remain;
	}
	public void setRemain(int remain) {
		this.remain = remain;
	}
	@Override
	public String toString() {
		return "AccountDto [type=" + type + ", remain=" + remain + "]";
	}
	
}
