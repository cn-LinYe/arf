package com.arf.axd_b2b.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.arf.axd_b2b.dao.IGoodsOrderDao;
import com.arf.axd_b2b.entity.GoodsOrder;
import com.arf.axd_b2b.service.IGoodsOrderService;
import com.arf.core.Filter;
import com.arf.core.Filter.Operator;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.google.common.collect.Lists;

@Service("goodsOrderService")
public class GoodsOrderServiceImpl extends
BaseServiceImpl<GoodsOrder, Long> implements IGoodsOrderService {

	@Resource(name = "goodsOrderDao")
	private IGoodsOrderDao goodsOrderDao;
	@Override
	protected BaseDao<GoodsOrder, Long> getBaseDao() {
		return goodsOrderDao;
	}
	@Override
	public GoodsOrder findByAxdOrderNo(String axdOrderNo) {
		List<Filter> filters = Lists.newArrayList();
		filters.add(new Filter("axdOrderNo",Operator.eq,axdOrderNo));
		List<GoodsOrder> orders = this.findList(null, filters,null);
		if(CollectionUtils.isNotEmpty(orders)){
			return orders.get(0);
		}else {
			return null;
		}
	}

}
