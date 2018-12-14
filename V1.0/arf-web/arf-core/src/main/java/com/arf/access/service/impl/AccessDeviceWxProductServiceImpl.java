package com.arf.access.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.access.dao.IAccessDeviceWxProductDao;
import com.arf.access.entity.AccessDeviceWxProduct;
import com.arf.access.service.IAccessDeviceWxProductService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("accessDeviceWxProductServiceImpl")
public class AccessDeviceWxProductServiceImpl extends
		BaseServiceImpl<AccessDeviceWxProduct, Long> implements
		IAccessDeviceWxProductService {
	
	@Resource(name = "accessDeviceWxProductDaoImpl")
	private IAccessDeviceWxProductDao accessDeviceWxProductDaoImpl;

	@Override
	protected BaseDao<AccessDeviceWxProduct, Long> getBaseDao() {
		return accessDeviceWxProductDaoImpl;
	}

	@Override
	public AccessDeviceWxProduct findByAccessNum(String accessNum) {
		return accessDeviceWxProductDaoImpl.findByAccessNum(accessNum);
	}

	@Override
	public AccessDeviceWxProduct findByDeviceId(String deviceId) {
		return accessDeviceWxProductDaoImpl.findByDeviceId(deviceId);
	}

}
