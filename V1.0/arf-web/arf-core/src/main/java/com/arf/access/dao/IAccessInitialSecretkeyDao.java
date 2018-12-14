package com.arf.access.dao;

import java.util.List;

import com.arf.access.entity.AccessInitialSecretkey;
import com.arf.core.dao.BaseDao;

public interface IAccessInitialSecretkeyDao extends BaseDao<AccessInitialSecretkey, Long>{

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
