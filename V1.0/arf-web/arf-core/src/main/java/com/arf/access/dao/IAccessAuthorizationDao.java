package com.arf.access.dao;

import java.util.List;

import com.arf.access.entity.AccessAuthorization;
import com.arf.access.entity.AccessAuthorization.Status;
import com.arf.core.dao.BaseDao;

public interface IAccessAuthorizationDao extends BaseDao<AccessAuthorization, Long>{

	/**
	 * 通过用户名查找授权信息
	 * @param userName
	 * @return
	 */
	public List<AccessAuthorization> findByUserName(String userName);
	public List<AccessAuthorization> findByCommunityNumberAndUserName(String communityNumber,String authorizeUserName,String userName);
	public List<AccessAuthorization> findByCommunityNumberAndUserNameStatus(String communityNumber, String authorizeUserName,String userName,List<AccessAuthorization.Status> status);
	
	public AccessAuthorization findByUserNameRoom(String userName,String communityNumber,String building,String unit,String floor,String room);

	/**
	 * 批量更新授权用户状态
	 * @param ids
	 * @param status
	 * @return
	 */
	public void updateStatus(List<Long> ids,Status status);
	
	/**
	 * 查找过期并且没有修改为过期状态的授权用户
	 * @return
	 */
	public List<AccessAuthorization> findByDateStatus(Status status);

	/**
	 * 通过id批量更新授权信息
	 * @param userName
	 * @return
	 */
	public void updateByArrayIds(List<Long> ids,Status status);
	
	public List<AccessAuthorization> findAllUserByHousrHolderRoomNumber(String userName, String roomNumber,Status status);
	
	/**
	 * 根据多个绑定编号修改
	 * @param boundNumbersArray
	 * @param status
	 * @return
	 */
	public int updateByBoundNumbers(String[] boundNumbersArray, Status status);
	/**
	 * 根据房屋绑定编号、状态查询
	 * @param boundNumber
	 * @param status
	 * @return
	 */
	public List<AccessAuthorization> findByRoomBoundNumber(String boundNumber,
			Status status);
	
	
	public AccessAuthorization findByUserNameCommunityNumberRoomInfo(
			String userName, String communityNumber, String building,
			String unit, AccessAuthorization.Status status);
	
	/**
	 * 查询不正常的
	 * @param userName
	 * @param communityNumber
	 * @param building
	 * @param unit
	 * @return
	 */
	public List<AccessAuthorization> findByUserNameCommunityNumberRoomInfoUnnormal(
			String userName, String communityNumber, String building,
			String unit);
	/**
	 * 根据房屋绑定编号、状态查询
	 * @param boundNumber
	 * @param status
	 * @return
	 */
	public List<AccessAuthorization> findByRoomBoundList(List<String> boundList,Status status);
}
