package com.arf.smart.dao;

import java.util.List;
import java.util.Map;

import com.arf.core.dao.BaseDao;
import com.arf.smart.entity.Smartlock;

public interface ISmartLockDao extends BaseDao<Smartlock, Long>{
	
	/**根据用户信息获取智能门锁的列表
	 * @param userName
	 * @return
	 */
	public List<Map<String, Object>> getLocksByUser(String userName);
}
