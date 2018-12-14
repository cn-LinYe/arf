package com.arf.laundry.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.laundry.dao.ILaundryOrderRefundDao;
import com.arf.laundry.entity.LaundryOrderRefund;
import com.arf.laundry.service.ILaundryOrderRefundService;

@Service("laundryOrderRefundServiceImpl")
public class LaundryOrderRefundServiceImpl extends BaseServiceImpl<LaundryOrderRefund, Long> implements ILaundryOrderRefundService {

	@Resource(name = "laundryOrderRefundDaoImpl")
	private ILaundryOrderRefundDao laundryOrderRefundDaoImpl;
	
	@Override
	protected BaseDao<LaundryOrderRefund, Long> getBaseDao() {
		return laundryOrderRefundDaoImpl;
	}
	
	public LaundryOrderRefund findByOutTradeNo(String outTradeNo){
		return laundryOrderRefundDaoImpl.findByOutTradeNo(outTradeNo);
	}

}
