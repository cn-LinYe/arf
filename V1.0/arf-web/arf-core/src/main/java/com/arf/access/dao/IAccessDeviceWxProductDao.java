package com.arf.access.dao;

import com.arf.access.entity.AccessDeviceWxProduct;
import com.arf.core.dao.BaseDao;

public interface IAccessDeviceWxProductDao extends
		BaseDao<AccessDeviceWxProduct, Long> {

	AccessDeviceWxProduct findByAccessNum(String accessNum);

	AccessDeviceWxProduct findByDeviceId(String deviceId);

}
