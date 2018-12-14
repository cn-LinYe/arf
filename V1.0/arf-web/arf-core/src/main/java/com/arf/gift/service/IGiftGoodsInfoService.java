package com.arf.gift.service;

import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.gift.entity.GiftGoodsInfo;

public interface IGiftGoodsInfoService extends BaseService<GiftGoodsInfo, Long>{

	/**
	 * 通过sku查找
	 * @param sku
	 * @return
	 */
	List<GiftGoodsInfo> findBySku(String ...sku);

	/**
	 * 查询可兑换的礼品
	 * @param communityNumber
	 * @return
	 */
	List<GiftGoodsInfo> findAllExchangableGift(String communityNumber);

}
