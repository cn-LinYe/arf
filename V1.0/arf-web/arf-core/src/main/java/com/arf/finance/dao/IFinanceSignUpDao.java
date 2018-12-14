package com.arf.finance.dao;

import java.util.List;

import com.arf.core.dao.BaseDao;
import com.arf.finance.entity.FinanceSignUp;

public interface IFinanceSignUpDao extends BaseDao<FinanceSignUp, Long> {

	List<FinanceSignUp> findByFinanceInfoIdphone(Long financeInfoId, String userName);

}
