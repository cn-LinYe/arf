package com.arf.access.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.access.dao.IAccessOpenSettingDao;
import com.arf.access.entity.AccessOpenSetting;
import com.arf.access.service.IAccessOpenSettingService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("accessOpenSettingService")
public class AccessOpenSettingImpl extends BaseServiceImpl<AccessOpenSetting, Long> implements IAccessOpenSettingService {

	@Resource(name="accessOpenSettingDao")
	IAccessOpenSettingDao accessOpenSettingDao;

	@Override
	protected BaseDao<AccessOpenSetting, Long> getBaseDao() {
		return accessOpenSettingDao;
	}


	@Override
	public AccessOpenSetting findByAccessId(Long accessId, String userName) {
		return accessOpenSettingDao.findByAccessId(accessId,userName);
	}


	@Override
	public List<AccessOpenSetting> findByUserName(String userName) {
		return accessOpenSettingDao.findByUserName(userName);
	}

}
