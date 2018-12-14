package com.arf.eparking.service;

import com.arf.core.service.BaseService;
import com.arf.eparking.entity.ParkingFeeWaiverSetting;

public interface IParkingFeeWaiverSettingService extends BaseService<ParkingFeeWaiverSetting, Long>{

	/**
	 * 通过商户编号获取停车费减免时长
	 * @param businessNum
	 * @return
	 */
	public ParkingFeeWaiverSetting findByBusinessNum(String businessNum);
}
