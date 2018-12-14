package com.arf.propertymgr.service;

import java.util.List;
import java.util.Map;

import com.arf.core.service.BaseService;
import com.arf.propertymgr.dto.PropertyRoomDto;
import com.arf.propertymgr.dto.PropertyRoomInfoDto;
import com.arf.propertymgr.entity.PropertyRoomInfo;
import com.arf.propertymgr.entity.PropertyRoomInfo.RoomType;
import com.arf.propertymgr.entity.PtyPropertyFeeConfig.Compare;

public interface IPropertyRoomInfoService extends BaseService<PropertyRoomInfo, Long> {

	/**
	 * 根据房间号查询
	 * @param roomNumber
	 * @return
	 */
	PropertyRoomInfo findByRoomNumber(String roomNumber);

	/**
	 * 一小区几栋
	 * @param communityNumber
	 * @return
	 */
	List<PropertyRoomInfo> findByCommunityGbyBuilding(String communityNumber);

	/**
	 * 一栋几单元
	 * @param communityNumber
	 * @param building
	 * @return
	 */
	List<PropertyRoomInfo> findByCommunityBuildingGbyUnits(String communityNumber, String building);

	/**
	 * 一单元几楼
	 * @param communityNumber
	 * @param building
	 * @param unit
	 * @return
	 */
	List<PropertyRoomInfo> findByCommunityBuildingUnitsGbyFloor(String communityNumber, String building, String unit);

	/**
	 * 一楼几房间
	 * @param communityNumber
	 * @param building
	 * @param unit
	 * @param floor
	 * @return
	 */
	List<PropertyRoomInfo> findByComBuilUnitFloor(String communityNumber,String building, String unit, String floor);
	
	/**
	 * 根据小区编号,楼栋名,单元名,查询房间
	 * @param communityNumber
	 * @param buildingName
	 * @param unitName
	 * @param room
	 * @return
	 */
	List<PropertyRoomInfo> findByComBuilUnitRoom(String communityNumber,String buildingName, String unitName, String room);

	/**
	 * 根据小区楼栋单元楼层房间条件模糊搜索房间信息
	 * @param communityNumber
	 * @param buildingName
	 * @param unitName
	 * @param floor
	 * @param room
	 * @return
	 */
	List<PropertyRoomInfo> findRoomInfoByCondition(String communityNumber,String buildingName, String unitName,String floor, String room);
	/**
	 * 通过小区编号查询,返回房间信息
	 * @param communityNumber
	 * @return
	 */
	List<PropertyRoomInfo> findByCommunity(String communityNumber);
	
	/**
	 * 通过小区编号查询,返回房间信息+户主用户名
	 * @param communityNumber
	 * @return
	 */
	List<PropertyRoomInfoDto> findByCommunityNumber(String communityNumber);

	/**
	 * 根据小区、栋、单元、楼、房间查询房间信息
	 * @param communityNumber
	 * @param building
	 * @param unit
	 * @param floor
	 * @param room
	 * @return
	 */
	PropertyRoomInfo findByComBuilUnitFloorRoom(String communityNumber,
			String building, String unit, String floor, String room);

	/**
	 * 根据小区编号、栋、单元查询房间
	 * @param communityNumber
	 * @param buildingName
	 * @param unitName
	 * @return
	 */
	List<PropertyRoomInfo> findByCommunityBuildingUnits(String communityNumber,
			String buildingName, String unitName);
	/**
	 * 根据物业费配置查找符合配置的房间(按面积)
	 * @param communityNumber
	 * @param building
	 * @param roomType
	 * @param floor
	 * @param compare
	 * @return
	 */
	List<PropertyRoomDto> findByConfig(String communityNumber,String building,RoomType roomType,String floor,Compare compare);
	/**
	 * 批量更新时间
	 * @param list
	 */
	void updateById(List<Long> list);
	
	/**
	 * 根据房屋编号集合查询
	 * @param list
	 */
	List<PropertyRoomInfo> findByRoomNumbers(List<String> roomNumberList);

	/**
	 * 通过关键字搜索房间用户信息
	 * @param communityNumber
	 * @param buildingName
	 * @param unitName
	 * @param keyword
	 * @return
	 */
	List<Map<String, Object>> searchByKeyWord(String communityNumber, String buildingName, String unitName,
			String keyword);

	/**
	 * 根据小区编号集合查询
	 * @param list
	 */
	List<PropertyRoomInfo> findByCommunityNumbers(List<String> communityNumberList);
}
