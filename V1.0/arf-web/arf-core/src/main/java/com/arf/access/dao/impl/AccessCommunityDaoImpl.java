package com.arf.access.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.access.dao.IAccessCommunityDao;
import com.arf.access.entity.AccessCommunity;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("accessCommunityDao")
public class AccessCommunityDaoImpl extends BaseDaoImpl<AccessCommunity, Long> implements IAccessCommunityDao{

	@Override
	public AccessCommunity findByCommunityNumber(String communityNumber) {
		StringBuffer sql =new StringBuffer(" from AccessCommunity where communityNumber=:communityNumber ");
		TypedQuery<AccessCommunity> query = entityManager.createQuery(sql.toString(),AccessCommunity.class);
		query.setParameter("communityNumber", communityNumber);
		List<AccessCommunity> list ;
		try {
			 list = query.getResultList();
		} catch (Exception e) {
			return null;
		}
		return list.isEmpty()?null:list.get(0);
	}

	@Override
	public List<AccessCommunity> findByCommunityList(List<String> communityList) {
		StringBuffer sql =new StringBuffer(" from AccessCommunity where communityNumber in(:communityList) ");
		TypedQuery<AccessCommunity> query = entityManager.createQuery(sql.toString(),AccessCommunity.class);
		query.setParameter("communityList", communityList);
		List<AccessCommunity> list ;
		try {
			 list = query.getResultList();
		} catch (Exception e) {
			return null;
		}
		return list;
	}

}
