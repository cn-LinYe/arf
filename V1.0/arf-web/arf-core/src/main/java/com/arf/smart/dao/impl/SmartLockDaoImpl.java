package com.arf.smart.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.smart.dao.ISmartLockDao;
import com.arf.smart.entity.Smartlock;

@Repository("smartLockDaoImpl")
public class SmartLockDaoImpl extends BaseDaoImpl<Smartlock, Long> implements ISmartLockDao{

	@Override
	public List<Map<String, Object>> getLocksByUser(String userName) {
		StringBuffer sql = new StringBuffer(" select slu.id,slu.type,slu.accreditType,slu.accreditStart,slu.accreditEnd,slu.status,");
		
		sql.append(" slu.createTime,slu.communicateCode,sl.sn,sl.battery,sl.lastSynchTime,sl.productModel,sl.firmwareVersion,sl.lockName,");
		sql.append(" sl.bluetoothMac,sl.certificateCode,sl.managerPassword,sl.tempPassword,sl.unlockPassword,sl.versionKey,sl.supplierType,");
		sql.append(" sl.gatewayAddress,sl.lockMode,sl.identifier  from");
		sql.append(" lock_smartlock_users slu left join lock_smartlock sl on slu.smartlockId = sl.id	left join lock_user lu on slu.userId = lu.id ");
		sql.append(" where lu.userName =:user_name and slu.status = '0' ");
		
		Query query =entityManager.createNativeQuery(sql.toString());
		if (userName!=null) {
			 query.setParameter("user_name", userName);			 
		}
		
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> locksList =query.getResultList();		
		return locksList;
	}

}
