package com.arf.smart.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.smart.dao.IPSubCustomFunctionDao;
import com.arf.smart.entity.PSubCustomFunction;
import com.arf.smart.service.IPSubCustomFunctionService;

@Service("pSubCustomFunctionService")
public class PSubCustomFunctionServiceImpl extends BaseServiceImpl<PSubCustomFunction, Long> implements IPSubCustomFunctionService{

	@Resource(name="pSubCustomFunctionDao")
	IPSubCustomFunctionDao pSubCustomFunctionDao;
	
	@Override
	protected BaseDao<PSubCustomFunction, Long> getBaseDao() {
		return pSubCustomFunctionDao;
	}

	@Override
	public List<PSubCustomFunction> findByParentMarkList(List<Integer> markList) {
		return pSubCustomFunctionDao.findByParentMarkList(markList);
	}

}
