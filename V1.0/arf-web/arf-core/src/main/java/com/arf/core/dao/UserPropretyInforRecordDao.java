package com.arf.core.dao;

import java.util.List;

import com.arf.core.entity.UserPropretyInforRecord;

/**
 * Dao - 用户物业信息记录
 * 
 * @author arf  liaotao
 * @version 4.0
 */
public interface UserPropretyInforRecordDao extends BaseDao<UserPropretyInforRecord, Long>{
	
	/**
	 * 根据房间id查询用户物业信息记录
	 * @param houseId
	 * 			房间id
	 * @return	用户物业信息记录
	 */
	UserPropretyInforRecord selectByHouseId(String user_name,String houseId);
	
	/**
	 * 根据用户名查找物业信息记录
	 * @param user_name
	 * @return	用户物业信息记录
	 */
	 List<UserPropretyInforRecord> selectByUsername(String user_name);
}
