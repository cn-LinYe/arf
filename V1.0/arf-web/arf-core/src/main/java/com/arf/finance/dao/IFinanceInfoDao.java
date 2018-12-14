package com.arf.finance.dao;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.finance.entity.FinanceInfo;

public interface IFinanceInfoDao extends BaseDao<FinanceInfo, Long> {

	PageResult<FinanceInfo> findListByCondition(String communityNumber,
			Integer pageNo, Integer pageSize);

}
