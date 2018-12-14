package com.arf.anxinshop.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.anxinshop.dao.IEsUserTagGroupDao;
import com.arf.anxinshop.entity.EsUserTagGroup;
import com.arf.anxinshop.service.IEsUserTagGroupService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("esUserTagGroupServiceImpl")
public class EsUserTagGroupServiceImpl extends 
	BaseServiceImpl<EsUserTagGroup,Long> implements IEsUserTagGroupService{

	@Resource(name = "esUserTagGroupDaoImpl")
	private IEsUserTagGroupDao esUserTagGroupDaoImpl;
	
	@Override
	protected BaseDao<EsUserTagGroup, Long> getBaseDao() {
		return esUserTagGroupDaoImpl;
	}

	@Override
	public List<EsUserTagGroup> findAllGroup() {
		return esUserTagGroupDaoImpl.findAllGroup();
	}

	@Override
	public List<Object> findGroupUser(String sql){
		return esUserTagGroupDaoImpl.findGroupUser(sql);
	}
}
