package com.arf.anxinshop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.anxinshop.dao.IEsUserTagItemDao;
import com.arf.anxinshop.entity.EsUserTagItem;
import com.arf.anxinshop.service.IEsUserTagItemService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
	
@Service("esUserTagItemServiceImpl")
public class EsUserTagItemServiceImpl extends 
	BaseServiceImpl<EsUserTagItem, Long> implements IEsUserTagItemService{

	@Resource(name = "esUserTagItemDaoImpl")
	private IEsUserTagItemDao esUserTagItemDaoImpl;
	
	@Override
	protected BaseDao<EsUserTagItem, Long> getBaseDao() {
		return esUserTagItemDaoImpl;
	}
	
	@Override
	public List<EsUserTagItem> findByGroupId(Long groupId){
		return esUserTagItemDaoImpl.findByGroupId(groupId);
	}
}
