package com.arf.express.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.express.dao.IExpressDeliveryRecordDao;
import com.arf.express.entity.ExpressDeliveryRecord;
import com.arf.express.service.IExpressDeliveryRecordService;

@Repository("expressDeliveryRecordServiceImpl")
public class ExpressDeliveryRecordServiceImpl extends BaseServiceImpl<ExpressDeliveryRecord, Long> implements IExpressDeliveryRecordService  {

	@Resource(name = "expressDeliveryRecordDaoImpl")
	private IExpressDeliveryRecordDao expressDeliveryRecordDao;
	
	@Override
	protected BaseDao<ExpressDeliveryRecord, Long> getBaseDao() {
		return expressDeliveryRecordDao;
	}

	@Override
	public PageResult<Map<String, Object>> findExpressOrder(String userName, Integer pageSize, Integer pageNo,Integer status,Integer businessNum) {
		return expressDeliveryRecordDao.findExpressOrder(userName, pageSize, pageNo, status,businessNum);
	}

	@Override
	public List<ExpressDeliveryRecord> findByUserNameBusinessNum(String userName, Integer businessNum) {
		return expressDeliveryRecordDao.findByUserNameBusinessNum(userName, businessNum);
	}

	@Override
	public ExpressDeliveryRecord findByUserNameOrderNo(String userName, String orderNo) {
		return expressDeliveryRecordDao.findByUserNameOrderNo(userName, orderNo);
	}
}
