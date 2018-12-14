package com.arf.violation.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.violation.dao.ITrafficViolationOrderDao;
import com.arf.violation.entity.TrafficViolationOrder;
import com.arf.violation.entity.TrafficViolationOrder.Status;
import com.arf.violation.service.ITrafficViolationOrderService;

@Service("trafficViolationOrderServiceImpl")
public class TrafficViolationOrderServiceImpl extends BaseServiceImpl<TrafficViolationOrder, Long> implements ITrafficViolationOrderService{

	@Resource(name = "trafficViolationOrderDao")
	private ITrafficViolationOrderDao trafficViolationOrderDao;
	@Override
	protected BaseDao<TrafficViolationOrder, Long> getBaseDao() {
		return trafficViolationOrderDao;
	}
	
	@Override
	public TrafficViolationOrder findByOrderNo(String orderNo) {
		return trafficViolationOrderDao.findByOrderNo(orderNo);
	}
	@Override
	public PageResult<Map<String,Object>> findByCondition(String outTradeNo, String userName, Integer pageSize,
			Integer pageNo, Status status,String businessNum,String startTime,String endTime) {
		return trafficViolationOrderDao.findByCondition(outTradeNo, userName, pageSize, pageNo, status, businessNum,startTime,endTime);
	}
	
	
	@Override
	public Integer findByStatus(Integer Status,String businessNum) {
		return trafficViolationOrderDao.findByStatus(Status,businessNum);
	}

	@Override
	public List<TrafficViolationOrder> findByModifyDate(Date todayStartDate, Date todayEndDate,String businessNum) {
		return trafficViolationOrderDao.findByModifyDate(todayStartDate, todayEndDate,businessNum);
	}

	@Override
	public TrafficViolationOrder findByAdditionServiceNo(String additionServiceNo) {
		return trafficViolationOrderDao.findByAdditionServiceNo(additionServiceNo);
	}

}
