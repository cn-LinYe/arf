package com.arf.advertisements.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.advertisements.dao.AdvertisementsDao;
import com.arf.advertisements.entity.Advertisements;
import com.arf.advertisements.entity.Advertisements.AdStatus;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("advertisementsDao")
public class AdvertisementsDaoImpl extends BaseDaoImpl<Advertisements, Long> implements AdvertisementsDao{

	@Override
	public List<Advertisements> findByStatus(AdStatus status) {
		StringBuffer hql = new StringBuffer("select ad from Advertisements ad where ad.adStatus = :status");
		hql.append(" and ad.adStartDate <=:nowDate");
		hql.append(" and ad.adEndDate >=:nowDate");
		/*hql.append(" and (ad.auditStatus = 1 or ad.auditStatus is null)");*/
		hql.append(" order by ad.adOrder");
		TypedQuery<Advertisements> typedQuery = super.entityManager.createQuery(hql.toString(), Advertisements.class);
		
		typedQuery.setParameter("status", status);
		typedQuery.setParameter("nowDate", new Date());
		
		List<Advertisements> list = typedQuery.getResultList();
		return list;
	}

}
