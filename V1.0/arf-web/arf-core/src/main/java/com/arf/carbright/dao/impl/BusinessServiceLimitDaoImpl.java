package com.arf.carbright.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.arf.carbright.dao.IBusinessServiceLimitDao;
import com.arf.carbright.entity.BusinessServiceLimit;
import com.arf.core.dao.impl.BaseDaoImpl;
@Repository("businessServiceLimitDaoImpl")
public class BusinessServiceLimitDaoImpl extends BaseDaoImpl<BusinessServiceLimit,Long>implements IBusinessServiceLimitDao{

	@Override
	public BusinessServiceLimit findByBusinessNum(Integer businessNum) {
		String hql="from BusinessServiceLimit where businessNum =:businessNum";
		TypedQuery<BusinessServiceLimit> query = entityManager.createQuery(hql,BusinessServiceLimit.class);
		query.setParameter("businessNum", businessNum);
		List<BusinessServiceLimit> businessServiceLimits=query.getResultList();
		return CollectionUtils.isEmpty(businessServiceLimits)?null:businessServiceLimits.get(0);
	}
}
