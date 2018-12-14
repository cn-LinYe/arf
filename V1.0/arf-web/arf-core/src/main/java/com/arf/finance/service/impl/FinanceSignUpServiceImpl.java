package com.arf.finance.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.finance.dao.IFinanceSignUpDao;
import com.arf.finance.entity.FinanceSignUp;
import com.arf.finance.service.IFinanceSignUpService;

@Service("financeSignUpServiceImpl")
public class FinanceSignUpServiceImpl extends
		BaseServiceImpl<FinanceSignUp, Long> implements IFinanceSignUpService {

	@Resource(name = "financeSignUpDaoImpl")
	private IFinanceSignUpDao financeSignUpDaoImpl;
	
	@Override
	protected BaseDao<FinanceSignUp, Long> getBaseDao() {
		return financeSignUpDaoImpl;
	}

	@Override
	public List<FinanceSignUp> findByFinanceInfoIdphone(Long financeInfoId, String userName) {
		return financeSignUpDaoImpl.findByFinanceInfoIdphone(financeInfoId,userName);
	}

}
