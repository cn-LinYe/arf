package com.arf.member.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.member.dao.IAxdConfigDao;
import com.arf.member.entity.AxdConfig;
import com.arf.member.service.IAxdConfigService;

@Service("axdConfigServiceImpl")
public class AxdConfigServiceImpl extends BaseServiceImpl<AxdConfig, Long> implements IAxdConfigService {

	@Resource(name = "axdConfigDaoImpl")
	private IAxdConfigDao axdConfigDaoImpl;
	
	@Override
	protected BaseDao<AxdConfig, Long> getBaseDao() {
		return axdConfigDaoImpl;
	}

	@Override
	public AxdConfig findByUsernameAndLicense(String userName, String license) {
		return axdConfigDaoImpl.findByUsernameAndLicense(userName,license);
	}
	
	


}
