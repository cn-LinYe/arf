package com.arf.access.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.arf.access.dao.IAccessUserPasswdDao;
import com.arf.access.entity.AccessUserPasswd;
import com.arf.access.entity.AccessUserPasswd.UseStatus;
import com.arf.access.service.IAccessUserPasswdService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("accessUserPasswdService")
public class AccessUserPasswdServiceImpl extends
		BaseServiceImpl<AccessUserPasswd, Long> implements
		IAccessUserPasswdService {
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Resource(name = "accessUserPasswdDao")
	private IAccessUserPasswdDao accessUserPasswd;
	
	@Override
	protected BaseDao<AccessUserPasswd, Long> getBaseDao() {
		return accessUserPasswd;
	}

	@Override
	public List<AccessUserPasswd> findByBlueMacAndPwd(String password, String bluetoothMac,String roomNumber,UseStatus useStatus) {
		return accessUserPasswd.findByBlueMacAndPwd(password, bluetoothMac, roomNumber, useStatus);
	}

	@Override
	public List<Map<String, Object>> findByRoomAndCommunity(String roomNumber, String communityNumber) {
		return accessUserPasswd.findByRoomAndCommunity(roomNumber, communityNumber);
	}

	@Override
	public AccessUserPasswd findByPwdAndMac(String password, String bluetoothMac) {
		return accessUserPasswd.findByPwdAndMac(password, bluetoothMac);
	}
}
