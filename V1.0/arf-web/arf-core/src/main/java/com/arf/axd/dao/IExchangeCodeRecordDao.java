package com.arf.axd.dao;

import com.arf.axd.entity.ExchangeCodeRecord;
import com.arf.core.dao.BaseDao;

public interface IExchangeCodeRecordDao extends BaseDao<ExchangeCodeRecord, Long>{

	/**
	 * 根据凭证码查找兑换信息
	 * @param exchangeCode
	 * @return
	 */
  ExchangeCodeRecord findByExchangeCode(String exchangeCode);
}
