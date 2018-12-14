package com.arf.silverleopard.serivce;

import com.arf.core.service.BaseService;
import com.arf.silverleopard.entity.SalesReceipts;

public interface SalesReceiptsService extends BaseService<SalesReceipts, Long>{

	int findCount(String sn);
}
