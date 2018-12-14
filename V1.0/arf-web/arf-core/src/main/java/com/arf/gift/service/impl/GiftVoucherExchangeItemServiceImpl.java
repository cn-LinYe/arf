package com.arf.gift.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.gift.dao.IGiftVoucherExchangeItemDao;
import com.arf.gift.entity.GiftVoucherExchangeItem;
import com.arf.gift.service.IGiftVoucherExchangeItemService;

@Service("giftVoucherExchangeItemServiceImpl")
public class GiftVoucherExchangeItemServiceImpl extends BaseServiceImpl<GiftVoucherExchangeItem, Long> implements IGiftVoucherExchangeItemService{

	@Resource(name="giftVoucherExchangeItemDaoImpl")
	IGiftVoucherExchangeItemDao giftVoucherExchangeItemDao;
	@Override
	protected BaseDao<GiftVoucherExchangeItem, Long> getBaseDao() {
		return giftVoucherExchangeItemDao;
	}
	@Override
	public List<Map<String,Object>> findByVoucherNumber(String voucherNumber) {
		return giftVoucherExchangeItemDao.findByVoucherNumber(voucherNumber);
	}

}
