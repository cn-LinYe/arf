package com.arf.goldcard.dto;

import java.math.BigDecimal;

import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSONArray;
import com.arf.goldcard.entity.UserGoldCardAccount;

public class UserGoldCardAccountDto extends UserGoldCardAccount {

	private static final long serialVersionUID = 1L;
	
	private BigDecimal faceValue;//面值
	private BigDecimal price;//购买价格
	private JSONArray privileges;//金卡特权

	public UserGoldCardAccountDto() {}

	public UserGoldCardAccountDto(UserGoldCardAccount userGoldCardAccount){
		BeanUtils.copyProperties(userGoldCardAccount, this);
	}

	public BigDecimal getFaceValue() {
		return faceValue;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setFaceValue(BigDecimal faceValue) {
		this.faceValue = faceValue;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public JSONArray getPrivileges() {
		return privileges;
	}

	public void setPrivileges(JSONArray privileges) {
		this.privileges = privileges;
	}
}
