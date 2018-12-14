package com.arf.propertymgr.dao;

import java.util.List;
import java.util.Map;

import com.arf.core.dao.BaseDao;
import com.arf.propertymgr.entity.PropertyRoomUserBind;
import com.arf.propertymgr.entity.PropertyRoomUserBind.Status;

public interface IPropertyRoomUserBindDao extends BaseDao<PropertyRoomUserBind, Long> {

	/**
	 * 根据用户名查询
	 * @param userName
	 * @return
	 */
	List<PropertyRoomUserBind> findByUserName(String userName);

	/**
	 * 根据用户名和绑定编号<br/>
	 * status != -1 and status != 2
	 * @param userName
	 * @param boundNumber
	 * @return
	 */
	PropertyRoomUserBind findByUserNameBoundNumber(String userName,String boundNumber);
	
	/**
	 * 根据用户名和绑定编号查询未解绑信息<br/>
	 * status != -1
	 * @param userName
	 * @param boundNumber
	 * @return
	 */
	PropertyRoomUserBind findByUserNameBoundNumberNotUnBound(String userName,String boundNumber);

	/**
	 * 根据房间编号查询<br/>
	 * status != -1 and status != 2
	 * @param roomNumber
	 * @return
	 */
	PropertyRoomUserBind findByRoomNumber(String roomNumber);
	
	/**
	 * 根据房间编号查询相关记录
	 * @param roomNumber
	 * @return
	 */
	List<PropertyRoomUserBind> findListByRoomNumber(String roomNumber);

	List<PropertyRoomUserBind> findByUserNameStatus(String userName,
			Status status);

	/**
	 * status = 3 or (status = 0 and boundType = 1) or status = 1
	 * @param userName
	 * @return
	 */
	List<PropertyRoomUserBind> findByUserNameNewMgruploadModifyedMgr(
			String userName);

	/**
	 * 根据房间编号查询户主<br/>
	 * status = 1 or (status = 0 and boundType = 1)
	 * @param roomNumber
	 * @return
	 */
	PropertyRoomUserBind findHouseHolderByRoomNumber(String roomNumber);

	/**
	 * 根据多个房间编号查询户主<br/>
	 * status = 1 or (status = 0 and boundType = 1)
	 * @param roomNumbers
	 * @return
	 */
	List<PropertyRoomUserBind> findByRoomNumbers(List<String> roomNumbers);
	
	/**
	 * 查看用户绑定房间数量
	 * @param userName
	 * @return
	 */
	Integer findBindRoomCount(String userName,String communityNumber);

	/**
	 * status = 3 or (status = 0 and boundType = 1) or status = 1 or status = 2
	 * @param userName
	 * @return
	 */
	List<PropertyRoomUserBind> findByUserNameNewMgruploadModifyedMgrRefuse(
			String userName);
	
	/**
	 * 根据小区编号，用户名，绑定状态查找
	 * @param communityNumber
	 * @param userName
	 * @param status
	 * @return
	 */
	List<Map<String, Object>> findByCommunityUserNameStatus(String communityNumber,String userName,
			Status status);

}
