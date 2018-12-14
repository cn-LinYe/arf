package com.arf.violation.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.violation.dao.ITrafficViolationOrderComplaintDao;
import com.arf.violation.entity.TrafficViolationOrderComplaint;
import com.arf.violation.service.ITrafficViolationOrderComplaintService;

@Service("trafficViolationOrderComplaintService")
public class TrafficViolationOrderComplaintServiceImpl extends BaseServiceImpl<TrafficViolationOrderComplaint, Long> implements ITrafficViolationOrderComplaintService{

	@Resource(name="trafficViolationOrderComplaintDao")
	private ITrafficViolationOrderComplaintDao trafficViolationOrderComplaintDao;
	
	@Override
	protected BaseDao<TrafficViolationOrderComplaint, Long> getBaseDao() {
		return trafficViolationOrderComplaintDao;
	}

}
