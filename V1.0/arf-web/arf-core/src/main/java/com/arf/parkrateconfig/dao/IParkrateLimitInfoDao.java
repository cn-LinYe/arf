package com.arf.parkrateconfig.dao;

import com.arf.core.dao.BaseDao;
import com.arf.parkrateconfig.entity.ParkrateLimitInfo;

public interface IParkrateLimitInfoDao extends BaseDao<ParkrateLimitInfo, Long>{
	/**
	 * 根据小区编号查询
	 * @param communityNumber
	 * @return
	 */
	ParkrateLimitInfo findByCommunityNumber(String communityNumber);
}
