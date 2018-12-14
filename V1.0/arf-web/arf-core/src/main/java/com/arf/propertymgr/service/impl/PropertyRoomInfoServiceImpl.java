package com.arf.propertymgr.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.propertymgr.dao.IPropertyRoomInfoDao;
import com.arf.propertymgr.dto.PropertyRoomDto;
import com.arf.propertymgr.dto.PropertyRoomInfoDto;
import com.arf.propertymgr.entity.PropertyRoomInfo;
import com.arf.propertymgr.entity.PropertyRoomInfo.RoomType;
import com.arf.propertymgr.entity.PtyPropertyFeeConfig.Compare;
import com.arf.propertymgr.service.IPropertyRoomInfoService;

@Service("propertyRoomInfoServiceImpl")
@Lazy(false)
public class PropertyRoomInfoServiceImpl extends BaseServiceImpl<PropertyRoomInfo, Long> implements IPropertyRoomInfoService {

	@Resource(name = "propertyRoomInfoDaoImpl")
	private IPropertyRoomInfoDao propertyRoomInfoDaoImpl;
	
	@Override
	protected BaseDao<PropertyRoomInfo, Long> getBaseDao() {
		return propertyRoomInfoDaoImpl;
	}

	@Override
	public PropertyRoomInfo findByRoomNumber(String roomNumber) {
		return propertyRoomInfoDaoImpl.findByRoomNumber(roomNumber);
	}

	@Override
	public List<PropertyRoomInfo> findByCommunityGbyBuilding(String communityNumber) {
		return propertyRoomInfoDaoImpl.findByCommunityGbyBuilding(communityNumber);
	}

	@Override
	public List<PropertyRoomInfo> findByCommunityBuildingGbyUnits(String communityNumber, String building) {
		return propertyRoomInfoDaoImpl.findByCommunityBuildingGbyUnits(communityNumber,building);
	}

	@Override
	public List<PropertyRoomInfo> findByCommunityBuildingUnitsGbyFloor(String communityNumber, String building, String unit) {
		return propertyRoomInfoDaoImpl.findByCommunityBuildingUnitsGbyFloor(communityNumber,building,unit);
	}

	@Override
	public List<PropertyRoomInfo> findByComBuilUnitFloor(String communityNumber, String building, String unit, String floor) {
		return propertyRoomInfoDaoImpl.findByComBuilUnitFloor(communityNumber,building,unit,floor);
	}

	@Override
	public List<PropertyRoomInfo> findByCommunity(String communityNumber) {
		return propertyRoomInfoDaoImpl.findByCommunity(communityNumber);
	}

	@Override
	public List<PropertyRoomInfoDto> findByCommunityNumber(
			String communityNumber) {
		return propertyRoomInfoDaoImpl.findByCommunityNumber(communityNumber);
	}

	@Override
	public PropertyRoomInfo findByComBuilUnitFloorRoom(String communityNumber,
			String building, String unit, String floor, String room) {
		return propertyRoomInfoDaoImpl.findByComBuilUnitFloorRoom(communityNumber, building, unit, floor,room);
	}

	@Override
	public List<PropertyRoomInfo> findByCommunityBuildingUnits(
			String communityNumber, String buildingName, String unitName) {
		return propertyRoomInfoDaoImpl.findByCommunityBuildingUnits(communityNumber,buildingName,unitName);
	}

	@Override
	public List<PropertyRoomInfo> findByComBuilUnitRoom(String communityNumber, String buildingName, String unitName,
			String room) {
		return propertyRoomInfoDaoImpl.findByComBuilUnitRoom(communityNumber,buildingName,unitName,room);
	}

	@Override
	public List<PropertyRoomDto> findByConfig(String communityNumber, String building, RoomType roomType, String floor,
			Compare compare) {
		return propertyRoomInfoDaoImpl.findByConfig(communityNumber, building, roomType, floor, compare);
	}

	@Override
	public void updateById(List<Long> list) {
		 propertyRoomInfoDaoImpl.updateById(list);
	}

	@Override
	public List<PropertyRoomInfo> findRoomInfoByCondition(String communityNumber, String buildingName, String unitName,
			String floor, String room) {
		return propertyRoomInfoDaoImpl.findRoomInfoByCondition(communityNumber, buildingName, unitName, floor, room);
	}

	@Override
	public List<PropertyRoomInfo> findByRoomNumbers(List<String> roomNumberList) {
		return propertyRoomInfoDaoImpl.findByRoomNumbers(roomNumberList);
	}

	@Override
	public List<Map<String, Object>> searchByKeyWord(String communityNumber, String buildingName, String unitName,
			String keyword) {
		return propertyRoomInfoDaoImpl.searchByKeyWord(communityNumber,buildingName,unitName,keyword);
	}

	@Override
	public List<PropertyRoomInfo> findByCommunityNumbers(List<String> communityNumberList) {
		return propertyRoomInfoDaoImpl.findByCommunityNumbers(communityNumberList);
	}


}
