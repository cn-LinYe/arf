package com.arf.finance.service;

import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.finance.entity.FinanceTag;

public interface IFinanceTagService extends BaseService<FinanceTag, Long> {

	List<FinanceTag> findByIds(List<Long> tagids);

}
