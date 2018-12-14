package com.arf.smart.dao;

import com.arf.smart.entity.UserCustomFunctionRecord;
import com.arf.core.dao.BaseDao;

public interface UserCustomFunctionRecordDao extends BaseDao<UserCustomFunctionRecord, Long>{
	/**
	 * 根据用户名查找用户自定义图标信息
	 * */
	public UserCustomFunctionRecord findByUserName(String userName,String cityCode,String communityNumber);

}
