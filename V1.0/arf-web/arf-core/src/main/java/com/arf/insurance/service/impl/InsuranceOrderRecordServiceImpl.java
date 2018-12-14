package com.arf.insurance.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.insurance.dao.IInsuranceOrderRecordDao;
import com.arf.insurance.entity.InsuranceOrderRecord;
import com.arf.insurance.service.IInsuranceOrderRecordService;

@Repository("insuranceOrderRecordServiceImpl")
public class InsuranceOrderRecordServiceImpl extends BaseServiceImpl<InsuranceOrderRecord, Long> implements IInsuranceOrderRecordService  {

	@Resource(name = "insuranceOrderRecordDaoImpl")
	private IInsuranceOrderRecordDao insuranceOrderRecordDao;
	
	@Override
	protected BaseDao<InsuranceOrderRecord, Long> getBaseDao() {
		return insuranceOrderRecordDao;
	}

	@Override
	public PageResult<InsuranceOrderRecord> findInsurancePageByCondition(String userName,Integer businessNum,String orderStartDate,String orderEndDate,Integer orderStatus,Integer pageSize,Integer pageNo) {
		return insuranceOrderRecordDao.findInsurancePageByCondition(userName,businessNum,orderStartDate,orderEndDate,orderStatus,pageSize,pageNo);
	}
	
	@Override
	public InsuranceOrderRecord findByOrderNo(String orderNo) {
		return insuranceOrderRecordDao.findByOrderNo(orderNo);
	}

	@Override
	public InsuranceOrderRecord findInsuranceRecord(String orderNo, Integer businessNum) {
		return insuranceOrderRecordDao.findInsuranceRecord(orderNo, businessNum);
	}


	@Override
	public InsuranceOrderRecord findByOutTradeNo(String outOrderNo) {
		return insuranceOrderRecordDao.findByOutTradeNo(outOrderNo);
	}

	@Override
	public List<InsuranceOrderRecord> findUserOrders(String userName) {
		return insuranceOrderRecordDao.findUserOrders(userName);
	}

	

}
