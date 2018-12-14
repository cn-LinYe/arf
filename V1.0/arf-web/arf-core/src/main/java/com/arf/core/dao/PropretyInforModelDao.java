package com.arf.core.dao;

import java.util.List;

import com.arf.core.entity.PropretyInforModel;

/**
 * Dao - 楼栋信息
 * 
 * @author arf  liaotao
 * @version 4.0
 */
public interface PropretyInforModelDao extends BaseDao<PropretyInforModel, Long>{
	/**
	 * 根据小区号获取楼栋信息
	 * @param communityNumber 小区号
	 * @return
	 */
	List<PropretyInforModel> selectCommunityHouse(String communityNumber);
	/**
	 * 根据房间号查询物业信息
	 * @param houseId
	 * 				房间号
	 * @return 物业费信息
	 */
	PropretyInforModel selectByHouseId(String houseId);
	/**
	 * 根据小区号和楼栋号查询
	 * @param communityNumber
	 * @param buildingNumber
	 * @return
	 */
	List<PropretyInforModel> selectByComBuild(String communityNumber,String buildingNumber);
	
	/**
	 * 根据小区号，楼栋号、楼层 获取 物业信息
	 * @param communityNumber 小区
	 * @param buildingNumber 楼栋号
	 * @param floorNumber 楼层
	 * @return
	 */
	List<PropretyInforModel> selectByComBuildFloor(String communityNumber,String buildingNumber,String floorNumber);
	
	/**
	 * 根据用户名小区号查找物业信息
	 * @param user_name
	 * 				用户名
	 * @param communityNumber
	 * 				小区号
	 * @return	物业信息
	 */
	List<PropretyInforModel> selectByUsernameAndCommunity(String user_name,String communityNumber);
	
	/**
	 * 根据用户名查找物业信息
	 * @param user_name
	 * 				用户名
	 * @return	物业信息
	 */
	List<PropretyInforModel> selectByUsername(String user_name);
	
	

	
}
