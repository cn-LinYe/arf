package com.arf.royalstar.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.royalstar.dao.RSCarwashingDeviceOrderDao;
import com.arf.royalstar.entity.RSCarwashingDeviceOrder;
import com.arf.royalstar.service.RSCarwashingDeviceOrderService;

@Service("rsCarwashingDeviceOrderServiceImpl")
public class RSCarwashingDeviceOrderServiceImpl extends BaseServiceImpl<RSCarwashingDeviceOrder, Long>
		implements RSCarwashingDeviceOrderService{

	@Resource(name = "rsCarwashingDeviceOrderDaoImpl")
	private RSCarwashingDeviceOrderDao rsCarwashingDeviceOrderDao;
	
	protected BaseDao<RSCarwashingDeviceOrder,Long> getBaseDao(){
		return rsCarwashingDeviceOrderDao;
	}
	
	@Override
	public RSCarwashingDeviceOrder findByOrderNo(String orderNo) {
		return rsCarwashingDeviceOrderDao.findByOrderNo(orderNo);
	}
}
