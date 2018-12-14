package com.arf.axd.axdgift.dao;

import com.arf.axd.axdgift.entity.AxdGiftStatisticBackups;
import com.arf.core.dao.BaseDao;

public interface IAxdGiftStatisticBackupsDao extends BaseDao<AxdGiftStatisticBackups,Long>{

	AxdGiftStatisticBackups findByKey(String key);

}
