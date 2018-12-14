package com.arf.access.service;

import com.arf.access.entity.AccessTempPasswd;
import com.arf.core.service.BaseService;

public interface IAccessTempPasswdService extends BaseService<AccessTempPasswd, Long>{
	/**
	 * 根据门禁分配一个临时密码
	 * @param bluetooth
	 * @return
	 */
	public AccessTempPasswd allocateOnePasswd(String communityNumber,int building,String userName,String bluetoothMac);
	
	/**
	 * 根据密码及蓝牙Mac查询
	 * @param password
	 * @param bluetoothMac
	 * @return
	 */
	public AccessTempPasswd findByBlueMacAndPwd(String password,String bluetoothMac);
}

