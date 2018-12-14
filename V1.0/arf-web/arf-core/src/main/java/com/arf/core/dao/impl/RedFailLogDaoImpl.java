package com.arf.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.RedFailLogDao;
import com.arf.core.entity.RedFailLog;

/**
 * Dao - 红包失败记录
 * 
 * @author arf
 * @version 4.0
 */
@Repository("redFailLogDaoImpl")
public class RedFailLogDaoImpl extends BaseDaoImpl<RedFailLog, Long> implements RedFailLogDao {
	
}
