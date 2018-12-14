package com.arf.eparking.service;

import com.arf.core.service.BaseService;
import com.arf.eparking.entity.ParkingInfo;

public interface ParkingInfoService extends BaseService<ParkingInfo, Long>{

	/**
	 * 通过停车场编号获取停车场信息 
	 * @param parkingNo
	 * @return
	 */
	ParkingInfo findByParkingNo(String parkingNo);
}
