package com.arf.gift.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.gift.dao.RebateRatioDao;
import com.arf.gift.entity.RebateRatio;
import com.arf.gift.service.RebateRatioService;

@Service("rebateRatioServiceImpl")
public class RebateRatioServiceImpl extends BaseServiceImpl<RebateRatio, Long> implements RebateRatioService {

	@Resource(name="rebateRatioDaoImpl")
	RebateRatioDao rebateRatioDaoImpl;
	
	@Override
	protected BaseDao<RebateRatio, Long> getBaseDao() {
		return rebateRatioDaoImpl;
	}

	@Override
	public RebateRatio findByCommunityNumber(String communityNumber,RebateRatio.RebateType type){
		return rebateRatioDaoImpl.findByCommunityNumber(communityNumber,type);
	}
}
