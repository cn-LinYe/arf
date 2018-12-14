package com.arf.platform.dto;

import java.io.Serializable;
import java.util.List;

public class EscapeDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String totalCount;//总逃费次数
	private int totalFee;//总逃费金额，单位分
	private List<EscapeRecordDto> records;//记录
	
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}
	public List<EscapeRecordDto> getRecords() {
		return records;
	}
	public void setRecords(List<EscapeRecordDto> records) {
		this.records = records;
	}
	@Override
	public String toString() {
		return "EscapeDto [totalCount=" + totalCount + ", totalFee=" + totalFee
				+ "]";
	}
	
}
