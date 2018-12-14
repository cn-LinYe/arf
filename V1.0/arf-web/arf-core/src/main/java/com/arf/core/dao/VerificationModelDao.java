package com.arf.core.dao;

import java.util.List;

import com.arf.core.entity.VerificationModel;

/**
 * Dao - 验证码存储表
 * 
 * @author arf
 * @version 4.0
 */
public interface VerificationModelDao extends BaseDao<VerificationModel, Long>{
	
	/**
	 * 通过账号（手机号）得到验证码存储表
	 * @param phone
	 * 				账号（手机号）
	 * @return  验证码存储表
	 */
	List<VerificationModel> checkByPhone(String phone);
}
