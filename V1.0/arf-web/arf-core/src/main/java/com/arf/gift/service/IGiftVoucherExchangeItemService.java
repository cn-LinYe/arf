package com.arf.gift.service;

import java.util.List;
import java.util.Map;

import com.arf.core.service.BaseService;
import com.arf.gift.entity.GiftVoucherExchangeItem;

public interface IGiftVoucherExchangeItemService extends BaseService<GiftVoucherExchangeItem, Long>{

	/**
	 * 通过代金券编号查询
	 * @param voucherNumber
	 * @return
	 */
	List<Map<String,Object>> findByVoucherNumber(String voucherNumber);
}
