package com.arf.gift.service;

import com.arf.core.service.BaseService;
import com.arf.gift.entity.RebateRatio;

public interface RebateRatioService extends BaseService<RebateRatio, Long> {
	
	/**
	 * 根据小区编码和赠送类型获得RebateRatio
	 * @param communityNumber
	 * @param type
	 * @return
	 */
	public RebateRatio findByCommunityNumber(String communityNumber,RebateRatio.RebateType type);

}
