package com.arf.eparking.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.eparking.dao.ParkingInfoDao;
import com.arf.eparking.entity.ParkingInfo;

@Repository("parkingInfoDaoImpl")
public class ParkingInfoDaoImpl extends BaseDaoImpl<ParkingInfo, Long> implements ParkingInfoDao {

	@Override
	public ParkingInfo findByParkingNo(String parkingNo) {
		String hql = "from ParkingInfo where parkingNo = :parkingNo";
		List<ParkingInfo> list = entityManager.createQuery(hql,ParkingInfo.class).setParameter("parkingNo", parkingNo).getResultList();
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}
	
}
