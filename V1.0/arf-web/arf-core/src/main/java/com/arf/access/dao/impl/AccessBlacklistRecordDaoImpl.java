package com.arf.access.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.arf.access.dao.IAccessBlacklistRecordDao;
import com.arf.access.entity.AccessBlacklistRecord;
import com.arf.access.entity.AccessBlacklistRecord.Status;
import com.arf.access.entity.AccessGuestRecord.GuestType;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("accessBlacklistRecordDaoImpl")
public class AccessBlacklistRecordDaoImpl extends
		BaseDaoImpl<AccessBlacklistRecord, Long> implements
		IAccessBlacklistRecordDao {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public AccessBlacklistRecord findByGuestUsernameOprateUsername(String guestIdentifyId,
			GuestType guestType, String userName,String oprateUsername) {
		StringBuffer hql = new StringBuffer("from AccessBlacklistRecord where 1 = 1");
		hql.append(" and guestIdentifyId = :guestIdentifyId and guestType = :guestType");
		hql.append(" and userName = :userName");
		if(StringUtils.isNotBlank(oprateUsername)){
			hql.append(" and oprateUsername = :oprateUsername");
		}
		TypedQuery<AccessBlacklistRecord> query = this.entityManager.createQuery(hql.toString(), AccessBlacklistRecord.class);
		query.setParameter("guestIdentifyId", guestIdentifyId);
		query.setParameter("guestType", guestType);
		query.setParameter("userName", userName);
		if(StringUtils.isNotBlank(oprateUsername)){
			query.setParameter("oprateUsername", oprateUsername);
		}
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<AccessBlacklistRecord> findByUsernameStatusOprateUsername(String userName,
			Status status,String oprateUsername) {
		StringBuffer hql = new StringBuffer("from AccessBlacklistRecord where 1 = 1");
		hql.append(" and status = :status");
		if(StringUtils.isNotBlank(userName)){
			hql.append(" and userName = :userName");
		}
		if(StringUtils.isNotBlank(oprateUsername)){
			hql.append(" and oprateUsername = :oprateUsername");
		}
		TypedQuery<AccessBlacklistRecord> query = this.entityManager.createQuery(hql.toString(), AccessBlacklistRecord.class);
		query.setParameter("status", status);
		if(StringUtils.isNotBlank(userName)){
			query.setParameter("userName", userName);
		}
		if(StringUtils.isNotBlank(oprateUsername)){
			query.setParameter("oprateUsername", oprateUsername);
		}
		return query.getResultList();
	}

	

}
