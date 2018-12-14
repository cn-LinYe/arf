package com.arf.axd.axdgift.dao.impl;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.axd.axdgift.dao.IAxdGiftConfigDao;
import com.arf.axd.axdgift.entity.AxdGiftConfig;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("axdGiftConfigDaoImpl")
public class AxdGiftConfigDaoImpl extends BaseDaoImpl<AxdGiftConfig, Long> implements IAxdGiftConfigDao  {

	@Override
	public AxdGiftConfig findByCommunityNumber(String communityNumber) {
		String hql = "from com.arf.axd.axdgift.entity.AxdGiftConfig where communityNo = :communityNo";
		TypedQuery<AxdGiftConfig> query = entityManager.createQuery(hql, AxdGiftConfig.class);
		query.setParameter("communityNo", communityNumber);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

}
