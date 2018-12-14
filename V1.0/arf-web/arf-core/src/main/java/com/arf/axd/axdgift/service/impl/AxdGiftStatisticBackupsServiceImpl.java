package com.arf.axd.axdgift.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.axd.axdgift.dao.IAxdGiftStatisticBackupsDao;
import com.arf.axd.axdgift.entity.AxdGiftStatisticBackups;
import com.arf.axd.axdgift.service.IAxdGiftStatisticBackupsService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("axdGiftStatisticBackupsServiceImpl")
public class AxdGiftStatisticBackupsServiceImpl extends BaseServiceImpl<AxdGiftStatisticBackups, Long> implements IAxdGiftStatisticBackupsService {

	@Resource(name = "axdGiftStatisticBackupsDaoImpl")
	private IAxdGiftStatisticBackupsDao axdGiftStatisticBackupsDaoImpl;
	
	@Override
	protected BaseDao<AxdGiftStatisticBackups, Long> getBaseDao() {
		return axdGiftStatisticBackupsDaoImpl;
	}

	@Override
	public AxdGiftStatisticBackups findByKey(String key) {
		return axdGiftStatisticBackupsDaoImpl.findByKey(key);
	}

}
