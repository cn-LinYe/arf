package com.arf.platform.dto;

import java.io.Serializable;

public class EscapeRecordDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer type;//0欠费,1逃费
	private String recordId;//记录Id
	private int fee;//费用，单位分
	
	public Integer getType() {
		return type;
	}
	public String getRecordId() {
		return recordId;
	}
	public int getFee() {
		return fee;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public void setFee(int fee) {
		this.fee = fee;
	}
	
}
