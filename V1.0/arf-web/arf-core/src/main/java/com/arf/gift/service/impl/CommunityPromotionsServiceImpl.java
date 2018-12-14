package com.arf.gift.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.gift.dao.CommunityPromotionsDao;
import com.arf.gift.entity.CommunityPromotions;
import com.arf.gift.service.CommunityPromotionsService;

@Service("communityPromotionsServiceImpl")
public class CommunityPromotionsServiceImpl extends BaseServiceImpl<CommunityPromotions, Long> implements CommunityPromotionsService {

	@Resource(name="communityPromotionsDaoImpl")
	CommunityPromotionsDao communityPromotionsDaoImpl;
	
	@Override
	protected BaseDao<CommunityPromotions, Long> getBaseDao() {
		return communityPromotionsDaoImpl;
	}

	public CommunityPromotions findByCommunityNumberMonthlyStatus(String communityNumber,
			CommunityPromotions.MonthlyPaymentStatus monthlyPaymentStatus){
		return communityPromotionsDaoImpl.findByCommunityNumberMonthlyStatus(communityNumber, monthlyPaymentStatus);
	}

}
