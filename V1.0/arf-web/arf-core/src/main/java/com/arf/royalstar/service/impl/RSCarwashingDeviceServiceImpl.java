package com.arf.royalstar.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.royalstar.dao.RSCarwashingDeviceDao;
import com.arf.royalstar.entity.RSCarwashingDevice;
import com.arf.royalstar.service.RSCarwashingDeviceService;

@Service("rsCarwashingDeviceServiceImpl")
public class RSCarwashingDeviceServiceImpl extends BaseServiceImpl<RSCarwashingDevice, Long>
		implements RSCarwashingDeviceService {
	
	@Resource(name = "rsCarwashingDeviceDaoImpl")
	private RSCarwashingDeviceDao rsCarwashingDeviceDao;
	
	@Override
	protected BaseDao<RSCarwashingDevice, Long> getBaseDao(){
		return rsCarwashingDeviceDao;
	}
	
	@Override
	public RSCarwashingDevice findByDeviceCode(String deviceCode){
		return rsCarwashingDeviceDao.findByDeviceCode(deviceCode);
	}

	@Override
	public Map<String, Object> resolvePayQRCode(String deviceCode,String userName) {
		return rsCarwashingDeviceDao.resolvePayQRCode(deviceCode,userName);
	}
}
