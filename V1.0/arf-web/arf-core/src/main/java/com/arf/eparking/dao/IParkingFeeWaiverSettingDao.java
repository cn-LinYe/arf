package com.arf.eparking.dao;

import com.arf.core.dao.BaseDao;
import com.arf.eparking.entity.ParkingFeeWaiverSetting;

public interface IParkingFeeWaiverSettingDao extends BaseDao<ParkingFeeWaiverSetting, Long>{

	/**
	 * 通过商户编号获取停车费减免时长
	 * @param businessNum
	 * @return
	 */
	public ParkingFeeWaiverSetting findByBusinessNum(String businessNum);
}
