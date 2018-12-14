package com.arf.smart.service;

import com.arf.smart.entity.UserCustomFunctionRecord;
import com.arf.core.service.BaseService;

public interface UserCustomFunctionRecordService extends BaseService<UserCustomFunctionRecord, Long>{
	/**
	 * 根据用户名查找用户自定义图标信息
	 * */
	public UserCustomFunctionRecord findByUserName(String userName,String cityCode,String communityNumber);

}
