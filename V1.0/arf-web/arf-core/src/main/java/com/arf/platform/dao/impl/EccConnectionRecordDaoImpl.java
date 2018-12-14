package com.arf.platform.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.platform.dao.IEccConnectionRecordDao;
import com.arf.platform.entity.EccConnectionRecord;

@Repository("eccConnectionRecordDaoImpl")
public class EccConnectionRecordDaoImpl extends BaseDaoImpl<EccConnectionRecord,Long> implements IEccConnectionRecordDao{

	@Override
	public EccConnectionRecord findLastOfflineRecord(String communityNumber) {
		String hql = "from EccConnectionRecord where communityNo = :communityNo and offlineTime is not null order by offlineTime desc";
		TypedQuery<EccConnectionRecord> query = entityManager.createQuery(hql,EccConnectionRecord.class);
		query.setParameter("communityNo", communityNumber);
		List<EccConnectionRecord> list = query.getResultList();
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}

	@Override
	public EccConnectionRecord findLastOnlineRecord(String communityNumber) {
		String hql = "from EccConnectionRecord where communityNo = :communityNo and offlineTime is null order by onlineTime desc";
		TypedQuery<EccConnectionRecord> query = entityManager.createQuery(hql,EccConnectionRecord.class);
		query.setParameter("communityNo", communityNumber);
		List<EccConnectionRecord> list = query.getResultList();
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public EccConnectionRecord findLastRecord(String communityNumber) {
		String hql = "from EccConnectionRecord where communityNo = :communityNo order by onlineTime desc";
		TypedQuery<EccConnectionRecord> query = entityManager.createQuery(hql,EccConnectionRecord.class);
		query.setParameter("communityNo", communityNumber);
		List<EccConnectionRecord> list = query.getResultList();
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}
	
}
