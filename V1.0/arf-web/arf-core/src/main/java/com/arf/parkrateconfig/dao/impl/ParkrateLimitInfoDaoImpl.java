package com.arf.parkrateconfig.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.parkrateconfig.dao.IParkrateLimitInfoDao;
import com.arf.parkrateconfig.entity.ParkrateLimitInfo;

@Repository("parkrateLimitInfoDaoImpl")
public class ParkrateLimitInfoDaoImpl extends BaseDaoImpl<ParkrateLimitInfo, Long> implements IParkrateLimitInfoDao{

	@Override
	public ParkrateLimitInfo findByCommunityNumber(String communityNumber) {
		String hql = "from ParkrateLimitInfo p where 1=1";
		if(communityNumber!=null){			
			hql=hql.concat(" and p.communityNumber=:communityNumber and p.isLimitMonth="+ParkrateLimitInfo.IsLimitMonth.Open.ordinal());
		}
		TypedQuery<ParkrateLimitInfo> query = entityManager.createQuery(hql, ParkrateLimitInfo.class);		
		if(communityNumber!=null){			
			query.setParameter("communityNumber", communityNumber);
		}
		List<ParkrateLimitInfo> list =query.getResultList();
		return (CollectionUtils.isEmpty(list))?null:list.get(0);
	}

}
