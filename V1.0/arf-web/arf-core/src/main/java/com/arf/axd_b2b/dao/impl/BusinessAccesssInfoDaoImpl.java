package com.arf.axd_b2b.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.arf.axd_b2b.dao.IBusinessAccesssInfoDao;
import com.arf.axd_b2b.entity.BusinessAccesssInfo;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("businessAccesssInfoDao")
public class BusinessAccesssInfoDaoImpl extends BaseDaoImpl<BusinessAccesssInfo, Long>
implements IBusinessAccesssInfoDao {

	@Override
	public BusinessAccesssInfo findNearest(Double lat, Double lng) {
		if(lat == null || lng == null){
			return null;
		}
		String sql = " from BusinessAccesssInfo "
					+" where 1=1 and status=:status and lat is not null and lng is not null "
					+" order by  "
					+" round(6378.138*2*asin(sqrt(pow(sin( (:lat*pi()/180-lat*pi()/180)/2),2)+cos(:lat*pi()/180)*cos(lat*pi()/180) "
					+" * pow(sin( (:lng*pi()/180-lng*pi()/180)/2),2)))*1000) "
					+" ASC ";
		Query query = this.entityManager.createQuery(sql,BusinessAccesssInfo.class);
		query.setParameter("status", BusinessAccesssInfo.Status.ONLINE);
		query.setParameter("lat", lat);
		query.setParameter("lng", lng);
		query.setMaxResults(1);
		List<BusinessAccesssInfo> list = query.getResultList();
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	@Override
	public BusinessAccesssInfo findByBusinessNum(String businessNum) {
		if(StringUtils.isBlank(businessNum)){
			return null;
		}
		String sql = " from BusinessAccesssInfo "
					+" where 1=1"
					+" and businessNum = :businessNum ";
		Query query = this.entityManager.createQuery(sql,BusinessAccesssInfo.class);
		query.setParameter("businessNum", businessNum);
		List<BusinessAccesssInfo> list = query.getResultList();
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}
}
