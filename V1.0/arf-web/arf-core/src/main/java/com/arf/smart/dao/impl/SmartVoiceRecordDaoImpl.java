package com.arf.smart.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.smart.dao.ISmartVoiceRecordDao;
import com.arf.smart.entity.SmartVoiceRecord;
import com.arf.smart.entity.SmartVoiceRecord.DeviceType;
import com.arf.smart.entity.SmartVoiceRecord.Type;

@Repository("smartVoiceRecordDaoImpl")
public class SmartVoiceRecordDaoImpl extends BaseDaoImpl<SmartVoiceRecord, Long> implements ISmartVoiceRecordDao {

	@Override
	public List<SmartVoiceRecord> findByUserDeviceTypeIdentifyRef(String userName, DeviceType deviceType,
			String identifyRef) {
		StringBuffer sql = new StringBuffer(" from SmartVoiceRecord where userName = :userName and status = :status");
		if (deviceType != null) {
			sql.append(" and deviceType=:deviceType");
		}
		if (identifyRef != null) {
			sql.append(" and identifyRef=:identifyRef");
		}
		sql.append(" order by type asc");
		TypedQuery<SmartVoiceRecord> query = entityManager.createQuery(sql.toString(), SmartVoiceRecord.class);
		query.setParameter("userName", userName);
		query.setParameter("status", SmartVoiceRecord.Status.NORMAL);
		if (deviceType != null) {
			query.setParameter("deviceType", deviceType);
		}
		if (identifyRef != null) {
			query.setParameter("identifyRef", identifyRef);
		}
		return query.getResultList();
	}

	public List<SmartVoiceRecord> findByUserCondition(String userName, DeviceType deviceType, String identifyRef,
			SmartVoiceRecord.Status status, Type type) {
		StringBuffer sql = new StringBuffer(" from SmartVoiceRecord where userName = :userName ");
		if (deviceType != null) {
			sql.append(" and deviceType=:deviceType");
		}
		if (identifyRef != null) {
			sql.append(" and identifyRef=:identifyRef");
		}
		if (status != null) {
			sql.append(" and status =:status");
		}
		if (type != null) {
			sql.append(" and type =:type");
		}
		sql.append(" order by status asc");
		TypedQuery<SmartVoiceRecord> query = entityManager.createQuery(sql.toString(), SmartVoiceRecord.class);
		query.setParameter("userName", userName);
		if (deviceType != null) {
			query.setParameter("deviceType", deviceType);
		}
		if (identifyRef != null) {
			query.setParameter("identifyRef", identifyRef);
		}
		if (status != null) {
			query.setParameter("status", status);
		}
		if (type != null) {
			query.setParameter("type", type);
		}
		return query.getResultList();
	}
	
	public List<SmartVoiceRecord> findByIdentifyRef(String identifyRef,DeviceType deviceType){
		StringBuffer sql = new StringBuffer(" from SmartVoiceRecord where identifyRef=:identifyRef and status = :status ");
		if (deviceType != null) {
			sql.append(" and deviceType=:deviceType");
		}
		TypedQuery<SmartVoiceRecord> query = entityManager.createQuery(sql.toString(), SmartVoiceRecord.class);
		query.setParameter("identifyRef", identifyRef);
		query.setParameter("status", SmartVoiceRecord.Status.NORMAL);
		if(deviceType != null){
			query.setParameter("deviceType", deviceType);
		}
		return query.getResultList();
	}
}
