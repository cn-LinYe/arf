package com.arf.anxinshop.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.anxinshop.dao.IEsUserTagsDao;
import com.arf.anxinshop.entity.EsUserTags;
import com.arf.anxinshop.service.IEsUserTagsService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("esUserTagsServiceImpl")
public class EsUserTagsServiceImpl extends BaseServiceImpl<EsUserTags, Long>
		implements IEsUserTagsService {

	@Resource(name = "esUserTagsDaoImpl")
	private IEsUserTagsDao esUserTagsDaoImpl;
	
	@Override
	protected BaseDao<EsUserTags, Long> getBaseDao() {
		return esUserTagsDaoImpl;
	}

	@Override
	public EsUserTags findByUserName(String userName) {
		return esUserTagsDaoImpl.findByUserName(userName);
	}

	@Override
	public void deleteAll(){
		esUserTagsDaoImpl.deleteAll();
	}
}
