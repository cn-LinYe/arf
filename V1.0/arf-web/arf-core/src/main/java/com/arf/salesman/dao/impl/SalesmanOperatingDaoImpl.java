package com.arf.salesman.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.salesman.dao.ISalesmanOperatingDao;
import com.arf.salesman.entity.SalemanOperating;

@Repository("salesmanOperatingDaoImpl")
public class SalesmanOperatingDaoImpl extends BaseDaoImpl<SalemanOperating, Long> implements ISalesmanOperatingDao{

	@Override
	public SalemanOperating findByCommunity(String comuunityNum) {
		String hql ="from SalemanOperating where communityNumber=:comuunityNum";
		TypedQuery<SalemanOperating> query =entityManager.createQuery(hql,SalemanOperating.class).setParameter("comuunityNum", comuunityNum);
		List<SalemanOperating> salemanOperatings=query.getResultList();
		return CollectionUtils.isEmpty(salemanOperatings)?null:salemanOperatings.get(0);
	}
}
