package com.arf.carbright.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.carbright.dao.IREngineeringBoxSynchInfoDao;
import com.arf.carbright.entity.REngineeringBoxSynchInfo;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("rEngineeringBoxSynchInfoDaoImpl")
public class REngineeringBoxSynchInfoDaoImpl extends BaseDaoImpl<REngineeringBoxSynchInfo, Long> implements IREngineeringBoxSynchInfoDao {

	@Override
	public int statisticsCountByBox(String communityNumber, String cabinetNum, String boxNum) {
		String hql = "select count(1) from REngineeringBoxSynchInfo r where to_days(r.synchDate) = to_days(now()) and r.communityNumber = :communityNumber and r.cabinetNum=:cabinetNum and r.boxNum like '%:boxNum%'";
		Long statisticsCount = this.entityManager.createQuery(hql, Long.class)
		.setParameter("communityNumber",communityNumber)
		.setParameter("cabinetNum", cabinetNum)
		.setParameter("boxNum", boxNum).getSingleResult();
		if(statisticsCount!=null){
			return statisticsCount.intValue();
		}
		return 0;
	}

}
