package com.arf.smart.dao;

import java.util.List;

import com.arf.core.dao.BaseDao;
import com.arf.smart.entity.PSubCustomFunction;

public interface IPSubCustomFunctionDao extends BaseDao<PSubCustomFunction, Long>{
	
	public List<PSubCustomFunction> findByParentMarkList(List<Integer> markList);

}
