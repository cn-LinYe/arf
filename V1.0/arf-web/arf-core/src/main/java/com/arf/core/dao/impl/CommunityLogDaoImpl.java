package com.arf.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.CommunityLogDao;
import com.arf.core.entity.CommunityLog;

/**
 * Dao - 用户切换小区的记录
 * 
 * @author arf
 * @version 4.0
 */
@Repository("communityLogDaoImpl")
public class CommunityLogDaoImpl extends BaseDaoImpl<CommunityLog, Long> implements CommunityLogDao {
	
}
