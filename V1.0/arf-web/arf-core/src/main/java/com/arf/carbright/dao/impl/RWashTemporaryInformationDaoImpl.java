package com.arf.carbright.dao.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.arf.carbright.dao.IRWashTemporaryInformationDao;
import com.arf.carbright.entity.RWashTemporaryInformation;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("rWashTemporaryInformationDaoImpl")
public class RWashTemporaryInformationDaoImpl extends BaseDaoImpl<RWashTemporaryInformation, Long> implements IRWashTemporaryInformationDao{

	@Override
	public List<RWashTemporaryInformation> findTemporaryUserbyCommunity(String communityNumber,String userName) {
		String hql = "from RWashTemporaryInformation r where r.userName=:userName and r.operatingDate<now() and r.effectiveDate>now()";
		if(StringUtils.isNotBlank(communityNumber)){//小区信息不能与空查询全部车牌
			hql=hql.concat(" and r.communityNumber = :communityNumber");
		}
		TypedQuery<RWashTemporaryInformation> query=this.entityManager.createQuery(hql, RWashTemporaryInformation.class);
		if(StringUtils.isNotBlank(communityNumber)){
			query.setParameter("communityNumber", communityNumber);
		}
		query.setParameter("userName", userName);		
		return query.getResultList();
	}

}
