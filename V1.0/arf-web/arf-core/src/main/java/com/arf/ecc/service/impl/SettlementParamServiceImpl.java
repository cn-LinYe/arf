package com.arf.ecc.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.ecc.dao.ISettlementParamDao;
import com.arf.ecc.entity.SettlementParam;
import com.arf.ecc.service.SettlementParamService;

@Repository("settlementParamServiceImpl")
public class SettlementParamServiceImpl extends BaseServiceImpl<SettlementParam, Long>
		implements SettlementParamService {

	@Resource(name="settlementParamDaoImpl")
	ISettlementParamDao settlementParamDao;
	
	@Override
	protected BaseDao<SettlementParam, Long> getBaseDao() {
		return settlementParamDao;
	}
	
	@Override
	public SettlementParam findByNumber(String communityNumber,SettlementParam.AssignType assignType){
		return settlementParamDao.findByNumber(communityNumber, assignType);
	}
}
