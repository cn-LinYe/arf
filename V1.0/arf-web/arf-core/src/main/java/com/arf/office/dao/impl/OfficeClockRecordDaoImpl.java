package com.arf.office.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.office.dao.IOfficeClockRecordDao;
import com.arf.office.entity.OfficeClockRecord;
import com.arf.office.entity.OfficeCompanyEquipmentRelation;

@Repository("officeClockRecordDaoImpl")
public class OfficeClockRecordDaoImpl extends
		BaseDaoImpl<OfficeClockRecord, Long> implements IOfficeClockRecordDao {

	@Override
	public OfficeClockRecord findByUsernameCompanyNumberAndtime(
			String companyNumber, String userName, Date now) {
		Date startTime = null;
		Date endTime = null;
		try {
			startTime = org.apache.commons.lang.time.DateUtils.parseDate(
					DateFormatUtils.format(now, "yyyy-MM-dd") + " 00:00:00", 
					new String[]{"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd"});
			endTime = org.apache.commons.lang.time.DateUtils.parseDate(
					DateFormatUtils.format(now, "yyyy-MM-dd") + " 23:59:59", 
					new String[]{"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd"});
		} catch (Exception e) {
			
		}
		StringBuffer hql = new StringBuffer("from OfficeClockRecord a where 1 = 1 ");
		hql.append(" and a.companyNumber = :companyNumber");
		hql.append(" and a.userName = :userName");
		hql.append(" and a.clockDate > :startTime");
		hql.append(" and a.clockDate < :endTime");
		TypedQuery<OfficeClockRecord> typedQuery = entityManager.createQuery(hql.toString(), OfficeClockRecord.class);
		typedQuery.setParameter("companyNumber", companyNumber);
		typedQuery.setParameter("userName", userName);
		typedQuery.setParameter("startTime", startTime);
		typedQuery.setParameter("endTime", endTime);
		try {
			return typedQuery.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<OfficeClockRecord> findByIdentifierAndtime(
			String equipmentIdentifier, Date date) {
		Date startTime = null;
		Date endTime = null;
		try {
			startTime = org.apache.commons.lang.time.DateUtils.parseDate(
					DateFormatUtils.format(date, "yyyy-MM-dd") + " 00:00:00", 
					new String[]{"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd"});
			endTime = org.apache.commons.lang.time.DateUtils.parseDate(
					DateFormatUtils.format(date, "yyyy-MM-dd") + " 23:59:59", 
					new String[]{"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd"});
		} catch (Exception e) {
			
		}
		StringBuffer hql = new StringBuffer("from OfficeClockRecord a where 1 = 1 ");
		hql.append(" and a.equipmentIdentifier = :equipmentIdentifier");
		hql.append(" and a.clockDate > :startTime");
		hql.append(" and a.clockDate < :endTime");
		TypedQuery<OfficeClockRecord> typedQuery = entityManager.createQuery(hql.toString(), OfficeClockRecord.class);
		typedQuery.setParameter("equipmentIdentifier", equipmentIdentifier);
		typedQuery.setParameter("startTime", startTime);
		typedQuery.setParameter("endTime", endTime);
		return typedQuery.getResultList();
	}

}
