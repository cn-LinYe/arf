package com.arf.anxinshop.dao;

import java.util.List;
import java.util.Map;

import com.arf.anxinshop.entity.EsUserTagGroup;
import com.arf.core.dao.BaseDao;

public interface IEsUserTagGroupDao extends BaseDao<EsUserTagGroup, Long> {

	public List<EsUserTagGroup> findAllGroup(); 
	
	public List<Object> findGroupUser(String sql);
}
