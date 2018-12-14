package com.arf.silverleopard.dao;

import com.arf.core.dao.BaseDao;
import com.arf.silverleopard.entity.SalesReceipts;

public interface SalesReceiptsDao extends BaseDao<SalesReceipts, Long>{

	int findCount(String sn);
}
