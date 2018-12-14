package com.arf.access.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Repository;

import com.arf.access.dao.IAccessUnlockLogDao;
import com.arf.access.entity.AccessUnlockLog;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("accessUnlockLogDao")
public class AccessUnlockLogDaoImpl extends BaseDaoImpl<AccessUnlockLog, Long> implements IAccessUnlockLogDao {

	@Override
	public List<AccessUnlockLog> findByUserNameAndtimeGetoneorallSuccess(
			String userName, Date time, boolean getOne) {
		Date startTime = null;
		Date endTime = null;
		try {
			startTime = org.apache.commons.lang.time.DateUtils.parseDate(
					DateFormatUtils.format(time, "yyyy-MM-dd") + " 00:00:00", 
					new String[]{"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd"});
			endTime = org.apache.commons.lang.time.DateUtils.parseDate(
					DateFormatUtils.format(time, "yyyy-MM-dd") + " 23:59:59", 
					new String[]{"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd"});
		} catch (Exception e) {
			
		}
		StringBuffer hql = new StringBuffer("from AccessUnlockLog a where 1 = 1 ");
		hql.append(" and a.userName = :userName");
		hql.append(" and a.createDate > :startTime");
		hql.append(" and a.createDate < :endTime");
		hql.append(" and a.unlockResp = :unlockResp");
		hql.append(" and a.unlockDevice = :unlockDevice");
		hql.append(" order by a.createDate desc");
		TypedQuery<AccessUnlockLog> typedQuery = entityManager.createQuery(hql.toString(), AccessUnlockLog.class);
		typedQuery.setParameter("userName", userName);
		typedQuery.setParameter("startTime", startTime);
		typedQuery.setParameter("endTime", endTime);
		typedQuery.setParameter("unlockResp", "开门成功");
		typedQuery.setParameter("unlockDevice", AccessUnlockLog.UnlockDevice.APP_OFFICE);
		if(getOne){
			List<AccessUnlockLog> accessUnlockLogList = new ArrayList<AccessUnlockLog>();
			if(CollectionUtils.isNotEmpty(typedQuery.getResultList())){
				accessUnlockLogList.add(typedQuery.getResultList().get(0));
			}
			return accessUnlockLogList;
		}else{
			return typedQuery.getResultList();
		}
	}

	@Override
	public List<AccessUnlockLog> findByStartDateEndDate(Date startTime,
			Date endTime) {
		StringBuffer hql = new StringBuffer("from AccessUnlockLog a where 1 = 1 ");
		hql.append(" and a.createDate >= :startTime");
		hql.append(" and a.createDate <= :endTime");
		hql.append(" order by a.communityNumber,a.createDate");
		TypedQuery<AccessUnlockLog> typedQuery = entityManager.createQuery(hql.toString(), AccessUnlockLog.class);
		typedQuery.setParameter("startTime", startTime);
		typedQuery.setParameter("endTime", endTime);
		return typedQuery.getResultList();
	}


}
