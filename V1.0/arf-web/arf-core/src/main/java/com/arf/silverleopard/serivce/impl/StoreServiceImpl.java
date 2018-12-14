package com.arf.silverleopard.serivce.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.silverleopard.dao.StoreDao;
import com.arf.silverleopard.entity.Store;
import com.arf.silverleopard.serivce.StoreService;

@Service("storeService")
public class StoreServiceImpl extends BaseServiceImpl<Store, Long> implements StoreService{
	
	@Resource(name = "storeDao")
	private StoreDao storeDao;
	
	@Override
	protected BaseDao<Store, Long> getBaseDao() {
		return storeDao;
	}

}
