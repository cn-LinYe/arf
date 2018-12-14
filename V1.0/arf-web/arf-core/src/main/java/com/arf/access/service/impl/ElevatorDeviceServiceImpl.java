package com.arf.access.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.access.dao.IElevatorDeviceDao;
import com.arf.access.entity.ElevatorDevice;
import com.arf.access.service.IElevatorDeviceService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("elevatorDeviceService")
public class ElevatorDeviceServiceImpl extends BaseServiceImpl<ElevatorDevice, Long> implements IElevatorDeviceService {

	@Resource(name="elevatorDeviceDao")
	IElevatorDeviceDao elevatorDeviceDao;

	@Override
	protected BaseDao<ElevatorDevice, Long> getBaseDao() {
		return elevatorDeviceDao;
	}

	@Override
	public List<ElevatorDevice> findBybuildUnitAndCommunity(String building, String unit, String communityNumber) {
		return elevatorDeviceDao.findBybuildUnitAndCommunity(building, unit, communityNumber);
	}

	@Override
	public List<ElevatorDevice> findBybuildUnitAndCommunity(List<String> buildings, List<String> units,
			List<String> communityNumbers) {
		return elevatorDeviceDao.findBybuildUnitAndCommunity(buildings, units, communityNumbers);
	}
	
}
