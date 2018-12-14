package com.arf.laundry.dto;

import org.springframework.beans.BeanUtils;

import com.arf.laundry.entity.LaundryOrder;

public class MerchantOrderDto extends LaundryOrder{

	private static final long serialVersionUID = 1L;
	private String userAvatar;//用户头像
	
	public MerchantOrderDto(LaundryOrder laundryOrder){
		BeanUtils.copyProperties(laundryOrder, this);
	}
	
	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}
}
