package com.arf.axd.axdgift.service;

import com.arf.axd.axdgift.entity.AxdGiftStatisticBackups;
import com.arf.core.service.BaseService;

public interface IAxdGiftStatisticBackupsService extends BaseService<AxdGiftStatisticBackups,Long>{

	AxdGiftStatisticBackups findByKey(String key);
	
}
