package com.arf.relievedassist.dao;

import java.util.List;
import java.util.Map;

import com.arf.core.dao.BaseDao;
import com.arf.relievedassist.entity.RelievedAssistUserRecord;

public interface IRelievedAssistUserRecordDao extends BaseDao<RelievedAssistUserRecord, Long>{
	/**
	 * 根据用户名集合查询
	 * @param userList
	 * @return
	 */
	public List<Map<String, Object>> findByUserList(List<String> userList);
	/**
	 * 根据小区查询
	 * @param communityNumber
	 * @return
	 */
	public List<Map<String, Object>> findByCommunityNumber(String communityNumber);
	/**
	 * 根据用户名和小区查找
	 * @param userName
	 * @param communityNumber
	 * @return
	 */
	public List<RelievedAssistUserRecord> findByUserAndCommunity(String userName,String communityNumber,Boolean isMain);
	
	/**
	 * 根据用户名和小区查找
	 * @param userName
	 * @param communityNumber
	 * @return
	 */
	public List<RelievedAssistUserRecord> findByUserAndCommunity(String userName,String communityNumber);
	
	/**查询
	 * @param communityNumber
	 * @param userName
	 * @return
	 */
	RelievedAssistUserRecord findUserByCommUser(String communityNumber,String userName);
	
	/**
	 * 根据用户名集合查询主用户
	 * @param userList
	 * @return
	 */
	public List<RelievedAssistUserRecord> findByUsers(List<String> userList);
}
