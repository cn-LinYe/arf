package com.arf.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.WxRedLogDao;
import com.arf.core.entity.WxRedLog;

/**
 * Dao - 微信红包发送的记录
 * 
 * @author arf
 * @version 4.0
 */
@Repository("wxRedLogDaoImpl")
public class WxRedLogDaoImpl extends BaseDaoImpl<WxRedLog, Long> implements WxRedLogDao {
	
}
