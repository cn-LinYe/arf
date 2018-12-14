package com.arf.access.dao;

import com.arf.access.entity.AccessDeviceStatusInfo;
import com.arf.access.entity.AccessDeviceStatusInfo.Status;
import com.arf.core.dao.BaseDao;

public interface IAccessDeviceStatusInfoDao extends BaseDao<AccessDeviceStatusInfo, Long>{
	
	/**
	 * 根据蓝牙mac地址和状态查询
	 * @param accessMac
	 * @param status
	 * @return
	 */
	AccessDeviceStatusInfo findByAccessMacAndStatus(String accessMac,Status status);

}
