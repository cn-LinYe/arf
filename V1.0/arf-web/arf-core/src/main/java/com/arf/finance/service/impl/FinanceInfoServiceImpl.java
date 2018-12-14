package com.arf.finance.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.finance.dao.IFinanceInfoDao;
import com.arf.finance.entity.FinanceInfo;
import com.arf.finance.service.IFinanceInfoService;

@Service("financeInfoServiceImpl")
public class FinanceInfoServiceImpl extends BaseServiceImpl<FinanceInfo, Long>
		implements IFinanceInfoService {

	@Resource(name = "financeInfoDaoImpl")
	private IFinanceInfoDao financeInfoDaoImpl;
	
	@Override
	protected BaseDao<FinanceInfo, Long> getBaseDao() {
		return financeInfoDaoImpl;
	}

	@Override
	public PageResult<FinanceInfo> findListByCondition(String communityNumber,
			Integer pageNo, Integer pageSize) {
		return financeInfoDaoImpl.findListByCondition(communityNumber,pageNo,pageSize);
	}

}
