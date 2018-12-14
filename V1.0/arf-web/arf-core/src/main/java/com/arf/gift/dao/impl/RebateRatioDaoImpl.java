package com.arf.gift.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.gift.dao.RebateRatioDao;
import com.arf.gift.entity.RebateRatio;

@Repository("rebateRatioDaoImpl")
public class RebateRatioDaoImpl extends BaseDaoImpl<RebateRatio, Long> implements RebateRatioDao {
	
	@Override
	public RebateRatio findByCommunityNumber(String communityNumber,RebateRatio.RebateType rebateType){
		if(communityNumber == null){
			return null;
		}
		String hql = " from RebateRatio where communityNumber=:communityNumber and rebateType =:rebateType";
		List<RebateRatio> list = entityManager.createQuery(hql,RebateRatio.class)
				.setParameter("communityNumber", communityNumber)
				.setParameter("rebateType", rebateType)
				.getResultList();
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}

}
