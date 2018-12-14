/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao;

import java.util.List;

import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.entity.Admin;

/**
 * Dao - 管理员
 * 
 * @author arf
 * @version 4.0
 */
public interface AdminDao extends BaseDao<Admin, Long> {

	/**
	 * 判断用户名是否存在
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否存在
	 */
	boolean usernameExists(String username);

	/**
	 * 根据用户名查找管理员
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 管理员，若不存在则返回null
	 */
	Admin findByUsername(String username);
	
	/**
	 *查找用户列表
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 管理员，若不存在则返回null
	 */
	 Page<Admin> findPage(Admin.Type admin, Pageable pageable);
	 
	 /**
	  * 根据管理员的类型查找所有的管理员
	  * @param type
	  * @return
	  */
	 List<Admin> findByType(Admin.Type type);
	 /**
	  * 根据用户名和凭证码获取管理员信息
	  * @param userName
	  * @param documentCode
	  * @return
	  */
	 Admin selectAdmin(String userName, String documentCode);

}