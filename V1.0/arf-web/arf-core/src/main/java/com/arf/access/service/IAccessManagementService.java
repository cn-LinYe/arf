package com.arf.access.service;

import java.util.List;
import java.util.Map;

import com.arf.access.entity.AccessManagement;
import com.arf.access.entity.AccessManagement.AccessType;
import com.arf.access.entity.AccessManagement.AuthencateAccessType;
import com.arf.access.entity.AccessManagement.Status;
import com.arf.core.service.BaseService;

public interface IAccessManagementService extends BaseService<AccessManagement, Long>{
	
	/**
	 * 根据id集合查询
	 * @param list
	 * @return
	 */
	public List<AccessManagement> findByIds(Long[] array);

	/**
	 * 根据id集合，bluetoothMac，蓝牙MAC地址，status状态查询
	 * @param list
	 * @return
	 */
	public List<AccessManagement> findByIdsAndStatus(Long[] Ids,String bluetoothMac,Status status);
	
	/**
	 * 根据communityNumber小区编号，bluetoothMac，蓝牙MAC地址，status状态查询
	 * @param list
	 * @return
	 */
	public List<AccessManagement> findByCommunityNumberAndStatus(String communityNumber,String keyword,String bluetoothMac,Status status);

	/**
	 * 根据communityNumber查找Mac为空的
	 * @param communityNumber
	 * @return
	 */
	public List<AccessManagement> findByCommunityNumberMacIsnull(String communityNumber);

	public AccessManagement findByAccessNum(String accessNum);

	/**
	 * 根据小区编号、状态、门禁类型
	 * @param communityNumber
	 * @param status
	 * @param accessgate
	 * @return
	 */
	public List<AccessManagement> findByCommunityStatusAccessType(
			String communityNumber,com.arf.access.entity.AccessManagement.Status status,
			AccessType accessgate);

	/**
	 * 根据小区编号、状态、门禁类型、栋、单元
	 * @param communityNumber
	 * @param status
	 * @param accessdoor
	 * @param building
	 * @param unit
	 * @return
	 */
	public AccessManagement findByCommunityStatusAccessTypeRoom(
			String communityNumber,com.arf.access.entity.AccessManagement.Status status,
			AccessType accessdoor,String buildingName,String unitName);

	/**
	 * 根据小区编号、栋名称、单元名称
	 * @param communityNumber
	 * @param buildingName
	 * @param unitName 可为空
	 * @return
	 */
	public AccessManagement findByCommunityBuildingInfo(String communityNumber,
			String buildingName, String unitName);

	/**
	 * 根据小区编号、栋、单元
	 * @param communityNumber
	 * @param building
	 * @param unit
	 * @return
	 */
	public List<AccessManagement> findAccessManagementByBuildingAndUnit(
			String communityNumber, Integer building, Integer unit);

	/**
	 * 查找小区下的门禁（大门、楼宇）
	 * @param communityNumber
	 * @return
	 */
	public List<AccessManagement> findByCommunity(String communityNumber);
	

	/**
	 * 查找小区下的门禁（大门、楼宇）
	 * @param communityNumbers
	 * @return
	 */
	public List<AccessManagement> findByCommunitys(List<String> communityNumbers);
	
	/**
	 * 根据蓝牙查找门禁
	 * @param bluetoothMac
	 * @return
	 */
	public AccessManagement findByBluetoothMac(String bluetoothMac);
	
	/**
	 * 与arfadmin保持同步<br/>
	 * com.arf.service.access.AccessManagementService.processBuildingUnitInfo(String, String, String)
	 * @param communityNumber
	 * @param buildingName
	 * @param unitName
	 * @return
	 */
	Map<String,Object> processBuildingUnitInfo(String communityNumber,String buildingName,String unitName);

	/**
	 * 根据小区、栋（处理后的）、单元（处理后的）查询status = 0的门禁<br/>
	 * 处理方法：<br/>
	 * building = "无".equals(building)?"":building<br/>
	 * unit = "无".equals(unit)?"":unit<br/>
	 * @param communityNumber
	 * @param building
	 * @param unit 
	 * @return
	 */
	public List<AccessManagement> findByCommunityBuildingnameUnitname(
			String communityNumber, String building, String unit);
	/**
	 * 根据蓝牙和门禁状态查找门禁
	 * @param bluetoothMac
	 * @param status
	 * @return
	 */
	public AccessManagement findByBluetoothMacAndStatus(String bluetoothMac,Status status);
	
	/**
	 * 根据门禁类型查询所有正常状态的门禁
	 * @param bluetoothMac
	 * @param status
	 * @param authencateAccessType
	 * @return
	 */
	public List<AccessManagement> findByStatus(Status status,AuthencateAccessType authencateAccessType);
}
