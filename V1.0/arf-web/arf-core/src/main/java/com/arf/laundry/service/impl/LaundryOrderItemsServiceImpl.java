package com.arf.laundry.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.laundry.dao.ILaundryOrderItemsDao;
import com.arf.laundry.entity.LaundryOrder;
import com.arf.laundry.entity.LaundryOrderItems;
import com.arf.laundry.service.ILaundryOrderItemsService;

@Service("laundryOrderItemsServiceImpl")
public class LaundryOrderItemsServiceImpl extends BaseServiceImpl<LaundryOrderItems, Long> implements ILaundryOrderItemsService {

	@Resource(name = "laundryOrderItemsDaoImpl")
	private ILaundryOrderItemsDao laundryOrderItemsDaoImpl;
	
	@Override
	protected BaseDao<LaundryOrderItems, Long> getBaseDao() {
		return laundryOrderItemsDaoImpl;
	}

}
