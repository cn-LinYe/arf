package com.arf.gift.dao;

import com.arf.core.dao.BaseDao;
import com.arf.gift.entity.RebateRatio;

public interface RebateRatioDao extends BaseDao<RebateRatio, Long> {
	
	public RebateRatio findByCommunityNumber(String communityNumber,RebateRatio.RebateType type);

}
