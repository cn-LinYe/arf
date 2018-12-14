package com.arf.silverleopard.serivce.impl;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.silverleopard.dao.SalesReceiptsDao;
import com.arf.silverleopard.entity.SalesReceipts;
import com.arf.silverleopard.serivce.SalesReceiptsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("salesReceiptsService")
public class SalesReceiptsServiceImpl extends BaseServiceImpl<SalesReceipts, Long> implements SalesReceiptsService{

	@Resource(name = "salesReceiptsDao")
	private SalesReceiptsDao salesReceiptsDao;
	
	@Override
	protected BaseDao<SalesReceipts, Long> getBaseDao() {
		return salesReceiptsDao;
	}

	@Override
	public int findCount(String sn) {
		return salesReceiptsDao.findCount(sn);
	}

}
