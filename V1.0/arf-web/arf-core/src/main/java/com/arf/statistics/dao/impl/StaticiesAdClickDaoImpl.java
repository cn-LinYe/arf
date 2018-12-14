package com.arf.statistics.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.statistics.dao.IStaticiesAdClickDao;
import com.arf.statistics.entity.StaticiesAdClick;
import com.arf.statistics.entity.StaticiesAdClick.ClickType;

@Repository("staticiesAdClickDao")
public class StaticiesAdClickDaoImpl extends BaseDaoImpl<StaticiesAdClick, Long> implements IStaticiesAdClickDao{

	@Override
	public StaticiesAdClick findByUserNameAndClickType(ClickType clickType, String userName,String accessMac,String adNum, String license
			, Date startDate, Date endDate) {
		StringBuffer hql = new StringBuffer("select sad from StaticiesAdClick sad where sad.clickType = :clickType ");
		hql.append(" and sad.clickDate <=:endDate");
		hql.append(" and sad.clickDate >=:startDate");
		if(StringUtils.isNotBlank(userName)){
			hql.append(" and sad.userName=:userName");
		}
		if(StringUtils.isNotBlank(accessMac)){
			hql.append(" and sad.accessMac=:accessMac");
		}
		if(StringUtils.isNotBlank(adNum)){
			hql.append(" and sad.adNum=:adNum");
		}
		if(StringUtils.isNotBlank(license)){
			hql.append(" and sad.license=:license");
		}
		TypedQuery<StaticiesAdClick> typedQuery = super.entityManager.createQuery(hql.toString(), StaticiesAdClick.class);
		
		typedQuery.setParameter("clickType", clickType);
		typedQuery.setParameter("endDate", endDate);
		typedQuery.setParameter("startDate", startDate);
		if(StringUtils.isNotBlank(userName)){
			typedQuery.setParameter("userName", userName);
		}
		if(StringUtils.isNotBlank(accessMac)){
			typedQuery.setParameter("accessMac", accessMac);
		}
		if(StringUtils.isNotBlank(adNum)){
			typedQuery.setParameter("adNum", adNum);
		}
		if(StringUtils.isNotBlank(license)){
			typedQuery.setParameter("license", license);
		}
		
		List<StaticiesAdClick> list = typedQuery.getResultList();
		return list.isEmpty()?null:list.get(0);
	}

}
