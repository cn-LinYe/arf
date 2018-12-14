package com.arf.gift.service;

import com.arf.core.service.BaseService;
import com.arf.gift.entity.CommunityPromotions;

public interface CommunityPromotionsService extends BaseService<CommunityPromotions, Long> {

	/**
	 * 按小区、月租缴费活动状态查询
	 * @param communityNumber
	 * @param monthlyPaymentStatus
	 * @return
	 */
	public CommunityPromotions findByCommunityNumberMonthlyStatus(String communityNumber,
			CommunityPromotions.MonthlyPaymentStatus monthlyPaymentStatus);
}
