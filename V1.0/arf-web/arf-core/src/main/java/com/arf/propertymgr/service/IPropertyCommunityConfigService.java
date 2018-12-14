package com.arf.propertymgr.service;

import com.arf.core.service.BaseService;
import com.arf.propertymgr.entity.PropertyCommunityConfig;

public interface IPropertyCommunityConfigService extends BaseService<PropertyCommunityConfig,Long>{

	PropertyCommunityConfig findSingleModel(String communityNumber);
}
