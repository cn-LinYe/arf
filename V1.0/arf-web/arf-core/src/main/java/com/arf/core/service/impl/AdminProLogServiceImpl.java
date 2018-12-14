package com.arf.core.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.AdminProLogDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.AdminProLog;
import com.arf.core.service.AdminProLogService;

/**
 * Service - 管理员日志记录
 * 
 * @author arf
 * @version 4.0
 */
@Service("adminProLogServiceImpl")
public class AdminProLogServiceImpl extends BaseServiceImpl<AdminProLog, Long> implements AdminProLogService{
	
	@Resource(name = "adminProLogDaoImpl")
	private AdminProLogDao adminProLogDao;
	
	@Override
	protected BaseDao<AdminProLog, Long> getBaseDao() {
		return adminProLogDao;
	}
	
	public AdminProLog findAdminProLog(int type,Long admins){
		return adminProLogDao.findAdminProLog(type, admins);
	}
}
