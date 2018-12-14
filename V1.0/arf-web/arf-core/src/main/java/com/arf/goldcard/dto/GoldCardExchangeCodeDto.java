package com.arf.goldcard.dto;

import java.io.Serializable;

import com.arf.goldcard.entity.GoldCardExchangeCode;

public class GoldCardExchangeCodeDto extends GoldCardExchangeCode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Byte periodType;
	public Integer usefulPeriod;
	public Byte getPeriodType() {
		return periodType;
	}
	public void setPeriodType(Byte periodType) {
		this.periodType = periodType;
	}
	public Integer getUsefulPeriod() {
		return usefulPeriod;
	}
	public void setUsefulPeriod(Integer usefulPeriod) {
		this.usefulPeriod = usefulPeriod;
	}
	
}
