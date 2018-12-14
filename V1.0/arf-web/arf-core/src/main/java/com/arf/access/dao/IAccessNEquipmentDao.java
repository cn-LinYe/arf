package com.arf.access.dao;

import java.util.List;
import java.util.Map;

import com.arf.access.entity.AccessNEquipment;
import com.arf.core.dao.BaseDao;

public interface IAccessNEquipmentDao extends BaseDao<AccessNEquipment, Long>{

	/***
	 * 根据小区，类型，楼栋查询设备
	 * @param communityNo
	 * @param equipmentType
	 * @return
	 */
	public List<AccessNEquipment> findByCommunityAndType(String communityNo,Integer equipmentType);
	
	/***
	 * 根据小区集合查询正常使用设备
	 * @param communityList
	 * @return
	 */
	public List<Map<String, Object>> findByCommunitys(List<String> communityList);
	/**
	 * 根据楼栋单元以及小区编号查找门禁
	 * @param communityNo
	 * @param building
	 * @param unit
	 * @return
	 */
	public List<AccessNEquipment> findByCommunityByBuilding(String communityNo,String buildName,String unitName,Integer building,Integer unit);
	
	/**
	 * 根据门禁编号查询
	 * @param accessNumber
	 * @return
	 */
	public AccessNEquipment findByAccessNum(String accessNumber);
}
