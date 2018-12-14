package com.arf.silverleopard.serivce;

import com.arf.core.service.BaseService;
import com.arf.silverleopard.entity.SalesReceiptsItem;

public interface SalesReceiptsItemService extends BaseService<SalesReceiptsItem, Long>{

	int findCount(String productUid);
}
