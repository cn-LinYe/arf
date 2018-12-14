package com.arf.goldcard.service;

import com.arf.core.service.BaseService;
import com.arf.goldcard.entity.GoldCardExchangeCode;

public interface IGoldCardExchangeCodeService extends BaseService<GoldCardExchangeCode, Long>{


	/**
	 * 根据兑换码查找记录
	 * @param exchangeCode
	 * @param status
	 * @return
	 */
	GoldCardExchangeCode findByExchangeCode(String exchangeCode);
}
