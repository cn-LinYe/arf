package com.arf.gift.dao;

import java.util.List;
import java.util.Map;

import com.arf.core.dao.BaseDao;
import com.arf.gift.entity.GiftVoucherExchangeItem;

public interface IGiftVoucherExchangeItemDao extends BaseDao<GiftVoucherExchangeItem, Long>{

	
	/**
	 * 通过代金券编号查询
	 * @param voucherNumber
	 * @return
	 */
	List<Map<String,Object>> findByVoucherNumber(String voucherNumber);
}
