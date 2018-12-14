package com.arf.propertymgr.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.propertymgr.dao.IPropertyCommunityConfigDao;
import com.arf.propertymgr.entity.PropertyCommunityConfig;

@Repository("propertyCommunityConfigDaoImpl")
public class PropertyCommunityConfigDaoImpl extends BaseDaoImpl<PropertyCommunityConfig,Long> implements IPropertyCommunityConfigDao {

	public PropertyCommunityConfig findSingleModel(String communityNumber){
		if(StringUtils.isEmpty(communityNumber)){
			return null;
		}
		String hql = " from PropertyCommunityConfig where 1=1 and communityNumber =:communityNumber";
		TypedQuery<PropertyCommunityConfig> query = this.entityManager.createQuery(hql, PropertyCommunityConfig.class);
		query.setParameter("communityNumber", communityNumber);
		List<PropertyCommunityConfig> resultList = query.getResultList();
		if(CollectionUtils.isNotEmpty(resultList)){
			return resultList.get(0);
		}
		return null;
	}
}
