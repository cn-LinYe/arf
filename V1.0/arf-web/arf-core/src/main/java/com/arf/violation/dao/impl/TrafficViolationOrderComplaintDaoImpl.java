package com.arf.violation.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.violation.dao.ITrafficViolationOrderComplaintDao;
import com.arf.violation.entity.TrafficViolationOrderComplaint;

@Repository("trafficViolationOrderComplaintDao")
public class TrafficViolationOrderComplaintDaoImpl extends BaseDaoImpl<TrafficViolationOrderComplaint, Long> implements ITrafficViolationOrderComplaintDao{

}
