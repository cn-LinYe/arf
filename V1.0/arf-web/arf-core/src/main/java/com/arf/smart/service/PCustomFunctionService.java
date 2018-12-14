package com.arf.smart.service;

import com.arf.smart.entity.PCustomFunction;

import java.util.List;
import java.util.Map;

import com.arf.core.service.BaseService;

public interface PCustomFunctionService extends BaseService<PCustomFunction, Long>{
	/***
	 * 根据状态查找图标
	 * status
	 * */
	public List<Map<String, Object>> findByStatus(PCustomFunction.Status status);
	
	/**
	 * 根据id集合查询
	 * @param list
	 * @return
	 */
	public List<Map<String, Object>> findByIds(Long[] array);

}
