package com.arf.statistics.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.statistics.dao.IStatisticsAxdClickDao;
import com.arf.statistics.entity.StatisticsAxdClick;

@Repository("statisticsAxdClickDaoImpl")
public class StatisticsAxdClickDaoImpl extends BaseDaoImpl<StatisticsAxdClick, Long> implements IStatisticsAxdClickDao{

	@Override
	public StatisticsAxdClick findByUserNameDay(String communityNumber, String day, String userName) {
		StringBuffer sb =new StringBuffer("from StatisticsAxdClick s where s.userName =:userName and s.day=:day");
		if (StringUtils.isNotBlank(communityNumber)) {
			sb.append(" and s.communityNumber=:communityNumber");
		}
		TypedQuery<StatisticsAxdClick> query =entityManager.createQuery(sb.toString(),StatisticsAxdClick.class);
		query.setParameter("userName", userName);
		query.setParameter("day", day);
		if (StringUtils.isNotBlank(communityNumber)) {
			query.setParameter("communityNumber", communityNumber);
		}
		List<StatisticsAxdClick>click =query.getResultList();
		return CollectionUtils.isEmpty(click)?null:click.get(0);
	}

}
