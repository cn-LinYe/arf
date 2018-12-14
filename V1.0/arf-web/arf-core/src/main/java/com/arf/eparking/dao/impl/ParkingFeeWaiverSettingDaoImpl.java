package com.arf.eparking.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.eparking.dao.IParkingFeeWaiverSettingDao;
import com.arf.eparking.entity.ParkingFeeWaiverSetting;

@Repository("parkingFeeWaiverSettingDao")
public class ParkingFeeWaiverSettingDaoImpl extends BaseDaoImpl<ParkingFeeWaiverSetting, Long> implements IParkingFeeWaiverSettingDao{

	@Override
	public ParkingFeeWaiverSetting findByBusinessNum(String businessNum) {
		StringBuffer hql = new StringBuffer("from ParkingFeeWaiverSetting where businessNum = :businessNum");
		TypedQuery<ParkingFeeWaiverSetting> query = entityManager.createQuery(hql.toString(),ParkingFeeWaiverSetting.class);
		query.setParameter("businessNum", businessNum);
		List<ParkingFeeWaiverSetting> list = query.getResultList();
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}

}
