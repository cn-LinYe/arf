package com.arf.installment.smartlock.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.arf.core.Filter;
import com.arf.core.Filter.Operator;
import com.arf.core.Order;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.installment.smartlock.dao.ILockInstallmentFundsOrderDao;
import com.arf.installment.smartlock.entity.LockInstallmentFundsOrder;
import com.arf.installment.smartlock.service.ILockInstallmentFundsOrderService;

@Service("lockInstallmentFundsOrderService")
public class LockInstallmentFundsOrderServiceImpl extends BaseServiceImpl<LockInstallmentFundsOrder, Long> implements ILockInstallmentFundsOrderService{

	@Resource(name="IClearGoldCardBalanceLogDao")
	ILockInstallmentFundsOrderDao lockInstallmentFundsOrderDao;
	
	@Override
	protected BaseDao<LockInstallmentFundsOrder, Long> getBaseDao() {
		return lockInstallmentFundsOrderDao;
	}

	@Override
	public LockInstallmentFundsOrder findByOutTradeNo(String outTradeNo) {
		List<LockInstallmentFundsOrder> list = this.findList(null,(List<Order>)null, 
				new Filter("outTradeNo",Operator.eq,outTradeNo));
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}

}
