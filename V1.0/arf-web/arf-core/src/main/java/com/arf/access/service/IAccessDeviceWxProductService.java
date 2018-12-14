package com.arf.access.service;

import com.arf.access.entity.AccessDeviceWxProduct;
import com.arf.core.service.BaseService;

public interface IAccessDeviceWxProductService extends
		BaseService<AccessDeviceWxProduct, Long> {

	AccessDeviceWxProduct findByAccessNum(String accessNum);

	AccessDeviceWxProduct findByDeviceId(String deviceId);

}
