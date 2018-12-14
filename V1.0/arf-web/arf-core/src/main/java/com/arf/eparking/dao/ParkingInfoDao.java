package com.arf.eparking.dao;

import com.arf.core.dao.BaseDao;
import com.arf.eparking.entity.ParkingInfo;

public interface ParkingInfoDao extends BaseDao<ParkingInfo, Long>{

	/**
	 * 通过停车场编号获取停车场信息
	 * @param parkingNo
	 * @return
	 */
	ParkingInfo findByParkingNo(String parkingNo);

	
}
