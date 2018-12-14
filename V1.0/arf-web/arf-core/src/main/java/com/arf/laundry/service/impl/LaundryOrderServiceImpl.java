package com.arf.laundry.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.laundry.dao.ILaundryOrderDao;
import com.arf.laundry.dto.MerchantOrderDto;
import com.arf.laundry.entity.LaundryOrder;
import com.arf.laundry.search.LaundryOrderCondition;
import com.arf.laundry.service.ILaundryOrderService;

@Service("laundryOrderServiceImpl")
public class LaundryOrderServiceImpl extends BaseServiceImpl<LaundryOrder, Long> implements ILaundryOrderService {

	@Resource(name = "laundryOrderDaoImpl")
	private ILaundryOrderDao laundryOrderDaoImpl;
	
	@Override
	protected BaseDao<LaundryOrder, Long> getBaseDao() {
		return laundryOrderDaoImpl;
	}

	@Override
	public LaundryOrder findByOutTradeNo(String outTradeNo){
		return laundryOrderDaoImpl.findByOutTradeNo(outTradeNo);
	}
	
	@Override
	public MerchantOrderDto findMerchantOrderByOutTradeNo(String outTradeNo){
		return laundryOrderDaoImpl.findMerchantOrderByOutTradeNo(outTradeNo);
	}
	
	@Override
	public PageResult<Map<String,Object>> findListByCondition(LaundryOrderCondition condition){
		return laundryOrderDaoImpl.findListByCondition(condition);
	}
	
	@Override
	public void autoReceivedOrder(Integer delayDays){
		laundryOrderDaoImpl.autoReceivedOrder(delayDays);
	}
}
