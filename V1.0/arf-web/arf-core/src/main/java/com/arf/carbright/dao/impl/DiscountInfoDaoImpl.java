package com.arf.carbright.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.arf.carbright.dao.IDiscountInfoDao;
import com.arf.carbright.entity.DiscountInfo;
import com.arf.carbright.entity.DiscountInfo.Status;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("discountInfoDaoImpl")
public class DiscountInfoDaoImpl extends BaseDaoImpl<DiscountInfo, Long> implements IDiscountInfoDao{

	@Override
	public DiscountInfo findByBusinessNum(Integer businessNum, Status status) {
		String hql ="from DiscountInfo where businessNum=:businessNum and status=:status";
		TypedQuery<DiscountInfo> query =this.entityManager.createQuery(hql,DiscountInfo.class);
		query.setParameter("businessNum", businessNum);
		query.setParameter("status", status);
		List<DiscountInfo> list=query.getResultList();
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}
}
