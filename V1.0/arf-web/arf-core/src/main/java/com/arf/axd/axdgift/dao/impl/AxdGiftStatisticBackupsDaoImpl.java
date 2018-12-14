package com.arf.axd.axdgift.dao.impl;

import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.arf.axd.axdgift.dao.IAxdGiftStatisticBackupsDao;
import com.arf.axd.axdgift.entity.AxdGiftStatisticBackups;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("axdGiftStatisticBackupsDaoImpl")
public class AxdGiftStatisticBackupsDaoImpl extends BaseDaoImpl<AxdGiftStatisticBackups, Long> implements IAxdGiftStatisticBackupsDao {

	@Override
	public AxdGiftStatisticBackups findByKey(String key) {
		if(StringUtils.isBlank(key)){
			return null;
		}
		String hql = "from AxdGiftStatisticBackups where hkey = :hkey";
		TypedQuery<AxdGiftStatisticBackups> query = entityManager.createQuery(hql, AxdGiftStatisticBackups.class);
		query.setParameter("hkey", key);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
}
