package com.arf.ecc.dao;

import com.arf.core.dao.BaseDao;
import com.arf.ecc.entity.SettlementParam;

public interface ISettlementParamDao extends BaseDao<SettlementParam, Long> {

	/**
	 * 根据小区编号查询
	 * @param communityNumber
	 * @return
	 */
	public SettlementParam findByNumber(String communityNumber,SettlementParam.AssignType assignType);
}
