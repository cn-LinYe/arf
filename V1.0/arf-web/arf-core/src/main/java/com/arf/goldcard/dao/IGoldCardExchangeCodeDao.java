package com.arf.goldcard.dao;

import com.arf.core.dao.BaseDao;
import com.arf.goldcard.entity.GoldCardExchangeCode;

public interface IGoldCardExchangeCodeDao extends BaseDao<GoldCardExchangeCode, Long>{

	
	/**
	 * 根据兑换码查找兑换记录
	 * @param exchangeCode
	 * @return
	 */
	GoldCardExchangeCode findByExchangeCode(String exchangeCode);
}
