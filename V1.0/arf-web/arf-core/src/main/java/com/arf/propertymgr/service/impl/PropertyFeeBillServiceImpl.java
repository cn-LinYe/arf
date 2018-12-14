package com.arf.propertymgr.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.Filter;
import com.arf.core.Filter.Operator;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.propertymgr.condition.PropertyFeeBillCondition;
import com.arf.propertymgr.dao.IPropertyFeeBillDao;
import com.arf.propertymgr.entity.PropertyFeeBill;
import com.arf.propertymgr.entity.PropertyFeeBill.PayStatus;
import com.arf.propertymgr.service.IPropertyFeeBillService;

@Service("propertyFeeBillService")
public class PropertyFeeBillServiceImpl extends BaseServiceImpl<PropertyFeeBill, Long>
		implements IPropertyFeeBillService {

	@Resource(name = "propertyFeeBillDaoImpl")
	private IPropertyFeeBillDao propertyFeeBillDao; 
	
	@Override
	protected BaseDao<PropertyFeeBill, Long> getBaseDao() {
		return propertyFeeBillDao;
	}

	@Override
	public List<PropertyFeeBill> findByBillNos(List<String> billNos) {
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter("billNo",Operator.in,billNos));
		return this.findList(null, filters, null);
	}

	@Override
	public List<PropertyFeeBill> findByCondition(PropertyFeeBillCondition condition) {
		return propertyFeeBillDao.findByCondition(condition);
	}

	@Override
	public List<PropertyFeeBill> findByUsernamePayStatus(String userName,
			PayStatus payStatus) {
		return propertyFeeBillDao.findByUsernamePayStatus(userName,payStatus);
	}

	@Override
	public List<PropertyFeeBill> findByConditionV2(
			PropertyFeeBillCondition condition) {
		return propertyFeeBillDao.findByConditionV2(condition);
	}

	@Override
	public void addBillBatch(List<PropertyFeeBill> bills) {
		propertyFeeBillDao.addBillBatch(bills);
		
	}

	@Override
	public List<PropertyFeeBill> findByPaidDate(Date startDate, Date endDate,List<String> communityList) {
		return propertyFeeBillDao.findByPaidDate(startDate, endDate,communityList);
	}
	@Override
	public List<Map<String, String>> findAgenentBills(String userName) {
		return propertyFeeBillDao.findAgenentBills(userName);
	}
	
}
