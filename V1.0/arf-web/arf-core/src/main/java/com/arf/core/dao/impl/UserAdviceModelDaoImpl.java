package com.arf.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.UserAdviceModelDao;
import com.arf.core.entity.UserAdviceModel;

/**
 * Dao - 用户意见反馈
 * 
 * @author arf
 * @version 4.0
 */
@Repository("userAdviceModelDaoImpl")
public class UserAdviceModelDaoImpl extends BaseDaoImpl<UserAdviceModel, Long> implements UserAdviceModelDao {
	
}
