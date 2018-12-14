package com.arf.salesman.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.salesman.dao.ISalesmanInfoDao;
import com.arf.salesman.entity.SalesmanInfo;

@Repository("salesmanInfoDaoImpl")
public class SalesmanInfoDaoImpl extends BaseDaoImpl<SalesmanInfo, Long> implements ISalesmanInfoDao {

	@Override
	public SalesmanInfo findByUserName(String userName) {
		String hql = " from SalesmanInfo where userName=:userName";
		TypedQuery<SalesmanInfo> query = entityManager.createQuery(hql, SalesmanInfo.class);
		query.setParameter("userName", userName);
		List<SalesmanInfo> salesmanInfos=null;
		try {
			salesmanInfos = query.getResultList();
		} catch (Exception e) {
			return null;
		}
		return CollectionUtils.isEmpty(salesmanInfos) ? null : salesmanInfos.get(0);
	}
}
