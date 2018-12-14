package com.arf.access.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.access.dao.IAccessInterceptPropFeeConfDao;
import com.arf.access.entity.AccessInterceptPropFeeConf;
import com.arf.access.service.IAccessInterceptPropFeeConfService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("accessInterceptPropFeeConfServiceImpl")
public class AccessInterceptPropFeeConfServiceImpl 
		extends BaseServiceImpl<AccessInterceptPropFeeConf, Long> 
		implements IAccessInterceptPropFeeConfService {

	@Resource(name = "accessInterceptPropFeeConfDaoImpl")
	private IAccessInterceptPropFeeConfDao accessInterceptPropFeeConfDaoImpl;
	
	@Override
	protected BaseDao<AccessInterceptPropFeeConf, Long> getBaseDao() {
		return accessInterceptPropFeeConfDaoImpl;
	}

	@Override
	public AccessInterceptPropFeeConf findByCommunityNumber(
			String communityNumber) {
		return accessInterceptPropFeeConfDaoImpl.findByCommunityNumber(communityNumber);
	}

	
	
}
