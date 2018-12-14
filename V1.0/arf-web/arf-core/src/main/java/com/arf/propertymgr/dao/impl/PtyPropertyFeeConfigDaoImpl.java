package com.arf.propertymgr.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.propertymgr.dao.IPtyPropertyFeeConfigDao;
import com.arf.propertymgr.entity.PtyPropertyFeeConfig;

@Repository("ptyPropertyFeeConfigDaoImpl")
public class PtyPropertyFeeConfigDaoImpl extends BaseDaoImpl<PtyPropertyFeeConfig, Long> implements IPtyPropertyFeeConfigDao{

	@Override
	public List<PtyPropertyFeeConfig> findCommunity() {
		StringBuffer sb =new StringBuffer("from PtyPropertyFeeConfig p where p.status =:status");
		TypedQuery<PtyPropertyFeeConfig> query =entityManager.createQuery(sb.toString(),PtyPropertyFeeConfig.class);
		query.setParameter("status", PtyPropertyFeeConfig.Status.open);
		try {
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
