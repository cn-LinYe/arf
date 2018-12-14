package com.arf.access.service;

import java.util.List;

import com.arf.access.entity.ElevatorDevice;
import com.arf.core.service.BaseService;

public interface IElevatorDeviceService extends BaseService<ElevatorDevice, Long>{

	/**
	 * 根据小区，楼栋单元查找设备
	 * @param building
	 * @param unit
	 * @param communityNumber
	 * @return
	 */
	List<ElevatorDevice> findBybuildUnitAndCommunity(String building,String unit,String communityNumber);
	
	/**
	 * 根据小区，楼栋单元等集合查找设备
	 * @param building
	 * @param unit
	 * @param communityNumber
	 * @return
	 */
	List<ElevatorDevice> findBybuildUnitAndCommunity(List<String> buildings,List<String> units,List<String> communityNumbers);
}
