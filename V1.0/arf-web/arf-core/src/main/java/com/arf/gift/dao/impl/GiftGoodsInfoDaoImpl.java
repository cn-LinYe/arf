package com.arf.gift.dao.impl;

import java.util.Arrays;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.gift.dao.IGiftGoodsInfoDao;
import com.arf.gift.entity.GiftGoodsInfo;

@Repository("giftGoodsInfoDao")
public class GiftGoodsInfoDaoImpl extends BaseDaoImpl<GiftGoodsInfo, Long> implements IGiftGoodsInfoDao{

	@Override
	public List<GiftGoodsInfo> findAllExchangableGift(String communityNumber) {
		StringBuffer sql = new StringBuffer("from GiftGoodsInfo g where status=:status and stock > 0 and ((communityNumber is null or communityNumber='' or communityNumber='0') ");
		if(communityNumber != null && communityNumber.length() > 0){
			sql.append(" OR find_in_set(:communityNumber,communityNumber) > 0 ");
		}
		sql.append(") ");
		
		TypedQuery<GiftGoodsInfo> query = this.entityManager.createQuery(sql.toString(), GiftGoodsInfo.class);
		query.setParameter("status", GiftGoodsInfo.Status.NORMAL);
		if(communityNumber != null && communityNumber.length() > 0){
			query.setParameter("communityNumber", Arrays.asList(communityNumber));
		}
		return query.getResultList();
	}

}
