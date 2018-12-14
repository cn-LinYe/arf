package com.arf.gift.dao;

import java.util.List;

import com.arf.core.dao.BaseDao;
import com.arf.gift.entity.GiftGoodsInfo;

public interface IGiftGoodsInfoDao extends BaseDao<GiftGoodsInfo, Long>{

	/**
	 * 查询可兑换的礼品
	 * @param communityNumber
	 * @return
	 */
	List<GiftGoodsInfo> findAllExchangableGift(String communityNumber);
}
