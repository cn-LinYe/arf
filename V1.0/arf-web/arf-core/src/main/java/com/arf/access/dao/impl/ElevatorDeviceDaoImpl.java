package com.arf.access.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.arf.access.dao.IElevatorDeviceDao;
import com.arf.access.entity.ElevatorDevice;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("elevatorDeviceDao")
public class ElevatorDeviceDaoImpl extends BaseDaoImpl<ElevatorDevice, Long> implements IElevatorDeviceDao {

	@Override
	public List<ElevatorDevice> findBybuildUnitAndCommunity(String building, String unit, String communityNumber) {
		StringBuffer sb = new StringBuffer("from ElevatorDevice where 1=1");
		sb.append(" and building = :building");
		sb.append(" and unit = :unit");
		sb.append(" and communityNumber = :communityNumber");
		TypedQuery<ElevatorDevice> query = this.entityManager.createQuery(sb.toString(), ElevatorDevice.class);
		query.setParameter("building", building);
		query.setParameter("unit", unit);
		query.setParameter("communityNumber", communityNumber);
		return query.getResultList();
	}

	@Override
	public List<ElevatorDevice> findBybuildUnitAndCommunity(List<String> buildings, List<String> units,
			List<String> communityNumbers) {
		if(CollectionUtils.isEmpty(communityNumbers)){
			return null;
		}
		StringBuffer sb = new StringBuffer("from ElevatorDevice where 1=1");
		sb.append(" and communityNumber in (:communityNumbers)");
		if(CollectionUtils.isNotEmpty(buildings)){
			sb.append(" and building in (:buildings)");
		}
		if(CollectionUtils.isNotEmpty(units)){
			sb.append(" and unit in (:units)");
		}
		
		TypedQuery<ElevatorDevice> query = this.entityManager.createQuery(sb.toString(), ElevatorDevice.class);
		query.setParameter("communityNumbers", communityNumbers);
		
		if(CollectionUtils.isNotEmpty(buildings)){
			query.setParameter("buildings", buildings);
		}
		if(CollectionUtils.isNotEmpty(units)){
			query.setParameter("units", units);
		}
		return query.getResultList();
	}

}
