package com.arf.access.dao;

import java.util.List;
import java.util.Map;

import com.arf.access.dto.AccessCardUserDto;
import com.arf.access.entity.AccessCardUser;
import com.arf.core.dao.BaseDao;

public interface IAccessCardUserDao extends BaseDao<AccessCardUser, Long> {

	/**
	 * 根据用户查询
	 * @param userName
	 * @return
	 */
	List<AccessCardUser> findByUserName(String userName);

	/**
	 * 根据用户、小区、房屋绑定编号（GROUP BY chipNum）
	 * @param userName
	 * @param communityNumber
	 * @return
	 */
	List<AccessCardUser> findByUserNameCommunity(String userName,
			String communityNumber,String boundNumber);

	/**
	 * 根据芯片编号、用户名(最近一条)
	 * @param chipNum
	 * @return
	 */
	AccessCardUser findByChipNumUserName(String chipNum,String userName);

	/**
	 * 根据用户查询，关联小区、门禁
	 * @param userName
	 * @param boundNumber
	 * @return
	 */
	List<AccessCardUserDto> findByUserNameJoinCommunityAccess(String userName,String boundNumber);

	/**
	 * 根据用户名、房屋绑定编号
	 * @param userName
	 * @param boundNumber
	 * @return
	 */
	List<AccessCardUser> findByUserNameRoomBoundNumber(String userName,
			String boundNumber);

	/**
	 * 据芯片编号(最近一条)
	 * @param chipNum
	 * @return
	 */
	AccessCardUser findbyChipNum(String chipNum);

	/**
	 * 根据用户名、卡编号(GROUP BY cardNum)
	 * @param cardNum
	 * @return
	 */
	List<AccessCardUserDto> findbyCardNumUsername(String cardNum,String userName);

	/**
	 * 根据房间名称查询该房间下所有的门禁卡
	 * @param cardNum
	 * @param userName
	 * @return
	 */
	List<AccessCardUserDto> findByRoomNumber(String roomNumber);
	
	/**
	 * 根据房间名称查询该房间下所有的门禁卡
	 * @param cardNum
	 * @param userName
	 * @return
	 */
	List<AccessCardUserDto> findByRoomList(List<String> roomList);
	
	/**
	 * 查询该小区所有已更新设置且未下发的门禁卡
	 * @param communityNumber
	 * @return
	 */
	List<Map<String,Object>> findByCommunity(String communityNumber,String cardVersion);
	
	/**
	 * 根据小区及卡id集合查询已更新设置且未下发的V2版门禁卡
	 * @param communityNum
	 * @param ids
	 * @param limit
	 * @return
	 */
	List<Map<String,Object>> findByCommunityAndId(List<String> communityNums,List<Long> ids);
	
	/**
	 * 根据芯片编号集合查询
	 * @param chipNumList
	 * @return
	 */
	List<AccessCardUser> findByChipNumList(List<String> chipNumList);
	
	/**
	 * 根据卡id集合查询
	 * @param idList
	 * @return
	 */
	List<AccessCardUser> findByIdList(List<Long> idList);
	
	public void updateByChipNum(String chipNum,Long cardId);
	
}
