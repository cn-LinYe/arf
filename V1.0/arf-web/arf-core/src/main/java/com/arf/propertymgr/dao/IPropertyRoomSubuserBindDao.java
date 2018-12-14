package com.arf.propertymgr.dao;

import java.util.List;
import java.util.Map;

import com.arf.core.dao.BaseDao;
import com.arf.propertymgr.entity.PropertyRoomSubuserBind;
import com.arf.propertymgr.entity.PropertyRoomSubuserBind.Status;
import com.arf.propertymgr.entity.PropertyRoomSubuserBind.UserType;

public interface IPropertyRoomSubuserBindDao extends BaseDao<PropertyRoomSubuserBind, Long>{

	/**
	 * 根据绑定编号
	 * @param boundNumber
	 * @return
	 */
	PropertyRoomSubuserBind findByBoundNumber(String boundNumber);
	
	/**
	 * 根据用户名和房间编号
	 * @param userName
	 * @param roomNumber
	 * @return
	 */
	PropertyRoomSubuserBind findByUserNameRoomNumber(String userName, String roomNumber);
	
	/**
	 * 根据户主和房间编号查询
	 * @param houseHolder
	 * @param roomNumber
	 * @return
	 */
	List<PropertyRoomSubuserBind> findByHouseHolderRoomNumber(
			String houseHolder, String roomNumber);
	
	/**
	 * 根据房间编号查询所有新申请用户<br/>
	 * status=0
	 * @param roomNumber
	 * @return
	 */
	List<PropertyRoomSubuserBind> findNewUserByRoomNumber(String roomNumber);

	/**
	 * 根据用户名
	 * @param userName
	 * @return
	 */
	List<PropertyRoomSubuserBind> findByUserName(String userName);

	/**
	 * 根据多个绑定编号查询
	 * @param boundNumbersArray
	 * @return
	 */
	List<PropertyRoomSubuserBind> findByBoundNumbers(String[] boundNumbersArray);

	/**
	 * 根据多个绑定编号修改
	 * @param boundNumbersArray
	 */
	int updateByBoundNumbers(String[] boundNumbersArray,Status status,String unboundUser);

	/**
	 * 根据用户名、用户类型查找
	 * @param userName
	 * @param manager
	 * @return
	 */
	List<PropertyRoomSubuserBind> findByUserNameUserType(String userName,
			UserType userType);

	/**
	 * 查找当前房间管理员<br/>
	 * userType = UserType.MANAGER
	 * @param roomNumber
	 * @return
	 */
	PropertyRoomSubuserBind findManager(String roomNumber);
	
	/**
	 * 查找当前房间已接收的管理员<br/>
	 * userType = PropertyRoomSubuserBind.UserType.MANAGER<br/>
	 * status = PropertyRoomSubuserBind.Status.ACCEPT
	 * @param roomNumber
	 * @return
	 */
	List<PropertyRoomSubuserBind> findManagerByRoomNumber(String roomNumber);

	/**
	 * 查找此房间已接收用户<br/>
	 * status = PropertyRoomSubuserBind.Status.ACCEPT
	 * @param roomNumber
	 * @return
	 */
	List<PropertyRoomSubuserBind> findByRoomNumber(String roomNumber);

	/**
	 * 
	 * @param roomNumber
	 * @return
	 */
	List<PropertyRoomSubuserBind> findListByRoomNumber(String roomNumber);
	
	/**
	 * 根据小区编号，用户名，绑定状态查找
	 * @param communityNumber
	 * @param userName
	 * @param status
	 * @return
	 */
	List<Map<String, Object>> findByCommunityUserNameStatus(String communityNumber,String userName,
			Status status);
	
	/**
	 * 查找此房间已接收用户<br/>
	 * status = PropertyRoomSubuserBind.Status.ACCEPT
	 * @param roomNumber
	 * @return
	 */
	List<PropertyRoomSubuserBind> findByRoomList(List<String> roomList);
}
