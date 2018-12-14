package com.arf.finance.service;

import com.arf.base.PageResult;
import com.arf.core.service.BaseService;
import com.arf.finance.entity.FinanceInfo;

public interface IFinanceInfoService extends BaseService<FinanceInfo, Long> {

	PageResult<FinanceInfo> findListByCondition(String communityNumber,
			Integer pageNo, Integer pageSize);

}
