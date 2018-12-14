package com.arf.core.service;

import com.arf.core.entity.AdminProLog;

/**
 * Service - 管理员日志记录
 * 
 * @author arf
 * @version 4.0
 */
public interface AdminProLogService extends BaseService<AdminProLog, Long> {
	/**
	 * 得到最新管理日志
	 * @param type
	 * 				类型
	 * @param admins
	 * 				操作对象
	 * @return
	 */
	AdminProLog findAdminProLog(int type,Long admins);
}
