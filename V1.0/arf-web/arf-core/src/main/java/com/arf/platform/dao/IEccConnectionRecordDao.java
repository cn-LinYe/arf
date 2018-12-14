package com.arf.platform.dao;

import com.arf.core.dao.BaseDao;
import com.arf.platform.entity.EccConnectionRecord;

public interface IEccConnectionRecordDao extends BaseDao<EccConnectionRecord,Long>{

	/**
	 * 根据小区查询最近一条的掉线记录
	 * @param communityNumber
	 * @return
	 */
	EccConnectionRecord findLastOfflineRecord(String communityNumber);

	/**
	 * 根据小区查询最近一条的上线记录
	 * @param communityNumber
	 * @return
	 */
	EccConnectionRecord findLastOnlineRecord(String communityNumber);

	/**
	 * 根据小区查询最近一条记录
	 * @param communityNumber
	 * @return
	 */
	EccConnectionRecord findLastRecord(String communityNumber);

	
	
}
