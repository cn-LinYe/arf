package com.arf.gift.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.gift.dao.CommunityPromotionsDao;
import com.arf.gift.entity.CommunityPromotions;

@Repository("communityPromotionsDaoImpl")
public class CommunityPromotionsDaoImpl extends BaseDaoImpl<CommunityPromotions, Long>
		implements CommunityPromotionsDao {

	/**
	 * 按小区、月租缴费活动状态查询
	 * @param communityNumber
	 * @return
	 */
	@Override
	public CommunityPromotions findByCommunityNumberMonthlyStatus(String communityNumber,
			CommunityPromotions.MonthlyPaymentStatus monthlyPaymentStatus) {
		if(communityNumber == null || monthlyPaymentStatus == null){
			return null;
		}
		String hql = "from CommunityPromotions where communityNumber =:communityNumber and monthlyPaymentStatus =:monthlyPaymentStatus";
		List<CommunityPromotions> list = entityManager.createQuery(hql,CommunityPromotions.class)
				.setParameter("communityNumber", communityNumber)
				.setParameter("monthlyPaymentStatus", monthlyPaymentStatus.ordinal())
				.getResultList();
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}

}
