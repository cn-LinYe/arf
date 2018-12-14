package com.arf.silverleopard.dao;

import com.arf.core.dao.BaseDao;
import com.arf.silverleopard.entity.SalesReceipts;
import com.arf.silverleopard.entity.SalesReceiptsItem;

public interface SalesReceiptsItemDao extends BaseDao<SalesReceiptsItem, Long>{

	int findCount(String productUid);
}
