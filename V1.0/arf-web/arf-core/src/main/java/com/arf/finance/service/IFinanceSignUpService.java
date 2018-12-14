package com.arf.finance.service;

import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.finance.entity.FinanceSignUp;

public interface IFinanceSignUpService extends BaseService<FinanceSignUp, Long> {

	List<FinanceSignUp> findByFinanceInfoIdphone(Long financeInfoId, String userName);

}
