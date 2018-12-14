package com.arf.access.dao;

import java.util.List;

import com.arf.access.entity.ElevatorDevice;
import com.arf.core.dao.BaseDao;

public interface IElevatorDeviceDao extends BaseDao<ElevatorDevice, Long>{

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
