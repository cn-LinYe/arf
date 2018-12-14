package com.arf.gift.dao;

import com.arf.core.dao.BaseDao;
import com.arf.gift.entity.CommunityPromotions;

public interface CommunityPromotionsDao extends BaseDao<CommunityPromotions, Long> {

	public CommunityPromotions findByCommunityNumberMonthlyStatus(String communityNumber,
			CommunityPromotions.MonthlyPaymentStatus monthlyPaymentStatus);
}
