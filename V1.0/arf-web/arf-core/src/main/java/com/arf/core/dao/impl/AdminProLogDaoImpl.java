package com.arf.core.dao.impl;


import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.AdminProLogDao;
import com.arf.core.entity.AdminProLog;

/**
 * Dao - 管理员日志记录
 * 
 * @author arf
 * @version 4.0
 */
@Repository("adminProLogDaoImpl")
public class AdminProLogDaoImpl extends BaseDaoImpl<AdminProLog, Long> implements AdminProLogDao {

	public AdminProLog findAdminProLog(int type,Long admins){

		try {
			String sql="SELECT * FROM xx_admin_pro_log ap WHERE ap.type=? AND ap.admins=? ORDER BY ap.create_date DESC limit 1";
			return  (AdminProLog) entityManager.createNativeQuery(sql,AdminProLog.class).setParameter(1, type).setParameter(2, admins).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
			
		
	}
}
