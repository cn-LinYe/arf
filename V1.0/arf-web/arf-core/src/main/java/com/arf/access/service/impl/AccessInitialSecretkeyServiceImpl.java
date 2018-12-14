package com.arf.access.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.access.dao.IAccessInitialSecretkeyDao;
import com.arf.access.entity.AccessInitialSecretkey;
import com.arf.access.service.IAccessInitialSecretkeyService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("accessInitialSecretkeyServiceImpl")
public class AccessInitialSecretkeyServiceImpl extends BaseServiceImpl<AccessInitialSecretkey, Long> implements IAccessInitialSecretkeyService{
	@Resource(name = "accessInitialSecretkeyDaoImpl")
	private IAccessInitialSecretkeyDao accessInitialSecretkeyDao;
	@Override
	protected BaseDao<AccessInitialSecretkey, Long> getBaseDao() {
		return accessInitialSecretkeyDao;
	}
	
	@Override
	public List<AccessInitialSecretkey> findBybluetoothMac(String bluetoothMac){
		return accessInitialSecretkeyDao.findBybluetoothMac( bluetoothMac);
	}
	
	@Override
	public List<AccessInitialSecretkey> findByCommunityNumber(String communityNumber){
		return accessInitialSecretkeyDao.findByCommunityNumber( communityNumber);
	}
	
	@Override
	public AccessInitialSecretkey findByCondition(String communityNumber, String building, int region) {
		return accessInitialSecretkeyDao.findByCondition(communityNumber, building, region);
	}
}
