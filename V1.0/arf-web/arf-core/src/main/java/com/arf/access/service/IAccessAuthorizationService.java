package com.arf.access.service;

import java.util.List;

import com.arf.access.entity.AccessAuthorization;
import com.arf.access.entity.AccessAuthorization.Status;
import com.arf.core.service.BaseService;

public interface IAccessAuthorizationService extends BaseService<AccessAuthorization, Long>{

	/**
	 * 根据communityNumber小区编号，authorizeUserName，授权用户，userName用户查询
	 * @param list
	 * @return
	 */
	public List<AccessAuthorization> findByCommunityNumberAndUserName(String communityNumber,String authorizeUserName,String userName);
	
	/**
	 * 批量更新
	 * @param ids
	 * @param status
	 */
	public void updateStatus(List<Long> ids,Status status);
	
	/**
	 * 根据communityNumber小区编号，authorizeUserName，授权用户，userName用户查询,status状态查询
	 * @param communityNumber
	 * @param authorizeUserName
	 * @param userName
	 * @param status
	 * @return
	 */
	public List<AccessAuthorization> findByCommunityNumberAndUserNameStatus(String communityNumber, String authorizeUserName,String userName,List<AccessAuthorization.Status> status);
	
	/**
	 * 查找唯一绑定数据
	 * @param userName
	 * @param communityNumber
	 * @param building
	 * @param unit
	 * @param floor
	 * @param room
	 * @return
	 */
	public AccessAuthorization findByUserNameRoom(String userName,String communityNumber,String building,String unit,String floor,String room);
	
	/**
	 * 查找过期并且没有修改为过期状态的授权用户
	 * @return
	 */
	public List<AccessAuthorization> findByDateStatus(Status status);
	/**
	 * 通过用户名查找授权信息
	 * @param userName
	 * @return
	 */
	public List<AccessAuthorization> findByUserName(String userName);
	
	/**
	 * 通过id批量更新授权信息
	 * @param userName
	 * @return
	 */
	public void updateByArrayIds(List<Long> ids,Status status);

	/**
	 * 通过户主，房间唯一码查询用户下面此房屋的所有从用户
	 * @param userName
	 * @param roomNumber
	 * @param status
	 * @return
	 */
	public List<AccessAuthorization> findAllUserByHousrHolderRoomNumber(String userName, String roomNumber,Status status);

	/**
	 * 根据多个绑定编号修改
	 * @param boundNumbersArray
	 * @param status
	 * @return
	 */
	public int updateByBoundNumbers(String[] boundNumbersArray,
			com.arf.access.entity.AccessAuthorization.Status status);

	/**
	 * 根据房屋绑定编号、状态查询
	 * @param boundNumber
	 * @param status
	 * @return
	 */
	public List<AccessAuthorization> findByRoomBoundNumber(String boundNumber,Status status);

	/**
	 * 
	 * @param userName
	 * @param communityNumber
	 * @param building
	 * @param unit
	 * @param status
	 * @return
	 */
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
