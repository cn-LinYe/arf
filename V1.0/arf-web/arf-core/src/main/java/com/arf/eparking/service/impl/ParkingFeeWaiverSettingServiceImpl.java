package com.arf.eparking.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.eparking.dao.IParkingFeeWaiverSettingDao;
import com.arf.eparking.entity.ParkingFeeWaiverSetting;
import com.arf.eparking.service.IParkingFeeWaiverSettingService;

@Service("parkingFeeWaiverSettingService")
public class ParkingFeeWaiverSettingServiceImpl extends BaseServiceImpl<ParkingFeeWaiverSetting, Long> implements IParkingFeeWaiverSettingService{

	@Resource(name="parkingFeeWaiverSettingDao")
	IParkingFeeWaiverSettingDao parkingFeeWaiverSettingDao;
	
	@Override
	protected BaseDao<ParkingFeeWaiverSetting, Long> getBaseDao() {
		return parkingFeeWaiverSettingDao;
	}

	@Override
	public ParkingFeeWaiverSetting findByBusinessNum(String businessNum) {
		return parkingFeeWaiverSettingDao.findByBusinessNum(businessNum);
	}

}
