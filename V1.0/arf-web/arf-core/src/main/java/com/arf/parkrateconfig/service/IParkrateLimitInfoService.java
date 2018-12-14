package com.arf.parkrateconfig.service;

import com.arf.core.service.BaseService;
import com.arf.parkrateconfig.entity.ParkrateLimitInfo;

public interface IParkrateLimitInfoService extends BaseService<ParkrateLimitInfo, Long>{
	
	/**
	 * 根据小区编号查询
	 * @param communityNumber
	 * @return
	 */
	ParkrateLimitInfo findByCommunityNumber(String communityNumber);

}
