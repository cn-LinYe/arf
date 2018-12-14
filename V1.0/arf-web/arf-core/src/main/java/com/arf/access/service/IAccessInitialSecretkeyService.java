package com.arf.access.service;

import java.util.List;

import com.arf.access.entity.AccessInitialSecretkey;
import com.arf.core.service.BaseService;

public interface IAccessInitialSecretkeyService extends BaseService<AccessInitialSecretkey, Long>{

	public List<AccessInitialSecretkey> findBybluetoothMac(String bluetoothMac);
	
	public List<AccessInitialSecretkey> findByCommunityNumber(String communityNumber);
	
	/**
	 * 根据条件获取密码
	 * @param communityNumber
	 * @param building
	 * @param region
	 * @return
	 */
	AccessInitialSecretkey findByCondition(String communityNumber,String building,int region);
	
}
