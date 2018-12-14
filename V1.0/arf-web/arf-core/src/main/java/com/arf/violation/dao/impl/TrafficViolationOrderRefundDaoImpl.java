package com.arf.violation.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.violation.dao.ITrafficViolationOrderRefundDao;
import com.arf.violation.entity.TrafficViolationOrderRefund;

@Repository("trafficViolationOrderRefundDao")
public class TrafficViolationOrderRefundDaoImpl extends BaseDaoImpl<TrafficViolationOrderRefund, Long> implements ITrafficViolationOrderRefundDao{

}
