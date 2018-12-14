package com.arf.access.dao;

import com.arf.access.entity.AccessTempPasswd;
import com.arf.core.dao.BaseDao;

public interface IAccessTempPasswdDao extends BaseDao<AccessTempPasswd, Long>{

	/**
	 * 根据密码及蓝牙Mac查询
	 * @param password
	 * @param bluetoothMac
	 * @return
	 */
	public AccessTempPasswd findByBlueMacAndPwd(String password,String bluetoothMac);
}
