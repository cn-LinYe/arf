package com.arf.core.dao;

import com.arf.core.entity.AdminProLog;

/**
 * Dao - 管理员日志记录
 * 
 * @author arf
 * @version 4.0
 */
public interface AdminProLogDao extends BaseDao<AdminProLog, Long>{
	/**
	 * 得到最新管理日志
	 * @param type
	 * 				类型
	 * @param admins
	 * 				操作对象
	 * @return
	 */
	AdminProLog findAdminProLog(int type ,Long admins);
}
