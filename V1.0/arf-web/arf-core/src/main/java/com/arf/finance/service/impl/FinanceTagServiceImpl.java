package com.arf.finance.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.finance.dao.IFinanceTagDao;
import com.arf.finance.entity.FinanceTag;
import com.arf.finance.service.IFinanceTagService;

@Service("financeTagServiceImpl")
public class FinanceTagServiceImpl extends BaseServiceImpl<FinanceTag, Long>
	implements IFinanceTagService{

	@Resource(name = "financeTagDaoImpl")
	private IFinanceTagDao financeTagDaoImpl;
	
	@Override
	protected BaseDao<FinanceTag, Long> getBaseDao() {
		return financeTagDaoImpl;
	}

	@Override
	public List<FinanceTag> findByIds(List<Long> tagids) {
		return financeTagDaoImpl.findByIds(tagids);
	}
	
}
