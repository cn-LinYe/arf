package com.arf.parkrateconfig.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.parkrateconfig.dao.impl.ParkrateLimitInfoDaoImpl;
import com.arf.parkrateconfig.entity.ParkrateLimitInfo;
import com.arf.parkrateconfig.service.IParkrateLimitInfoService;

@Service("parkrateLimitInfoServiceImpl")
public class ParkrateLimitInfoServiceImpl extends BaseServiceImpl<ParkrateLimitInfo, Long> implements IParkrateLimitInfoService{

	@Resource(name="parkrateLimitInfoDaoImpl")
	ParkrateLimitInfoDaoImpl parkrateLimitInfoDaoImpl;
	
	@Override
	protected BaseDao<ParkrateLimitInfo, Long> getBaseDao() {
		return parkrateLimitInfoDaoImpl;
	}

	@Override
	public ParkrateLimitInfo findByCommunityNumber(String communityNumber) {
		return parkrateLimitInfoDaoImpl.findByCommunityNumber(communityNumber);
	}

}
