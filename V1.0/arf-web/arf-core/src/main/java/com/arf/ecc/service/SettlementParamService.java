package com.arf.ecc.service;

import com.arf.core.service.BaseService;
import com.arf.ecc.entity.SettlementParam;

public interface SettlementParamService extends BaseService<SettlementParam, Long> {

	/**
	 * 根据小区编号查询
	 * @param communityNumber
	 * @return
	 */
	public SettlementParam findByNumber(String communityNumber,SettlementParam.AssignType assignType);
}
