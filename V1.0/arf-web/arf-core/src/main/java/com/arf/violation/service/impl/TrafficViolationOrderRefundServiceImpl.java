package com.arf.violation.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.violation.dao.ITrafficViolationOrderRefundDao;
import com.arf.violation.entity.TrafficViolationOrderRefund;
import com.arf.violation.service.ITrafficViolationOrderRefundService;

@Service("trafficViolationOrderRefundServiceImpl")
public class TrafficViolationOrderRefundServiceImpl extends BaseServiceImpl<TrafficViolationOrderRefund, Long> implements ITrafficViolationOrderRefundService{

	@Resource(name = "trafficViolationOrderRefundDao")
	private ITrafficViolationOrderRefundDao trafficViolationOrderRefundDao;
	
	@Override
	protected BaseDao<TrafficViolationOrderRefund, Long> getBaseDao() {
		return trafficViolationOrderRefundDao;
	}

}
