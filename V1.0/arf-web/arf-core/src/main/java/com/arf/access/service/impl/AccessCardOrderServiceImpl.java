package com.arf.access.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.access.dao.IAccessCardOrderDao;
import com.arf.access.entity.AccessCardOrder;
import com.arf.access.entity.AccessCardOrder.HadBound;
import com.arf.access.entity.AccessCardOrder.PayStatus;
import com.arf.access.service.IAccessCardOrderService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("accessCardOrderServiceImpl")
public class AccessCardOrderServiceImpl extends
		BaseServiceImpl<AccessCardOrder, Long> implements
		IAccessCardOrderService {

	@Resource(name = "accessCardOrderDaoImpl")
	private IAccessCardOrderDao accessCardOrderDaoImpl;
	
	@Override
	protected BaseDao<AccessCardOrder, Long> getBaseDao() {
		return accessCardOrderDaoImpl;
	}

	@Override
	public List<AccessCardOrder> findByUsernameRoomBoundNumberPayStatusHadBound(String userName,String roomBoundNumber,PayStatus payStatus,HadBound hadBound) {
		return accessCardOrderDaoImpl.findByUsernameRoomBoundNumberPayStatusHadBound(userName,roomBoundNumber,payStatus,hadBound);
	}

	@Override
	public AccessCardOrder findByOutTradeNo(String outTradeNo) {
		return accessCardOrderDaoImpl.findByOutTradeNo(outTradeNo);
	}

	@Override
	public List<AccessCardOrder> findByUsernameRoomNumberPayStatusHadBound(String userName, String roomNumber,
			PayStatus payStatus, HadBound hadBound) {
		return accessCardOrderDaoImpl.findByUsernameRoomNumberPayStatusHadBound(userName,roomNumber,payStatus,hadBound);
	}

}
