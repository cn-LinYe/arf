package com.arf.smart.service;

import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.smart.entity.PSubCustomFunction;

public interface IPSubCustomFunctionService extends BaseService<PSubCustomFunction, Long>{
	
	public List<PSubCustomFunction> findByParentMarkList(List<Integer> markList);

}
