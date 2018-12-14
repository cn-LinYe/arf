package com.arf.smart.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.smart.dao.PCustomFunctionDao;
import com.arf.smart.entity.PCustomFunction;
import com.arf.smart.entity.PCustomFunction.Status;
import com.arf.smart.service.PCustomFunctionService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("pcustomFunctionService")
public class PCustomFunctionServiceImpl extends BaseServiceImpl<PCustomFunction, Long> implements PCustomFunctionService{

	@Resource(name="pcustomFunctionDao")
	PCustomFunctionDao pcustomFunctionDao;
	
	@Override
	protected BaseDao<PCustomFunction, Long> getBaseDao() {
		return pcustomFunctionDao;
	}

	@Override
	public List<Map<String, Object>> findByStatus(Status status) {
		return pcustomFunctionDao.findByStatus(status);
	}

	@Override
	public List<Map<String, Object>> findByIds(Long[] array) {
		return pcustomFunctionDao.findByIds(array);
	}

}
