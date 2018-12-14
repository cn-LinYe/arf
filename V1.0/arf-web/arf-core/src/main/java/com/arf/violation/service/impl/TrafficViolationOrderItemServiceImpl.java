package com.arf.violation.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.violation.dao.ITrafficViolationOrderItemDao;
import com.arf.violation.entity.TrafficViolationOrderItem;
import com.arf.violation.service.ITrafficViolationOrderItemService;
@Service("trafficViolationOrderItemServiceImpl")
public class TrafficViolationOrderItemServiceImpl extends BaseServiceImpl<TrafficViolationOrderItem, Long> implements ITrafficViolationOrderItemService{

	@Resource(name="trafficViolationOrderItemDaoImpl")
	ITrafficViolationOrderItemDao trafficViolationOrderItemDao;
	@Override
	protected BaseDao<TrafficViolationOrderItem, Long> getBaseDao() {
		return trafficViolationOrderItemDao;
	}
	@Override
	public TrafficViolationOrderItem findByUniqueCode(String uniqueCode) {
		return trafficViolationOrderItemDao.findByUniqueCode(uniqueCode);
	}
	@Override
	public List<Map<String,Object>> findByOrderNo(String orderNo) {
		return trafficViolationOrderItemDao.findByOrderNo(orderNo);
	}
	@Override
	public int findByLicense(String license) {
		return trafficViolationOrderItemDao.findByLicense(license);
	}
	@Override
	public void updateOrderStatus(String orderNo,Integer status) {
		 trafficViolationOrderItemDao.updateOrderStatus(orderNo, status);
	}
	@Override
	public List<String> findByUniqueCodes(List<String> uniqueCodes) {
		return trafficViolationOrderItemDao.findByUniqueCodes(uniqueCodes);
	}
	@Override
	public List<Date> findByTimeLicense(List<Date> time, String license) {
		return trafficViolationOrderItemDao.findByTimeLicense(time, license);
	}
}
