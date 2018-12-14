package com.arf.propertymgr.dao;

import com.arf.core.dao.BaseDao;
import com.arf.propertymgr.entity.PropertyCommunityConfig;

public interface IPropertyCommunityConfigDao extends BaseDao<PropertyCommunityConfig,Long>{

	PropertyCommunityConfig findSingleModel(String communityNumber);
}
