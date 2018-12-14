package com.arf.propertymgr.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.arf.core.Filter;
import com.arf.core.Filter.Operator;
import com.arf.core.Order;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.propertymgr.dao.IPropertyFeeBillOrderDao;
import com.arf.propertymgr.entity.PropertyFeeBillOrder;
import com.arf.propertymgr.service.IPropertyFeeBillOrderService;

@Service("propertyFeeBillOrderService")
public class PropertyFeeBillOrderServiceImpl extends BaseServiceImpl<PropertyFeeBillOrder, Long>
		implements IPropertyFeeBillOrderService {

	@Resource(name = "propertyFeeBillOrderDaoImpl")
	private IPropertyFeeBillOrderDao propertyFeeBillOrderDao;
	
	@Override
	protected BaseDao<PropertyFeeBillOrder, Long> getBaseDao() {
		return propertyFeeBillOrderDao;
	}

	@Override
	public PropertyFeeBillOrder findByOutTradeNo(String outTradeNo) {
		List<PropertyFeeBillOrder> list = this.findList(null, (List<Order>)null, new Filter("outTradeNo",Operator.eq,outTradeNo));
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}
}
