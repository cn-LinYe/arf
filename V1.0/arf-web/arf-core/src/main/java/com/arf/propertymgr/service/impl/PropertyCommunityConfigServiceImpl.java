package com.arf.propertymgr.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.propertymgr.dao.IPropertyCommunityConfigDao;
import com.arf.propertymgr.entity.PropertyCommunityConfig;
import com.arf.propertymgr.service.IPropertyCommunityConfigService;

@Service("propertyCommunityConfigServiceImpl")
public class PropertyCommunityConfigServiceImpl extends BaseServiceImpl<PropertyCommunityConfig, Long>
		implements IPropertyCommunityConfigService {

	@Resource(name = "propertyCommunityConfigDaoImpl")
	private IPropertyCommunityConfigDao propertyCommunityConfigDao;
	
	@Override
	protected BaseDao<PropertyCommunityConfig,Long> getBaseDao(){
		return propertyCommunityConfigDao;
	}
	
	@Override
	public PropertyCommunityConfig findSingleModel(String communityNumber) {
		return propertyCommunityConfigDao.findSingleModel(communityNumber);
	}

}
