package com.arf.anxinshop.service;

import java.util.List;
import java.util.Map;

import com.arf.anxinshop.entity.EsUserTagGroup;
import com.arf.core.service.BaseService;

public interface IEsUserTagGroupService extends BaseService<EsUserTagGroup,Long>{

	public List<EsUserTagGroup> findAllGroup();
	
	public List<Object> findGroupUser(String sql);
	
}
