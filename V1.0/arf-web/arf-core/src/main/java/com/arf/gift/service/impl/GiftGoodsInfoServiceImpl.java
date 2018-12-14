package com.arf.gift.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.Filter;
import com.arf.core.Filter.Operator;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.gift.dao.IGiftGoodsInfoDao;
import com.arf.gift.entity.GiftGoodsInfo;
import com.arf.gift.service.IGiftGoodsInfoService;

@Service("giftGoodsInfoService")
public class GiftGoodsInfoServiceImpl extends BaseServiceImpl<GiftGoodsInfo, Long> implements IGiftGoodsInfoService{

	@Resource(name = "giftGoodsInfoDao")
	IGiftGoodsInfoDao giftGoodsInfoDao;
	
	@Override
	protected BaseDao<GiftGoodsInfo, Long> getBaseDao() {
		return giftGoodsInfoDao;
	}

	@Override
	public List<GiftGoodsInfo> findBySku(String... sku) {
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter("sku", Operator.in, Arrays.asList(sku)));
		List<GiftGoodsInfo> list = this.findList(null, filters, null);
		return list;
	}

	@Override
	public List<GiftGoodsInfo> findAllExchangableGift(String communityNumber) {
		return giftGoodsInfoDao.findAllExchangableGift(communityNumber);
	}

}
