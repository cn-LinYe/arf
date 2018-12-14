package com.arf.silverleopard.serivce.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.silverleopard.dao.SalesReceiptsDao;
import com.arf.silverleopard.dao.SalesReceiptsItemDao;
import com.arf.silverleopard.entity.SalesReceipts;
import com.arf.silverleopard.entity.SalesReceiptsItem;
import com.arf.silverleopard.serivce.SalesReceiptsItemService;
import com.arf.silverleopard.serivce.SalesReceiptsService;

@Service("salesReceiptsItemService")
public class SalesReceiptsItemServiceImpl extends BaseServiceImpl<SalesReceiptsItem, Long> implements SalesReceiptsItemService{

	@Resource(name = "salesReceiptsItemDao")
	private SalesReceiptsItemDao salesReceiptsItemDao;
	
	@Override
	protected BaseDao<SalesReceiptsItem, Long> getBaseDao() {
		return salesReceiptsItemDao;
	}

	@Override
	public int findCount(String productUid) {
		return salesReceiptsItemDao.findCount(productUid);
	}

}
