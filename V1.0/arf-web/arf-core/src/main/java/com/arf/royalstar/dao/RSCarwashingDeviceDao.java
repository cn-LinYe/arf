package com.arf.royalstar.dao;

import java.util.Map;

import com.arf.core.dao.BaseDao;
import com.arf.royalstar.entity.RSCarwashingDevice;

public interface RSCarwashingDeviceDao extends BaseDao<RSCarwashingDevice,Long>{

	/**
	 * 根据设备编号查询设备信息
	 * @param deviceCode
	 * @return
	 */
	public RSCarwashingDevice findByDeviceCode(String deviceCode);
	
	/**
	 * 查找商户及订单信息
	 * @param userName
	 * @return
	 */
	Map<String,Object> resolvePayQRCode(String deviceCode , String userName);
}
