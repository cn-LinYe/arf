package com.arf.eparking.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.eparking.dao.OrderfeeRuleItemDao;
import com.arf.eparking.entity.OrderfeeRuleItem;
import com.arf.eparking.entity.OrderfeeRuleItem.Items;
import com.arf.eparking.entity.OrderfeeRuleItem.Use;
import com.arf.eparking.service.OrderfeeRuleItemService;

@Service("orderfeeRuleItemServiceImpl")
public class OrderfeeRuleItemServiceImpl extends BaseServiceImpl<OrderfeeRuleItem, Long> implements OrderfeeRuleItemService {

	@Resource(name = "orderfeeRuleItemDaoImpl")
	private OrderfeeRuleItemDao orderfeeRuleItemDaoImpl;
	
	@Override
	protected BaseDao<OrderfeeRuleItem, Long> getBaseDao() {
		return orderfeeRuleItemDaoImpl;
	}

	@Override
	@Deprecated //{@link com.arf.eparking.service.impl.OrderfeeRuleItemServiceImpl.findByParkingIdUse(String, Integer, Items)}
	public OrderfeeRuleItem findByParkingIdUse(String parkingId, Integer use,String key) {
		return orderfeeRuleItemDaoImpl.findByParkingIdUse(parkingId,use,key);
	}

	@Override
	public OrderfeeRuleItem findByParkingIdUse(String parkingId, Use use,Items items) {
		return orderfeeRuleItemDaoImpl.findByParkingIdUse(parkingId,use,items);
	}

	@Override
	public List<OrderfeeRuleItem> findByParkingIdUse(String parkingId, Use use) {
		return orderfeeRuleItemDaoImpl.findByParkingIdUse(parkingId,use);
	}

	
}
