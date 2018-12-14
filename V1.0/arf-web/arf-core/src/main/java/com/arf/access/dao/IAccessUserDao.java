package com.arf.access.dao;

import java.util.List;
import java.util.Map;

import com.arf.access.entity.AccessUser;
import com.arf.access.entity.AccessUser.Status;
import com.arf.core.dao.BaseDao;

public interface IAccessUserDao extends BaseDao<AccessUser,Long>{

	/**
	 * 门禁列表accessList不为空、<br/>
	 * 状态status = 正常、<br/>
	 * 授权开始时间authorizeStartDate<=当前时间、<br/>
	 * 授权截止时间authorizeEndDate>=当前时间
	 * @param communityNumber	小区编号
	 * @param userName 			用户名
	 * @return
	 */
	public List<AccessUser> findByUserNameCommunity(String communityNumber,String userName);
	
	/**
	 * 根据条件查询状态正常的门禁用户信息
	 * @param communityNumber
	 * @param building
	 * @param unit
	 * @param keyword
	 * @return
	 */
	public List<Map<String,Object>> findByCondition(String communityNumber,String building,String unit,String keyword);

	/**
	 * 
	 * @param communityNumber	小区编号
	 * @param userName			用户名
	 * @param building			楼栋
	 * @param unit				单元
	 * @param status			状态
	 * @return
	 */
	public AccessUser findByUserNameCommunityBuildingUnit(String communityNumber,
			String userName, String building, String unit,Status status);
	
	/**
	 * 
	 * @param userName			用户名
	 * @param roomBoundNumber	绑定编号
	 * @param status			状态:0正常1禁用2删除
	 * @return
	 */
	public AccessUser findByUserNameBoundNumberStatus(String userName, String roomBoundNumber,Status status);

	/**
	 * 根据小区编号、用户名、楼层、房间
	 * @param communityNumber
	 * @param userName
	 * @param floor
	 * @param room
	 * @return
	 */
	public AccessUser findByUserNameCommunityFloorRoom(String communityNumber,
			String userName, String floor, String room);

	public AccessUser findByUserNameCommunityBuildingNameUnitNameStatus(String communityNumber, String userName, String buildingName, String unitName,
			Status status);

	/**
	 * 根据小区和用户名查找用户的大门门禁<br/>
	 * a.building is null or a.building = '' and  a.unit is null or a.unit = ''
	 * @param communityNumber
	 * @param userName
	 * @return
	 */
	public AccessUser findGateAccessByUserNameCommunity(String communityNumber,
			String userName);

	/**
	 * @param userName
	 * @param boundNumber
	 * @param status
	 * @return
	 */
	public List<AccessUser> findByUsernameBoundnumberStatus(String userName,
			String boundNumber, Status status);

	public List<AccessUser> findByUserNameCommunityNumberRoomInfoUnnormal(
			String communityNumber, String userName, String building,
			String unit);

	public List<AccessUser> findByBoundnumbersStatus(List<String> boundNumbers,
			Status status);

	public List<AccessUser> findByUserNameStatus(String user_name, int status);
	
	public List<AccessUser> findByUserNamesAndCommunityNumbers(List<String> numList, List<String> nameList);
}
