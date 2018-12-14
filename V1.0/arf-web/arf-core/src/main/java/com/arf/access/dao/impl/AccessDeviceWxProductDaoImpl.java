package com.arf.access.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.arf.access.dao.IAccessDeviceWxProductDao;
import com.arf.access.entity.AccessDeviceWxProduct;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("accessDeviceWxProductDaoImpl")
public class AccessDeviceWxProductDaoImpl extends
		BaseDaoImpl<AccessDeviceWxProduct, Long> implements
		IAccessDeviceWxProductDao {

	@Override
	public AccessDeviceWxProduct findByAccessNum(String accessNum) {
		StringBuffer hql = new StringBuffer("from AccessDeviceWxProduct where accessNum = :accessNum");
		TypedQuery<AccessDeviceWxProduct> typedQuery = super.entityManager.createQuery(hql.toString(), AccessDeviceWxProduct.class);
		typedQuery.setParameter("accessNum", accessNum);
		List<AccessDeviceWxProduct> accessDeviceWxProductList = typedQuery.getResultList();
		if(CollectionUtils.isNotEmpty(accessDeviceWxProductList)){
			return accessDeviceWxProductList.get(0);
		}else{
			return null;
		}
	}

	@Override
	public AccessDeviceWxProduct findByDeviceId(String deviceId) {
		StringBuffer hql = new StringBuffer("from AccessDeviceWxProduct where deviceId = :deviceId");
		TypedQuery<AccessDeviceWxProduct> typedQuery = super.entityManager.createQuery(hql.toString(), AccessDeviceWxProduct.class);
		typedQuery.setParameter("deviceId", deviceId);
		List<AccessDeviceWxProduct> accessDeviceWxProductList = typedQuery.getResultList();
		if(CollectionUtils.isNotEmpty(accessDeviceWxProductList)){
			return accessDeviceWxProductList.get(0);
		}else{
			return null;
		}
	}

}
