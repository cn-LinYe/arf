package com.arf.access.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.arf.access.dao.IAccessInitialSecretkeyDao;
import com.arf.access.entity.AccessInitialSecretkey;
import com.arf.access.entity.AccessInterceptPropFeeConf;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("accessInitialSecretkeyDaoImpl")
public class AccessInitialSecretkeyDaoImpl extends BaseDaoImpl<AccessInitialSecretkey, Long> implements IAccessInitialSecretkeyDao{

	@Override
	public List<AccessInitialSecretkey> findBybluetoothMac(String bluetoothMac){
		StringBuffer hql = new StringBuffer(" from AccessInitialSecretkey where bluetoothMac =:bluetoothMac");
		TypedQuery<AccessInitialSecretkey> query = this.entityManager.createQuery(hql.toString(), AccessInitialSecretkey.class);
		query.setParameter("bluetoothMac", bluetoothMac);
		return query.getResultList();
	}
	
	@Override
	public List<AccessInitialSecretkey> findByCommunityNumber(String communityNumber){
		StringBuffer hql = new StringBuffer(" from AccessInitialSecretkey where communityNumber =:communityNumber");
		hql.append(" order by region");
		TypedQuery<AccessInitialSecretkey> query = this.entityManager.createQuery(hql.toString(), AccessInitialSecretkey.class);
		query.setParameter("communityNumber", communityNumber);
		return query.getResultList();
	}
	
	@Override
	public AccessInitialSecretkey findByCondition(String communityNumber, String building, int region) {
		StringBuffer hql = new StringBuffer("from AccessInitialSecretkey where 1 = 1");
		hql.append(" and communityNumber = :communityNumber");
		if (StringUtils.isNotBlank(building)) {
			hql.append(" and building = :building");
		}
		hql.append(" and region = :region");
		TypedQuery<AccessInitialSecretkey> query = this.entityManager.createQuery(hql.toString(), AccessInitialSecretkey.class);
		query.setParameter("communityNumber", communityNumber);
		if (StringUtils.isNotBlank(building)) {
			query.setParameter("building", building);
		}
		query.setParameter("region", region);
		try {
			List<AccessInitialSecretkey> list=query.getResultList();
			return list.isEmpty()?null:list.get(0);
		} catch (Exception e) {
			return null;
		}
	}
}
