package com.arf.finance.dao;

import java.util.List;

import com.arf.core.dao.BaseDao;
import com.arf.finance.entity.FinanceTag;

public interface IFinanceTagDao extends BaseDao<FinanceTag, Long> {

	List<FinanceTag> findByIds(List<Long> tagids);

	
	
}
