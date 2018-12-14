package com.arf.carbright.dto;

import java.io.Serializable;

public class PackageTypeSettingsDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final int Default_Limit_Of_Day = 999999;
	
	/** 该套餐类型每天的接单量限制  */
	private Integer limitOfDay;

	public Integer getLimitOfDay() {
		return limitOfDay;
	}

	public void setLimitOfDay(Integer limitOfDay) {
		this.limitOfDay = limitOfDay;
	}
	
}
